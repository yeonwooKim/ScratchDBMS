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

    public Result eval(Table t, Record r) {
        Iterator<BooleanTerm> it = arr.iterator();
        boolean unknown = false;
        while (it.hasNext()) {
            switch (it.next().eval(t, r)) {
                case TRUE:
                    return Result.TRUE;
                case FALSE:
                    continue;
                case UNKNOWN:
                    unknown = true;
                    continue;
            }
        }
        if (unknown)
            return Result.UNKNOWN;
        return Result.FALSE;
    }
}
