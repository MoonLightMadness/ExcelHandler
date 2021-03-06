package log;





import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 初始化请调用init()方法
 * @ClassName : utils.high.logsystem.LogSystem
 * @Description :日志系统--系统类
 * @Date 2021-03-31 20:43:24
 * @Author ZhangHL
 */
public class LogSystem {
    private List<LogEntity> list;
    private  Logger log;
    private  int logCount=0;
    public void init(){
        log=new Logger();
        list=new ArrayList<>();
    }
    /**
     * 将一个日志实体加入到日志系统中
     * @class *ClassName*
     * @Description //TODO
     * @Param
     * @return
     * @Author Zhang huai lan
     * @Date 20:55 2021/3/31
     **/
    private void add(LogEntity log){
        try {
            synchronized (LogSystem.class){
                ListIterator<LogEntity> iterator = list.listIterator();
                //控制台输出
                System.out.println(log.toString());
                iterator.add(log);
                logCount++;
                if(logCount>=LogConstantArg.AUTO_SAVE_MAX_COUNT){
                    this.save();
                    logCount=0;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 将日志系统中的日志持久化
     * @class *ClassName*
     * @Description //TODO
     * @Param
     * @return
     * @Author Zhang huai lan
     * @Date 20:56 2021/3/31
     **/
    public void save(){
        synchronized (LogSystem.class){
            File f=new File(LogConstantArg.logPath);
            try {
                if(!f.exists()){
                    f.createNewFile();
                }
                OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(f,true), StandardCharsets.UTF_8);
                Iterator<LogEntity> iterator = list.iterator();
                while (iterator.hasNext()){
                    LogEntity entity = iterator.next();
                    if(entity != null){
                        writer.write(entity +"\n");
                    }
                    iterator.remove();
                }
                writer.close();
                //list.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void immediatelySaveMode(boolean opt){
        if(opt){
            LogConstantArg.AUTO_SAVE_MAX_COUNT=1;
        }else {
            LogConstantArg.AUTO_SAVE_MAX_COUNT=1000;
        }
    }
    public void ok(String msg, Object... args){
        this.add(log.ok(msg,args));
    }
    public void error(String msg, Object... args){
        this.add(log.error(msg, args));
    }
    public  void info(String msg, Object... args){
        this.add(log.info(msg, args));
    }
}
