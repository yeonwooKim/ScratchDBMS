import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yeonwoo_kim on 11/6/16.
 */
public class DBManager {
    private static DBManager manager = null;
    private ArrayList<Table> tables;

    private DBManager() {
        tables = new ArrayList<>();
    }

    public static DBManager getDBManager() {
        if (manager == null)
            manager = new DBManager();
        return manager;
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

    public void addTable(Table t) {
        tables.add(t);
    }

    public Message dropTable(String tablename) {
        Table t = findTable(tablename);
        if (t == null) {
            return new Message(MessageName.NO_SUCH_TABLE);
        }

        if (t.getReferredList().size() != 0) {
            return new Message(MessageName.DROP_REFERENCED_TABLE_ERROR);
        }

        tables.remove(t);
        return new Message(MessageName.DROP_SUCCESS, tablename);
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
    public Message descTable(String tablename) {
        Table t = findTable(tablename);
        if (t == null) {
            return new Message(MessageName.NO_SUCH_TABLE);
        }

        desc(t);
        return null;
    }

    public Message showTables() {
        Iterator<Table> it = tables.iterator();
        if (!it.hasNext()) {
            return new Message(MessageName.SHOW_TABLES_NO_TABLE);
        }
        System.out.println("----------------");
        while (it.hasNext()) {
            System.out.println(it.next().getTableName());
        }
        System.out.println("----------------");
        return null;
    }
}
