import java.io.Serializable;

/**
 * Created by yeonwoo_kim on 11/19/16.
 */
public class Value implements Serializable, Comparable<Value> {
    private TypeName typename;
    private boolean isNull;
    private Integer intVal;
    private String stringVal;
    private String dateVal;

    public Value(TypeName typename) {
        this.typename = typename;
        isNull = false;
        intVal = null;
        stringVal = null;
        dateVal = null;
    }

    public Value() {
        isNull = true;
    }

    public boolean isCompatible(Type typ) {
        if (isNull)
            return true;
        TypeName t = typ.getTypename();
        if (t == typename) {
            if (t == TypeName.CHAR) {
                int len = typ.getLenChar();
                if (len < stringVal.length())
                    stringVal = stringVal.substring(0, len);
            }
            return true;
        }
        else return false;
    }

    public void setIntVal(Integer intVal) {
        this.intVal = intVal;
    }
    private Integer getIntVal() {
        return intVal;
    }
    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }
    private String getStringVal() {
        return stringVal;
    }
    public void setDateVal(String dateVal) {
        this.dateVal = dateVal;
    }
    private String getDateVal() {
        return dateVal;
    }

    public TypeName getTypename() {
        return typename;
    }

    public boolean isNull() {
        return isNull;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof Value) {
            Value v = (Value) o;
            if (v.getTypename() == typename) {
                switch (typename) {
                    case INT:
                        if (v.getIntVal().equals(intVal))
                            return true;
                        return false;
                    case CHAR:
                        if (v.getStringVal().equals(stringVal))
                            return true;
                        return false;
                    case DATE:
                        if (v.getDateVal().equals(dateVal))
                            return true;
                        return false;
                }
            }
        }
        return false;
    }

    @Override
    public int compareTo(Value o) {
        switch (typename) {
            case INT:
                if (intVal > o.getIntVal())
                    return 1;
                else if (intVal < o.getIntVal())
                    return -1;
                return 0;
            case CHAR:
                return stringVal.compareTo(o.getStringVal());
            case DATE:
                return dateVal.compareTo(o.getDateVal());
        }
        return 0;
    }
}
