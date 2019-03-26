package cz.kribsky;

import com.google.common.collect.Range;
import com.vendavo.interview.thermometer.Color;
import com.vendavo.interview.thermometer.Thermometer;

public class ThermometerImpl implements Thermometer {

    private enum ColorAssigner {

        // @formatter:off
        RED(Color.RED,                  Range.atLeast(40d)),
        ORANGE(Color.ORANGE,            Range.closedOpen(35d, 40d)),
        YELLOW(Color.YELLOW,            Range.closedOpen(25d, 35d)),
        GREEN(Color.GREEN,              Range.closedOpen(10d, 25d)),
        LIGHT_BLUE(Color.LIGHT_BLUE,    Range.closedOpen(5d, 10d)),
        BLUE(Color.BLUE,                Range.closedOpen(-10d, 5d)),
        DARK_BLUE(Color.DARK_BLUE,      Range.lessThan(-10d));
        // @formatter:on

        final Color color;
        final Range<Double> range;

        ColorAssigner(Color color, Range<Double> range) {
            this.color = color;
            this.range = range;
        }
    }

    /**
     * Cases:
     * <p>
     * over 40> ... RED
     * <35 - 40) ... ORANGE
     * <25 - 35) ... YELLOW
     * <10 - 25) ... GREEN
     * <5 - 10) ... LIGHT_BLUE
     * <-10 - 5) ... BLUE
     * below -10) ... DARK_BLUE
     *
     * @param temperature current temperature
     * @return color according to given temperature
     */
    @Override
    public Color measure(double temperature) throws IllegalAccessException {
        for (ColorAssigner value : ColorAssigner.values()) {
            if (value.range.contains(temperature)) {
                return value.color;
            }
        }

        return Color.UNKNOWN;
    }
}
