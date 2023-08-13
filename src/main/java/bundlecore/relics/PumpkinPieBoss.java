package bundlecore.relics;

import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static bundlecore.BundleCoreMain.makeID;

public class PumpkinPieBoss extends BundleRelic implements BundleChecker {

    private static final String NAME = "PumpkinPieBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public PumpkinPieBoss() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        pl().increaseMaxHp(6, true);
        BaseMod.MAX_HAND_SIZE += 2;
    }

    @Override
    public void onUnequip() {
        BaseMod.MAX_HAND_SIZE -= 2;
    }

    @Override
    public void atBattleStart(){
        addToBot(new GainEnergyAction(3));
        addToBot(new DrawCardAction(5));
    }

    public boolean canSpawn(){
        return (isBFoodXTerra());
    }

}
