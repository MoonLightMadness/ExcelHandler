package grammar.syntax;

import grammar.enums.SyntaxMode;
import grammar.handler.OrderHandler;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyser {
    /**
     *
     *  A -> B -> C
     *
     *  SET FILENAME.SHEETNAME
     *  property = value ...
     *  LIMIT
     *  property = value ...
     *
     *  VAR file = FILEPATH
     *  VAR sheet = SHEETNAME
     *
     *  MAIN{
     *      SET $file$.$sheet$
     *      property = value
     *      LIMIT
     *      property = EXEC(get_11)
     *  }
     *
     *
     *  SYNTAX get_11 {
     *      GET FILENAME.SHEETNAME
     *      property
     *      LIMIT
     *      property = value
     *      property = value
     *  }
     *
     *  KeyWords={SET,GET,DELETE,LIMIT,SYNTAX,EXEC,MAIN,var}
     *
     *  1.生成变量字典 (变量名 = 值)
     *    生成函数字典 (函数名 = 语句)
     *  2.进入MAIN函数
     *  3.分析句式
     *  4.执行程序
     */

    private String mainFuncName;

    private String mainFuncBody;

    private Map<String,String> functionMap;

    private Map<String,String> variableMap;

    private Stack<String> order;

    public void analyse(String command){
        getMainFunc(command);
        getFunctionMap(command);
        getVariableMap(command);
        generateOrder(command);
        syntaxParse(command);
    }


    private void getFunctionMap(String command){
        functionMap = new HashMap<>();
        Pattern pattern = Pattern.compile("SYNTAX(.*?)\\{((\\n|.)*?)}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            functionMap.put(matcher.group(1).trim(),matcher.group(2).trim());
        }
    }

    private void getVariableMap(String command){
        variableMap = new HashMap<>();
        Pattern pattern = Pattern.compile("var(.*?)=(.*?)[\\n$]",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            variableMap.put(matcher.group(1).trim(),matcher.group(2).trim());
        }
    }

    private void getMainFunc(String command){
        Pattern pattern = Pattern.compile("MAIN(.*?)\\{((\\n|.)*?)}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            mainFuncName = "MAIN";
            mainFuncBody = matcher.group(2).trim();
        }
    }

    private void generateOrder(String command){
        OrderHandler orderHandler = new OrderHandler(command,this);
        order = orderHandler.getOrder();
    }

    /**
     * 语法解析
     * @return
     * @author zhl
     * @date 2021-08-25 08:09
     * @version V1.0
     */
    private void syntaxParse(String command){
        //确定解析顺序
        order = new OrderHandler(command,this).getOrder();
    }






    @Test
    public void test(){
        String str = "var a=b\n" +
                "\n" +
                "syntax get_11{\n" +
                "\n" +
                "\t//Text\n" +
                "\tab = exec(get_12)\n" +
                "}\n" +
                "\n" +
                "\n" +
                "syntax get_13{\n" +
                "\n" +
                "\t//TODO\n" +
                "}\n" +
                "\n" +
                "syntax get_12{\n" +
                "\tcc = exec(get_13)\n" +
                "\t//something\n" +
                "}\n" +
                "\n" +
                "var c = d\n" +
                "\n" +
                "main {\n" +
                "\n" +
                "\tz = exec(get_11)\n" +
                "\n" +
                "}\n";
        try {
            Analyser analyser = new Analyser();
            analyser.analyse(str);
            OrderHandler orderHandler = new OrderHandler(str,analyser);
            Stack<String> order = orderHandler.getOrder();
            while (!order.empty()){
                System.out.println(order.pop());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMainFuncName() {
        return mainFuncName;
    }

    public String getMainFuncBody() {
        return mainFuncBody;
    }

    public Map<String, String> getFunctionMap() {
        return functionMap;
    }

    public Map<String, String> getVariableMap() {
        return variableMap;
    }

    public Stack<String> getOrder() {
        return order;
    }
}
