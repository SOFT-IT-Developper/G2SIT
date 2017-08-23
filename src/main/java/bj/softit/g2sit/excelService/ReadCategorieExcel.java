package bj.softit.g2sit.excelService;

import bj.softit.g2sit.domain.Categorie;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.weaver.ast.Not;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ReadCategorieExcel {
    private Categorie categorie;
    public ReadCategorieExcel(Categorie categorie){
        this.categorie = categorie;
    }

    public ReadCategorieExcel() {

    }

    List<Categorie> categorieList(){
//        HSSFWorkbook workbook = new HSSFWorkbook(excelfile.getInputStream());
//        XSSFWorkbook workbook = new XSSFWorkbook(excelfile.getInputStream());
        return null;
    }
  public void  readTest(){
        String fileName = "C:\\WORKSPACE/gt.xlsx";
        File file = new File(fileName);

        FileInputStream fileInputStream;
        Workbook workbook = null;
        Sheet sheet;
        Iterator<Row> rowIterator;
        try {
            fileInputStream = new FileInputStream(file);
            String fileExtension = fileName.substring(fileName.indexOf("."));
            System.out.println(fileExtension);
            if(fileExtension.equals(".xls")){
                workbook  = new HSSFWorkbook(new POIFSFileSystem(fileInputStream));
            }
            else if(fileExtension.equals(".xlsx")){
                workbook  = new XSSFWorkbook(fileInputStream);
            }
            else {
                System.out.println("Wrong File Type");
            }
            FormulaEvaluator evaluator=workbook.getCreationHelper().createFormulaEvaluator();
            sheet = workbook.getSheetAt(1);
            rowIterator = sheet.iterator();
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    //Check the cell type after evaluating formulae
                    //If it is formula cell, it will be evaluated otherwise no change will happen
                    switch (evaluator.evaluateInCell(cell).getCellType()){
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + " ");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + " ");
                            break;
                        case Cell.CELL_TYPE_FORMULA:
//                            Not again
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            break;
                    }
                }
                System.out.println("\n");
            }
System.out.println(sheet);
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   /* public static void main(String[] args){
        new ReadCategorieExcel().readTest();
    }*/
}
