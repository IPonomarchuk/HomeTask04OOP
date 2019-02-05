package com.aqacourses.test.student;

import com.aqacourses.test.interfaces.ParseFileInterface;
import com.aqacourses.test.interfaces.WriteToDbInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class StudentPedin extends Student implements WriteToDbInterface, ParseFileInterface {

    private File file;
    private BufferedWriter bufferedWriter;

    /**
     * Parse file to ArrayList
     *
     * @param pathToFile
     * @return ArrayList with student data
     */
    @Override
    public ArrayList parseFile(String pathToFile) {

        Scanner s = null;
        try {
            s = new Scanner(new File(pathToFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> data = new ArrayList<>();
        while (s.hasNext()) {
            data.add(s.next());
        }
        s.close();

        return data;
    }

    /**
     * Write student data to MS SQL DB
     *
     * @param data
     */
    @Override
    public void writeToDb(List<String> data) {

        try {
            openConnection();
            if (super.validateData(data)) {
                for (String datum : data) {
                    bufferedWriter.write(getDate() + " - " + datum);
                    bufferedWriter.newLine();
                }
                bufferedWriter.write("==================\n");
                System.out.println("All data is written to MS SQL DB");
                closeConnection();
            }
        } catch (IOException e) {
            System.err.println("ERROR!!!");
            e.printStackTrace();
        }

    }

    /**
     * Open connection to MS SQL DB
     */
    private void openConnection() throws IOException {
        String path = "D:/MSSQL-DB.txt";
        file = new File(path);
        bufferedWriter = new BufferedWriter(new FileWriter(file, true));
    }

    /**
     * Close connection to MS SQL DB
     */
    private void closeConnection() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("Cannot close connection to MS SQL DB");
            e.printStackTrace();
        }
        System.out.println("Close connection to MS SQL DB");
    }

}
