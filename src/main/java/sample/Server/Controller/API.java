package sample.Controller;
import org.json.JSONObject;
import sample.Model.Game.CardAddress;

public class API {
    public GameController gameController;
    public ProgramController programController;

    public API(){
        programController = new ProgramController();
        gameController = new GameController(programController);
        programController.setGameController(gameController);
    }

    public JSONObject run(String input) throws Exception {
        JSONObject request = new JSONObject(input);
        String commandType = request.getString("command");
        String userHash = null;
        if(!request.isNull("userHash"))
            userHash = request.getString("userHash");
        Integer gameId = null;
        if(!request.isNull("gameId"))
            gameId = request.getInt("gameId");


        if(commandType.equals("crate_new_user")){
            String username = (String) request.get("username");
            String password = (String) request.get("password");
            String nickname = (String) request.get("nickname");
            return new JSONObject(programController.register(username,password,nickname));
        }
        if(commandType.equals("login_user")){
            String username = (String) request.get("username");
            String password = (String) request.get("password");
            return new JSONObject(programController.login(username,password));
        }
        if(commandType.equals("logout")){
            programController.logout(userHash);
        }
        if(commandType.equals("change_Profile_nickname")){
            return new JSONObject(programController.changeNickname(userHash, (String) request.get("nickname")));
        }
        if(commandType.equals("change_Profile_password")){
            return new JSONObject(programController.changePassword(userHash, (String) request.get("currentPass"),(String) request.get("newPass")));
        }


        if(commandType.equals("show_scorboard")){
            return new JSONObject(programController.showScoreboard());
        }
        if(commandType.equals("buyCard")){
            return new JSONObject(programController.buyCard(userHash,(String) request.get("cardName")));
        }
        if(commandType.equals("getMoney")){
            return new JSONObject(programController.getMoney(userHash));
        }
        if(commandType.equals("shopShowAll")) {
            return new JSONObject(programController.showShopCards());
        }

        if(commandType.equals("createDeck")){
            return new JSONObject(programController.createDeck(userHash,(String) request.get("deckName")));
        }
        if(commandType.equals("deleteDeck")){
            return new JSONObject(programController.deleteDeck(userHash, (String) request.get("deckName")));
        }
        if(commandType.equals("setDeckActivate")){
            return new JSONObject(programController.selectActiveDeck(userHash,(String) request.get("deckName")));
        }
        if(commandType.equals("showAllDecks")){
            return new JSONObject(programController.showAllDeck(userHash));
        }
        if(commandType.equals("showDeck")){
            String deckName = (String) request.get("deckName");
            boolean isSideDeck = !(Boolean.parseBoolean( request.get("isMainSide").toString()));
            return new JSONObject(programController.showDeck(userHash,deckName,isSideDeck));
        }
        if(commandType.equals("addCardToDeck")){
            String deckName = (String) request.get("deckName");
            String cardName = (String) request.get("cardName");
            boolean isSideDeck = !(Boolean.parseBoolean( request.get("isMainDeck").toString()));
            return new JSONObject(programController.addCardToDeck(userHash,cardName,deckName,isSideDeck));
        }
        if(commandType.equals("removeCardFromDeck")){
            String deckName = (String) request.get("deckName");
            String cardName = (String) request.get("cardName");
            boolean isSideDeck = !(Boolean.parseBoolean( request.get("isMainDeck").toString()));
            return new JSONObject(programController.removeCardFromDeck(userHash,cardName,deckName,isSideDeck));
        }

        if(commandType.equals("getOpponentsWaiting")){
            return new JSONObject(programController.getOpponentsWaiting(userHash));
        }
        if(commandType.equals("addNewOpponentRequest")){
            return new JSONObject(programController.checkForStartDuel(userHash,request.getString("opponentName")));
        }
        if(commandType.equals("startGameWithDefinedUser")){
            return new JSONObject(programController.attemptToStartDuelWithDefinedUser(userHash,request.getString("opponent")));
        }
        if(commandType.equals("deleteGameRequest")){
            return new JSONObject(programController.deleteGameRequest(userHash));
        }
        if(commandType.equals("getIsGameStartWithMe")){
            return new JSONObject(gameController.isGameStartWithUser(userHash));
        }

        if(commandType.equals("duelNewGame")){
            String opponent = (String) request.get("opponent");
            //int rounds = Integer.parseInt(request.get("round").toString());
            int rounds = 1;
          //  return new JSONObject(programController.createDuel(userHash,opponent,rounds,gameController));
            //ai
        }
        if(commandType.equals("isActivePlayer")){
            return new JSONObject(gameController.isActivePlayer(gameId,userHash));
        }
        if(commandType.equals("add_card_to_hand")){
            return new JSONObject(gameController.addCardFromDeckToHand(gameId));
        }
        if(commandType.equals("nextPhase")) {
            return new JSONObject(gameController.nextPhase(gameId));
        }
        if(commandType.equals("get_phase")){
            return new JSONObject(gameController.getPhase(gameId));
        }
        if(commandType.equals("selectCard")){
            String zone = (String) request.get("zone");
            CardAddress selectedCardAddress = null;
            switch (zone){
                case "monster_zone":
                    if(!(Boolean.parseBoolean( request.get("isActivePlayer").toString())))
                        selectedCardAddress = CardAddress.OPPONENT_MONSTER_ZONE;
                    else
                        selectedCardAddress = CardAddress.MONSTER_ZONE;
                    break;
                case "spell_zone":
                    if(!(Boolean.parseBoolean( request.get("isActivePlayer").toString())))
                        selectedCardAddress = CardAddress.SPELL_ZONE;
                    else
                        selectedCardAddress = CardAddress.OPPONENT_SPELL_ZONE;
                    break;
                case "hand_zone":
                    selectedCardAddress = CardAddress.HAND;
                    break;
                case "field_zone":
                    if(!(Boolean.parseBoolean( request.get("isActivePlayer").toString())))
                        selectedCardAddress = CardAddress.FIELD_ZONE;
                    else
                        selectedCardAddress =  CardAddress.OPPONENT_FIELD_ZONE;
                    break;
            }
            return new JSONObject(gameController.selectCard(gameId,selectedCardAddress, Integer.parseInt(request.get("id").toString())));
        }
        if(commandType.equals("summon")){
            return new JSONObject(gameController.summonMonster(gameId));
        }
        if(commandType.equals("tribute")){
            int numberOfTribute =  Integer.parseInt((String) request.get("numberOfTributes"));
            if(numberOfTribute == 1){
                int address1 = Integer.parseInt((String) request.get("address1"));
                return new JSONObject(gameController.getTributeForSummonMonster(gameId,address1));
            }
            else{
                int address1 = Integer.parseInt((String) request.get("address1"));
                int address2 = Integer.parseInt((String) request.get("address2"));
                return new JSONObject(gameController.getTributesForSummonMonster(gameId,address1,address2));
            }
        }
        if(commandType.equals("setMonster")){
            return new JSONObject(gameController.setMonster(gameId));
        }
        if(commandType.equals("changeMonsterMode")){
            return new JSONObject(gameController.changeMonsterMode(gameId));
        }
        if(commandType.equals("setSpell")){
            gameController.setSpellAndTrap(gameId);
        }
        if(commandType.equals("activeEffect")){
            return new JSONObject(gameController.activateEffect(gameId));
        }
        if(commandType.equals("isGameOver")){
            return new JSONObject(gameController.isRoundOver(gameId));
        }
        if(commandType.equals("attack")){
            return new JSONObject(gameController.attack(gameId,Integer.parseInt(String.valueOf(request.get("id")))));
        }
        if(commandType.equals("directAttack")){
            return new JSONObject(gameController.directAttack(gameId));
        }
        if(commandType.equals("showGraveyard")){
            return new JSONObject(gameController.getGraveyard(gameId,Boolean.parseBoolean((String) request.get("isActivePlayer"))));
        }
        if(commandType.equals("get_board")){
            return new JSONObject(gameController.getBoard(gameId));
        }


        if(commandType.equals("back_select")){
            return new JSONObject(gameController.deselectCard(gameId));
        }
        if(commandType.equals("show_selected_card")){
            return new JSONObject(gameController.getSelectedCard(gameId));
        }

        if(commandType.equals("increase_money")){
            return new JSONObject(programController.increaseMoney(userHash,Integer.parseInt( request.get("amount").toString())));
        }
        if(commandType.equals("set_winner")){
            String nickname = (String) request.get("who?");
            gameController.isRoundOver(gameId);
        }

        return null;
    }
}
