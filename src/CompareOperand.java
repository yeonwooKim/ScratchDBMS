/**
 * Created by yeonwoo_kim on 11/21/16.
 */
enum OpType {ATTRIBUTE, VALUE}
public class CompareOperand {
    private Attribute attr;
    private Value v;
    private OpType t;

    public CompareOperand() {
        attr = null;
        v = null;
    }

    public void setAttribute(Attribute attr) {
        t = OpType.ATTRIBUTE;
        this.attr = attr;
    }

    public void setValue(Value v) {
        t = OpType.VALUE;
        this.v = v;
    }

    public Value getValue() {
        if (t == OpType.VALUE)
            return v;
        return null;
    }

    public Attribute getAttribute() {
        if (t == OpType.ATTRIBUTE)
            return attr;
        return null;
    }

    public TypeName getTypename() {
        if (t == OpType.ATTRIBUTE) {
            return attr.getAttributeType().getTypename();
        }
        return v.getTypename();
    }
}
