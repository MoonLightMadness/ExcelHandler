package grammar.handler;

import grammar.syntax.Analyser;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName : grammar.handler.OrderHandler
 * @Description :
 * @Date 2021-08-25 08:21:37
 * @Author ZhangHL
 */
public class OrderHandler {

    private String command;

    private Analyser analyser;

    private Stack<String> order;

    public OrderHandler(String command,Analyser analyser){
        this.command = command;
        this.analyser = analyser;
        order = new Stack<>();
    }

    /**
     * 获取脚本执行的顺序
     * @return @return {@link String[] }
     * @author zhl
     * @date 2021-08-25 08:24
     * @version V1.0
     */
    public Stack<String> getOrder(){
        String body = analyser.getMainFuncBody();
        order.push(analyser.getMainFuncName());
        Pattern pattern = Pattern.compile("exec\\((.*?)\\)",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()){
            getOrdinaryFunctionOrder(matcher.group(1).trim());
        }
        return order;
    }

    private void getOrdinaryFunctionOrder(String name){
        Map<String,String> funcs = analyser.getFunctionMap();
        String body = funcs.get(name);
        if(null != body){
            order.push(name);
            Pattern pattern = Pattern.compile("exec\\((.*?)\\)",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(body);
            while (matcher.find()){
                getOrdinaryFunctionOrder(matcher.group(1).trim());
            }
        }
    }


}
