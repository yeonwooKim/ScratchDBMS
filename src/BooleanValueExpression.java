import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yeonwoo_kim on 11/21/16.
 */
public class BooleanValueExpression {
    private ArrayList<BooleanTerm> arr;

    public BooleanValueExpression() {
        arr = new ArrayList<>();
    }

    public boolean isEmpty() { return arr.isEmpty(); }
    public void addTerm(BooleanTerm bt) {
        arr.add(bt);
    }

    public boolean eval(Table t, Record r) {
        Iterator<BooleanTerm> it = arr.iterator();
        while (it.hasNext()) {
            if (it.next().eval(t, r)) {
                return true;
            }
        }
        return false;
    }
}
