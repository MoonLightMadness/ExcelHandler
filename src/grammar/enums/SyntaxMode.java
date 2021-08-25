package grammar.enums;

public enum SyntaxMode {

    /**
     * SET模式
     */
    SET("1","SET"),

    /**
     * GET模式
     */
    GET("2","GET"),

    /**
     * DELETE模式
     */
    DELETE("3","DELETE")



    ;

    private String code;

    private String mode;

    SyntaxMode(String code, String mode){
        this.mode = mode;
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public String getMode(){
        return mode;
    }
}
