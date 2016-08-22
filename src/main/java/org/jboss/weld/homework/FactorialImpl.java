package org.jboss.weld.homework;

import java.math.BigInteger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FactorialImpl implements Factorial {

	@Override
	public BigInteger compute(long number) {	
		if (number <= 1) {
			return BigInteger.valueOf(1L);
		} else {
			BigInteger result = compute(number - 1L);
			result=result.multiply(BigInteger.valueOf(number));
			return result;
		}

	}

}
