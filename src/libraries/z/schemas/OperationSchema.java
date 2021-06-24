package libraries.z.schemas;

import libraries.jsetl.Constraint;
import libraries.jsetl.exception.Failure;

public abstract class OperationSchema extends Constraint {
    protected abstract void predicates() throws Failure;
}
