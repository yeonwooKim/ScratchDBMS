import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yeonwoo_kim on 11/6/16.
 */
public class Table {
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
            if (n.getAttributeName().equalsIgnoreCase(attrName))
                return n;
        }
        return null;
    }

    public boolean setPrimaryKey(ArrayList<String> p) {
        if (!primaryKey.isEmpty()) {
            MessagePrinter.printMessage(
                    new Message(MessageName.DUPLICATE_PRIMARY_KEY_DEF_ERROR));
            return false;
        }

        Iterator<String> it = p.iterator();
        ArrayList<Attribute> nAttrList = new ArrayList<>();
        while (it.hasNext()) {
            String n = it.next();
            Attribute nAttr = findAttribute(n);
            if (nAttr == null) {
                MessagePrinter.printMessage(
                        new Message(MessageName.NON_EXISTING_COLUMN_DEF_ERROR, n));
                return false;
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
        return true;
    }

    public String getTableName() {
        return tableName;
    }

    private boolean checkDuplicate(String attrName) {
        Iterator<Attribute> it = attrList.iterator();
        while (it.hasNext()) {
            Attribute n = it.next();
            if (n.getAttributeName().equalsIgnoreCase(attrName))
                return true;
        }
        return false;
    }

    public boolean addAttribute(Attribute attr) {
        if (checkDuplicate(attr.getAttributeName())) {
            MessagePrinter.printMessage(
                    new Message(MessageName.DUPLICATE_COLUMN_DEF_ERROR));
            return false;
        }
        attrList.add(attr);
        return true;
    }

    public void addReferred(Table t) {
        referredList.add(t);
    }

    public boolean setForiegnKey(ArrayList<String> foreignKey,
                                 Table table, ArrayList<String> reference) {
        if (foreignKey.size() != reference.size()) {
            MessagePrinter.printMessage(
                    new Message(MessageName.REFERENCE_TYPE_ERROR));
            return false;
        }

        ArrayList<Attribute> prime = table.getPrimaryKey();
        ArrayList<Attribute> aAttrList = new ArrayList<>();
        if (prime.size() != reference.size()) {
            MessagePrinter.printMessage(
                    new Message(MessageName.REFERENCE_NON_PRIMARY_KEY_ERROR));
            return false;
        }
        Iterator<String> itForeign = foreignKey.iterator();
        Iterator<String> itReference = reference.iterator();
        while (itForeign.hasNext()) {
            String a = itForeign.next();
            Attribute aAttr = findAttribute(a);
            if (aAttr == null) {
                MessagePrinter.printMessage(
                        new Message(MessageName.NON_EXISTING_COLUMN_DEF_ERROR, a));
                return false;
            }
            aAttrList.add(aAttr);
            String b = itReference.next();
            Attribute bAttr = table.findAttribute(b);
            if (bAttr == null) {
                MessagePrinter.printMessage(
                        new Message(MessageName.REFERENCE_COLUMN_EXISTENCE_ERROR));
                return false;
            }

            if (!prime.contains(bAttr)) {
                MessagePrinter.printMessage(
                        new Message(MessageName.REFERENCE_NON_PRIMARY_KEY_ERROR));
                return false;
            }

            if (aAttr.getAttributeType().compareTo(bAttr.getAttributeType()) != 0) {
                MessagePrinter.printMessage(
                        new Message(MessageName.REFERENCE_TYPE_ERROR));
                return false;
            }
        }

        table.addReferred(this);
        Iterator<Attribute> it = aAttrList.iterator();
        while (it.hasNext()) {
            it.next().setForeignKey();
        }
        return true;
    }

}
