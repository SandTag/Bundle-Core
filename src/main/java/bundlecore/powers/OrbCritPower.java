package bundlecore.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import static bundlecore.BundleCoreMain.makeID;

public class OrbCritPower extends BundlePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID("OrbCritPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public OrbCritPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new OrbCritPower(owner, amount);
    }

}
