import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yeonwoo_kim on 11/6/16.
 */
public class Table implements Serializable {
    private ArrayList<Attribute> attrList;
    private ArrayList<Table> referredList;
    private ArrayList<Attribute> primaryKey;
    private String tableName;

    public Table(String tableName) {
        this.tableName = tableName.toLowerCase();
        attrList = new ArrayList<>();
        referredList = new ArrayList<>();
        primaryKey = new ArrayList<>();
    }

    public ArrayList<Attribute> getAttrList() {
        return attrList;
    }

    public ArrayList<Table> getReferredList() {
        return referredList;
    }

    public ArrayList<Attribute> getPrimaryKey() {
        return primaryKey;
    }

    public Attribute findAttribute(String attrName) {
        Iterator<Attribute> it = attrList.iterator();
        while (it.hasNext()) {
            Attribute n = it.next();
            if (attrName.equalsIgnoreCase(n.getAttributeName()))
                return n;
        }
        return null;
    }

    public Message setPrimaryKey(ArrayList<String> p) {
        if (!primaryKey.isEmpty()) {
            return new Message(MessageName.DUPLICATE_PRIMARY_KEY_DEF_ERROR);
        }

        Iterator<String> it = p.iterator();
        ArrayList<Attribute> nAttrList = new ArrayList<>();
        while (it.hasNext()) {
            String n = it.next();
            Attribute nAttr = findAttribute(n);
            if (nAttr == null) {
               return new Message(MessageName.NON_EXISTING_COLUMN_DEF_ERROR, n);
            }
            nAttrList.add(nAttr);
        }

        Iterator<Attribute> it2 = nAttrList.iterator();
        while (it2.hasNext()) {
            Attribute nAttr = it2.next();
            nAttr.setPrimaryKey();
            nAttr.setNotNull();
            primaryKey.add(nAttr);
        }
        return null;
    }

    public String getTableName() {
        return tableName;
    }

    private boolean checkDuplicate(String attrName) {
        Iterator<Attribute> it = attrList.iterator();
        while (it.hasNext()) {
            Attribute n = it.next();
            if (attrName.equalsIgnoreCase(n.getAttributeName()))
                return true;
        }
        return false;
    }

    public Message addAttribute(Attribute attr) {
        if (checkDuplicate(attr.getAttributeName())) {
            return new Message(MessageName.DUPLICATE_COLUMN_DEF_ERROR);
        }
        attrList.add(attr);
        return null;
    }

    public void addReferred(Table t) {
        referredList.add(t);
    }

    public Message setForeignKey(ArrayList<String> foreignKey,
                                 Table table, ArrayList<String> reference) {
        if (foreignKey.size() != reference.size()) {
            return new Message(MessageName.REFERENCE_TYPE_ERROR);
        }

        ArrayList<Attribute> prime = table.getPrimaryKey();
        ArrayList<Attribute> aAttrList = new ArrayList<>();
        if (prime.size() != reference.size()) {
            return new Message(MessageName.REFERENCE_NON_PRIMARY_KEY_ERROR);
        }
        Iterator<String> itForeign = foreignKey.iterator();
        Iterator<String> itReference = reference.iterator();
        while (itForeign.hasNext()) {
            String a = itForeign.next();
            Attribute aAttr = findAttribute(a);
            if (aAttr == null) {
                return new Message(MessageName.NON_EXISTING_COLUMN_DEF_ERROR, a);
            }
            aAttrList.add(aAttr);
            String b = itReference.next();
            Attribute bAttr = table.findAttribute(b);
            if (bAttr == null) {
                return new Message(MessageName.REFERENCE_COLUMN_EXISTENCE_ERROR);
            }

            if (!prime.contains(bAttr)) {
                return new Message(MessageName.REFERENCE_NON_PRIMARY_KEY_ERROR);
            }

            if (aAttr.getAttributeType().compareTo(bAttr.getAttributeType()) != 0) {
                return new Message(MessageName.REFERENCE_TYPE_ERROR);
            }
        }

        table.addReferred(this);
        Iterator<Attribute> it = aAttrList.iterator();
        while (it.hasNext()) {
            it.next().setForeignKey();
        }
        return null;
    }

}
