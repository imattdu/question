package com.matt.project.question;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author matt
 * @create 2021-01-11 21:15
 */
public class SouceTest {

    public static void main(String[] args) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher("name");
        boolean isMatched = matcher.matches();
        System.out.println(isMatched);
    }
}
