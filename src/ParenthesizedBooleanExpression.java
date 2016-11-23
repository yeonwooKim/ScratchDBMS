/**
 * Created by yeonwoo_kim on 11/21/16.
 */
public class ParenthesizedBooleanExpression {
    private BooleanValueExpression bve;

    public ParenthesizedBooleanExpression(BooleanValueExpression bve) {
        this.bve = bve;
    }

    public boolean eval(Table t, Record r) {
        return bve.eval(t, r);
    }
}
