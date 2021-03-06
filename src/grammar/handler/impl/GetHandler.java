package grammar.handler.impl;

import grammar.handler.Handler;
import grammar.handler.utils.CommandParseUtil;
import grammar.syntax.Analyser;
import grammar.syntax.Executer;
import handler.Excel;
import handler.HighOrderExcel;
import org.apache.poi.ss.usermodel.Cell;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName : grammar.handler.impl.GetHandler
 * @Description :
 * @Date 2021-08-25 08:19:25
 * @Author ZhangHL
 */
public class GetHandler implements Handler {

    private HighOrderExcel highOrderExcel;

    private Executer executer;

    List<String> properties;

    Map<String, String> limits;

    Map<String, Integer> indexBackUp;

    @Override
    public Map<String, List<String>> execute(String command, Analyser analyser, Executer executer) {
        this.executer = executer;
        highOrderExcel = CommandParseUtil.getHighOrderExcel(command);
        properties = CommandParseUtil.getProperties(command);
        limits = CommandParseUtil.getLimits(command);
        return get();
    }

    private Map<String, List<String>> get() {
        Map<String, List<String>> result = new HashMap<>();
        for (String property : properties) {
            List<Cell> temp = highOrderExcel.getColonmsCellByTopRowName(property);
            if (limits != null) {
                for (String limit : limits.keySet()) {
                    temp = removeLimits(temp, limit);
                }
            }
            List<String> values = new ArrayList<>();
            for (Cell cell : temp) {
                values.add(cell.getStringCellValue());
            }
            result.put(property, values);
        }
        highOrderExcel.close();
        return result;
    }

    private List<Cell> removeLimits(List<Cell> origin, String limit) {
        List<Cell> newer = new ArrayList<>();
        String value = limits.get(limit);
        //函数调用
        if (value.toUpperCase(Locale.ROOT).startsWith("EXEC(") &&
                value.toUpperCase(Locale.ROOT).endsWith(")")) {
            //函数调用
            List<String> funcValue = functionCall(value);
            newer = CommandParseUtil.limitEqualIn(origin, limit, funcValue, highOrderExcel);
        } else {
            newer = CommandParseUtil.limitEqualIn(origin, limit, value, highOrderExcel);
        }

        return newer;
    }

    private List<String> functionCall(String command) {
        command = command.substring(5,command.length()-1);
        return executer.getFuncResults().get(command);
    }

    private void initializeIndexs() {
        for (String property : properties) {
            indexBackUp.put(property, getIndex(property));
        }
        if (null != limits) {
            Set<String> limitSet = limits.keySet();
            for (String str : limitSet) {
                indexBackUp.put(str, getIndex(str));
            }
        }
    }


    /**
     * 获取给定名字的顶部标题栏所在的列的索引
     *
     * @param topRowName 顶部标题栏的名字
     * @return @return int
     * @author zhl
     * @date 2021-08-25 11:05
     * @version V1.0
     */
    private int getIndex(String topRowName) {
        return highOrderExcel.getTopRowNameIndex(topRowName);
    }


}
