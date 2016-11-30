import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yeonwoo_kim on 11/21/16.
 */
public class BooleanTerm {
    private ArrayList<BooleanFactor> arr;

    public BooleanTerm() {
        arr = new ArrayList<>();
    }

    public void addFactor(BooleanFactor bf) {
        arr.add(bf);
    }

    public Result eval(Table t, Record r) {
        Iterator<BooleanFactor> it = arr.iterator();
        boolean unknown = false;
        while (it.hasNext()) {
            switch (it.next().eval(t, r)) {
                case FALSE:
                    return Result.FALSE;
                case UNKNOWN:
                    unknown = true;
                    continue;
                case TRUE:
                    continue;
            }
        }
        if (unknown)
            return Result.UNKNOWN;
        return Result.TRUE;
    }
}
