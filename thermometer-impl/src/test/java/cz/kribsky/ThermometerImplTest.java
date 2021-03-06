package cz.kribsky;

import com.vendavo.interview.thermometer.Color;
import com.vendavo.interview.thermometer.Thermometer;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.Predicate;

class ThermometerImplTest {


    private Random random;
    private Thermometer thermometer;

    @BeforeEach
    void before() {
        random = new Random(12345);
        thermometer = new ThermometerImpl();
    }

    @Test
    void shouldReturnSomething() throws IllegalAccessException {
        double temperature = random.nextDouble();
        Assert.assertNotNull("Returned null value for: " + temperature, thermometer.measure(temperature));
    }

    @Test
    void shouldMeasureRedCorrectly() throws IllegalAccessException {
        final double inclusiveLimit = 40d;
        sequenceTest(temperature -> temperature >= inclusiveLimit, Color.RED);
        Assert.assertEquals(thermometer.measure(inclusiveLimit), Color.RED);
    }

    @Test
    void shouldMeasureOrangeCorrectly() throws IllegalAccessException {
        double inclusiveLowerLimit = 35d;
        sequenceTest(inclusiveLowerLimit, 40d, Color.ORANGE);
        Assert.assertEquals(thermometer.measure(inclusiveLowerLimit), Color.ORANGE);
    }

    @Test
    void shouldMeasureYellowCorrectly() throws IllegalAccessException {
        double inclusiveLowerLimit = 25d;
        sequenceTest(inclusiveLowerLimit, 35d, Color.YELLOW);
        Assert.assertEquals(thermometer.measure(inclusiveLowerLimit), Color.YELLOW);
    }

    @Test
    void shouldMeasureGreenCorrectly() throws IllegalAccessException {
        double inclusiveLowerLimit = 10d;
        sequenceTest(inclusiveLowerLimit, 25d, Color.GREEN);
        Assert.assertEquals(thermometer.measure(inclusiveLowerLimit), Color.GREEN);
    }

    @Test
    void shouldMeasureLightBlueCorrectly() throws IllegalAccessException {
        double inclusiveLowerLimit = 5d;
        sequenceTest(inclusiveLowerLimit, 10d, Color.LIGHT_BLUE);
        Assert.assertEquals(thermometer.measure(inclusiveLowerLimit), Color.LIGHT_BLUE);
    }

    @Test
    void shouldMeasureBlueCorrectly() throws IllegalAccessException {
        double inclusiveLowerLimit = -10d;
        sequenceTest(inclusiveLowerLimit, 5d, Color.BLUE);
        Assert.assertEquals(thermometer.measure(inclusiveLowerLimit), Color.BLUE);
    }

    @Test
    void shouldMeasureDarkBlueCorrectly() {
        sequenceTest(temperature -> temperature < -10d, Color.DARK_BLUE);
    }

    @Test
    void shouldFail() {
        try {
            sequenceTest(aDouble -> aDouble < 5, Color.LIGHT_BLUE);
            Assert.fail("Should messure wrongly!");
        } catch (AssertionError e) {

        }
    }

    private void sequenceTest(double inclusiveLowerLimit, double exclusiveUpperLimit, Color expectedColor) {
        sequenceTest(aDouble -> aDouble >= inclusiveLowerLimit && aDouble < exclusiveUpperLimit, expectedColor);
    }

    private void sequenceTest(Predicate<Double> temperatureRangePredicate, Color expectedColor) {
        System.out.println("running for collor" + expectedColor);
        random.doubles(1_000_000, -40, 50)
                .forEach(randomDouble -> {
                            try {
                                Color measure = thermometer.measure(randomDouble);
                                if (measure == expectedColor) {
                                    Assert.assertTrue(
                                            "Is not a valid Color expecting: " + expectedColor +
                                                    " but got: " + measure + " for temperature: " + randomDouble,
                                            temperatureRangePredicate.test(randomDouble)
                                    );
                                }
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }

}