package com.filatov.calc.util;

/**
 * Класс-помощник для работы со строками
 */
public class StringHelper {
    public static String toCapital(String message){
        return message.substring(0, 1).toUpperCase() + message.substring(1);
    }
}
