package org.jboss.weld.homework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.weld.homework.Factorial;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class FactorialTest {

    @Inject
    private Factorial factorial;
    
    @Inject
    @Parallel
    private Factorial factorialP;

    @Deployment
    public static Archive<?> getDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addPackages(true, Factorial.class.getPackage())
                .addClass(EventObserver.class);
    }

    @Test
    public void testFactorial() {
        assertEquals(BigInteger.ONE, factorial.compute(0));
        assertEquals(BigInteger.ONE, factorial.compute(1));
        assertEquals(BigInteger.valueOf(2), factorial.compute(2));
        assertEquals(BigInteger.valueOf(6), factorial.compute(3));
        assertEquals(BigInteger.valueOf(24), factorial.compute(4));
        assertEquals(BigInteger.valueOf(120), factorial.compute(5));
        assertEquals(BigInteger.valueOf(3628800), factorial.compute(10));
    }
    
    @Test
    public void testFactorialParallel() {
        assertEquals(BigInteger.ONE, factorialP.compute(0));
        assertEquals(BigInteger.ONE, factorialP.compute(1));
        assertEquals(BigInteger.valueOf(2), factorialP.compute(2));
        assertEquals(BigInteger.valueOf(6), factorialP.compute(3));
        assertEquals(BigInteger.valueOf(24), factorialP.compute(4));
        assertEquals(BigInteger.valueOf(120), factorialP.compute(5));
        assertEquals(BigInteger.valueOf(3628800), factorialP.compute(10));
    }

    @Test
    public void testEvent(EventObserver observer) {
        observer.reset();
        factorial.compute(6);
        assertNotNull(observer.getEvent());
        assertNotNull(observer.getEvent().getResult());
        assertEquals(6, observer.getEvent().getNumber());
        assertEquals(BigInteger.valueOf(720), observer.getEvent().getResult());
    }

    @ApplicationScoped
    protected static class EventObserver {

        private FactorialComputationFinished event;

        public FactorialComputationFinished getEvent() {
            return event;
        }

        public void reset() {
            this.event = null;
        }

        public void observe(@Observes FactorialComputationFinished event) {
            this.event = event;
        }
    }

}
