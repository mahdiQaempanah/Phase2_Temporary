package sample.Model;

public class ApiMessage {
    public static String error = "error";
    public static String successful = "successful";
    private final String type;
    private final String message;
    public ApiMessage(String type, String message) throws Exception{
        if(!type.equals("error") && !type.equals("successful")){
            throw new Exception("wrong initialize in apiMessage");
        }
        this.type = type;
        this.message = message;
    }
    
    public String getType(){
        return this.type;
    }
    
    public String getMessage(){
        return this.message;
    }
}
