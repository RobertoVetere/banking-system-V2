package com.banksystem.banksystemappApp.models;

import java.util.concurrent.ThreadLocalRandom;

public class RandomClass {

    //This is a class where the random methods are
    public static String randomString() {
        // El banco de caracteres
        String dataBase = "1234567890";
        // La cadena en donde iremos agregando un carácter aleatorio
        String randomString = "";
        for (int x = 0; x < 4; x++) {
            int randomIndex = randomNumber(0, dataBase.length() - 1);
            char randomCharacter = dataBase.charAt(randomIndex);
            randomString += randomCharacter;
        }
        return randomString;
    }

    public static String randomAccountNumber() {

        String iban = "ES91";
        String numbers1 = randomString();
        String numbers2 = randomString();
        String numbers3 = randomString();
        String numbers4 = randomString();
        String esp = " ";

        String randomAccountNumber = iban + esp + numbers1 + esp + numbers2 + esp + numbers3 + esp + numbers4;

        return randomAccountNumber;
    }

    public static int randomNumber(int minimo, int maximo) {
        // nextInt retorna en rango pero con límite superior exclusivo, por eso sumamos 1
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }
}
