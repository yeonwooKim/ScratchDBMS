/**
 * Created by yeonwoo_kim on 11/21/16.
 */
enum PredType { ID, CONST }
public class Predicate {
    private IdentifierPredicate idPred;
    private ConstantPredicate constPred;
    private PredType t;

    public Predicate() {
        idPred = null;
        constPred = null;
        t = null;
    }

    public void setIdPredicate(IdentifierPredicate idPred) {
        t = PredType.ID;
        this.idPred = idPred;
    }

    public void setConstPredicate(ConstantPredicate constPred) {
        t = PredType.CONST;
        this.constPred = constPred;
    }

    public Result eval(Table table, Record r) {
        if (t == PredType.ID) {
            return idPred.eval(table, r);
        }
        return constPred.eval(table, r);
    }
}
