import java.io.Serializable;

/**
 * Created by yeonwoo_kim on 11/6/16.
 */
enum Typename { INT, CHAR, DATE }
class Type implements Comparable<Type> {
    //TODO: char length error
    private Typename typename;
    private int charlength;

    public Type(Typename typename, int char_length) {
        this.typename = typename;
        this.charlength = char_length;
    }

    public Typename getTypename() {
        return typename;
    }

    public int getCharlength() {
        return charlength;
    }

    public String toString() {
        String ret = null;
        boolean isChar = false;
        switch (typename) {
            case INT:
                ret = "int";
                break;
            case CHAR:
                ret = "char";
                isChar = true;
                break;
            case DATE:
                ret = "date";
                break;
        }
        if (isChar)
            ret = ret + "(" + Integer.toString(charlength) + ")";
        return ret;
    }


    @Override
    public int compareTo(Type o) {
        if (o.getTypename() == typename &&
                o.getCharlength() == charlength)
            return 0;
        return -1;
    }
}

public class Attribute implements Serializable {
    private Type attrType;
    private String attrName;
    private boolean isPrimaryKey;
    private boolean isForeignKey;
    private boolean isNotNull;

    public Attribute(Type attrType, String attrName) {
        this.attrType = attrType;
        this.attrName = attrName.toLowerCase();
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
