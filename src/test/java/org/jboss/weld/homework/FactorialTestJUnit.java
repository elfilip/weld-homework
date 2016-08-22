package org.jboss.weld.homework;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class FactorialTestJUnit {
	  @Test
	    public void testFactorialJUnit(){
	    	FactorialImpl f=new FactorialImpl();
	    	
	    	assertEquals(BigInteger.valueOf(2), f.compute(2));


	    }
}
