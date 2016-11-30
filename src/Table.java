import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by yeonwoo_kim on 11/6/16.
 */
public class Table implements Serializable {
    private ArrayList<Attribute> attrList;
    private ArrayList<Table> referredList;
    private ArrayList<Pair<Table, ArrayList<Integer>>> referringList;
    private ArrayList<Integer> primaryKey;
    private String tableName;
    private String tableAlias;

    public Table(String tableName) {
        this.tableName = tableName.toLowerCase();
        attrList = new ArrayList<>();
        referredList = new ArrayList<>();
        referringList = new ArrayList<>();
        primaryKey = new ArrayList<>();
        tableAlias = null;
    }

    public Table(ArrayList<Attribute> attrList, String tableName, String tableAlias) { //JOIN table constructor
        this.attrList = attrList;
        this.tableName = tableName;
        referredList = new ArrayList<>();
        referringList = new ArrayList<>();
        primaryKey = new ArrayList<>();
        this.tableAlias = tableAlias;
    }

    public String getTableAlias() { return tableAlias; }
    public ArrayList<Attribute> getAttrList() { return attrList; }
    public ArrayList<Table> getReferredList() { return referredList; }
    public ArrayList<Pair<Table, ArrayList<Integer>>> getReferringList() { return referringList; }
    public ArrayList<Integer> getPrimaryKey() { return primaryKey; }

    public boolean hasAttribute(String tableName, String attrName) { //JOIN table method
        Iterator<Attribute> it = attrList.iterator();
        while (it.hasNext()) {
            Attribute n = it.next();
            if (attrName.equals(n.getAttributeName()) &&
                    (tableName == null || tableName.equals(n.getTableName())))
                return true;
        }
        return false;
    }

    public Attribute findAttribute(String tableName, String attrName) { //JOIN table method
        Attribute attr = null;
        Iterator<Attribute> it = attrList.iterator();
        while (it.hasNext()) {
            Attribute n = it.next();
            if (attrName.equals(n.getAttributeName()) &&
                    (tableName == null || tableName.equals(n.getTableName()))) {
                if (attr == null || attr == n)
                    attr = n;
                else
                    return null;
            }
        }
        return attr;
    }

    public Attribute findAttribute(String attrName) {
        Iterator<Attribute> it = attrList.iterator();
        while (it.hasNext()) {
            Attribute n = it.next();
            if (attrName.equals(n.getAttributeName()))
                return n;
        }
        return null;
    }

    public Message setPrimaryKey(ArrayList<String> p) {
        // Set primary key, check all possible errors
        if (!primaryKey.isEmpty()) {
            return new Message(MessageName.DUPLICATE_PRIMARY_KEY_DEF_ERROR);
        }

        Iterator<String> it = p.iterator();
        ArrayList<Attribute> nAttrList = new ArrayList<>();
        while (it.hasNext()) {
            String n = it.next();
            Attribute nAttr = findAttribute(n);
            if (nAttr == null) {
                Message m = new Message(MessageName.NON_EXISTING_COLUMN_DEF_ERROR);
                m.setNameArg(n);
               return m;
            }
            nAttrList.add(nAttr);
        }

        Iterator<Attribute> it2 = nAttrList.iterator();
        while (it2.hasNext()) {
            Attribute nAttr = it2.next();
            nAttr.setPrimaryKey();
            nAttr.setNotNull();
            primaryKey.add(attrList.indexOf(nAttr));
        }
        Collections.sort(primaryKey);
        return null;
    }

    public String getTableName() { return tableName; }

    private boolean checkDuplicate(String attrName) {
        Iterator<Attribute> it = attrList.iterator();
        while (it.hasNext()) {
            Attribute n = it.next();
            if (attrName.equals(n.getAttributeName()))
                return true;
        }
        return false;
    }

    public Message addAttribute(Attribute attr) {
        if (checkDuplicate(attr.getAttributeName())) {
            return new Message(MessageName.DUPLICATE_COLUMN_DEF_ERROR);
        }
        attr.setTableName(tableName);
        attrList.add(attr);
        return null;
    }

    public void addReferred(Table t) { referredList.add(t); }

    public void removeReferred(Table t) { referredList.remove(t); }

    public void addReferring(Table t, ArrayList<Integer> arr) {
        Pair<Table, ArrayList<Integer>> p = new Pair<>(t, arr);
        referringList.add(p);
    }

    public ArrayList<Integer> getForeignKey(Table t) {
        Iterator<Pair<Table, ArrayList<Integer>>> it = referringList.iterator();
        Pair<Table, ArrayList<Integer>> p;
        while (it.hasNext()) {
            p = it.next();
            if (p.getKey() == t)
                return p.getValue();
        }
        return null;
    }

    public Message setForeignKey(ArrayList<String> foreignKey,
                                 Table table, ArrayList<String> reference) {
        // Set foreign key, check all possible errors
        if (foreignKey.size() != reference.size()) {
            return new Message(MessageName.REFERENCE_TYPE_ERROR);
        }

        ArrayList<Integer> prime = table.getPrimaryKey();
        int size = prime.size();
        ArrayList<Integer> indexList = new ArrayList<>(size);
        for (int i = 0 ; i < size ; i ++)
            indexList.add(-1);
        if (prime.size() != reference.size()) {
            return new Message(MessageName.REFERENCE_NON_PRIMARY_KEY_ERROR);
        }
        Iterator<String> itForeign = foreignKey.iterator();
        Iterator<String> itReference = reference.iterator();
        while (itForeign.hasNext()) {
            String a = itForeign.next();
            Attribute aAttr = findAttribute(a);
            if (aAttr == null) {
                Message m = new Message(MessageName.NON_EXISTING_COLUMN_DEF_ERROR);
                m.setNameArg(a);
                return m;
            }
            String b = itReference.next();
            Attribute bAttr = table.findAttribute(b);
            if (bAttr == null) {
                return new Message(MessageName.REFERENCE_COLUMN_EXISTENCE_ERROR);
            }

            if (!prime.contains(table.getAttrList().indexOf(bAttr))) {
                return new Message(MessageName.REFERENCE_NON_PRIMARY_KEY_ERROR);
            }

            if (aAttr.getAttributeType().compareTo(bAttr.getAttributeType()) != 0) {
                return new Message(MessageName.REFERENCE_TYPE_ERROR);
            }

            aAttr.setForeignKey(referringList.size());
            int pindex = table.getAttrList().indexOf(bAttr);
            int index = prime.indexOf(pindex);
            indexList.set(index, attrList.indexOf(aAttr));
        }
        table.addReferred(this);
        addReferring(table, indexList);
        return null;
    }

    private String getName(int index) {
        Attribute attr = attrList.get(index);
        String name = attr.getAlias();
        if (name != null)
            return name;
        return attr.getAttributeName();
    }
    public ArrayList<String> getNames(ArrayList<Integer> projection) {
        ArrayList<String> names = new ArrayList<>();
        if (projection == null) {
            int size = attrList.size();
            for (int i = 0 ; i < size ; i ++) {
                names.add(getName(i));
            }
        }
        else {
            Iterator<Integer> it = projection.iterator();
            while (it.hasNext()) {
                names.add(getName(it.next()));
            }
        }
        return names;
    }
}
