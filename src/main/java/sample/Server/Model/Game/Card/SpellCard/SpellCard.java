package sample.Model.Game.Card.SpellCard;

import sample.Model.Game.*;
import sample.Model.Game.Card.*;
import sample.Model.Game.Card.SpellCard.Spell.*;

public class SpellCard extends Card{
    private Spell spell;
    private SpellCategory spellCategory;
    private Icon icon;
    boolean isActivateInTurn;

    public SpellCard(String name,String description,String number,String spellCategory,String icon){
        setName(name);
        setDescription(description);
        setNumber(number);
        setSpellCategory(spellCategory);
        setIcon(icon);
        setSpell(name);
    }

    public void activate(Game game,String cardName){
        this.getSpell().activate(game);
        this.getSpell().activate(game, cardName);
        if(!(this.getIcon().equals(Icon.EQUIP) || this.getIcon().equals(Icon.CONTINUOUS))) game.getActivePlayer().getField().killSpellCard(this);
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpellCategory(SpellCategory spellCategory) {
        this.spellCategory = spellCategory;
    }

    public SpellCategory getSpellCategory() {
        return spellCategory;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Icon getIcon() {
        return icon;
    }

    public boolean isActivateInTurn() {
        return isActivateInTurn;
    }

    public void setActivateInTurn(boolean activateInTurn) {
        isActivateInTurn = activateInTurn;
    }

    public void setSpellCategory(String spellCategory){
        switch (spellCategory) {
            case "Spell" :
                this.spellCategory = SpellCategory.SPELL;
            case "Trap" :
                this.spellCategory = SpellCategory.TRAP;
        }
    }

    public void setIcon(String icon){
        switch (icon) {
            case "Normal" :
                this.icon = Icon.NORMAL;
            case "Equip" :
                this.icon = Icon.EQUIP;
            case "Field" :
                this.icon = Icon.FIELD;
            case "Ritual" :
                this.icon = Icon.RITUAL;
            case "Continuous" :
                this.icon = Icon.CONTINUOUS;
            case "Quick-play" :
                this.icon = Icon.QUICK_PLAY;
            case "Counter" :
                this.icon = Icon.COUNTER;
        }
    }

    public void setSpell(String name){
        switch (name) {
            case "Trap Hole" :
                this.spell = new TrapHole();
            case "Mirror Force" :
                this.spell = new MirrorForce();
            case "Magic Cylinder" :
                this.spell = new MagicCylinder();
            case "Mind Crush" :
                this.spell = new MindCrush();
            case "Torrential Tribute" :
                this.spell = new TorrentialTribute();
            case "Negate Attack" :
                this.spell = new NegateAttack();
            case "Solemn Warning" :
                this.spell = new SolemnWarning();
            case "Call of The Haunted" :
                this.spell = new CallOfTheHaunted();
            case "Monster Reborn" :
                this.spell = new MonsterReborn();
            case "Terraforming" :
                this.spell = new Terraforming();
            case "Pot of Greed" :
                this.spell = new PotOfGreed();
            case "Raigeki" :
                this.spell = new Raigeki();
            case "Swords of Revealing Light" :
                this.spell = new SwordsOfRevealingLight();
            case "Harpie's Feather Duster" :
                this.spell = new HarpiesFeatherDuster();
            case "Dark Hole" :
                this.spell = new DarkHole();
            case "Mystical space typhoon" :
                this.spell = new MysticalSpaceTyphoon();
            case "Yami" :
                this.spell = new Yami();
            case "Forest" :
                this.spell = new Forest();
            case "Umiiruka" :
                this.spell = new Umiiruka();
            case "Sword of dark destruction" :
                this.spell = new SwordOfDarkDestruction();
            case "Black Pendant" :
                this.spell = new BlackPendant();
            case "Advanced Ritual Art" :
                this.spell = new AdvancedRitualArt();
        }
    }
}
