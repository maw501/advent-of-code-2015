package main.java.days;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class Day4 {
    static int day = 4;
    static String data = "iwrupvqb";

    public static void main(String[] args) {
        System.out.println("Day " + day + " star 1: " + starOne(data));
        System.out.println("Day " + day + " star 2: " + starTwo(data));
    }

    public static int starOne(String inputString) {
        return findMD5IntegerSuffix(inputString, 5);
    }

    public static int starTwo(String inputString) {
        return findMD5IntegerSuffix(inputString, 6);
    }

    public static int findMD5IntegerSuffix(String inputString, int length) {
        boolean foundHash = false;
        int i = 0;
        while (!foundHash) {
            String hash = getMD5Hash(inputString + i);
            String sub = hash.substring(0, length);
            if (subStringToInt(sub, length) == 0) {
                foundHash = true;
                return i;
            }
            i++;
        }
        return i;
    }

    public static int subStringToInt(String inputString, int stringLength) {
        String sub = inputString.substring(0, stringLength);
        try {
            Integer parsedInt = Integer.parseInt(sub);
            return parsedInt;
        } catch (
                NumberFormatException e) {
            return 99;
        }
    }

    public static String getMD5Hash(String hashInput) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(hashInput.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
