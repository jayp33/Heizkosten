package com.japhdroid.heizkosten;

import junit.framework.TestCase;

import java.lang.reflect.Method;

/**
 * Created by User on 18.11.2015.
 */
public class CostCalculatorTest extends TestCase {

    public void testTooFastIndex() throws Exception {
        // Testing private method: int getTooFastIndex(int)
        Method method = CostCalculator.class.getDeclaredMethod("getTooFastIndex", new Class[]{int.class});
        method.setAccessible(true);
        int actual = (int) method.invoke(new CostCalculator(), 5);
        assertEquals(0, actual);
        actual = (int) method.invoke(new CostCalculator(), 71);
        assertEquals(9, actual);
    }

    public void testSpeeding_5_InCity() throws Exception {
        Status status = new Status();
        status.allowedSpeed = 50;
        status.actualSpeed = 55;
        status.inCity = true;
        CostCalculator calc = new CostCalculator();
        Speeding speeding = calc.speeding(status);
        assertEquals(15, speeding.getFee());
        assertEquals(0, speeding.getPoints());
        assertEquals(0, speeding.getRevocation());
    }

    public void testSpeeding_43_OnHighway() throws Exception {
        Status status = new Status();
        status.allowedSpeed = 100;
        status.actualSpeed = 143;
        status.inCity = false;
        CostCalculator calc = new CostCalculator();
        Speeding speeding = calc.speeding(status);
        assertEquals(160, speeding.getFee());
        assertEquals(2, speeding.getPoints());
        assertEquals(1, speeding.getRevocation());
    }
}
