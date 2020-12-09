package main.java.days;

import java.util.ArrayList;

public class Day5 {
    static int day = 5;
    static char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        System.out.println("Day " + day + " star 1: " + starOne(data));
        System.out.println("Day " + day + " star 2: " + starTwo(data));
    }

    public static int starOne(ArrayList<String> data) {
        for (String s : data) {
            boolean c1 = stringHasThreeVowels(s);
            boolean c2 = stringHasTwoConsecutiveLetters(s);
            System.out.println(s + " : " + c1 + " and " + c2);
        }
        return 1;
    }

    public static int starTwo(ArrayList<String> data) {
        return 1;
    }

    public static boolean stringHasTwoConsecutiveLetters(String s) {
        char prevChar = s.charAt(0);
        for (char c : s.substring(1).toCharArray()) {
            if (c == prevChar) {
                return true;
            } else {
                prevChar = c;
            }
        }
        return false;
    }

    public static boolean stringHasThreeVowels(String s) {
        int counter = 0;
        for (char c : vowels) {
            counter = counter + countCharInString(s, c);
        }
        return counter >= 3;
    }

    public static int countCharInString(String s, char c) {
        int counter = 0;
        for (int index = s.indexOf(c);
             index >= 0;
             index = s.indexOf(c, index + 1)) {
            counter++;
        }
        return counter;
    }

}
