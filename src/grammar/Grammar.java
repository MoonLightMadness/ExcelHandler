package grammar;

public class Grammar {
    /**
     * 1. Select xxx From (SheetName) Where aaa = bbb And(OR)...
     * 2. Update xxx Set aaa = bbb,ccc = ddd And(OR)...
     * 3. Delete From xxx Where aaa = bbb And(OR)...
     * example:
     *  Update source.1.市监厅 Set 是否在生产环境部署 = 是 Where 业务办理项编码 = (Select 业务办理项编码 From test.Sheet1)
     */
}
