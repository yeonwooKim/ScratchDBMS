/**
 * Created by yeonwoo_kim on 11/7/16.
 */
/* Message printer is in charge of printing prompt and result messages */
/* Message types */

public enum MessageName {
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
    SHOW_TABLES,
    SHOW_TABLES_NO_TABLE,
    DESC_TABLE,
    NO_SUCH_TABLE,
    CHAR_LENGTH_ERROR,
    SELECT, INSERT, DELETE
}
