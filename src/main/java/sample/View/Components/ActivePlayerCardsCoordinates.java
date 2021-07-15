package sample.View.Components;

import javafx.geometry.Point2D;

public class ActivePlayerCardsCoordinates {
    public static final Point2D[] monsterZone;
    public static final Point2D[] spellZone;
    public static final Point2D deckZone;
    public static final Point2D fieldZone;
    public static final Point2D graveYard;
    public static final Point2D userInfo;
    public static final Point2D startHand;

    static {
        deckZone = new Point2D(1397,774);
        graveYard = new Point2D(1397,507);
        fieldZone = new Point2D(461,507);
        userInfo = new Point2D(394,669);
        startHand = new Point2D(620,852);

        spellZone = new Point2D[5];
        spellZone[0] = new Point2D(929,707);
        spellZone[1] = new Point2D(1085,707);
        spellZone[2] = new Point2D(773,707);
        spellZone[3] = new Point2D(1240,707);
        spellZone[4] = new Point2D(617,707);

        monsterZone = new Point2D[5];
        monsterZone[0] = new Point2D(929,570);
        monsterZone[1] = new Point2D(1085,570);
        monsterZone[2] = new Point2D(773,570);
        monsterZone[3] = new Point2D(1240,570);
        monsterZone[4] = new Point2D(617,570);
    }
}
