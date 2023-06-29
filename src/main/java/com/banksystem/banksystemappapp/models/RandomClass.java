package com.banksystem.banksystemappapp.models;

import java.util.concurrent.ThreadLocalRandom;

public class RandomClass {

    // This is a class where the random methods are

    /**
     * Generates a random string of 4 digits.
     *
     * @return The randomly generated string.
     */
    public static String randomString() {
        String dataBase = "1234567890";
        StringBuilder randomString = new StringBuilder();
        for (int x = 0; x < 4; x++) {
            int randomIndex = randomNumber(0, dataBase.length() - 1);
            char randomCharacter = dataBase.charAt(randomIndex);
            randomString.append(randomCharacter);
        }
        return randomString.toString();
    }

    /**
     * Generates a random account number in the format "ES91 XXXX XXXX XXXX XXXX".
     *
     * @return The randomly generated account number.
     */
    public static String randomAccountNumber() {
        String iban = "ES91";
        String esp = " ";
        String randomAccountNumber = iban + esp + randomString() + esp + randomString() + esp + randomString() + esp + randomString();
        return randomAccountNumber;
    }

    /**
     * Generates a random number within the specified range.
     *
     * @param lowerBound The lower bound of the range.
     * @param upperBound The upper bound of the range.
     * @return The randomly generated number.
     * @throws IllegalArgumentException if the lower bound is greater than the upper bound.
     */
    public static int randomNumber(int lowerBound, int upperBound) {
        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("Lower bound cannot be greater than upper bound.");
        }
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1);
    }
}