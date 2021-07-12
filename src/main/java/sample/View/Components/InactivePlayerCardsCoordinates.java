package sample.View.Components;

import javafx.geometry.Point2D;

public class InactivePlayerCardsCoordinates {
    public static final Point2D[] monsterZone;
    public static final Point2D[] spellZone;
    public static final Point2D deckZone;
    public static final Point2D fieldZone;
    public static final Point2D graveYard;
    public static final Point2D userInfo;
    public static final Point2D startHand;

    static {
        deckZone = new Point2D(461,105);
        graveYard = new Point2D(461,377);
        fieldZone = new Point2D(1397,377);
        userInfo = new Point2D(1385,174);
        startHand = new Point2D(614,19);

        spellZone = new Point2D[5];
        spellZone[0] = new Point2D(929,174);
        spellZone[1] = new Point2D(773,174);
        spellZone[2] = new Point2D(1085,174);
        spellZone[3] = new Point2D(617,174);
        spellZone[4] = new Point2D(1240,174);

        monsterZone = new Point2D[5];
        monsterZone[0] = new Point2D(929,310);
        monsterZone[1] = new Point2D(773,310);
        monsterZone[2] = new Point2D(1085,310);
        monsterZone[3] = new Point2D(617,310);
        monsterZone[4] = new Point2D(1240,310);
    }
}
