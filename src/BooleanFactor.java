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

    public boolean eval(Table t, Record r) {
        if (not && !bt.eval(t, r)) {
            return true;
        }
        if (!not && bt.eval(t, r)) {
            return true;
        }
        return false;
    }
}
