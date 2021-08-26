package grammar.handler.utils;

import grammar.syntax.Analyser;
import handler.Excel;
import handler.HighOrderExcel;
import org.apache.poi.ss.usermodel.Cell;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName : grammar.handler.utils.CommandParseUtil
 * @Description :
 * @Date 2021-08-26 08:38:28
 * @Author ZhangHL
 */
public class CommandParseUtil {

    private static final Pattern limitPattern = Pattern.compile("LIMIT\\n((.|\\n)*?)END", Pattern.CASE_INSENSITIVE);

    private static final Pattern propertyPattern = Pattern.compile(".*?[setgdl].*?\\n((.|\\n)*?)LIMIT", Pattern.CASE_INSENSITIVE);

    private static final Pattern filePathPattern = Pattern.compile("(.*?)\\.(.*?)\\n", Pattern.CASE_INSENSITIVE);

    private static final Pattern sheetNamePattern = Pattern.compile("(.*?)\\.(.*?)\\n", Pattern.CASE_INSENSITIVE);

    public static Map<String, String> getLimits(String commad) {
        Map<String, String> limits = new HashMap<>();
        Matcher matcher = limitPattern.matcher(commad);
        if (matcher.find()) {
            String temp = matcher.group(1).trim();
            temp = temp.replaceAll("\\n", "");
            for (String equition : temp.split(",")) {
                String[] ss = equition.split("=");
                limits.put(ss[0].trim(), ss[1].trim());
            }
            return limits;
        }
        return null;
    }

    public static List<String> getProperties(String command) {
        Matcher matcher = propertyPattern.matcher(command);
        List<String> results = new ArrayList<>();
        while (matcher.find()) {
            String str = matcher.group(1).trim();
            results.addAll(Arrays.asList(str.split(",")));
        }
        return results;
    }

    public static String getFilePath(String command) {
        Matcher matcher = filePathPattern.matcher(command);
        String filePath = null;
        if (matcher.find()) {
            filePath = matcher.group(1).split(" ")[1].trim() + ".xlsx";
        }
        return filePath;
    }

    public static String getSheetName(String command) {
        Matcher matcher = sheetNamePattern.matcher(command);
        String sheetName = null;
        if (matcher.find()) {
            sheetName = matcher.group(2).trim();
        }
        return sheetName;
    }

    public static String getVariableValue(String name, Analyser analyser) {
        return analyser.getVariableMap().get(name);
    }

    /**
     * 重建结果集，保留其Cell所在Row的与其Limit值相等的值
     * @param source         源列表
     * @param limitName
     * @param limitValue     limit的值
     * @param highOrderExcel
     * @return @return {@link List<Cell> }
     * @author zhl
     * @date 2021-08-26 10:12
     * @version V1.0
     */
    public static List<Cell> limitEqualIn(List<Cell> source, String limitName,
                                          String limitValue, HighOrderExcel highOrderExcel) {
        List<Cell> results = new ArrayList<>();
        int index = highOrderExcel.getTopRowNameIndex(limitName);
        for (Cell cell : source) {
            if (cell.getRow().getCell(index).getStringCellValue().equals(limitValue)) {
                results.add(cell);
            }
        }
        return results;
    }

    public static List<Cell> limitEqualIn(List<Cell> source,String limitName,List<String> limits,
                                          HighOrderExcel highOrderExcel){
        List<Cell> results = new ArrayList<>();
        int index = highOrderExcel.getTopRowNameIndex(limitName);
        for(Cell cell : source){
            for (String limit : limits){
                if (cell.getRow().getCell(index).getStringCellValue().equals(limit)) {
                    results.add(cell);
                    break;
                }
            }
        }
        return results;
    }

    /**
     * 重建结果集，保留其Cell所在Row的与其Limit值不相等的值
     * @param source         原列表
     * @param limitName
     * @param limitValue     limit的值
     * @param highOrderExcel
     * @return @return {@link List<Cell> }
     * @author zhl
     * @date 2021-08-26 10:13
     * @version V1.0
     */
    public static List<Cell> limitNotEqualIn(List<Cell> source, String limitName,
                                             String limitValue, HighOrderExcel highOrderExcel) {
        List<Cell> results = new ArrayList<>();
        int index = highOrderExcel.getTopRowNameIndex(limitName);
        for (Cell cell : source) {
            if (!cell.getRow().getCell(index).getStringCellValue().equals(limitValue)) {
                results.add(cell);
            }
        }
        return results;
    }

    public static HighOrderExcel getHighOrderExcel(String command){
        Excel excel = new Excel();
        String filePath = CommandParseUtil.getFilePath(command);
        String sheetName = CommandParseUtil.getSheetName(command);
        excel.setFilePath(filePath);
        excel.setSheetName(sheetName);
        return new HighOrderExcel(excel);
    }
}
