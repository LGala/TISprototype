package libraries.jsetl;

import libraries.jsetl.annotation.NotNull;
import libraries.jsetl.exception.Fail;

/**
 * Rewrite rules to deal with meta-constraints like {@code or} or {@code true}.
 */
class RwRulesMeta extends LibConstraintsRules {

    //////////////////////////////////////////////////////
    ////////////////// CONSTRUCTORS //////////////////////
    //////////////////////////////////////////////////////

    /**
     * Constructs an instance of rewrite rules and stores a reference to the solver
     * @param solver reference to the solver
     */
    protected RwRulesMeta(@NotNull Solver solver) {
        super(solver);
        assert solver != null;
    }

    //////////////////////////////////////////////////////
    ////////////////// PROTECTED METHODS /////////////////
    //////////////////////////////////////////////////////

    /**
     * Uses the appropriate method to solve the given atomic constraint.
     * @param aConstraint atomic constraint to solve.
     * @return {@code true} the constraint has been handled, {@code false} otherwise.
     * @throws Fail if inconsistencies are found.
     */
    @Override
    protected boolean solveConstraint(@NotNull AConstraint aConstraint) {
        assert aConstraint != null;

        if (aConstraint.constraintKindCode == Environment.orCode)
                or(aConstraint);        // non-deterministic or between constraints
        else if (aConstraint.constraintKindCode == Environment.orTestCode)
                orTest(aConstraint);    // deterministic or test between constraints
        else if (aConstraint.constraintKindCode == Environment.notTestCode)
                notTest(aConstraint);    // deterministic not test of a constraint
        else if (aConstraint.constraintKindCode == Environment.impliesTestCode)
                impliesTest(aConstraint);  // deterministic implication between constraints
        else if(aConstraint.constraintKindCode == Environment.trueCode)
            aConstraint.setSolved(true); //always true
        else if(aConstraint.constraintKindCode == Environment.falseCode)
            solver.fail(aConstraint); //always false, thus fails
        else
                return false;
        return true;
    }

    /**
     * Nondeterministically solves the given disjunction {@code constraint1 or constraint2}.
     * {@code constraint1, constraint2} are {@code Constraint} or {@code AConstraint} and
     * are respectively the first and second argument of {@code aConstraint}.
     * @param aConstraint atomic constraint disjunction.
     * @throws Fail if inconsistencies are found.
     */
    protected void or(@NotNull AConstraint aConstraint) {
        assert aConstraint != null;
        assert aConstraint.argument1 instanceof AConstraint || aConstraint.argument1 instanceof Constraint;
        assert aConstraint.argument2 instanceof AConstraint || aConstraint.argument1 instanceof Constraint;
        assert aConstraint.argument3 == null;
        assert aConstraint.argument4 == null;
        assert aConstraint.constraintKindCode == Environment.orCode;

        Constraint constraint1, constraint2;
        if (aConstraint.argument1 instanceof AConstraint)
            constraint1 = new Constraint((AConstraint)aConstraint.argument1);
        else
            constraint1 = (Constraint) aConstraint.argument1;
        if (aConstraint.argument2 instanceof AConstraint)
            constraint2 = new Constraint((AConstraint)aConstraint.argument2);
        else
            constraint2 = (Constraint) aConstraint.argument2;

        switch (aConstraint.getAlternative()) {
            case 0:
                solver.backtracking.addChoicePoint(aConstraint);
                aConstraint.setSolved(true);

                solver.add(constraint1.clone());
                break;
            case 1:
                solver.add(constraint2.clone());
                aConstraint.setSolved(true);
                break;
        }
    }

    /**
     * Extracts the two constraints in disjunction (one of the two constraints must be satisfied)
     * then solves their disjunction using an auxiliary solver.
     * Other constraints in the constraint store are not taken into account in the resolution process
     * and the test is performed only if the constraint involved are ground.
     * @param aConstraint {@code orTest} atomic constraint.
     * @throws Fail if inconsistencies are found.
     */
    protected void orTest(@NotNull AConstraint aConstraint) {
        assert aConstraint != null;
        assert aConstraint.argument1 instanceof Constraint || aConstraint.argument1 instanceof AConstraint;
        assert aConstraint.argument2 instanceof Constraint || aConstraint.argument2 instanceof AConstraint;
        assert aConstraint.argument3 == null;
        assert aConstraint.argument4 == null;
        assert aConstraint.constraintKindCode == Environment.orTestCode;

        Constraint argument1, argument2;
        if (aConstraint.argument1 instanceof AConstraint)
            argument1 = new Constraint((AConstraint)aConstraint.argument1);
        else
            argument1 = (Constraint)aConstraint.argument1;
        if (aConstraint.argument2 instanceof AConstraint)
            argument2 = new Constraint((AConstraint)aConstraint.argument2);
        else
            argument2 = (Constraint)aConstraint.argument2;

        orTestConjs(argument1, argument2, aConstraint);
    }

    /**
     * Extracts the constraint in the negation (the constraint must be unsatisfiable)
     * then solves it using an auxiliary solver.
     * Other constraints in the constraint store are not taken into account in the resolution process
     * and the test is performed only if the constraint being negated is ground.
     * @param aConstraint {@code notTest} atomic constraint.
     * @throws Fail if inconsistencies are found.
     */
    protected void notTest(@NotNull AConstraint aConstraint) {
        assert aConstraint != null;
        assert aConstraint.argument1 instanceof Constraint || aConstraint.argument1 instanceof AConstraint;
        assert aConstraint.argument2 == null;
        assert aConstraint.argument3 == null;
        assert aConstraint.argument4 == null;
        assert aConstraint.constraintKindCode == Environment.notTestCode;

        if (aConstraint.argument1 instanceof AConstraint)
            notTestConj(new Constraint((AConstraint)aConstraint.argument1), aConstraint);
        else
            notTestConj((Constraint)aConstraint.argument1, aConstraint);
    }

    /**
     * Extracts the constraints in the implication
     * (the truth value of the first constraint must imply the truth value of the second constraint)
     * then solves it using an auxiliary solver.
     * Other constraints in the constraint store are not taken into account in the resolution process
     * and the test is performed only if the constraints involved are ground.
     * Actually, the first constraint must be ground, the second may not be ground if the first is
     * not satisfiable.
     * @param aConstraint {@code impliesTest} atomic constraint.
     * @throws Fail if inconsistencies are found.
     */
    protected void impliesTest(@NotNull AConstraint aConstraint) {
        assert aConstraint != null;
        assert aConstraint.argument1 instanceof Constraint || aConstraint.argument1 instanceof AConstraint;
        assert aConstraint.argument2 instanceof Constraint || aConstraint.argument2 instanceof AConstraint;
        assert aConstraint.argument3 == null;
        assert aConstraint.argument4 == null;
        assert aConstraint.constraintKindCode == Environment.impliesTestCode;

        Constraint argument1, argument2;
        if (aConstraint.argument1 instanceof AConstraint)
            argument1 = new Constraint((AConstraint)aConstraint.argument1);
        else
            argument1 = (Constraint)aConstraint.argument1;
        if (aConstraint.argument2 instanceof AConstraint)
            argument2 = new Constraint((AConstraint)aConstraint.argument2);
        else
            argument2 = (Constraint)aConstraint.argument2;

        impliesTestConjs(argument1, argument2, aConstraint);
    }


    //////////////////////////////////////////////////////
    ////////////////// PRIVATE METHODS ///////////////////
    //////////////////////////////////////////////////////

    /**
     * Solves a constraint disjunction (one of the two constraints must be satisfied) using an auxiliary solver.
     * Other constraints in the constraint store are not taken into account in the resolution process
     * and the test is performed only if the constraint involved are ground.
     * @param constraint1 first argument of disjunction.
     * @param constraint2 second argument of disjunction.
     * @param aConstraint {@code orTest} atomic constraint.
     * @throws Fail if inconsistencies are found.
     */
    private void orTestConjs(@NotNull Constraint constraint1, @NotNull Constraint constraint2, @NotNull AConstraint aConstraint) {
        assert constraint1 != null;
        assert constraint2 != null;
        assert aConstraint != null;
        assert aConstraint.constraintKindCode == Environment.orTestCode;

        if (!constraint1.isGround() || !constraint2.isGround())
            return;

        Solver auxSolver = new Solver();
        auxSolver.add(constraint1.clone());

        if (auxSolver.check())
            aConstraint.setSolved(true);
        else {
            auxSolver.clearStore();
            auxSolver.add(constraint2.clone());
            if (auxSolver.check())
                aConstraint.setSolved(true);
            else
                solver.fail(aConstraint);
        }
    }

    /**
     * Solves a negation test (the constraint must be unsatisfiable) using an auxiliary solver.
     * Other constraints in the constraint store are not taken into account in the resolution process
     * and the test is performed only if the constraint being negated is ground.
     * @param constraint constraint being negated.
     * @param aConstraint {@code notTest} atomic constraint.
     * @throws Fail if inconsistencies are found.
     */
   private void notTestConj(@NotNull Constraint constraint, @NotNull AConstraint aConstraint) {
        assert constraint != null;
        assert aConstraint != null;
        assert aConstraint.constraintKindCode == Environment.notTestCode;

        if (!constraint.isGround())
            return;
        Solver auxSolver = new Solver();
        auxSolver.add(constraint.clone());
        if (auxSolver.check())
            solver.fail(aConstraint);
        else
            aConstraint.setSolved(true);
   }

    /**
     * Solves an implication test (the truth value of the first constraint must imply the truth value of the second)
     * using an auxiliary solver.
     * Other constraints in the constraint store are not taken into account in the resolution process
     * and the test is performed only if the constraints involved are ground.
     * Actually, the first constraint must be ground, the second may not be ground if the first is
     * not satisfiable.
     * @param argument1 first argument of the implication.
     * @param argument2 second argument of the implication.
     * @param aConstraint {@code impliesTest} atomic constraint.
     * @throws Fail if the constraint is not satisfiable.
     */
   private void impliesTestConjs(@NotNull Constraint argument1, @NotNull Constraint argument2, @NotNull AConstraint aConstraint) {
       assert argument1 != null;
       assert argument2 != null;
       assert aConstraint != null;
       assert aConstraint.constraintKindCode == Environment.impliesTestCode;

       if (!argument1.isGround())
           return;
       Solver auxSolver = new Solver();
       auxSolver.add(argument1.clone());
       if (!auxSolver.check())
           aConstraint.setSolved(true);
       else {  // argument1 is true
           if (!argument2.isGround())
               return;
           else {
               auxSolver.clearStore();
               auxSolver.add(argument2.clone());
               if (auxSolver.check())
                    aConstraint.setSolved(true);
               else
                    solver.fail(aConstraint);
           }
       }
   }

}
