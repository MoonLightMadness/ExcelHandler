import grammar.syntax.Executer;
import log.LogSystem;
import log.LogSystemFactory;
import org.junit.Test;
import org.yaml.snakeyaml.reader.StreamReader;
import utils.SimpleUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
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


    private static LogSystem log = LogSystemFactory.getLogSystem();

    public static void main(String[] args) {
        String path = args[0];
        //计时开始
        long start = System.currentTimeMillis();
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            while ((temp = reader.readLine()) != null) {
                stringBuilder.append(temp).append("\n");
            }
            reader.close();
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
            log.info("环节结束，用时:"+(System.currentTimeMillis()-start)+"ms");
        } catch (FileNotFoundException e) {
            log.error("未找到文件，原因：{}",e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("指令执行失败，原因：{}",e);
            e.printStackTrace();
        }
    }
}
