/**
 * Created by yeonwoo_kim on 11/7/16.
 */

import com.sleepycat.je.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/* Berkeley class is in charge of opening, closing the berkely db,
   updating and retrieving database manager inside for each successful creation and drop
 */
public class Berkeley {
    private static Berkeley berk = null; // singleton class
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

        DBManager m = retrieveManager(); // Retrieve manager from file if there is
        if (m != null)
            DBManager.setDBManager(m);
    }

    public void close() { // Close database
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
        // Update manager data in file whenever manager changes (drop / create table)
        Cursor cursor = null;
        DatabaseEntry key;
        DatabaseEntry data = new DatabaseEntry();

        try {
            cursor = db.openCursor(null, null);
            key = new DatabaseEntry("DBM1".getBytes("UTF-8"));
            OperationStatus os = cursor.getSearchKey(key, data, LockMode.DEFAULT);
            if (os != OperationStatus.NOTFOUND)
                cursor.delete(null);
            data = new DatabaseEntry(serialize(DBManager.getDBManager()));
            cursor.put(key, data);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
        }
    }

    public DBManager retrieveManager() {
        Cursor cursor = null;
        DatabaseEntry key;
        DatabaseEntry data = new DatabaseEntry();

        try {
            cursor = db.openCursor(null, null);
            key = new DatabaseEntry("DBM1".getBytes("UTF-8"));
            cursor.getSearchKey(key, data, LockMode.DEFAULT);
            DBManager m = (DBManager) deserialize(data.getData());
            cursor.close();
            return m;
        } catch (Exception e) {
            cursor.close();
            return null;
        }
    }

    public void insertRecord(String tablename, Record rec) {
        Cursor cursor = null;
        DatabaseEntry key;
        DatabaseEntry data;

        try {
            cursor = db.openCursor(null, null);
            key = new DatabaseEntry(tablename.getBytes("UTF-8"));
            data = new DatabaseEntry(serialize(rec));
            cursor.put(key, data);
            cursor.close();
        } catch (Exception e) {
            cursor.close();
        }
    }

    private static boolean recordHasValue(DatabaseEntry data, ArrayList<Integer> index, ArrayList<Value> values) {
        try {
            Record rec = (Record) deserialize(data.getData());
            ArrayList<Value> values1 = rec.getIndices(index);
            if (values1.equals(values))
                return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean tableHasRecord(String tablename, ArrayList<Integer> index, ArrayList<Value> values) {
        Cursor cursor = null;
        DatabaseEntry key;
        DatabaseEntry data = new DatabaseEntry();

        try {
            cursor = db.openCursor(null, null);
            key = new DatabaseEntry(tablename.getBytes("UTF-8"));
            OperationStatus os = cursor.getSearchKey(key, data, LockMode.DEFAULT);
            if (os == OperationStatus.NOTFOUND) {
                cursor.close();
                return false;
            }
            if (recordHasValue(data, index, values)) {
                cursor.close();
                return true;
            }

            while (cursor.get(key, data, Get.NEXT_DUP, null) != null) {
                if (recordHasValue(data, index, values)) {
                    cursor.close();
                    return true;
                }
            }
            cursor.close();
            return false;
        } catch (Exception e) {
            cursor.close();
            return false;
        }
    }

    public void removeTable(String tablename) {
        DatabaseEntry key;

        try {
            key = new DatabaseEntry(tablename.getBytes("UTF-8"));
            db.delete(null, key);
        } catch (Exception e) {
        }
    }

    private void recordToNull(Record rec, ArrayList<Integer> index) {
        Iterator<Integer> it = index.iterator();
        while (it.hasNext()) {
            rec.setIndex(it.next(), new Value());
        }
    }

    private void cascadeToNull(String tablename, ArrayList<Integer> index, ArrayList<Value> values) {
        Cursor cursor = null;
        DatabaseEntry key;
        DatabaseEntry data = new DatabaseEntry();

        try {
            cursor = db.openCursor(null, null);
            key = new DatabaseEntry(tablename.getBytes("UTF-8"));
            OperationStatus os = cursor.getSearchKey(key, data, LockMode.DEFAULT);
            if (os == OperationStatus.NOTFOUND) {
                cursor.close();
                return;
            }
            Record rec = (Record) deserialize(data.getData());
            if (rec.getIndices(index).equals(values)) {
                recordToNull(rec, index);
                cursor.delete();
                data = new DatabaseEntry(serialize(rec));
                db.put(null, key, data);
            }

            while (cursor.get(key, data, Get.NEXT_DUP, null) != null) {
                rec = (Record) deserialize(data.getData());
                if (rec.getIndices(index).equals(values)) {
                    recordToNull(rec, index);
                    cursor.delete();
                    data = new DatabaseEntry(serialize(rec));
                    db.put(null, key, data);
                }
            }
            cursor.close();
        } catch (Exception e) {
            cursor.close();
        }
    }

    private boolean isNonCascadeDeletable(Table t, Record rec) {
        ArrayList<Table> r = t.getReferredList();
        Iterator<Table> it = r.iterator();
        Table refTable;
        ArrayList<Integer> index;
        ArrayList<Integer> primaryKey = t.getPrimaryKey();
        ArrayList<Value> values = rec.getIndices(primaryKey);
        while (it.hasNext()) {
            refTable = it.next();
            index = refTable.getForeignKey(t);
            if (tableHasRecord(refTable.getTableName(), index, values)) {
                return false;
            }
        }
        return true;
    }

    private void deleteCascade(Table t, Record rec) {
        ArrayList<Table> r = t.getReferredList();
        Iterator<Table> it = r.iterator();
        Table refTable;
        ArrayList<Integer> index;
        ArrayList<Integer> primaryKey = t.getPrimaryKey();
        ArrayList<Value> values = rec.getIndices(primaryKey);
        while (it.hasNext()) {
            refTable = it.next();
            index = refTable.getForeignKey(t);
            cascadeToNull(refTable.getTableName(), index, values);
        }
    }

    private boolean isCascadable(Table t) {
        ArrayList<Table> r = t.getReferredList();
        Iterator<Table> it = r.iterator();
        Table refTable;
        ArrayList<Integer> index;
        ArrayList<Attribute> attr;
        while (it.hasNext()) {
            refTable = it.next();
            index = refTable.getForeignKey(t);
            attr = refTable.getAttrList();
            Iterator<Integer> it2 = index.iterator();
            while (it2.hasNext()) {
                if (attr.get(it2.next()).isNotNull())
                    return false;
            }
        }
        return true;
    }

    private void incrDeleteSuccess(Message m) {
        String[] nums = m.getNameArg().split(" ");
        m.setNameArg(Integer.toString(Integer.parseInt(nums[0]) + 1) + " " + nums[1]);
    }

    private void incrDeleteFailure(Message m) {
        String[] nums = m.getNameArg().split(" ");
        m.setNameArg(nums[0] + " " + Integer.toString(Integer.parseInt(nums[1]) + 1));
    }

    public Message removeRecord(String tablename, BooleanValueExpression bve) {
        Message m = new Message(MessageName.DELETE_SUCCESS);
        m.setNameArg("0 0");
        Table t = DBManager.getDBManager().findTable(tablename);
        if (t == null) {
            return new Message(MessageName.NO_SUCH_TABLE);
        }
        boolean cascadable = isCascadable(t);
        Cursor cursor = null;
        DatabaseEntry key;
        DatabaseEntry data = new DatabaseEntry();
        Record rec;
        try {
            cursor = db.openCursor(null, null);
            key = new DatabaseEntry(tablename.getBytes("UTF-8"));
            OperationStatus os = cursor.getSearchKey(key, data, LockMode.DEFAULT);
            if (os == OperationStatus.NOTFOUND) {
                cursor.close();
                return m;
            }
            rec = (Record) deserialize(data.getData());
            if (bve == null || bve.eval(t, rec)) {
                if (cascadable) {
                    deleteCascade(t, rec);
                    incrDeleteSuccess(m);
                    cursor.delete();
                }
                else {
                    if (isNonCascadeDeletable(t, rec)){
                        incrDeleteSuccess(m);
                        cursor.delete();
                    }
                    else
                        incrDeleteFailure(m);
                }
            }
            while (cursor.get(key, data, Get.NEXT_DUP, null) != null) {
                rec = (Record) deserialize(data.getData());
                if (bve == null || bve.eval(t, rec)) {
                    if (cascadable) {
                        deleteCascade(t, rec);
                        incrDeleteSuccess(m);
                        cursor.delete();
                    }
                    else {
                        if (isNonCascadeDeletable(t, rec)) {
                            incrDeleteSuccess(m);
                            cursor.delete();
                        }
                        else
                            incrDeleteFailure(m);
                    }
                }
            }
            cursor.close();
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            cursor.close();
            return null;
        }
    }

    private boolean incrCursor(String[] tables, ArrayList<Cursor> cursors, ArrayList<DatabaseEntry> datas, int size) {
        DatabaseEntry key = new DatabaseEntry(), data = new DatabaseEntry();
        if (cursors.get(size - 1).get(key, data, Get.NEXT_DUP, null) != null) {
            datas.set(size - 1, data);
            return true;
        }
        else {
            if (size  == 1) {
                return false;
            }
            else {
                try {
                    key = new DatabaseEntry(tables[size - 1].getBytes("UTF-8"));
                    cursors.get(size - 1).getSearchKey(key, data, LockMode.DEFAULT);
                    datas.set(size - 1, data);
                    return incrCursor(tables, cursors, datas, size - 1);
                } catch (Exception e) {
                    return false;
                }
            }
        }
    }

    public void select(String tablename, BooleanValueExpression bve, ArrayList<Integer> projection) {
        Table t = DBManager.getDBManager().findTable(tablename);
        ArrayList<String> names = t.getNames(projection);
        MessagePrinter.selectionSucceeded(names);

        String[] tables = tablename.split("@");
        ArrayList<Cursor> cursors = new ArrayList<>();
        ArrayList<DatabaseEntry> datas = new ArrayList<>();
        try {
            for (int i = 0; i < tables.length; i++) {
                Cursor cursor = db.openCursor(null, null);
                DatabaseEntry key;
                DatabaseEntry data;
                key = new DatabaseEntry(tables[i].getBytes("UTF-8"));
                data = new DatabaseEntry();
                OperationStatus os = cursor.getSearchKey(key, data, LockMode.DEFAULT);
                if (os != OperationStatus.SUCCESS) {
                    throw new Exception();
                }
                cursors.add(cursor);
                datas.add(data);
            }
            do {
                ArrayList<Value> arr = new ArrayList<>();
                Iterator<DatabaseEntry> it = datas.iterator();
                while (it.hasNext()) {
                    Record rec = (Record) deserialize(it.next().getData());
                    arr.addAll(rec.getValues());
                }
                Record newRecord = new Record(arr);
                if (bve == null || bve.eval(DBManager.getDBManager().findTable(tablename), newRecord)) {
                    ArrayList<Value> res = (projection == null) ? newRecord.getValues() : newRecord.getIndices(projection);
                    MessagePrinter.selectionRecordPrint(res);
                }
            } while(incrCursor(tables, cursors, datas, tables.length));

            MessagePrinter.selectionEnded(names);
            Iterator<Cursor> it = cursors.iterator();
            while (it.hasNext()) {
                it.next().close();
            }
        } catch (Exception e) {
            MessagePrinter.selectionEnded(names);
            Iterator<Cursor> it = cursors.iterator();
            while (it.hasNext()) {
                it.next().close();
            }
        }
    }
}
