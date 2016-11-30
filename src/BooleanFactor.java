/**
 * Created by yeonwoo_kim on 11/21/16.
 */
public class BooleanFactor {
    private boolean not;
    private BooleanTest bt;

    public BooleanFactor() {
        not = false;
        bt = null;
    }

    public void setTest(BooleanTest bt) {
        this.bt = bt;
    }

    public void setNot() {
        not = true;
    }

    public Result eval(Table t, Record r) {
        switch (bt.eval(t, r)) {
            case TRUE:
                if (not)
                    return Result.FALSE;
                return Result.TRUE;
            case FALSE:
                if (not)
                    return Result.TRUE;
                return Result.FALSE;
            case UNKNOWN:
                return Result.UNKNOWN;
        }
        return Result.UNKNOWN;
    }
}
