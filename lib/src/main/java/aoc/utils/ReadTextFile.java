package aoc.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class ReadTextFile {
    public static ArrayList<String> readFile(int day) {
        ArrayList<String> res = new ArrayList<>();
        try {
            File myObj = new File(
                    "src/main/resources/day" + day + ".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                res.add(line);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred, could not find file.");
            e.printStackTrace();
        }
        return res;
    }
}
