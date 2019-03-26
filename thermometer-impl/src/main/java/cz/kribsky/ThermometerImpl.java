package cz.kribsky;

import com.google.common.collect.Range;
import com.vendavo.interview.thermometer.Color;
import com.vendavo.interview.thermometer.Thermometer;

import java.util.Collections;
import java.util.Map;

public class ThermometerImpl implements Thermometer {


    private static final Map<Color, Range<Double>> COLOR_RANGE_MAP = Collections.unmodifiableMap(
            Map.of(
                    // @formatter:off
                    Color.RED,          Range.atLeast(40d),
                    Color.ORANGE,       Range.closedOpen(35d, 40d),
                    Color.YELLOW,       Range.closedOpen(25d, 35d),
                    Color.GREEN,        Range.closedOpen(10d, 25d),
                    Color.LIGHT_BLUE,   Range.closedOpen(5d, 10d),
                    Color.BLUE,         Range.closedOpen(-10d, 5d),
                    Color.DARK_BLUE,    Range.lessThan(-10d)
                    // @formatter:on
            )
    );

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
        for (Map.Entry<Color, Range<Double>> colorRangeEntry : COLOR_RANGE_MAP.entrySet()) {
            if(colorRangeEntry.getValue().contains(temperature)){
                return colorRangeEntry.getKey();
            }
        }

        return Color.UNKNOWN;
    }
}
