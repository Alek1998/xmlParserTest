package com.company.utils;

/**
 * Created by alek.aleksanyan on 1/24/2017.
 */
public class FilterUtils {

    public static boolean numberChecked(Integer currentNumber, Integer searchNumber, boolean small, boolean equal, boolean big) {

        return (small && currentNumber < searchNumber || equal && currentNumber == searchNumber || big && currentNumber > searchNumber);
    }

    public static boolean stringChecked(String currentString, String searchString) {

        return currentString.replaceAll("^\\s+", "").replaceAll("\\s+$", "").toLowerCase().contains(
                searchString.replaceAll("^\\s+", "").replaceAll("\\s+$", "").toLowerCase());
    }
}
