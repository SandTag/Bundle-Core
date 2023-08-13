package bundlecore.relics;

import bundlecore.bundleloadedbools.BundleChecker;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.FireBreathingPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static bundlecore.BundleCoreMain.makeID;

public class PhoCommon extends BundleRelic implements BundleChecker {

    private static final String NAME = "PhoCommon";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.COMMON;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public PhoCommon() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        pl().increaseMaxHp(3, true);
    }

    public void atTurnStart() {
        addToTop(new ApplyPowerAction(pl(), null, new FireBreathingPower(pl(), 1), 1, true));
    }

    public boolean canSpawn(){
        return (isBFoodXTerra());
    }

}
