package libraries.z.type.decorators;

import libraries.jsetl.Constraint;
import libraries.jsetl.LSet;
import libraries.jsetl.exception.Failure;

public final class P<T> extends LSet implements Decorator {
	public P(LSet values) throws Failure {
		super(values);

		verifyDecoratorConstraint();
	}

	@Override
	public void verifyDecoratorConstraint() throws Failure {
		Constraint.truec().solve();
	}
}