package sample.View.Graphic;

import javafx.application.Application;
import javafx.stage.Stage;

public class CardMenu extends Application {



    @Override
    public void start(Stage stage) throws Exception {

    }


/*
    public void showDeck(String command) throws Exception {

        ShowDeck showDeck = new ShowDeck();
        ShowDeck showDeck1 = (ShowDeck) showDeck.run(command);

        if (Boolean.toString(showDeck1.cards) == null && showDeck1.deckName != null) {

            JSONObject response
                    = js_Pass("command", "show_deck", "deckName", showDeck1.deckName, "main_side_?",  Boolean.toString( showDeck1.side));

            if (response.get("type").equals("error")) System.out.println(response.get("message"));

            else {
                Gson gson = new Gson();
                ShowDeckJson Deck = gson.fromJson((JsonElement) response.get("message"), ShowDeckJson.class);
                showDeck(Deck);
            }
        } else if (Boolean.toString(showDeck1.cards) != null && showDeck1.deckName != null && Boolean.toString(showDeck1.side) == null) {
            JSONObject response
                    = js_Pass("command", "show_deck_all");
            if (response.get("type").equals("error")) System.out.println(response.get("message"));

            else {
                Gson gson = new Gson();
                ShowAllDecksJson allDecks = gson.fromJson((JsonElement) response.get("message"), ShowAllDecksJson.class);
                showAllDecks(allDecks);
            }

        } else System.out.println("invalid command");
    }


    public void removeCardOfDeck(String command) throws Exception {
        AddCardToDeck addCardToDeck = new AddCardToDeck();
        AddCardToDeck addCardToDeck1 = (AddCardToDeck) addCardToDeck.run(command);

        StringBuilder sb = new StringBuilder();
        String cardName;
        for (String nameSpell:addCardToDeck1.cardName){
            sb.append(nameSpell);
            sb.append(" ");
        }

        cardName = sb.toString().trim();

        JSONObject response
                = js_Pass("command", "remove_card_deck", "deckName", addCardToDeck1.deckName, "cardName",cardName , "main_side_?",  Boolean.toString(addCardToDeck1.side));

        if (response
                .get("type").equals("error")) System.out.println(response
                .get("message"));

        else {
            System.out.println(response
                    .get("message"));
        }
    }

    public void addCardToDeck(String command) throws Exception {
        AddCardToDeck addCardToDeck = new AddCardToDeck();
        AddCardToDeck addCardToDeck1 = (AddCardToDeck) addCardToDeck.run(command);

        StringBuilder sb = new StringBuilder();
        String cardName;
        for (String nameSpell:addCardToDeck1.cardName){
            sb.append(nameSpell);
            sb.append(" ");
        }

        cardName = sb.toString().trim();


        JSONObject response = js_Pass("command", "add_card_deck", "deckName",
                addCardToDeck1.deckName, "cardName",cardName , "main_side_?",  Boolean.toString(addCardToDeck1.side));

        if (response.get("type").equals("error")) System.out.println(response.get("message"));

        else {
            System.out.println(response
                    .get("message"));
        }
    }

    public void showDeck(ShowDeckJson object) {
        System.out.println("Deck: " + object.getName());
        if (object.isSideDeck()) System.out.println("Side deck:");
        else System.out.println("Main deck:");
        System.out.println("Monsters:");
        ///print monsters in irderd way

        LinkedList<CardGeneralInfo> orderdMonsters = new LinkedList<>(object.getMonsters());
        LinkedList<CardGeneralInfo> interChange = new LinkedList<>();
        int quantity = orderdMonsters.size();
        for (int i = 0; i <= quantity - 2; i++) {
            for (int j = i + 1; i <= quantity - 1; i++) {

                if (orderdMonsters.get(i).getName().compareTo(orderdMonsters.get(j).getName()) < 0) {
                    interChange.add(orderdMonsters.get(j));
                    orderdMonsters.set(j, orderdMonsters.get(i));
                    orderdMonsters.set(i, interChange.get(0));
                    interChange.clear();
                }

            }
        }
        for (CardGeneralInfo card :
                orderdMonsters) {
            System.out.println(card.getName() + ":" + card.getDescription());
        }

//spell and traps

        LinkedList<CardGeneralInfo> orderdSpellsAndTraps = new LinkedList<>(object.getSpellAndTraps());

        int quantity1 = orderdSpellsAndTraps.size();
        for (int i = 0; i <= quantity1 - 2; i++) {
            for (int j = i + 1; i <= quantity1 - 1; i++) {

                if (orderdSpellsAndTraps.get(i).getName().compareTo(orderdSpellsAndTraps.get(j).getName()) < 0) {
                    interChange.add(orderdSpellsAndTraps.get(j));
                    orderdSpellsAndTraps.set(j, orderdSpellsAndTraps.get(i));
                    orderdSpellsAndTraps.set(i, interChange.get(0));
                    interChange.clear();
                }

            }
        }
        for (CardGeneralInfo card :
                orderdSpellsAndTraps) {
            System.out.println(card.getName() + ":" + card.getDescription());
        }
    }


    public void showAllDecks(ShowAllDecksJson object) {
        System.out.println("Decks:");
        System.out.println("Active deck:");
        if (object.activeDeck != null) {
            //valid boodan nist
            System.out.println(object.activeDeck.name + ":" + " main deck " + object.activeDeck.mainDeckSize + "," + "side deck " + object.activeDeck.sideDeckSize);
        }
        System.out.println("Other decks:");

        for (DeckGeneralInfo object1 : object.decks) {
            System.out.println(object1.name + ":" + " main deck " + object1.mainDeckSize + "," + "side deck " + object1.sideDeckSize);
            System.out.println();

        }


    }

 */

}
