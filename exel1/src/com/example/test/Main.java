package com.example.test;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement = null;
        connection = null;
        String dbName = "Students.db";
        String excelFilePath = "\"C:\\Users\\Ishita Sinha\\Desktop\\Students .xlsx\"";
        String url = "jdbc:sqlite:" + "C:\\Users\\Ishita Sinha\\Desktop\\exel1"+ dbName + ".db";

// Create a connection to the database
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

// Opening Worksheet
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(excelFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);  // first sheet

// Generate maximum number of column in sheet
        int max=0;
        Iterator<Row> rowIterator = firstSheet.iterator();
        while (rowIterator.hasNext()) {
            Row nextRow = rowIterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            int totalNoOfRows = firstSheet.getLastRowNum(); // To get the number of rows present in sheet
            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int col1 = nextCell.getColumnIndex();
                for (int row = 1; row <= totalNoOfRows; row++) {
                    if (col1 >= max)
                        max = col1;
                }
            }
        }

// creating schema
        ArrayList sch = new ArrayList();
        ArrayList val = new ArrayList();
        for (int first = 1; first <= max + 1; first++) {
            sch.add("'" + first + "'");
            val.add("?");
        }
        String schema = sch.toString().replace("[", "(").replace("]", ")").trim();
        String values = val.toString().replace("[", "(").replace("]", ")").trim();

// execute queries using create statement
        String table = "CREATE TABLE " + dbName + schema + ";";
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.executeUpdate(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String delete = "DELETE FROM " + dbName + ";";
        stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.executeUpdate(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
// execute query using prepare statement
        String insert = "INSERT INTO " + dbName + schema + "VALUES" + values + ";";
        try {
            statement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        }

// Read excel data
        rowIterator = firstSheet.iterator();
        while (rowIterator.hasNext()) {
            int cellCount = 0;
            Row nextRow = rowIterator.next();
            ArrayList<String> data = new ArrayList<String>();
            data.clear();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int col = nextCell.getColumnIndex();
                if (nextCell.getCellType() == CellType.STRING) {
                    data.add(cellCount, nextCell.getStringCellValue());
                }
                else if (nextCell.getCellType() == CellType.NUMERIC) {
                    data.add(cellCount, NumberToTextConverter.toText(nextCell.getNumericCellValue()));
                }
                try {
                    statement.setString(col + 1, data.get(cellCount).toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                cellCount += 1;
            }

// Import data in batch using prepared statement methods
            try {
                statement.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.clearBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
// Closing workbook and database connection
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
