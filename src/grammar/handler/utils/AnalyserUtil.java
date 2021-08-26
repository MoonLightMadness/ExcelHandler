package grammar.handler.utils;

import java.util.regex.Pattern;

/**
 * @ClassName : grammar.handler.utils.AnalyserUtil
 * @Description :
 * @Date 2021-08-26 14:05:30
 * @Author ZhangHL
 */
public class AnalyserUtil {

    public static final Pattern functionMapPattern = Pattern.compile("SYNTAX(.*?)\\{((\\n|.)*?)}",Pattern.CASE_INSENSITIVE);

    public static final Pattern variableMapPattern = Pattern.compile("var(.*?)=(.*?)[\\n$]",Pattern.CASE_INSENSITIVE);

    public static final Pattern mainFuncPattern = Pattern.compile("MAIN(.*?)\\{((\\n|.)*?)}",Pattern.CASE_INSENSITIVE);




}
