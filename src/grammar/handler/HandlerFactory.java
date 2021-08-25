package grammar.handler;

import grammar.enums.SyntaxMode;
import grammar.handler.impl.DeleteHandler;
import grammar.handler.impl.GetHandler;
import grammar.handler.impl.SetHandler;

/**
 * @ClassName : grammar.handler.HandlerFactory
 * @Description :
 * @Date 2021-08-25 10:16:19
 * @Author ZhangHL
 */
public class HandlerFactory {

    private HandlerFactory(){}

    public static Handler getHandler(String str){
        str = str.trim();
        String head = str.substring(0,str.indexOf(" "));
        if(SyntaxMode.SET.getMode().equals(head)){
            return new SetHandler();
        }
        if(SyntaxMode.GET.getMode().equals(head)){
            return new GetHandler();
        }
        if(SyntaxMode.DELETE.getMode().equals(head)){
            return new DeleteHandler();
        }
        return null;
    }
}
