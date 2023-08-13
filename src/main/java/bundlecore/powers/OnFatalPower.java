package bundlecore.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static bundlecore.BundleCoreMain.makeID;

/*public class OnFatalPower  extends BundlePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID("OnFatalPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public OnFatalPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount >= target.currentHealth && target != this.owner) {
            flash();
            // Do thing
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new OrbCritPower(owner, amount);
    }

}
*/