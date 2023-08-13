package bundlecore.jankymonsters;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class BlurMonsterPower extends AbstractPower {
    public static final String POWER_ID = "bundlecore:BlurMonsterPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Blur");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    /**
     * Blur that works for monsters, text update not included.
     */
    public BlurMonsterPower(AbstractCreature owner, int amount, boolean playerControlled) {
        this.name = NAME;
        this.ID = "bundlecore:BlurMonsterPower";
        this.owner = owner;
        this.amount = amount;
        this.description = DESCRIPTIONS[0];
        loadRegion("blur");
        this.isTurnBased = true;
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void atEndOfRound() {
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

/*    public void onActivation() {
        if (this.owner.currentBlock >= 1) {
            int blockAmnt = this.owner.currentBlock;
            addToBot(new ApplyPowerAction(this.owner, this.owner, new NextTurnBlockPower(this.owner, blockAmnt), blockAmnt));
        }
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }*/

}
