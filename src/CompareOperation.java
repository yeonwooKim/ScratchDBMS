/**
 * Created by yeonwoo_kim on 11/21/16.
 */
public class CompareOperation {
    private CompareOperand operand;
    private String operator;

    public CompareOperation(CompareOperand operand, String operator) {
        this.operand = operand;
        this.operator = operator;
    }

    // < COMP_OP : "<" | ">" | "=" | ">=" | "<=" | "!=" >
    public boolean eval(Table t, Attribute attr, Value v, Record r) {
        if (v == null) {
            int index = t.getAttrList().indexOf(attr);
            v = r.getIndex(index);
        }
        Value v1 = operand.getValue();
        if (v1 == null) {
            int index = t.getAttrList().indexOf(operand.getAttribute());
            v1 = r.getIndex(index);
        }
        if (v.isNull() || v1.isNull())
            return false;

        if (operator.equals("<")) {
            if (v.compareTo(v1) < 0)
                return true;
            return false;
        }
        else if (operator.equals(">")) {
            if (v.compareTo(v1) > 0)
                return true;
            return false;
        }
        else if (operator.equals("=")) {
            if (v.compareTo(v1) == 0)
                return true;
            return false;
        }
        else if (operator.equals(">=")) {
            if (v.compareTo(v1) >= 0)
                return true;
            return false;
        }
        else if (operator.equals("<=")) {
            if (v.compareTo(v1) <= 0)
                return true;
            return false;
        }
        else {
            if (v.compareTo(v1) != 0)
                return true;
            return false;
        }
    }
}
