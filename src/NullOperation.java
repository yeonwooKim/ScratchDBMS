/**
 * Created by yeonwoo_kim on 11/21/16.
 */
public class NullOperation {
    boolean isNull;

    public NullOperation(boolean isNull) {
        this.isNull = isNull;
    }

    public boolean getNull() {
        return isNull;
    }

    public Result eval(Table t, Attribute attr, Record r) {
        int index = t.getAttrList().indexOf(attr);
        if (r.getIndex(index).isNull() && isNull)
            return Result.TRUE;
        if (!r.getIndex(index).isNull() && !isNull)
            return Result.TRUE;
        return Result.FALSE;
    }
}
