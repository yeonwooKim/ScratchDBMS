/**
 * Created by yeonwoo_kim on 11/21/16.
 */
public class ConstantPredicate {
    private Value lop;
    private CompareOperation compOp;

    public ConstantPredicate() {
        lop = null;
        compOp = null;
    }

    public void setConst(Value lop) {
        this.lop = lop;
    }

    public void setCompOperation(CompareOperation compOp) {
        this.compOp = compOp;
    }

    public Result eval(Table t, Record r) {
        return compOp.eval(t, null, lop, r);
    }
}
