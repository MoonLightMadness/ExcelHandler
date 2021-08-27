package grammar.syntax;

import grammar.handler.Handler;
import grammar.handler.HandlerFactory;
import org.junit.Test;
import utils.SimpleUtils;

import java.util.*;

/**
 * @ClassName : grammar.syntax.Executer
 * @Description :
 * @Date 2021-08-25 09:31:23
 * @Author ZhangHL
 */
public class Executer {

    private Analyser analyser;

    private Map<String, List<String>> results;

    private Map<String,List<String>> funcResults;

    /**
     * 执行命令
     *
     * @param command 命令
     * @return
     * @author zhl
     * @date 2021-08-25 10:14
     * @version V1.0
     */
    public Map<String, List<String>> execute(String command) {
        analyser = new Analyser();
        analyser.analyse(command);
        Stack<String> order = analyser.getOrder();
        Map<String, List<String>> result = new HashMap<>();
        funcResults = new HashMap<>();
        while (!order.empty()) {
            Map<String, List<String>>re = subExecution(order.pop());
            if(null != re){
                result.putAll(re);
            }
        }
        return result;
    }

    private Map<String, List<String>> subExecution(String funcname) {
        String body = null;
        if("MAIN".equals(funcname.toUpperCase(Locale.ROOT))){
            body = analyser.getMainFuncBody();
        }else {
            body = analyser.getFunctionMap().get(funcname);
            funcResults.put(funcname,functionExecution(body));
            return null;
        }
        Handler handler = HandlerFactory.getHandler(body);
        Map<String, List<String>> subResult = handler.execute(body, analyser,this);
        return subResult;
    }

    private List<String> functionExecution(String body){
        Handler handler = HandlerFactory.getHandler(body);
        Map<String, List<String>> subResult = handler.execute(body, analyser,this);
        for(String s : subResult.keySet()){
            return subResult.get(s);
        }
        return null;
    }

    public Map<String, List<String>> getFuncResults() {
        return funcResults;
    }
}
