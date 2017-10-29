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

    /**
     * Get the morse code version of an alphanumeric character.
     *
     * @param c The character to convert
     * @return The morse code string of the character
     */
    private static String getCharToMorse(char c) {
        String ret;
        switch (c) {
            case 'a':
            case 'A':
                ret = ".-";
                break;
            case 'b':
            case 'B':
                ret = "-...";
                break;
            case 'c':
            case 'C':
                ret = "-.-.";
                break;
            case 'd':
            case 'D':
                ret = "-..";
                break;
            case 'e':
            case 'E':
                ret = ".";
                break;
            case 'f':
            case 'F':
                ret = "..-.";
                break;
            case 'g':
            case 'G':
                ret = "--.";
                break;
            case 'h':
            case 'H':
                ret = "....";
                break;
            case 'i':
            case 'I':
                ret = "..";
                break;
            case 'j':
            case 'J':
                ret = ".---";
                break;
            case 'k':
            case 'K':
                ret = "-.-";
                break;
            case 'l':
            case 'L':
                ret = ".-..";
                break;
            case 'm':
            case 'M':
                ret = "--";
                break;
            case 'n':
            case 'N':
                ret = "-.";
                break;
            case 'o':
            case 'O':
                ret = "---";
                break;
            case 'p':
            case 'P':
                ret = ".--.";
                break;
            case 'q':
            case 'Q':
                ret = "--.-";
                break;
            case 'r':
            case 'R':
                ret = ".-.";
                break;
            case 's':
            case 'S':
                ret = "...";
                break;
            case 't':
            case 'T':
                ret = "-";
                break;
            case 'u':
            case 'U':
                ret = "..-";
                break;
            case 'v':
            case 'V':
                ret = "...-";
                break;
            case 'w':
            case 'W':
                ret = ".--";
                break;
            case 'x':
            case 'X':
                ret = "-..-";
                break;
            case 'y':
            case 'Y':
                ret = "-.--";
                break;
            case 'z':
            case 'Z':
                ret = "--..";
                break;
            case '0':
                ret = "-----";
                break;
            case '1':
                ret = ".----";
                break;
            case '2':
                ret = "..---";
                break;
            case '3':
                ret = "...--";
                break;
            case '4':
                ret = "....-";
                break;
            case '5':
                ret = ".....";
                break;
            case '6':
                ret = "-....";
                break;
            case '7':
                ret = "--...";
                break;
            case '8':
                ret = "---..";
                break;
            case '9':
                ret = "----.";
                break;
            case ' ':
                ret = "/";
                break;
            default:
                return "";
        }
        return ret;
    }

    /**
     * Get the morse code version of a string.
     *
     * @param input The string to convert
     * @return The morse code string of the input
     */
    public static String getStringToMorse(String input) {
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            output.append(getCharToMorse(c)).append(" ");
        }
        return output.toString();
    }
}