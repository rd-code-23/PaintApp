package com.teambeta.sketcherapp.model;

import java.util.concurrent.ThreadLocalRandom;

/* Static class to hold useful functions */
public class GeneratorFunctions {

    /**
     * Return a random integer within the closed interval of min to max.
     *
     * @param min The minimum number
     * @param max The maximum number
     * @return The random number from min to max
     */
    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(max - min + 1) + min;
    }

    /**
     * Return a random double within the closed interval of min to max.
     *
     * @param min The minimum number
     * @param max The maximum number
     * @return The random number from min to max
     */
    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(max - min + 1.0) + min;
    }

}
