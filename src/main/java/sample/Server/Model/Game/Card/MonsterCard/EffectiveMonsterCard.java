package sample.Model.Game.Card.MonsterCard;

import sample.Model.Game.Card.MonsterCard.Effect.*;

public class EffectiveMonsterCard extends MonsterCard{
    private Effect effect;

    public EffectiveMonsterCard(String name, String description, String number, String level, String atk, String def,String attribute, String type) {
        super(name, description, number, level, atk, def, attribute, type);
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }
}
