import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yeonwoo_kim on 11/6/16.
 */
public class DBManager {
    ArrayList<Table> tables;

    public DBManager() {
        tables = new ArrayList<>();
    }

    public Table findTable(String tablename) {
        Iterator<Table> it = tables.iterator();
        while (it.hasNext()) {
            Table n = it.next();
            if (n.getTableName().compareToIgnoreCase(tablename) == 0)
                return n;
        }
        return null;
    }

    public boolean addTable(Table t) {
        if (findTable(t.getTableName()) != null) {
            MessagePrinter.printMessage(
                    new Message(MessageName.TABLE_EXISTENCE_ERROR));
            return false;
        }
        tables.add(t);
        return true;
    }

    public boolean dropTable(String tablename) {
        Table t = findTable(tablename);
        if (t == null) {
            MessagePrinter.printMessage(
                    new Message(MessageName.NO_SUCH_TABLE));
            return false;
        }

        if (t.getReferredList().size() != 0) {
            MessagePrinter.printMessage(
                    new Message(MessageName.DROP_REFERENCED_TABLE_ERROR));
            return false;
        }

        tables.remove(t);
        return true;
    }

    private static void desc(Table t) {
        System.out.println("-------------------------------------------------");
        System.out.println("table_name [" + t.getTableName() + "]");
        System.out.println("column_name\ttype\tnull\tkey");
        ArrayList<Attribute> attrList = t.getAttrList();
        Iterator<Attribute> it = attrList.iterator();
        while (it.hasNext()) {
            Attribute n = it.next();
            char isNull = (n.isNotNull()) ? 'N' : 'Y';
            String key = (n.isPrimaryKey()) ?
                    ((n.isForeignKey()) ? "PRI/FOR" : "PRI") :
                    ((n.isForeignKey()) ? "FOR" : "");
            System.out.println(n.getAttributeName() + "\t" +
                    n.getAttributeType().toString() + "\t" +
                    isNull + "\t" + key);
        }
        System.out.println("-------------------------------------------------");
    }
    public boolean descTable(String tablename) {
        Table t = findTable(tablename);
        if (t == null) {
            MessagePrinter.printMessage(
                    new Message(MessageName.NO_SUCH_TABLE));
            return false;
        }

        desc(t);
        return true;
    }

    public boolean showTables() {
        Iterator<Table> it = tables.iterator();
        if (!it.hasNext()) {
            MessagePrinter.printMessage(
                    new Message(MessageName.SHOW_TABLES_NO_TABLE));
            return false;
        }
        System.out.println("----------------");
        while (it.hasNext()) {
            System.out.println(it.next().getTableName());
        }
        System.out.println("----------------");
        return true;
    }
}
