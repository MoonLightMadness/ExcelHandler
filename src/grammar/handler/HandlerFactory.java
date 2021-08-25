package grammar.handler;

import grammar.enums.SyntaxMode;
import grammar.handler.impl.DeleteHandler;
import grammar.handler.impl.GetHandler;
import grammar.handler.impl.SetHandler;

import java.util.Locale;

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
        String head = str.substring(0,str.indexOf(" ")).trim();
        if(SyntaxMode.SET.getMode().equals(head.toUpperCase(Locale.ROOT))){
            //return new SetHandler();
        }
        if(SyntaxMode.GET.getMode().equals(head.toUpperCase(Locale.ROOT))){
            return new GetHandler();
        }
        if(SyntaxMode.DELETE.getMode().equals(head.toUpperCase(Locale.ROOT))){
            //return new DeleteHandler();
        }
        return null;
    }
}
