package test;

import handler.Excel;
import handler.HighOrderExcel;
import org.junit.Test;

import java.util.List;

/**
 * @ClassName : test.excel
 * @Description :
 * @Date 2021-08-24 16:42:44
 * @Author ZhangHL
 */
public class excel {

    @Test
    public void readTest1(){
        Excel excel = new Excel();
        excel.setFilePath("workbook.1.path");
        excel.setSheetName("workbook.1.sheet");
        excel.setTopRowName("workbook.1.toprow");
        HighOrderExcel highOrderExcel = new HighOrderExcel(excel);
        List<String> list = highOrderExcel.getColomnsValueByTopRowName(excel.getTopRowName());

        highOrderExcel.close();
        System.out.println(list.size());
    }
}
