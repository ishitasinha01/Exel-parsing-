package com.example.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class file {
    //code to create a new file
    public static void main(String[] args) {
        File myfile = new File("file.txt"); // this line also used in readig and deleting
        try {
            myfile.createNewFile();
        } catch (IOException e) {
            System.out.println("unable to create this file");
            e.printStackTrace();
        }

        // code to write in a file
        try{
            FileWriter fileWriter = new FileWriter("file.txt");
            fileWriter.write("this is our first file from the java ");
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        // reading  a file
        try {
            Scanner sc = new Scanner(myfile);
            while (sc.hasNextLine()){
               String line = sc.nextLine();
                System.out.println(line);
            }
            sc.close(); // closing a file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // deleting a file
        if(myfile.delete()){
            System.out.println("i have deleted"+myfile.getName());
        }else{
            System.out.println("some error");
        }



    }
}
