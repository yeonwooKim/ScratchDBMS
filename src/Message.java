/**
 * Created by yeonwoo_kim on 11/7/16.
 */
public class Message {
    private static Message syntaxError,
                    createTableSuccess,
                    duplicateColumnDef,
                    duplicatePrimaryKeyDef,
                    referenceType,
                    referenceNonPrimaryKey,
                    referenceColumnExistence,
                    referenceTableExistence,
                    nonExistingColumnDef,
                    tableExistence,
                    dropSuccess,
                    dropReferencedTable,
                    showTables,
                    showTablesNoTable,
                    descTable,
                    noSuchTable,
                    charLength,
                    select,
                    insertSuccess,
                    delete,
                    insertTypeMismatch,
                    insertColumnNonNullable,
                    insertColumnExistence,
                    insertDuplicatePrimaryKey,
                    insertReferentialIntegrity;

    public static Message getSyntaxError() {
        if (syntaxError == null)
            syntaxError = new Message(MessageName.SYNTAX_ERROR);
        return syntaxError;
    }

    public static Message getCreateTableSuccess() {
        if (createTableSuccess == null)
            createTableSuccess = new Message(MessageName.CREATE_TABLE_SUCCESS);
        return createTableSuccess;
    }

    public static Message getDuplicateColumnDef() {
        if (duplicateColumnDef == null)
            duplicateColumnDef = new Message(MessageName.DUPLICATE_COLUMN_DEF_ERROR);
        return duplicateColumnDef;
    }

    public static Message getDuplicatePrimaryKeyDef() {
        if (duplicatePrimaryKeyDef == null)
            duplicatePrimaryKeyDef = new Message(MessageName.DUPLICATE_PRIMARY_KEY_DEF_ERROR);
        return duplicatePrimaryKeyDef;
    }

    public static Message getReferenceType() {
        if (referenceType == null)
            referenceType = new Message(MessageName.REFERENCE_TYPE_ERROR);
        return referenceType;
    }

    public static Message getReferenceNonPrimaryKey() {
        if (referenceNonPrimaryKey == null)
            referenceNonPrimaryKey = new Message(MessageName.REFERENCE_NON_PRIMARY_KEY_ERROR);
        return referenceNonPrimaryKey;
    }

    public static Message getReferenceColumnExistence() {
        if (referenceColumnExistence == null)
            referenceColumnExistence = new Message(MessageName.REFERENCE_COLUMN_EXISTENCE_ERROR);
        return referenceColumnExistence;
    }

    public static Message getReferenceTableExistence() {
        if (referenceTableExistence == null)
            referenceTableExistence = new Message(MessageName.REFERENCE_TABLE_EXISTENCE_ERROR);
        return referenceTableExistence;
    }

    public static Message getNonExistingColumnDef() {
        if (nonExistingColumnDef == null)
            nonExistingColumnDef = new Message(MessageName.NON_EXISTING_COLUMN_DEF_ERROR);
        return nonExistingColumnDef;
    }

    public static Message getTableExistence() {
        if (tableExistence == null)
            tableExistence = new Message(MessageName.TABLE_EXISTENCE_ERROR);
        return tableExistence;
    }

    public static Message getDropSuccess() {
        if (dropSuccess == null)
            dropSuccess = new Message(MessageName.DROP_SUCCESS);
        return dropSuccess;
    }

    public static Message getDropReferencedTable() {
        if (dropReferencedTable == null)
            dropReferencedTable = new Message(MessageName.DROP_REFERENCED_TABLE_ERROR);
        return dropReferencedTable;
    }

    public static Message getShowTables() {
        if (showTables == null)
            showTables = new Message(MessageName.SHOW_TABLES);
        return showTables;
    }

    public static Message getShowTablesNoTable() {
        if (showTablesNoTable == null)
            showTablesNoTable = new Message(MessageName.SHOW_TABLES_NO_TABLE);
        return showTablesNoTable;
    }

    public static Message getDescTable() {
        if (descTable == null)
            descTable = new Message(MessageName.DESC_TABLE);
        return descTable;
    }

    public static Message getNoSuchTable() {
        if (noSuchTable == null)
            noSuchTable = new Message(MessageName.NO_SUCH_TABLE);
        return noSuchTable;
    }

    public static Message getCharLength() {
        if (charLength == null)
            charLength = new Message(MessageName.CHAR_LENGTH_ERROR);
        return charLength;
    }

    public static Message getSelect() {
        if (select == null)
            select = new Message(MessageName.SELECT);
        return select;
    }

    public static Message getInsertSuccess() {
        if (insertSuccess == null)
            insertSuccess = new Message(MessageName.INSERT_SUCCESS);
        return insertSuccess;
    }

    public static Message getDelete() {
        if (delete == null)
            delete = new Message(MessageName.DELETE);
        return delete;
    }

    public static Message getInsertTypeMismatch() {
        if (insertTypeMismatch == null)
            insertTypeMismatch = new Message(MessageName.INSERT_TYPE_MISMATCH);
        return insertTypeMismatch;
    }

    public static Message getInsertColumnNonNullable() {
        if (insertColumnNonNullable == null)
            insertColumnNonNullable = new Message(MessageName.INSERT_COLUMN_NON_NULLABLE);
        return insertColumnNonNullable;
    }

    public static Message getInsertColumnExistence() {
        if (insertColumnExistence == null)
            insertColumnExistence = new Message(MessageName.INSERT_COLUMN_EXISTENCE);
        return insertColumnExistence;
    }

    public static Message getInsertDuplicatePrimaryKey() {
        if (insertDuplicatePrimaryKey == null)
            insertDuplicatePrimaryKey = new Message(MessageName.INSERT_DUPLICATE_PRIMARY_KEY);
        return insertDuplicatePrimaryKey;
    }

    public static Message getInsertReferentialIntegrity() {
        if (insertReferentialIntegrity == null)
            insertReferentialIntegrity = new Message(MessageName.INSERT_REFERENTIAL_INTEGRITY);
        return insertReferentialIntegrity;
    }

    private MessageName messagename;
    private String nameArg;

    private Message(MessageName messagename) {
        this.messagename = messagename;
        nameArg = "";
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
