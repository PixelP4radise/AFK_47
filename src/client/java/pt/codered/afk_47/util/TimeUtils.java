package pt.codered.afk_47.util;

import java.util.Random;

public class TimeUtils {

    private static final Random random = new Random();

    // Private constructor to prevent instantiation of this Utility class
    private TimeUtils() {
    }

    /**
     * Returns a random integer between min and max (inclusive).
     * Good for Casting delays.
     */
    public static int getRandomDelay(int minTicks, int maxTicks) {
        if (minTicks >= maxTicks) {
            return minTicks;
        }
        // nextInt bound is exclusive, so we add 1 to make maxTicks inclusive
        return random.nextInt((maxTicks - minTicks) + 1) + minTicks;
    }

    /**
     * Returns a "Human-like" reaction time using a Bell Curve.
     *
     * @param averageTicks   The ideal reaction time (e.g. 4 ticks = 200ms)
     * @param deviationTicks How much it varies (e.g. 1.5 ticks)
     */
    public static int getHumanReaction(double averageTicks, double deviationTicks) {
        // nextGaussian() returns a number with mean 0.0 and deviation 1.0
        double gaussian = random.nextGaussian();

        // Scale it to our desired average and deviation
        long result = Math.round(averageTicks + (gaussian * deviationTicks));

        // Ensure we never return 0 or negative numbers (instant reactions are impossible)
        // Math.max ensures the minimum is 2
        return (int) Math.max(2, result);
    }
}
