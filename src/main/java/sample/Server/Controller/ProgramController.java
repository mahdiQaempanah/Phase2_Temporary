package sample.Controller;

import org.json.JSONObject;
import sample.Model.ApiMessage;
import sample.Model.JsonObject.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sample.Model.RequestForPlayGame;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramController {
    private final static String usersInfoPath = "target\\classes\\Database\\UsersInfo.txt";
    private final static String monstersInfoPath = "target\\classes\\Database\\MonstersInfo.txt";
    private final static String spellAndTrapsINfoPath = "target\\classes\\Database\\Spell&TrapsInfo.txt";
    private ArrayList<AccountJson> loggedInUsers = new ArrayList<>();
    private ArrayList<RequestForPlayGame> requestForPlayGames = new ArrayList<>();
    private GameController gameController;

    public ProgramController(){
    }

    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }

    public ApiMessage register(String username, String password, String nickname) throws Exception {

        if(doesUserWithThisUsernameExist(username)){
            return new ApiMessage(ApiMessage.error,"user with username " + username + " already exists");
        }

        else if(doesUserWithThisNicknameExist(nickname)){
            return new ApiMessage(ApiMessage.error, "user with nickname " + nickname + " already exists");
        }

        else{
            addUserInfoToDatabase(username , password , nickname);
            return new ApiMessage(ApiMessage.successful, "user created successfully!");
        }

    }

    public ApiMessage login(String username, String password) throws Exception {

        if(!doesUserWithThisUsernameExist(username)){
            return new ApiMessage(ApiMessage.error,"Username and password didn’t match!");
        }

        else if(!isCorrectPassword(username, password)){
            return new ApiMessage(ApiMessage.error,"Username and password didn’t match!");
        }

        else{
            addLoggedInUser(getUserInfoByUsername(username));
            return new ApiMessage(ApiMessage.successful, "{\"message\":\"user logged in successfully!\",\"userHash\":\""+username+"\"}");
        }

    }

    public ApiMessage logout(String userHash) throws Exception {
        deleteLoggedInUser(userHash);
        return new ApiMessage(ApiMessage.successful,"user logged out successfully!");
    }

    public ApiMessage showScoreboard() throws Exception {
        ArrayList<AccountJson> users = getUsersInfo();
        ArrayList<ScoreboardInfo> scoreboard = (ArrayList<ScoreboardInfo>) users.stream().map(x -> new ScoreboardInfo(x.getNickname(),x.getScore())).collect(Collectors.toList());
        return new ApiMessage(ApiMessage.successful,new Gson().toJson(scoreboard));
    }

    public ApiMessage changeNickname(String userHash,String nickname) throws Exception {
        if(doesUserWithThisNicknameExist(nickname)){
            return new ApiMessage(ApiMessage.error,"user with nickname " + nickname + " already exists");
        }
        else{
            AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
            loggedInUser.setNickname(nickname);
            changeUserInfoInDataBase(loggedInUser);
            return new ApiMessage(ApiMessage.successful,"nickname changed successfully!");
        }
    }

    public ApiMessage changePassword(String userHash,String currentPassword, String newPassword) throws Exception {
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);

        if(!loggedInUser.getPassword().equals(currentPassword)){
            return new ApiMessage(ApiMessage.error,"current password is invalid");
        }

        else if(loggedInUser.getPassword().equals(newPassword)){
            return new ApiMessage(ApiMessage.error,"please enter a new password");
        }

        else{
            loggedInUser.setPassword(newPassword);
            changeUserInfoInDataBase(loggedInUser);
            return new ApiMessage(ApiMessage.successful,"password changed successfully!");
        }
    }

    public ApiMessage createDeck(String userHash, String deckName) throws Exception {
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        if(loggedInUser.getDeckByName(deckName) != null){
            return new ApiMessage(ApiMessage.error,"deck with name "+deckName+" already exists");
        }

        else{
            loggedInUser.addDeck(deckName);
            changeUserInfoInDataBase(loggedInUser);
            return new ApiMessage(ApiMessage.successful, "deck created successfully!");
        }
    }

    public ApiMessage deleteDeck(String userHash, String deckName) throws Exception {
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        DeckJson deck = loggedInUser.getDeckByName(deckName);

        if(deck == null){
            return new ApiMessage(ApiMessage.error,"deck with name "+deckName+"  does not exist");
        }

        else{
            if(loggedInUser.getActiveDeckName().equals(deck.getName()))
                loggedInUser.setActiveDeckName("");
            loggedInUser.deleteDeck(deck);
            changeUserInfoInDataBase(loggedInUser);
            return new ApiMessage(ApiMessage.successful,"deck deleted successfully");
        }
    }

    public ApiMessage selectActiveDeck(String userHash, String deckName) throws Exception {
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        DeckJson deck = loggedInUser.getDeckByName(deckName);

        if(deck == null){
            return new ApiMessage(ApiMessage.error,"deck with name " + deckName + " does not exist");
        }

        else{
            loggedInUser.setActiveDeckName(deck.getName());
            changeUserInfoInDataBase(loggedInUser);
            return new ApiMessage(ApiMessage.successful,"deck activated successfully");
        }
    }

    public ApiMessage addCardToDeck(String userHash, String cardName, String deckName, boolean inSideDeck) throws Exception {
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        DeckJson deck = loggedInUser.getDeckByName(deckName);

        if(!loggedInUser.doesHaveThisCard(cardName)){
            return new ApiMessage(ApiMessage.error,"card with name "+cardName+" does not exist");
        }

        else if(deck == null){
            return new ApiMessage(ApiMessage.error,"deck with name "+deckName+" does not exist");
        }

        else if(!inSideDeck && deck.isMainDeckFull()){
            return new ApiMessage(ApiMessage.error,"main deck is full");
        }

        else if(inSideDeck && deck.isSideDeckFull()){
            return new ApiMessage(ApiMessage.error,"side deck is full");
        }

        else if(deck.getCntOfThisCard(cardName) == 3){
            return new ApiMessage(ApiMessage.error,"there are already three cards with name "+cardName+" in deck "+deckName);
        }

        else{
            if(inSideDeck)
                deck.addToSideDeck(cardName);
            else
                deck.addToMainDeck(cardName);
            changeUserInfoInDataBase(loggedInUser);
            return new ApiMessage(ApiMessage.successful,"card added to deck successfully");
        }
    }

    public ApiMessage removeCardFromDeck(String userHash, String cardName, String deckName, boolean fromSideDeck) throws Exception {
        //hame ro hazf konim ya yekisho
        //manaye mojoodi chiye
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        DeckJson deck = loggedInUser.getDeckByName(deckName);

        if(deck == null){
            return new ApiMessage(ApiMessage.error,"deck with name "+deckName+" does not exist");
        }

        else if(!fromSideDeck && deck.getCntOfThisCardInMainDeck(cardName) == 0){
            return new ApiMessage(ApiMessage.error,"card with name "+cardName+"  does not exist in main deck");
        }

        else if(fromSideDeck && deck.getCntOfThisCardInSideDeck(cardName) == 0){
            return new ApiMessage(ApiMessage.error,"card with name "+cardName+"  does not exist in side deck");
        }

        else{
            if(!fromSideDeck)
                deck.removeFromMainDeck(cardName);
            else
                deck.removeFromSideDeck(cardName);
            changeUserInfoInDataBase(loggedInUser);
            return new ApiMessage(ApiMessage.successful,"card removed form deck successfully");
        }

    }

    public ApiMessage showAllDeck(String userHash) throws Exception {
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        ShowAllDecksJson ans = new ShowAllDecksJson(loggedInUser);
        return new ApiMessage(ApiMessage.successful,new Gson().toJson(ans));
    }

    public ApiMessage showDeck(String userHash, String deckName, boolean isSideDeck) throws Exception {
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        DeckJson deck = loggedInUser.getDeckByName(deckName);

        if(deck == null){
            return new ApiMessage(ApiMessage.error,"deck with name "+deckName+" does not exist");
        }
        else{
            ShowDeckJson ans = new ShowDeckJson(deckName , isSideDeck);
            if(!isSideDeck){
                getCardGeneralInfoForShowDeck(ans , deck.getMainDeck());
            }
            else{
                getCardGeneralInfoForShowDeck(ans , deck.getSideDeck());
            }
            return new ApiMessage(ApiMessage.successful,new Gson().toJson(ans));
        }
    }

    public ApiMessage showPurchasedCards(String userHash) throws Exception {
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        ArrayList<CardGeneralInfo> purchaseCards =  new ArrayList<>();
        for (CardJson card : loggedInUser.getPurchasedCards()) {
            if(doesMonsterExistWithThisName(card.getName())){
                MonsterJson monster = getMonsterByName(card.getName());
                assert monster != null;
                purchaseCards.add(new CardGeneralInfo(monster));
            }
            else{
                SpellAndTrapJson spellAndTrap = getSpellAndTrapByName(card.getName());
                assert spellAndTrap != null;
                purchaseCards.add(new CardGeneralInfo(spellAndTrap));
            }
        }
        return new ApiMessage(ApiMessage.successful,new Gson().toJson(purchaseCards));
    }

    public ApiMessage buyCard(String userHash, String cardName) throws Exception {
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        if(!doesMonsterExistWithThisName(cardName)&&!doesSpellOrTrapExistsWithThisName(cardName)){
            return new ApiMessage(ApiMessage.error,"there is no card with this name.");
        }

        CardGeneralInfo card;
        if(doesMonsterExistWithThisName(cardName)){
            MonsterJson monster = getMonsterByName(cardName);
            assert monster != null;
            card = new CardGeneralInfo(monster);
        }
        else{
            SpellAndTrapJson spellAndTrap = getSpellAndTrapByName(cardName);
            assert spellAndTrap != null;
            card = new CardGeneralInfo(spellAndTrap);
        }

        if(loggedInUser.getMoney() < card.getPrice()){
            return new ApiMessage(ApiMessage.error,"not enough money.");
        }

        loggedInUser.addToPurchasedCards(cardName);
        loggedInUser.decreaseMoney(card.getPrice());
        changeUserInfoInDataBase(loggedInUser);
        return new ApiMessage(ApiMessage.successful,"card successfully purchased.");
    }

    public ApiMessage increaseMoney(String userHash, int amount) throws Exception {
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        loggedInUser.increaseMoney(amount);
        changeUserInfoInDataBase(loggedInUser);
        return new ApiMessage(ApiMessage.successful,null);
    }

    public ApiMessage showShopCards() throws Exception {
        ArrayList<CardGeneralInfo> ans = new ArrayList<>();

        for (MonsterJson monster : getMonstersInfo()) {
            ans.add(new CardGeneralInfo(monster));
        }

        for (SpellAndTrapJson spellAndTrap : getSpellAndTrapsInfo()) {
            ans.add(new CardGeneralInfo(spellAndTrap));
        }

        return new ApiMessage(ApiMessage.successful,new Gson().toJson(ans));
    }

    public ApiMessage getMoney(String userHash) throws Exception {
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        return new ApiMessage(ApiMessage.successful,"{\"money\":" + loggedInUser.getMoney() + "}");
    }

    public ApiMessage getOpponentsWaiting(String userHash) throws Exception {
        ArrayList<RequestForPlayGame> answer = new ArrayList<>();
        for (RequestForPlayGame requestForPlayGame : requestForPlayGames) {
            if(requestForPlayGame.getWantedOpponentName() != null && requestForPlayGame.getWantedOpponentName().equals(userHash))
                answer.add(requestForPlayGame);
        }
        return new ApiMessage(ApiMessage.successful,new Gson().toJson(answer));
    }

    public ApiMessage createDuel(String userHash, String usernamePlayer2) throws Exception {
        JSONObject ans = new JSONObject();
        AccountJson loggedInUser = getLoggedInUserByUsername(userHash);
        if(!doesUserWithThisUsernameExist(usernamePlayer2)){
            ans.put("createGame",false);
            ans.put("detail","there is no player with this username");
            return new ApiMessage(ApiMessage.error,ans.toString());
        }

        AccountJson player2 = getUserInfoByUsername(usernamePlayer2);
        assert player2 != null;

        if(loggedInUser.getActiveDeck() == null){
            ans.put("createGame",false);
            ans.put("detail",loggedInUser.getUsername()+" has no active deck");
            return new ApiMessage(ApiMessage.error,ans.toString());
        }

        if(player2.getActiveDeck() == null){
            ans.put("createGame",false);
            ans.put("detail",player2.getUsername()+" has no active deck");
            return new ApiMessage(ApiMessage.error,ans.toString());
        }

        if(!loggedInUser.isAllowedActiveDeck()){
            ans.put("createGame",false);
            ans.put("detail",loggedInUser.getUsername()+"’s deck is invalid");
            return new ApiMessage(ApiMessage.error,ans.toString());
        }

        if(!player2.isAllowedActiveDeck()){
            ans.put("createGame",false);
            ans.put("detail",player2.getUsername()+"’s deck is invalid");
            return new ApiMessage(ApiMessage.error,ans.toString());
        }


        return gameController.createGame(loggedInUser , player2);
    }

    private void getCardGeneralInfoForShowDeck(ShowDeckJson ans, ArrayList<CardJson> deck) throws IOException {
        for (CardJson card : deck) {
            if(doesMonsterExistWithThisName(card.getName())){
                MonsterJson monster = getMonsterByName(card.getName());
                assert monster != null;
                ans.addToMonsters(new CardGeneralInfo(monster));
            }
            else{
                SpellAndTrapJson spellAndTrap = getSpellAndTrapByName(card.getName());
                assert spellAndTrap != null;
                ans.addToSpellAndTraps(new CardGeneralInfo(spellAndTrap));
            }
        }
    }



    public synchronized void changeUserInfoInDataBase(AccountJson newUserInfo) throws IOException {
        ArrayList<AccountJson> users = getUsersInfo();
        FileWriter fileWriter = new FileWriter(usersInfoPath);
        for(int i = 0 ; i < users.size() ; i++){
            if(users.get(i).getUsername().equals(newUserInfo.getUsername())){
                users.remove(i);
                users.add(newUserInfo);
                break;
            }
        }
        fileWriter.write(new Gson().toJson(users));
        fileWriter.close();
    }

    private synchronized void addUserInfoToDatabase(String username, String password, String nickname) throws IOException {
        AccountJson newUser = new AccountJson(username, password, nickname);
        newUser.increaseMoney(100000);//not sure
        ArrayList<AccountJson> users = getUsersInfo();
        FileWriter fileWriter = new FileWriter(usersInfoPath);

        users.add(newUser);
        fileWriter.write(new Gson().toJson(users));
        fileWriter.close();
    }

    public boolean doesUserWithThisUsernameExist(String username) throws IOException {
        ArrayList<AccountJson> users = getUsersInfo();
        for (AccountJson user : users) {
            if(user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public boolean doesUserWithThisNicknameExist(String nickname) throws IOException {
        ArrayList<AccountJson> users = getUsersInfo();
        for (AccountJson user : users) {
            if(user.getNickname().equals(nickname))
                return true;
        }
        return false;
    }

    private boolean doesMonsterExistWithThisName(String name) throws IOException {
        ArrayList<MonsterJson> monsters = getMonstersInfo();
        for (MonsterJson monster : monsters) {
            if(monster.getName().equals(name))
                return true;
        }
        return false ;
    }

    private boolean doesSpellOrTrapExistsWithThisName(String name) throws IOException {
        ArrayList<SpellAndTrapJson> spellAndTraps = getSpellAndTrapsInfo();
        for (SpellAndTrapJson spellAndTrap : spellAndTraps) {
            if(spellAndTrap.getName().equals(name))
                return true;
        }
        return false ;
    }

    private boolean isCorrectPassword(String username, String password) throws IOException {
        AccountJson user = getUserInfoByUsername(username);
        assert user != null;
        return user.getPassword().equals(password);
    }

    private MonsterJson getMonsterByName(String name) throws IOException {
        ArrayList<MonsterJson> monsters = getMonstersInfo();
        for (MonsterJson monster : monsters) {
            if(monster.getName().equals(name))
                return monster;
        }
        return null;
    }

    private SpellAndTrapJson getSpellAndTrapByName(String name) throws IOException {
        ArrayList<SpellAndTrapJson> spellAndTraps = getSpellAndTrapsInfo();
        for (SpellAndTrapJson spellAndTrap : spellAndTraps) {
            if(spellAndTrap.getName().equals(name))
                return spellAndTrap;
        }
        return null;
    }

    private AccountJson getUserInfoByUsername(String username) throws IOException {
        ArrayList<AccountJson> users = getUsersInfo();
        for (AccountJson user : users) {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public AccountJson getUserInfoByNickname(String nickname) throws IOException {
        ArrayList<AccountJson> users = getUsersInfo();
        for (AccountJson user : users) {
            if(user.getNickname().equals(nickname))
                return user;
        }
        return null;
    }

    private synchronized ArrayList<MonsterJson> getMonstersInfo() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(monstersInfoPath)));
        return new Gson().fromJson(json ,new TypeToken<List<MonsterJson>>(){}.getType());
    }

    private synchronized ArrayList<SpellAndTrapJson> getSpellAndTrapsInfo() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(spellAndTrapsINfoPath)));
        return new Gson().fromJson(json ,new TypeToken<List<SpellAndTrapJson>>(){}.getType());
    }

    private synchronized ArrayList<AccountJson> getUsersInfo() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(usersInfoPath)));
        return new Gson().fromJson(json ,new TypeToken<List<AccountJson>>(){}.getType());
    }


    private synchronized void addLoggedInUser(AccountJson loggedInUser){
        loggedInUsers.add(loggedInUser);
    }

    private synchronized void deleteLoggedInUser(String username){
        loggedInUsers.remove(getLoggedInUserByUsername(username));
    }

    private synchronized AccountJson getLoggedInUserByUsername(String username){
        for (AccountJson user : loggedInUsers) {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public synchronized ApiMessage checkForStartDuel(String userHash, String opponentName) throws Exception {
        JSONObject ans = new JSONObject();
        if(opponentName.isEmpty()){
            for (RequestForPlayGame requestForPlayGame : requestForPlayGames) {
                if(requestForPlayGame.getWantedOpponentName() == null || requestForPlayGame.getWantedOpponentName().equals(userHash)){
                    requestForPlayGames.remove(requestForPlayGame);
                    return createDuel(userHash,requestForPlayGame.getUserWantPlayGame());
                }
            }
            requestForPlayGames.add(new RequestForPlayGame(userHash,null));
            ans.put("gameCreate",false);
            System.out.println(ans);
            return new ApiMessage(ApiMessage.successful,ans.toString());
        }
        else{
            for (RequestForPlayGame requestForPlayGame : requestForPlayGames) {
                if(requestForPlayGame.getUserWantPlayGame().equals(opponentName) && requestForPlayGame.getWantedOpponentName().equals(userHash)){
                    requestForPlayGames.remove(requestForPlayGame);
                    return createDuel(userHash,opponentName);
                }
            }
            requestForPlayGames.add(new RequestForPlayGame(userHash,opponentName));
            ans.put("gameCreate",false);
            return new ApiMessage(ApiMessage.successful,ans.toString());
        }
    }

    public synchronized ApiMessage attemptToStartDuelWithDefinedUser(String userHash, String opponent) throws Exception {
        for (RequestForPlayGame requestForPlayGame : requestForPlayGames) {
            if(requestForPlayGame.getUserWantPlayGame().equals(opponent) && requestForPlayGame.getWantedOpponentName().equals(userHash)){
                requestForPlayGames.remove(requestForPlayGame);
                return createDuel(userHash,opponent);
            }
        }

        return new ApiMessage(ApiMessage.error,"user cancel its request.");
    }

    public synchronized ApiMessage deleteGameRequest(String userHash) throws Exception {
        for (RequestForPlayGame requestForPlayGame : requestForPlayGames) {
            if(requestForPlayGame.getUserWantPlayGame().equals(userHash)){
                requestForPlayGames.remove(requestForPlayGame);
                return new ApiMessage(ApiMessage.successful,"");
            }
        }
        return new ApiMessage(ApiMessage.successful,"");
    }



}
