package dk.easv.drawing.dal;

import dk.easv.drawing.be.Shapes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Saving {
    private final String PATH = "./data/";


    public boolean createFile(String fileName) {
        File myDir = new File(PATH);
        File myFile = new File(PATH + fileName + ".sav");
        if (myDir.mkdir()) {
            System.out.println(PATH + " Directory created");
        }

        if (myFile.exists()) {
            System.out.println("File already exists");
            return false;
        }
        else {
            try {
                if (myFile.createNewFile()) {
                    System.out.println(fileName + ".sav File has been created");
                    return true;
                }
                else
                    return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public boolean deleteFile(String fileName) {
        File myDir = new File(PATH);
        File myFile = new File(PATH + fileName + ".sav");
        if (myDir.exists()) {
            if (myFile.delete()) {
                System.out.println(fileName + ".sav File has been deleted");
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public boolean addToFile(String filename, List<Shapes> shapes) {
        File myFile = new File(PATH + filename + ".sav");
        if (myFile.exists()) {
            FileWriter fw = null;
            try {
                fw = new FileWriter(myFile.getPath(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                for (Shapes s: shapes) {
                    fw.write(s.toString() + "\n");
                }
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public List<Shapes> readFile(String fileName) {
        List<Shapes> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH + fileName + ".sav"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //list.add(line);
                String[] parts = line.split(" ");
                if (parts.length == 4) {
                    list.add(new Shapes(Integer.parseInt(parts[3]), parts[0], Integer.parseInt(parts[1]), false, parts[2]));
                }
                list.add(new Shapes(Integer.parseInt(parts[4]), parts[0], Integer.parseInt(parts[1]), true, parts[2]));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean checkFileIfExists(String fileName) {
        File myFile = new File(PATH + fileName + ".sav");
        return myFile.exists();
    }
}
