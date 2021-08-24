package handler;

import config.Configer;

/**
 * 只支持xlsx文件
 * @ClassName : handler.Excel
 * @Description :
 * @Date 2021-08-24 16:00:13
 * @Author ZhangHL
 */
public class Excel {

    private String filePath;

    private String sheetName;

    private String topRowName;

    private Configer configer;

    public Excel(){
        configer = new Configer();
    }

}
