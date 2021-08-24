package grammar.syntax;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     *      SET file.sheet
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
     *  KeyWords={SET,GET,DELETE,LIMIT,SYNTAX,EXEC,MAIN,VAR}
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

    public void analyse(String command){
        getMainFunc(command);
        getFunctionMap(command);
        getVariableMap(command);
    }

    private void getFunctionMap(String command){
        functionMap = new HashMap<>();
        Pattern pattern = Pattern.compile("SYNTAX(.*?)\\{(\\n|.)*?}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            functionMap.put(matcher.group(1).trim(),matcher.group(2).trim());
        }
    }

    private void getVariableMap(String command){
        variableMap = new HashMap<>();
        Pattern pattern = Pattern.compile("var(.*?)=(.*?)[\\n$]");
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            variableMap.put(matcher.group(1).trim(),matcher.group(2).trim());
        }
    }

    private void getMainFunc(String command){
        Pattern pattern = Pattern.compile("MAIN(.*?)\\{(\\n|.)*?}");
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            mainFuncName = matcher.group(1).trim();
            mainFuncBody = matcher.group(2).trim();
        }
    }

    @Test
    public void test(){
        Pattern pattern = Pattern.compile("var(.*?)=(.*?)[\\n$]",Pattern.CASE_INSENSITIVE);
        String str = "SYNTAX get_11 {\n" +
                "          GET FILENAME.SHEETNAME\n" +
                "          property\n" +
                "         LIMIT\n" +
                "         property = value\n" +
                "          property = value\n" +
                "      }\n" +
                "VAR ppp = aaa\n" +
                "\n" +
                "main{\n" +
                " xxxx\n" +
                "bbbb\n" +
                "}\n" +
                "     SYNTAX get_12 {\n" +
                "          GET FILENAME.SHEETNAME\n" +
                "          property\n" +
                "         LIMIT\n" +
                "         property = value\n" +
                "          property = value\n" +
                "      }\n" +
                "VAR bb = cc\n";
        try {
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                System.out.println(matcher.group(1));
                System.out.println(matcher.group(2));

            }
        }catch (Exception e) {

        }
    }

}
