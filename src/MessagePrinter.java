/**
 * Created by yeonwoo_kim on 11/7/16.
 */
/* Message printer is in charge of printing prompt and result messages */
/* Message types */

enum MessageName {
    SYNTAX_ERROR,
    CREATE_TABLE_SUCCESS, // table name
    DUPLICATE_COLUMN_DEF_ERROR,
    DUPLICATE_PRIMARY_KEY_DEF_ERROR,
    REFERENCE_TYPE_ERROR,
    REFERENCE_NON_PRIMARY_KEY_ERROR,
    REFERENCE_COLUMN_EXISTENCE_ERROR,
    REFERENCE_TABLE_EXISTENCE_ERROR,
    NON_EXISTING_COLUMN_DEF_ERROR, // column name
    TABLE_EXISTENCE_ERROR,
    DROP_SUCCESS, // table name
    DROP_REFERENCED_TABLE_ERROR, // table name
    SHOW_TABLES_NO_TABLE,
    NO_SUCH_TABLE,
    CHAR_LENGTH_ERROR,
    SELECT, INSERT, DELETE
}

class Message {
    private MessageName messagename;
    private String nameArg;

    public Message(MessageName messagename) {
        this.messagename = messagename;
        nameArg = "";
    }

    public Message(MessageName messagename, String nameArg) {
        this.messagename = messagename;
        this.nameArg = nameArg;
    }

    public MessageName getMessagename() {
        return messagename;
    }

    public String getNameArg() {
        return nameArg;
    }

    public void setNameArg(String nameArg) {
        this.nameArg = nameArg;
    }
}

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
    public static void printMessage(Message m)
    {
        if (m == null)
            return;
        MessageName q = m.getMessagename();
        String n = m.getNameArg();
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

            case SHOW_TABLES_NO_TABLE:
                System.out.println("There is no table");
                break;

            case NO_SUCH_TABLE:
                System.out.println("No such table");
                break;

            case CHAR_LENGTH_ERROR:
                System.out.println("Char length should be over 0");
                break;

            case SELECT:
                System.out.println("\'SELECT\' requested");
                break;
            case INSERT:
                System.out.println("\'INSERT\' requested");
                break;
            case DELETE:
                System.out.println("\'DELETE\' requested");
                break;
            default:
                System.out.println("Syntax error");
                break;
        }
        printPrompt();
    }
}