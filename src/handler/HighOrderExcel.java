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

    /**
     * 通过给定的标题行名称，获取该列（不包括标题）所有的{@link Cell}的值
     * @param rowName 行名称
     * @return @return {@link List<String> }
     * @author zhl
     * @date 2021-08-24 16:53
     * @version V1.0
     */
    public List<String> getColonmsValueByTopRowName(String rowName){
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

    /**
     * 通过给定的标题行名称，获取该列（不包括标题）所有的{@link Cell}
     * @param rowName 行名称
     * @return @return {@link List<Cell> }
     * @author zhl
     * @date 2021-08-24 16:52
     * @version V1.0
     */
    public List<Cell> getColonmsCellByTopRowName(String rowName){
        List<Cell> result = new ArrayList<>();
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
                    result.add(temp);
                }
            }
            return result;
        }
        return null;
    }

    /**
     * 获取标题行所在的列数
     * @param rowName 行名称
     * @return @return int
     * @author zhl
     * @date 2021-08-24 16:51
     * @version V1.0
     */
    public int getTopRowNameIndex(String rowName) {
        XSSFSheet sheet = workbook.getSheet(excel.getSheetName());
        int index = 0;
        //获取第一行
        XSSFRow firstRow = sheet.getRow(0);
        Iterator<Cell> iterator = firstRow.iterator();
        while (iterator.hasNext()) {
            XSSFCell cell = (XSSFCell) iterator.next();
            if (cell.getStringCellValue().equals(rowName)) {
                index = cell.getColumnIndex();
            }
            break;
        }
        return index;
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
