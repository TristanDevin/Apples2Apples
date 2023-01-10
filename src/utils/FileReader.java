package utils;

import java.io.*;
import java.util.*;

//This class is used to read the input file and return an arraylist of strings

public class FileReader {

    public FileReader() {
    }

    public ArrayList<String> scan(String filepath) {
        ArrayList<String> text = new ArrayList<String>();

        try {
            File file = new File(filepath);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                text.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found, please enter a valid file name");
            e.printStackTrace();
        }
        return text;
    }

}
