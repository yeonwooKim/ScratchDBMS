/**
 * Created by yeonwoo_kim on 11/21/16.
 */
enum IDPredType {NULL, COMP}
public class IdentifierPredicate {
    private IDPredType t;
    private Attribute attr;

    private NullOperation nullOp;

    private CompareOperation compOp;

    public IdentifierPredicate() {
        t = null;
        attr = null;
        nullOp = null;
        compOp = null;
    }

    public void setID(Attribute attr) {
        this.attr = attr;
    }

    public void setCompOperation(CompareOperation compOp) {
        t = IDPredType.COMP;
        this.compOp = compOp;
    }

    public void setNullOperation(NullOperation nullOp) {
        t = IDPredType.NULL;
        this.nullOp = nullOp;
    }

    public Result eval(Table table, Record r) {
        if (t == IDPredType.COMP) {
            return compOp.eval(table, attr, null, r);
        }
        return nullOp.eval(table, attr, r);
    }
}
