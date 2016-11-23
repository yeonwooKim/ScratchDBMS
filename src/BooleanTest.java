/**
 * Created by yeonwoo_kim on 11/21/16.
 */
enum TestType {PAREN, PRED};
public class BooleanTest {
    private ParenthesizedBooleanExpression pbe;
    private Predicate pred;
    private TestType t;

    public BooleanTest() {
        pbe = null;
        pred = null;
        t = null;
    }

    public void setParen(ParenthesizedBooleanExpression pbe) {
        t = TestType.PAREN;
        this.pbe = pbe;
    }

    public void setPredicate(Predicate pred) {
        t = TestType.PRED;
        this.pred = pred;
    }

    public boolean eval(Table table, Record r) {
        if (t == TestType.PRED) {
            return pred.eval(table, r);
        }
        return pbe.eval(table, r);
    }
}
