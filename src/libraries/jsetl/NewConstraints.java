package libraries.jsetl;

import libraries.jsetl.annotation.NotNull;
import libraries.jsetl.exception.Failure;
import libraries.jsetl.exception.NotDefConstraintException;

/**
 * The {@code NewConstraints} class allows the user to define new constraints.
 * User-defined constraints are dealt with as the built-in
 * constraints: they can be added to the constraint store using the method
 * {@code add} and solved using the {@code solver} methods for
 * constraint solving.
 *
 * @author Elio Panegai
 */
public abstract class NewConstraints {

    ///////////////////////////////////////////////////////
    ////////////// DATA MEMBERS ///////////////////////////
    ///////////////////////////////////////////////////////

    /**
     *  Reference to the solver.
     */    
    public Solver solver;


    ///////////////////////////////////////////////////////
    /////////////// CONSTRUCTORS //////////////////////////
    ///////////////////////////////////////////////////////

    /**
     * Sets the internal {@code solver} to the given one and adds this constraint to the solver.
     * @param solver reference to the solver.
     */
    public NewConstraints(@NotNull Solver solver) {
        this.solver = solver;
        this.solver.setNewConstraints(this);
    }

    ///////////////////////////////////////////////////////
    /////////////// ABSTRACT METHODS //////////////////////
    ///////////////////////////////////////////////////////

    /**
     * Solves the provided constraint if it is able to handle it.
     *
     * In order to solve non-deterministic constraints the user must use a switch case over {@code constraint.getAlternative()}
     * and use the method {@code Solver#addChoicePoint(constraint)} in each case except for the last one.
     * @param constraint the constraint to solve.
     * @throws Failure if the constraint is not satisfiable.
     * @throws NotDefConstraintException if this class can't handle constraints of the provided kind.
     */
    protected abstract void user_code(@NotNull Constraint constraint)
    throws NotDefConstraintException;


    ///////////////////////////////////////////////////////
    /////////////// PROTECTED METHODS /////////////////////
    ///////////////////////////////////////////////////////

    /**
     * Sets the internal {@code solver} to the given one.
     * @param solver reference to the solver.
     */
    protected void setSolver(@NotNull Solver solver) {
        assert solver != null;
        this.solver = solver;
    }
    
}
