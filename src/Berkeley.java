/**
 * Created by yeonwoo_kim on 11/7/16.
 */

import com.sleepycat.je.*;

import java.io.*;

public class Berkeley {
    private static Berkeley berk = null;
    private Environment dbEnv = null;
    private Database db = null;

    public static Berkeley getBerkeley() {
        if (berk == null) {
            berk = new Berkeley();
        }
        return berk;
    }

    public void open() {
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        dbEnv = new Environment(new File("db/"), envConfig);

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setSortedDuplicates(true);
        db = dbEnv.openDatabase(null, "scratchDB", dbConfig);

        DBManager m = retrieveManager();
        if (m != null)
            DBManager.setDBManager(m);
    }

    public void close() {
        if (db != null)
            db.close();
        if (dbEnv != null)
            dbEnv.close();
    }

    private static byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }
    public void updateManager() {
        Cursor cursor = null;
        DatabaseEntry key;
        DatabaseEntry data;

        try {
            cursor = db.openCursor(null, null);
            key = new DatabaseEntry("DBM".getBytes("UTF-8"));
            data = new DatabaseEntry(serialize(DBManager.getDBManager()));
            cursor.put(key, data);
            cursor.close();
        } catch (Exception e) {
            cursor.close();
        }
    }

    public DBManager retrieveManager() {
        Cursor cursor = null;
        DatabaseEntry key;
        DatabaseEntry data = new DatabaseEntry();

        try {
            cursor = db.openCursor(null, null);
            key = new DatabaseEntry("DBM".getBytes("UTF-8"));
            cursor.getSearchKey(key, data, LockMode.DEFAULT);
            DBManager m = (DBManager) deserialize(data.getData());
            cursor.close();
            return m;
        } catch (Exception e) {
            cursor.close();
            return null;
        }
    }
}
