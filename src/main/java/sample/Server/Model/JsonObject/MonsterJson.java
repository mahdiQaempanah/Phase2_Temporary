package sample.Model.JsonObject;


public class MonsterJson {
    private String name;
    private String description;
    private int price;
    private int level;
    private String attribute;
    private String monsterType;
    private String cardType;
    private int atk;
    private int def;


    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public String getCardType() {
        return cardType;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

}
