import java.io.Serializable;

/**
 * Created by yeonwoo_kim on 11/6/16.
 */

/* Attribute class describes each attribute in a table */
public class Attribute implements Serializable {
    private Type attrType;
    private String attrName;
    private boolean isPrimaryKey;
    private int foreignKey;
    private boolean isNotNull;
    private int index;

    public Attribute(Type attrType, String attrName) {
        this.attrType = attrType;
        this.attrName = attrName.toLowerCase();
        this.isNotNull = false;
        this.foreignKey = -1;
        this.isPrimaryKey = false;
    }

    public Type getAttributeType() {
        return attrType;
    }

    public String getAttributeName() {
        return attrName;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey() {
        isPrimaryKey = true;
        isNotNull = true;
    }

    public int getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(int f) {
        foreignKey = f;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull() {
        isNotNull = true;
    }

    public void setIndex(int index) { this.index = index; }

    public int getIndex() { return index; }
}
