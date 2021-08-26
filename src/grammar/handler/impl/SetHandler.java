package grammar.handler.impl;

import grammar.handler.Handler;
import grammar.handler.utils.CommandParseUtil;
import grammar.syntax.Analyser;
import grammar.syntax.Executer;
import handler.Excel;
import handler.HighOrderExcel;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;
import java.util.Map;

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
        if (null != limits) {
            for (String property : properties){
                columns = highOrderExcel.getColonmsCellByTopRowName(property);
            }
        }
    }


}
