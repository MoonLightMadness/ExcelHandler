package grammar.syntax;

import java.util.Stack;

/**
 * @ClassName : grammar.syntax.Executer
 * @Description :
 * @Date 2021-08-25 09:31:23
 * @Author ZhangHL
 */
public class Executer {

    private Analyser analyser;


    public void execute(String command){
        analyser = new Analyser();
        analyser.analyse(command);
        Stack<String> order = analyser.getOrder();
        while (!order.empty()){
            subExecution(order.pop());
        }
    }

    private void subExecution(String funcname){
        String body = analyser.getFunctionMap().get(funcname);

    }

}
