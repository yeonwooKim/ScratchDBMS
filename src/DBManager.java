import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yeonwoo_kim on 11/6/16.
 */
/* DBManager class handles all the DDL queries
   Manages a list of Table classes
 */
public class DBManager implements Serializable {
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

    public static void setDBManager(DBManager m) {
        manager = m;
    }

    public Table findTable(String tablename) {
        Iterator<Table> it = tables.iterator();
        while (it.hasNext()) {
            Table n = it.next();
            if (n.getTableName().equalsIgnoreCase(tablename))
                return n;
        }
        return null;
    }

    public void addTable(Table t) {
        tables.add(t);
    }

    public Message dropTable(String tablename) {
        Message m;
        Table t = findTable(tablename);
        if (t == null) {
            return Message.getNoSuchTable();
        }

        if (t.getReferredList().size() != 0) {
            m = Message.getDropReferencedTable();
            m.setNameArg(tablename);
        }

        tables.remove(t);
        m = Message.getDropSuccess();
        m.setNameArg(tablename);
        return m;
    }

    private static void desc(Table t) {
        System.out.println("-------------------------------------------------");
        System.out.println("table_name [" + t.getTableName() + "]");
        System.out.println("column_name\t\t\ttype\t\tnull\t\tkey");
        ArrayList<Attribute> attrList = t.getAttrList();
        Iterator<Attribute> it = attrList.iterator();
        while (it.hasNext()) {
            Attribute n = it.next();
            char isNull = (n.isNotNull()) ? 'N' : 'Y';
            String key = (n.isPrimaryKey()) ?
                    ((n.isForeignKey()) ? "PRI/FOR" : "PRI") :
                    ((n.isForeignKey()) ? "FOR" : "");
            System.out.println(n.getAttributeName() + "\t\t\t\t" +
                    n.getAttributeType().toString() + "\t\t" +
                    isNull + "\t\t" + key);
        }
        System.out.println("-------------------------------------------------");
    }
    public Message descTable(String tablename) {
        Table t = findTable(tablename);
        if (t == null) {
            return Message.getNoSuchTable();
        }

        desc(t);
        return null;
    }

    public Message showTables() {
        Iterator<Table> it = tables.iterator();
        if (!it.hasNext()) {
            return Message.getShowTablesNoTable();
        }
        System.out.println("----------------");
        while (it.hasNext()) {
            System.out.println(it.next().getTableName());
        }
        System.out.println("----------------");
        return null;
    }
}
