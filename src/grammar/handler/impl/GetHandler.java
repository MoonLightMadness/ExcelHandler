package grammar.handler.impl;

import grammar.handler.Handler;
import grammar.syntax.Analyser;
import handler.Excel;
import handler.HighOrderExcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName : grammar.handler.impl.GetHandler
 * @Description :
 * @Date 2021-08-25 08:19:25
 * @Author ZhangHL
 */
public class GetHandler implements Handler {


    private Analyser analyser;

    private Pattern pattern;

    private HighOrderExcel highOrderExcel;

    @Override
    public Map<String,List<String>> execute(String command, Analyser analyser) {
        this.analyser = analyser;
        highOrderExcel = getHighOrderExcel(command);
        List<String> properties = getProperties(command);
        Map<String,String> limits = getLimits(command);

        return null;
    }

    private HighOrderExcel getHighOrderExcel(String command){
        Excel excel = new Excel();
        String filePath = getFilePath(command);
        String sheetName = getSheetName(command);
        excel.setFilePath(filePath);
        excel.setSheetName(sheetName);
        return new HighOrderExcel(excel);
    }

    /**
     * 获取给定名字的顶部标题栏所在的列的索引
     * @param topRowName 顶部标题栏的名字
     * @return @return int
     * @author zhl
     * @date 2021-08-25 11:05
     * @version V1.0
     */
    private int getIndex(String topRowName){
        return highOrderExcel.getTopRowNameIndex(topRowName);
    }

    private Map<String,String> getLimits(String commad){
        pattern = Pattern.compile("LIMIT\\n((.|\\n)*?)END",Pattern.CASE_INSENSITIVE);
        Map<String,String> limits = new HashMap<>();
        Matcher matcher = pattern.matcher(commad);
        if(matcher.find()){
            String temp = matcher.group(1).trim();
            temp = temp.replaceAll("\\n","");
            for(String equition: temp.split(",")){
                String[] ss = equition.split("=");
                limits.put(ss[0].trim(),ss[1].trim());
            }
            return limits;
        }
        return null;
    }

    private List<String> getProperties(String command){
        pattern = Pattern.compile("(.*?)\\nLIMIT",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(command);
        List<String> results = new ArrayList<>();
        while (matcher.find()){
            results.add(matcher.group(1).trim());
        }
        return results;
    }
    
    private String getFilePath(String command){
        pattern = Pattern.compile("(.*?)\\.(.*?)\\n",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(command);
        String filePath = null;
        if(matcher.find()){
            filePath = matcher.group(1).split(" ")[1].trim();
        }
        return filePath;
    }
    
    private String getSheetName(String command){
        pattern = Pattern.compile("(.*?)\\.(.*?)\\n",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(command);
        String sheetName = null;
        if(matcher.find()){
            sheetName = matcher.group(1).split(" ")[2].trim();
        }
        return sheetName;
    }

    private String getVariableValue(String name){
        return analyser.getVariableMap().get(name);
    }


}
