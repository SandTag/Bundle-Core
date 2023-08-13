package bundlecore.relics;

import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.functions.potionOnDiscard.DiscardPotionRelic;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static bundlecore.BundleCoreMain.makeID;

public class BottleCleanerCommon extends BundleRelic implements BundleChecker, DiscardPotionRelic {

    private static final String NAME = "BottleCleanerCommon";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.COMMON;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public BottleCleanerCommon() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
        setDescriptionWithCard();
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void setDescriptionWithCard() {
        tips.clear();

        description = DESCRIPTIONS[0];
        this.tips.add(new PowerTip(this.name, this.description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(DESCRIPTIONS[1]), BaseMod.getKeywordDescription(DESCRIPTIONS[1])));

        initializeTips();
    }

    @Override
    public void onDiscardPotionAdvanced(AbstractPotion p) {
        pl().potionSlots ++;
        pl().potions.add(new PotionSlot(pl().potionSlots - 1));
        for (AbstractPotion y : pl().potions){
            y.initializeData();}
    }

    public boolean canSpawn(){
        return (isCollectDied() && isQuestRelicEnabled());
    }
}