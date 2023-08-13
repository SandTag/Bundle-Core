package bundlecore.relics;

import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import static bundlecore.BundleCoreMain.makeID;

public class AncientMeteoriteBoss extends BundleRelic implements BundleChecker {

    private static final String NAME = "AncientMeteoriteBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = AbstractRelic.RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public AncientMeteoriteBoss() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip()
    {
        pl().energy.energyMaster--;
        BaseMod.MAX_HAND_SIZE ++;
        this.counter = -1;
    }

    @Override
    public void onUnequip(){
        pl().energy.energyMaster++;
        BaseMod.MAX_HAND_SIZE --;
    }

    @Override
    public void atTurnStart(){
        addToBot(new DrawCardAction(5));
    }

    public boolean canSpawn(){
        return (isBFoodXPeg() && AbstractDungeon.floorNum >= 2);
    }

}
