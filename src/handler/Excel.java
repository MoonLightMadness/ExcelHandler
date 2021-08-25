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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTopRowName() {
        return topRowName;
    }

    public void setTopRowName(String topRowName) {
        this.topRowName = sheetName;
    }
}
