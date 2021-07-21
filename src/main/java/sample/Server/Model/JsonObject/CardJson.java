package sample.Model.JsonObject;


public class CardJson {
    private String name;

    public CardJson(String cardName){
        this.name = cardName;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
