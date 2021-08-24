package handler;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName : handler.HighOrderExcel
 * @Description :
 * @Date 2021-08-24 16:18:29
 * @Author ZhangHL
 */
public class HighOrderExcel {

    private Excel excel;

    private XSSFWorkbook workbook;

    private OPCPackage opcPackage;


    public HighOrderExcel(Excel excel){
        try {
            this.excel = excel;
            opcPackage = OPCPackage.open(new File(excel.getFilePath()));
            workbook = new XSSFWorkbook(opcPackage);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getColomnsValueByTopRowName(String rowName){
        List<String> result = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet(excel.getSheetName());
        //获取第一行
        XSSFRow firstRow = sheet.getRow(0);
        Iterator<Cell> iterator = firstRow.iterator();
        while (iterator.hasNext()){
            XSSFCell cell = (XSSFCell) iterator.next();
            if(cell.getStringCellValue().equals(rowName)){
                int index = cell.getColumnIndex();
                //开始获取内容
                Iterator<Row> rows = sheet.iterator();
                while (rows.hasNext()){
                    XSSFRow row = (XSSFRow) rows.next();
                    //跳过第一排
                    if(row.getCell(index).getStringCellValue().equals(rowName)){
                        continue;
                    }
                    XSSFCell temp = row.getCell(index);
                    result.add(temp.getStringCellValue());
                }
            }
            return result;
        }
        return null;
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void close(){
        try {
            opcPackage.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
