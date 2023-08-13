package bundlecore.jankymonsters;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FixedAfterImagePower extends AbstractPower {
    public static final String POWER_ID = "bundlecore:FixedAfterImagePower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("After Image");

    /**
     * A simple fixed version of after image for monsters.
     */
    public FixedAfterImagePower(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = "bundlecore:FixedAfterImagePower";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("afterImage");
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (Settings.FAST_MODE) {
            addToBot(new GainBlockAction(this.owner, this.owner, this.amount, true));
        } else {
            addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
        }
        flash();
    }
}
