package grammar.handler.impl;

import grammar.handler.Handler;
import grammar.handler.utils.CommandParseUtil;
import grammar.syntax.Analyser;
import grammar.syntax.Executer;
import handler.Excel;
import handler.HighOrderExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.*;

/**
 * @ClassName : grammar.handler.impl.SetHandler
 * @Description :
 * @Date 2021-08-25 10:18:15
 * @Author ZhangHL
 */
public class SetHandler implements Handler {

    private HighOrderExcel highOrderExcel;

    private Executer executer;

    List<String> properties;

    Map<String, String> limits;

    @Override
    public Map<String, List<String>> execute(String command, Analyser analyser, Executer executer) {
        this.executer = executer;
        highOrderExcel = CommandParseUtil.getHighOrderExcel(command);
        properties = CommandParseUtil.getProperties(command);
        limits = CommandParseUtil.getLimits(command);
        set();
        return null;
    }

    private void set() {
        List<Cell> columns;
        List<Integer> changedPropertiesIndex = new ArrayList<>();
        List<Integer> limitsIndex = new ArrayList<>();
        Map<String, List<String>> alllimit = new HashMap<>();
        for (String property : properties) {
            changedPropertiesIndex.add(highOrderExcel.getTopRowNameIndex(property));
        }
        for (String key : limits.keySet()) {
            limitsIndex.add(highOrderExcel.getTopRowNameIndex(key));
        }
        if (null != limits) {
            alllimit = getAllLimit();
        }
        search(alllimit,changedPropertiesIndex);
        highOrderExcel.save("./output.xlsx");
    }

    private void search(Map<String, List<String>> alllimit,List<Integer> changedPropertiesIndex){
        boolean[] canSuit = new boolean[alllimit.keySet().size()];
        int count = 0;
        List<Cell> cells = highOrderExcel.getColonmsCellByTopRowName(getPropertyName(properties.get(0)));
        for (Cell cell : cells) {
            for (String key : alllimit.keySet()) {
                int index = highOrderExcel.getTopRowNameIndex(key);
                List<String> limits = alllimit.get(key);
                for (String limit : limits) {
                    if (cell.getRow().getCell(index).getStringCellValue().equals(limit)) {
                        canSuit[count++] = true;
                        break;
                    }
                }
            }
            count = 0;
            if(check(canSuit)){
                Row row = cell.getRow();
                for(Integer in : changedPropertiesIndex){
                    row.getCell(in).setCellValue(getPropertyValue(in));
                }
            }
        }
    }

    private String getPropertyValue(int index){
        String res = properties.get(index);
        return res.split("=")[1].trim();
    }

    private String getPropertyName(String property){
        return property.split("=")[0].trim();
    }

    private boolean check(boolean[] canSuit){
        for (boolean b : canSuit){
            if(false == b){
                return false;
            }
        }
        return true;
    }

    private Map<String, List<String>> getAllLimit() {
        Map<String, List<String>> alllimit = new HashMap<>();
        for (String key : limits.keySet()) {
            String value = limits.get(key);
            if (value.toUpperCase(Locale.ROOT).startsWith("EXEC(") &&
                    value.toUpperCase(Locale.ROOT).endsWith(")")) {
                alllimit.put(key, functionCall(value));
            } else {
                List<String> temp = new ArrayList<>();
                temp.add(value);
                alllimit.put(key, temp);
            }
        }
        return alllimit;
    }


    private List<String> functionCall(String command) {
        command = command.substring(5, command.length() - 1);
        return executer.getFuncResults().get(command);
    }


}
