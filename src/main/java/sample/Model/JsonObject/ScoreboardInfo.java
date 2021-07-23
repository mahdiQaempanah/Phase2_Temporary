package sample.Model.JsonObject;

public class ScoreboardInfo {
    public final String nickname;
    public final int score;
    public final boolean isOnline;

    public ScoreboardInfo(String nickname, int score, boolean isOnline){
        this.nickname = nickname;
        this.score = score;
        this.isOnline = isOnline;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
