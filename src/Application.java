import grammar.syntax.Executer;
import org.junit.Test;
import org.yaml.snakeyaml.reader.StreamReader;
import utils.SimpleUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName : PACKAGE_NAME.Application
 * @Description :
 * @Date 2021-08-27 10:34:38
 * @Author ZhangHL
 */
public class Application {

    public static void main(String[] args) {
        String path = args[0];
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            while ((temp = reader.readLine()) != null) {
                stringBuilder.append(temp).append("\n");
            }
            Executer executer = new Executer();
            Map<String, List<String>> result = executer.execute(stringBuilder.toString());
            if (null != result) {
                Set<String> set = result.keySet();
                for (String ss : set) {
                    List<String> values = result.get(ss);
                    System.out.println(ss+"---size:"+values.size());
                    for (String value : values) {
                        if (!SimpleUtils.isEmptyString(value)) {
                            System.out.println(value);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
