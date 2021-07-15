package sample.Controller;


import sample.Model.Game.Card.MonsterCard.Mode;
import sample.Model.Game.CardAddress;
import org.json.JSONObject;

public class API {
    private static boolean justOneObject = false;
    private GameController gameController;
    private ProgramController programController;
    private String request;

    public API(){
        assert !justOneObject;
        justOneObject = true;
        programController = new ProgramController();
        gameController = new GameController(programController);
    }

    public JSONObject run(JSONObject request) throws Exception {
        String commandType = request.getString("command");
        //import cards
        if(commandType.equals("duelNewGame")){
            String opponent = (String) request.get("opponent");
            //int rounds = Integer.parseInt(request.get("round").toString());
            int rounds = 1;
            return new JSONObject(programController.createDuel(opponent,rounds,gameController));
            //ai
        }

        if(commandType.equals("add_card_to_hand")){
            return new JSONObject(gameController.addCardFromDeckToHand());
        }

        if(commandType.equals("nextPhase")) {
            return new JSONObject(gameController.nextPhase());
        }

        if(commandType.equals("get_phase")){
            return new JSONObject(gameController.getPhase());
        }

        if(commandType.equals("selectCard")){
            String zone = (String) request.get("zone");
            CardAddress selectedCardAddress = null;
            switch (zone){
                case "monster_zone":
                    if(!(Boolean.parseBoolean( request.get("isActivePlayer").toString())))
                       selectedCardAddress = CardAddress.MONSTER_ZONE;
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
                        selectedCardAddress = CardAddress.OPPONENT_FIELD_ZONE;
                    break;
            }
            return new JSONObject(gameController.selectCard(selectedCardAddress, Integer.parseInt(request.get("id").toString())));
        }

        if(commandType.equals("summon")){
            return new JSONObject(gameController.summonMonster());
        }

        if(commandType.equals("tribute")){
            int numberOfTribute =  Integer.parseInt((String) request.get("numberOfTributes"));
            if(numberOfTribute == 1){
                int address1 = Integer.parseInt((String) request.get("address1"));
                return new JSONObject(gameController.getTributeForSummonMonster(address1));
            }
            else{
                int address1 = Integer.parseInt((String) request.get("address1"));
                int address2 = Integer.parseInt((String) request.get("address2"));
                return new JSONObject(gameController.getTributesForSummonMonster(address1,address2));
            }
        }

        if(commandType.equals("setMonster")){
            return new JSONObject(gameController.setMonster());
        }

        if(commandType.equals("setMonsterMode")){
            return new JSONObject(gameController.changeMonsterMode());
        }

        if(commandType.equals("setSpell")){
            gameController.setSpellAndTrap();
        }

        if(commandType.equals("activeEffect")){
            return new JSONObject(gameController.activateEffect());
            //check
        }

        if(commandType.equals("isGameOver")){
            return new JSONObject(gameController.isRoundOver());
        }

        if(commandType.equals("attack")){
            return new JSONObject(gameController.attack(Integer.parseInt(String.valueOf(request.get("id")))));
        }

        if(commandType.equals("directAttack")){
            return new JSONObject(gameController.directAttack());
        }


        if(commandType.equals("get_board")){
            return new JSONObject(gameController.getBoard());
        }

        if(commandType.equals("show_scorboard")){
            return new JSONObject(programController.showScoreboard());
        }
        if(commandType.equals("buyCard")){
            return new JSONObject(programController.buyCard((String) request.get("cardName")));
        }
        if(commandType.equals("shop_show_all")) {
            return new JSONObject(programController.showShopCards());
        }
        if(commandType.equals("crate_deck")){
            return new JSONObject(programController.createDeck((String) request.get("deckName")));
        }
        if(commandType.equals("delete_deck")){
            return new JSONObject(programController.deleteDeck((String) request.get("deckName")));
        }
        if(commandType.equals("set_deck_activate")){
            return new JSONObject(programController.selectActiveDeck((String) request.get("deckName")));
        }
        if(commandType.equals("show_deck_all")){
            return new JSONObject(programController.showAllDeck());
        }
        if(commandType.equals("back_select")){
            return new JSONObject(gameController.deselectCard());
        }
        if(commandType.equals("show_selected_card")){
            return new JSONObject(gameController.getSelectedCard());
        }


        if(commandType.equals("show_graveyard")){
            return new JSONObject(gameController.getGraveyard());
        }
        if(commandType.equals("increase_money")){
            return new JSONObject(programController.increaseMoney( Integer.parseInt( request.get("amount").toString())));
        }
        if(commandType.equals("set_winner")){
            //what to do
        }

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
        if(commandType.equals("change_Profile_nickname")){
            return new JSONObject(programController.changeNickname((String) request.get("nickname")));
        }
        if(commandType.equals("change_Profile_password")){
            return new JSONObject(programController.changePassword((String) request.get("currentPass"),(String) request.get("newPass")));
        }
        if(commandType.equals("add_card_deck")){
            String deckName = (String) request.get("deckName");
            String cardName = (String) request.get("cardName");
            boolean isSideDeck = !(Boolean.parseBoolean( request.get("main_side_?").toString()));
            return new JSONObject(programController.addCardToDeck(cardName,deckName,isSideDeck));
        }
        if(commandType.equals("remove_card_deck")){
            String deckName = (String) request.get("deckName");
            String cardName = (String) request.get("cardName");
            boolean isSideDeck = !(Boolean.parseBoolean( request.get("main_side_?").toString()));
            return new JSONObject(programController.removeCardFromDeck(cardName,deckName,isSideDeck));
        }
        if(commandType.equals("show_deck")){
            String deckName = (String) request.get("deckName");
            boolean isSideDeck = !(Boolean.parseBoolean( request.get("main_side_?").toString()));
            return new JSONObject(programController.showDeck(deckName,isSideDeck));
        }
        if(commandType.equals("show_deck_all")){
            return new JSONObject(programController.showAllDeck());
        }

        if(commandType.equals("set_winner")){
            String nickname = (String) request.get("who?");
            gameController.isRoundOver();
        }

        return null;
    }
}
