package bundlecore.powers;
import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import static bundlecore.BundleCoreMain.makeID;

public class AntifactPower extends BundlePower implements CloneablePowerInterface, OnReceivePowerPower {
    public static final String POWER_ID = makeID("AntifactPower");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    /**
     * The opposite of ArtifactPower.
     */
    public AntifactPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target == this.owner && power.type == AbstractPower.PowerType.BUFF) {
            CardCrawlGame.sound.play("NULLIFY_SFX");
            flashWithoutSound();
            if (this.amount == 1) {
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            } else {
                addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));
            }
            flashWithoutSound();
            return false;
        }
        return true;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AntifactPower(owner, owner, amount);
    }
}
