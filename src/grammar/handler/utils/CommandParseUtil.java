package grammar.handler.utils;

import grammar.syntax.Analyser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName : grammar.handler.utils.CommandParseUtil
 * @Description :
 * @Date 2021-08-26 08:38:28
 * @Author ZhangHL
 */
public class CommandParseUtil {

    private static final Pattern limitPattern = Pattern.compile("LIMIT\\n((.|\\n)*?)END", Pattern.CASE_INSENSITIVE);

    private static final Pattern propertyPattern = Pattern.compile("(.*?)\\nLIMIT", Pattern.CASE_INSENSITIVE);

    private static final Pattern filePathPattern = Pattern.compile("(.*?)\\.(.*?)\\n", Pattern.CASE_INSENSITIVE);

    private static final Pattern sheetNamePattern = Pattern.compile("(.*?)\\.(.*?)\\n", Pattern.CASE_INSENSITIVE);

    public static Map<String, String> getLimits(String commad) {
        Map<String, String> limits = new HashMap<>();
        Matcher matcher = limitPattern.matcher(commad);
        if (matcher.find()) {
            String temp = matcher.group(1).trim();
            temp = temp.replaceAll("\\n", "");
            for (String equition : temp.split(",")) {
                String[] ss = equition.split("=");
                limits.put(ss[0].trim(), ss[1].trim());
            }
            return limits;
        }
        return null;
    }

    public static List<String> getProperties(String command) {
        Matcher matcher = propertyPattern.matcher(command);
        List<String> results = new ArrayList<>();
        while (matcher.find()) {
            String str = matcher.group(1).trim();
            results.addAll(Arrays.asList(str.split(",")));
        }
        return results;
    }

    public static String getFilePath(String command) {
        Matcher matcher = filePathPattern.matcher(command);
        String filePath = null;
        if (matcher.find()) {
            filePath = matcher.group(1).split(" ")[1].trim() + ".xlsx";
        }
        return filePath;
    }

    public static String getSheetName(String command) {
        Matcher matcher = sheetNamePattern.matcher(command);
        String sheetName = null;
        if (matcher.find()) {
            sheetName = matcher.group(2).trim();
        }
        return sheetName;
    }

    public static String getVariableValue(String name, Analyser analyser) {
        return analyser.getVariableMap().get(name);
    }
}
