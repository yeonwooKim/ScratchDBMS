import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yeonwoo_kim on 11/6/16.
 */

/* Attribute class describes each attribute in a table */
public class Attribute implements Serializable, Cloneable {
    private Type attrType;
    private String attrName;
    private boolean isPrimaryKey;
    private int foreignKey; // Contains the index of the foreign key list in table
    private boolean isNotNull;
    private int index; // Temporary variable used in insert
    private String alias;
    private String tableName;
    private String tableAlias;

    public Attribute(Type attrType, String attrName) {
        this.attrType = attrType;
        this.attrName = attrName.toLowerCase();
        this.isNotNull = false;
        this.foreignKey = -1;
        this.isPrimaryKey = false;
        alias = null;
    }

    public Type getAttributeType() { return attrType; }
    public String getAttributeName() { return attrName; }
    public String getAlias() {
        return alias;
    }
    public int getForeignKey() { return foreignKey; }
    public boolean isPrimaryKey() { return isPrimaryKey; }
    public boolean isNotNull() { return isNotNull; }
    public int getIndex() { return index; }
    public String getTableName() { return tableName; }
    public String getTableAlias() { return tableAlias; }

    public void setPrimaryKey() {
        isPrimaryKey = true;
        isNotNull = true;
    }
    public void setForeignKey(int f) { foreignKey = f; }
    public void setNotNull() { isNotNull = true; }
    public void setAlias(String alias) { this.alias = alias; }
    public void setIndex(int index) { this.index = index; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    public void setTableAlias(String tableAlias) { this.tableAlias = tableAlias; }

    @Override
    public Object clone() {
        Attribute clone = null;
        try {
            clone = (Attribute) super.clone();
        } catch(Exception e) {
        }

        return clone;
    }
}
