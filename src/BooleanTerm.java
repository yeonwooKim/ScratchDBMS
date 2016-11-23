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

    public boolean eval(Table t, Record r) {
        Iterator<BooleanFactor> it = arr.iterator();
        while (it.hasNext()) {
            if (!it.next().eval(t, r))
                return false;
        }
        return true;
    }
}
