package org.jboss.weld.homework;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ejb.Asynchronous;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Parallel
@ApplicationScoped
public class FactorialImplParallel implements Factorial {

	@Inject
	MathOperations op;

	@Override
	@Asynchronous
	public BigInteger compute(long number) {
		BigInteger result = null;
		if (number == 0 || number==1) {
			result = BigInteger.ONE;
		} else {
			Future<BigInteger> one = op.multiplySequenceAsync(1, number / 2);
			Future<BigInteger> two = op.multiplySequenceAsync(number / 2 + 1, number);

			try {				
				result = one.get().multiply(two.get());
			} catch (InterruptedException e) {
				throw new RuntimeException("Interrupted exception occured: ", e);
			} catch (ExecutionException e) {
				throw new RuntimeException("ExecutionException exception occured: ", e);
			} 

		}
		return result;
	}

}
