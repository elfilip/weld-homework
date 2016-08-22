package org.jboss.weld.homework;

import java.math.BigInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@ApplicationScoped
public class FactorialImpl implements Factorial {
	@Inject
	Event<FactorialComputationFinished> event;
	@Inject
	MathOperations op;

	public BigInteger compute(long number) {
		BigInteger result;
		if (number == 0) {
			result = BigInteger.ONE;
		} else {
			result = op.multiplySequence(1L, number);
		}
		event.fire(new FactorialComputationFinished(number, result));
		return result;

	}

}
