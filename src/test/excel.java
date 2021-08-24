package test;

import com.sun.deploy.config.Config;
import config.Configer;
import handler.Excel;
import handler.HighOrderExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;

import java.util.List;

/**
 * @ClassName : test.excel
 * @Description :
 * @Date 2021-08-24 16:42:44
 * @Author ZhangHL
 */
public class excel {

    Configer configer = new Configer();

    @Test
    public void readTest1(){
        Excel excel = new Excel();
        excel.setFilePath("workbook.1.path");
        excel.setSheetName("workbook.1.sheet");
        excel.setTopRowName("workbook.1.toprow");
        HighOrderExcel highOrderExcel = new HighOrderExcel(excel);
        List<String> list = highOrderExcel.getColonmsValueByTopRowName(excel.getTopRowName());

        highOrderExcel.close();
        System.out.println(list.size());
    }

    @Test
    public void setTest1(){
        Excel excel = new Excel();
        excel.setFilePath("workbook.1.path");
        excel.setSheetName("workbook.1.sheet");
        excel.setTopRowName("workbook.1.toprow");
        HighOrderExcel highOrderExcel = new HighOrderExcel(excel);
        List<Cell> cells = highOrderExcel.getColonmsCellByTopRowName(excel.getTopRowName());
        System.out.println(cells.get(0).getStringCellValue());
        cells.get(0).setCellValue("124");
        //cells.get(0).getRow().createCell(cells.get(0).getColumnIndex()).setCellValue("111");
        System.out.println(cells.get(0).getStringCellValue());
        highOrderExcel.save(configer.readConfig("output.path"));
        highOrderExcel.close();
    }
}
