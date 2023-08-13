package bundlecore.powers;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

/*public class FixedDrawNextTurnPower extends AbstractPower {
    public static final String POWER_ID = "Draw Card";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Draw Card");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FixedDrawNextTurnPower(AbstractCreature owner, int drawAmount) {
        this.name = NAME;
        this.ID = "Draw Card";
        this.owner = owner;
        this.amount = drawAmount;
        updateDescription();
        loadRegion("carddraw");
        this.priority = 20;
    }

    public void updateDescription() {
        if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void atStartOfTurn () {
        flash();
        addToBot(new DrawCardAction(this.owner, this.amount));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Draw Card"));
    }
}*/
