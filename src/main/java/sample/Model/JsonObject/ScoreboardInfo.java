package sample.Model.JsonObject;

public class ScoreboardInfo {
    public final String nickname;
    public final int score;
    public ScoreboardInfo(String nickname, int score){
        this.nickname = nickname;
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }
}
