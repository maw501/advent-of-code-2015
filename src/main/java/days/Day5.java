package main.java.days;

import java.util.ArrayList;
import java.util.HashSet;

public class Day5 {
    static int day = 5;
    static char[] vowels = {'a', 'e', 'i', 'o', 'u'};

    public static void main(String[] args) {
        ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        System.out.println("Day " + day + " star 1: " + starOne(data));
        System.out.println("Day " + day + " star 2: " + starTwo(data));
    }

    public static int starOne(ArrayList<String> data) {
        int numberNiceStings = 0;
        for (String s : data) {
            boolean niceString = stringHasThreeVowels(s) &&
                    stringHasTwoConsecutiveLetters(s) &&
                    stringHasNoBannedSubstring(s);
            if (niceString) numberNiceStings++;
        }
        return numberNiceStings;
    }

    public static int starTwo(ArrayList<String> data) {
        int counter = 0;
        for (String s : data) {
            boolean c1 = stringHasTwoPairs(s, 0);
            boolean c2 = stringHasTwoPairs(s, 1);
            boolean c3 = stringLetterRepeatsByOne(s, 0);
            boolean c4 = stringLetterRepeatsByOne(s, 1);
            //System.out.println(s + " : " + c3 + " : " + c4);
            if ((c1 || c2) && (c3 || c4)) counter++;
            //System.out.println(s + " even: " + c1 + " odd: " + c2);
        }
        return counter;
    }

    public static boolean stringHasTwoPairs(String s, int startIndex) {
        HashSet<String> hash = new HashSet<>();
        hash.add(s.substring(startIndex, startIndex + 2));
        int stringLength = s.length();
        for (int i = startIndex + 2; i < stringLength - 1; i = i + 2) {
            String sub = s.substring(i, i + 2);
            if (hash.contains(sub)) {
//                System.out.println(
//                        s + " found: " + sub + " start: " + startIndex);
                return true;
            }
        }
        return false;
    }

    public static boolean stringLetterRepeatsByOne(String s, int startIndex) {
        int stringLength = s.length();
        char prevChar = s.charAt(0);
        for (int i = startIndex + 2; i < stringLength - 1; i = i + 2) {
            char currChar = s.charAt(i);
            if (prevChar == currChar) return true;
        }
        return false;
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

    public static boolean stringHasNoBannedSubstring(String s) {
        char prevChar = s.charAt(0);
        for (char currChar : s.substring(1).toCharArray()) {
            if (prevChar == 'a' && currChar == 'b') return false;
            if (prevChar == 'c' && currChar == 'd') return false;
            if (prevChar == 'p' && currChar == 'q') return false;
            if (prevChar == 'x' && currChar == 'y') return false;
            prevChar = currChar;
        }
        return true;
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
