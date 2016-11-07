import java.io.Serializable;

public class Type implements Comparable<Type>, Serializable {
    private TypeName typename;
    private int lenChar;

    public Type(TypeName typename, int char_length) {
        this.typename = typename;
        this.lenChar = char_length;
    }

    public TypeName getTypename() {
        return typename;
    }

    public int getLenChar() {
        return lenChar;
    }

    public boolean isValid() {
        if (typename == TypeName.CHAR && lenChar <= 0)
            return false;
        else
            return true;
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
            ret = ret + "(" + Integer.toString(lenChar) + ")";
        return ret;
    }


    @Override
    public int compareTo(Type o) {
        if (o.getTypename() == typename &&
                o.getLenChar() == lenChar)
            return 0;
        return -1;
    }
}
