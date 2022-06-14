import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ReadExel {
    public static void main(String[] args) throws IOException {
        File exelFile = new File("EXPENSES.xlsx");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(exelFile);
        } catch (FileNotFoundException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        // we create xssf workbook obj for xlsx file
        HSSFWorkbook workbook = new HSSFWorkbook(fis);
        // we get first sheet

        HSSFSheet sheet = workbook.getSheetAt(Integer.parseInt("0"));

        // iterate on rows

        Iterator<Row> rowIt = sheet.iterator();

        while(rowIt.hasNext()){
            Row row = rowIt.next();

            // iterate on cells for the current row

            Iterator<Cell> cellIt = row.cellIterator();

            while (cellIt.hasNext()){
                Cell cell = cellIt.next();
                System.out.println(cell.toString()+";");
            }
            System.out.println();
        }
        workbook.close();
        fis.close();
    }
}
