package libraries.z.type.decorators;

import libraries.jsetl.exception.Failure;

public interface Decorator {
    public void verifyDecoratorConstraint() throws Failure;
}
