import javafx.util.Pair;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yeonwoo_kim on 11/19/16.
 */
public class Record implements Serializable {
    private ArrayList<Value> values;

    public Record() {
       values = new ArrayList<>();
    }

    public Value getIndex(int i) {
        return values.get(i);
    }

    private static void addForeignKeyList(ArrayList<ArrayList<Value>> foreignKey, int index, Value v,
                                          int attrIndex, Table table) {
        if (index != -1) {
            if (v.isNull())
                foreignKey.set(index, null);
            if (foreignKey.get(index) != null) {
                int idx = table.getReferringList().get(index).getValue().indexOf(attrIndex);
                foreignKey.get(index).set(idx, v);
            }
        }
    }
    private static Message checkReferentialConstraint(Table table, ArrayList<ArrayList<Value>> foreignKey) {
        Iterator<Pair<Table, ArrayList<Integer>>> itForeign = table.getReferringList().iterator();
        Pair<Table, ArrayList<Integer>> p;
        int i = 0;
        while (itForeign.hasNext()) {
            p = itForeign.next();
            if (foreignKey.get(i) != null &&
                    !Berkeley.getBerkeley().tableHasRecord(p.getKey().getTableName(), p.getKey().getPrimaryKey(), foreignKey.get(i))) {
                return Message.getInsertReferentialIntegrity();
            }
            i ++;
        }
        return null;
    }
    public Message addValues(boolean explicit, ArrayList<Value> values, Table table) {
        ArrayList<Attribute> attrList = table.getAttrList();
        Iterator<Attribute> it = attrList.iterator();
        ArrayList<Value> primaryKey = new ArrayList<>();
        ArrayList<ArrayList<Value>> foreignKey = new ArrayList<>();
        for (int i = 0 ; i < table.getReferringList().size() ; i ++) {
            int len = table.getReferringList().get(i).getValue().size();
            ArrayList<Value> arr = new ArrayList<>(len);
            for (int j = 0 ; j < len ; j ++)
                arr.add(null);
            foreignKey.add(arr);
        }
        if (!explicit) {
            Iterator<Value> it2 = values.iterator();
            Attribute attr;
            Value v;
            int i = 0;
            while (it.hasNext()) {
                attr = it.next();
                if (!it2.hasNext()) {
                    return Message.getInsertTypeMismatch();
                }
                v = it2.next();
                if (!v.isCompatible(attr.getAttributeType())) {
                    return Message.getInsertTypeMismatch();
                }
                if (attr.isNotNull() && v.isNull()) {
                    Message m = Message.getInsertColumnNonNullable();
                    m.setNameArg(attr.getAttributeName());
                    return m;
                }
                if (attr.isPrimaryKey()) {
                    primaryKey.add(v);
                }
                this.values.add(v);
                int index = attr.getForeignKey();
                addForeignKeyList(foreignKey, index, v, i, table);
                i ++;
            }
            if (it2.hasNext()) {
                return Message.getInsertTypeMismatch();
            }
        }
        else {
            Attribute attr;
            Value v;
            int i = 0;
            while (it.hasNext()) {
                attr = it.next();
                if (attr.getIndex() == -1 && attr.isNotNull()) {
                    Message m = Message.getInsertColumnNonNullable();
                    m.setNameArg(attr.getAttributeName());
                    return m;
                }
                else if (attr.getIndex() == -1) {
                    int index = attr.getForeignKey();
                    if (index != -1) {
                        foreignKey.set(index, null);
                    }
                    this.values.add(new Value());
                }
                else {
                    v = values.get(attr.getIndex());
                    if (!v.isCompatible(attr.getAttributeType())) {
                        return Message.getInsertTypeMismatch();
                    }
                    if (attr.isNotNull() && v.isNull()) {
                        Message m = Message.getInsertColumnNonNullable();
                        m.setNameArg(attr.getAttributeName());
                        return m;
                    }
                    if (attr.isPrimaryKey()) {
                        primaryKey.add(v);
                    }
                    int index = attr.getForeignKey();
                    addForeignKeyList(foreignKey, index, v, i, table);
                    this.values.add(v);
                }
                i ++;
            }
        }
        if (Berkeley.getBerkeley().tableHasRecord(table.getTableName(), table.getPrimaryKey(), primaryKey)) {
            return Message.getInsertDuplicatePrimaryKey();
        }
        return checkReferentialConstraint(table, foreignKey);
    }

    public static Message verifyColumnList(ArrayList<String> list, Table table) {
        Iterator<String> it = list.iterator();
        String s;
        int i = 0;

        ArrayList<Attribute> attrList = table.getAttrList();
        Iterator<Attribute> it2 = attrList.iterator();
        while (it2.hasNext()) {
            it2.next().setIndex(-1);
        }

        while (it.hasNext()) {
            s = it.next();
            Attribute attr = table.findAttribute(s);
            if (attr == null) {
                Message m = Message.getInsertColumnExistence();
                m.setNameArg(s);
                return m;
            }
            attr.setIndex(i);
            i ++;
        }

        return null;
    }
}
