import java.util.ArrayList;
import java.util.Iterator;

public class MessagePrinter
{
    public static void printPrompt()
    {
        System.out.print("DB_2014-17184> ");
    }
    private static void createTableFailed() {
        System.out.print("Create table has failed: ");
    }
    private static void dropTableFailed() {
        System.out.print("Drop table has failed: ");
    }
    private static void insertionFailed() { System.out.print("Insertion has failed: "); }
    private static void selectionFailed() {
        System.out.print("Selection has failed: ");
    }
    public static void selectionRecordPrint(ArrayList<Value> arr) {
        Iterator<Value> it = arr.iterator();
        while (it.hasNext()) {
            System.out.printf("| %-19s", it.next().toString());
        }
        System.out.println("|");
    }
    public static void selectionSucceeded(ArrayList<String> projection) {
        int size = projection.size();
        for (int i = 0 ; i < size ; i ++)
            System.out.print("+--------------------");
        System.out.println("+");
        Iterator<String> it = projection.iterator();
        while (it.hasNext()) {
            System.out.printf("| %-19s", it.next().toUpperCase());
        }
        System.out.println("|");
        for (int i = 0 ; i < size ; i ++)
            System.out.print("+--------------------");
        System.out.println("+");
    }
    public static void selectionEnded(ArrayList<String> projection) {
        int size = projection.size();
        for (int i = 0 ; i < size ; i ++)
            System.out.print("+--------------------");
        System.out.println("+");
    }
    public static void printMessage(Message m)
    {
        MessageName q = m.getMessagename();
        String n = m.getNameArg();
        Message m1;
        switch(q)
        {
            case SYNTAX_ERROR:
                System.out.println("Syntax error");
                break;
            case CREATE_TABLE_SUCCESS:
                System.out.println("\'[" + n + "]\' table is created");
                break;
            case DUPLICATE_COLUMN_DEF_ERROR:
                createTableFailed();
                System.out.println("column definition is duplicated");
                break;
            case DUPLICATE_PRIMARY_KEY_DEF_ERROR:
                createTableFailed();
                System.out.println("primary key definition is duplicated");
                break;
            case REFERENCE_TYPE_ERROR:
                createTableFailed();
                System.out.println("foreign key references wrong type");
                break;
            case REFERENCE_NON_PRIMARY_KEY_ERROR:
                createTableFailed();
                System.out.println("foreign key references non primary key column");
                break;
            case REFERENCE_COLUMN_EXISTENCE_ERROR:
                createTableFailed();
                System.out.println("foreign key references non existing column");
                break;
            case REFERENCE_TABLE_EXISTENCE_ERROR:
                createTableFailed();
                System.out.println("foreign key references non existing table");
                break;
            case NON_EXISTING_COLUMN_DEF_ERROR:
                createTableFailed();
                System.out.println("\'[" + n + "]\' does not exist in column definition");
                break;
            case TABLE_EXISTENCE_ERROR:
                createTableFailed();
                System.out.println("table with the same name already exists");
                break;

            case DROP_SUCCESS:
                System.out.println("\'[" + n + "]\' table is dropped");
                break;
            case DROP_REFERENCED_TABLE_ERROR:
                dropTableFailed();
                System.out.println("\'[" + n + "]\' is referenced by other table");
                break;

            case SHOW_TABLES:
                m1 = DBManager.getDBManager().showTables();
                if (m1 != null) {
                    printMessage(m1);
                    return;
                }
                break;
            case SHOW_TABLES_NO_TABLE:
                System.out.println("There is no table");
                break;

            case DESC_TABLE:
                m1 = DBManager.getDBManager().descTable(m.getNameArg());
                if (m1 != null) {
                    printMessage(m1);
                    return;
                }
                break;
            case NO_SUCH_TABLE:
                System.out.println("No such table");
                break;

            case CHAR_LENGTH_ERROR:
                System.out.println("Char length should be over 0");
                break;

            case SELECT_SUCCESS:
                break;
            case SELECT_COLUMN_RESOLVE_ERROR:
                selectionFailed();
                System.out.println("fail to resolve \'[" + n + "]\'");
                break;
            case SELECT_TABLE_EXISTENCE_ERROR:
                selectionFailed();
                System.out.println("\'[" + n + "]\' does not exist");
                break;

            case INSERT_SUCCESS:
                System.out.println("The row is inserted");
                break;
            case INSERT_DUPLICATE_PRIMARY_KEY:
                insertionFailed();
                System.out.println("Primary key duplication");
                break;
            case INSERT_REFERENTIAL_INTEGRITY:
                insertionFailed();
                System.out.println("Referential integrity violation");
                break;
            case INSERT_TYPE_MISMATCH:
                insertionFailed();
                System.out.println("Types are not matched");
                break;
            case INSERT_COLUMN_EXISTENCE:
                insertionFailed();
                System.out.println("\'[" + n + "]\' does not exist");
                break;
            case INSERT_COLUMN_NON_NULLABLE:
                insertionFailed();
                System.out.println("\'[" + n + "]\' is not nullable");
                break;

            case DELETE_SUCCESS: {
                String[] nums = n.split(" ");
                int fail = Integer.parseInt(nums[1]);
                System.out.println("[" + nums[0] + "] row(s) are deleted");
                if (fail != 0)
                    System.out.println("[" + nums[1] + "] row(s) are not deleted due to referential integrity");
                break;
            }
            case WHERE_INCOMPARABLE:
                System.out.println("Where clause try to compare incomparable values");
                break;
            case WHERE_TABLE_NOT_SPECIFIED:
                System.out.println("Where clause try to reference tables which are not specified");
                break;
            case WHERE_COLUMN_NOT_EXIST:
                System.out.println("Where clause try to reference non existing column");
                break;
            case WHERE_AMBIGUOUS_REFERENCE:
                System.out.println("Where clause contains ambiguous reference");
                break;

            default:
                System.out.println("Syntax error");
                break;
        }
        printPrompt();
    }
}