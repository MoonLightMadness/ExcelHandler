package handler;

import log.LogSystem;
import log.LogSystemFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
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

    private LogSystem log = LogSystemFactory.getLogSystem();

    public HighOrderExcel(Excel excel) {
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
     *
     * @param rowName 行名称
     * @return @return {@link List<String> }
     * @author zhl
     * @date 2021-08-24 16:53
     * @version V1.0
     */
    public List<String> getColonmsValueByTopRowName(String rowName) {
        List<String> result = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet(excel.getSheetName());
        //获取第一行
        XSSFRow firstRow = sheet.getRow(0);
        Iterator<Cell> iterator = firstRow.iterator();
        while (iterator.hasNext()) {
            XSSFCell cell = (XSSFCell) iterator.next();
            if (cell.getStringCellValue().equals(rowName)) {
                int index = cell.getColumnIndex();
                //开始获取内容
                Iterator<Row> rows = sheet.iterator();
                while (rows.hasNext()) {
                    XSSFRow row = (XSSFRow) rows.next();
                    //跳过第一排
                    if (row.getCell(index).getStringCellValue().equals(rowName)) {
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
     *
     * @param rowName 行名称
     * @return @return {@link List<Cell> }
     * @author zhl
     * @date 2021-08-24 16:52
     * @version V1.0
     */
    public List<Cell> getColonmsCellByTopRowName(String rowName) {
        List<Cell> result = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet(excel.getSheetName());
        //获取第一行
        XSSFRow firstRow = sheet.getRow(0);
        Iterator<Cell> iterator = firstRow.iterator();
        while (iterator.hasNext()) {
            XSSFCell cell = (XSSFCell) iterator.next();
            if (cell.getStringCellValue().equals(rowName)) {
                int index = cell.getColumnIndex();
                //开始获取内容
                Iterator<Row> rows = sheet.iterator();
                while (rows.hasNext()) {
                    XSSFRow row = (XSSFRow) rows.next();
                    if (null != row) {
                        //跳过第一排
                        XSSFCell tempCell = row.getCell(index);
                        if (tempCell != null) {
                            if (row.getCell(index).getStringCellValue().equals(rowName)) {
                                continue;
                            }
                            result.add(tempCell);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取标题行所在的列数
     *
     * @param rowName 行名称
     * @return @return int
     * @author zhl
     * @date 2021-08-24 16:51
     * @version V1.0
     */
    public int getTopRowNameIndex(String rowName) {
        XSSFSheet sheet = workbook.getSheet(excel.getSheetName());
        if (sheet != null) {
            int index = 0;
            //获取第一行
            XSSFRow firstRow = sheet.getRow(0);
            Iterator<Cell> iterator = firstRow.iterator();
            while (iterator.hasNext()) {
                XSSFCell cell = (XSSFCell) iterator.next();
                if (cell.getStringCellValue().equals(rowName)) {
                    index = cell.getColumnIndex();
                    break;
                }
            }
            return index;
        } else {
            System.out.println("sheet为空----" + excel.getSheetName());
        }
        return 0;
    }

    public void setCellValue(Cell cell, String value) {
        cell.setCellValue(value);
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    /**
     * 获得标题排的众标题的值
     *
     * @return @return {@link List<String> }
     * @author zhl
     * @date 2021-08-24 20:59
     * @version V1.0
     */
    public List<String> getTopRowsValues() {
        List<String> result = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet(excel.getSheetName());
        Row row = sheet.getRow(0);
        Iterator<Cell> iterator = row.cellIterator();
        while (iterator.hasNext()) {
            Cell cell = iterator.next();
            result.add(cell.getStringCellValue());
        }
        return result;
    }

    /**
     * 获得标题排的众标题
     *
     * @return @return {@link List<Cell> }
     * @author zhl
     * @date 2021-08-24 21:00
     * @version V1.0
     */
    public List<Cell> getTopRows() {
        List<Cell> result = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet(excel.getSheetName());
        Row row = sheet.getRow(0);
        Iterator<Cell> iterator = row.cellIterator();
        while (iterator.hasNext()) {
            Cell cell = iterator.next();
            result.add(cell);
        }
        return result;
    }

    /**
     * 注意不能写入原本文件，否则会报错并清空原本文件数据 <br/>
     * 但实际上原本文件也会进行更改
     *
     * @param path 路径
     * @return
     * @author zhl
     * @date 2021-08-24 18:30
     * @version V1.0
     */
    public void save(String path) {
        //路径检测
        if (path.equals(excel.getFilePath())) {
            log.error("不能与原本路径相同");
            return;
        }
        try {
            OutputStream os = new FileOutputStream(path);
            workbook.write(os);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            opcPackage.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
