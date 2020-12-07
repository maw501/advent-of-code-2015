package main.java.days;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class Day4 {
    static int day = 4;
    static String data = "iwrupvqb";

    public static void main(String[] args) {
        //ArrayList<String> data = main.java.utils.ReadTextFile.readFile(day);
        System.out.println("Day " + day + " star 1: " + starOne(data));
        System.out.println("Day " + day + " star 2: " + starTwo(data));
    }

    public static int starOne(String inputString) {
        return findMD5Integer(inputString, 5);
    }

    public static int starTwo(String inputString) {
        return findMD5Integer(inputString, 6);
    }

    public static int findMD5Integer(String inputString, int length) {
        boolean foundHash = false;
        int i = 0;
        String hash = "";
        String sub = "";
        while (!foundHash) {
            String data = inputString + i;
            hash = getMD5Hash(data);
            sub = hash.substring(0, length);
            try {
                Integer parsedInt = Integer.parseInt(sub);
                if (parsedInt == 0) {
                    foundHash = true;
                    return i;
                }
            } catch (NumberFormatException e) {
            }
            i++;
        }
        return i;
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
