import java.io.Serializable;

/**
 * Created by yeonwoo_kim on 11/6/16.
 */

public class Attribute implements Serializable {
    private Type attrType;
    private String attrName;
    private boolean isPrimaryKey;
    private boolean isForeignKey;
    private boolean isNotNull;

    public Attribute(Type attrType, String attrName) {
        this.attrType = attrType;
        this.attrName = attrName.toLowerCase();
        this.isNotNull = false;
        this.isForeignKey = false;
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

    public boolean isForeignKey() {
        return isForeignKey;
    }

    public void setForeignKey() {
        isForeignKey = true;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull() {
        isNotNull = true;
    }
}
