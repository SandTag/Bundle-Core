package bundlecore.relics;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import bundlecore.bundleloadedbools.BundleChecker;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import static bundlecore.BundleCoreMain.makeID;

public class DefreshPotionBoss extends BundleRelic implements BundleChecker {

    private static final String NAME = "DefreshPotionBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = AbstractRelic.RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public DefreshPotionBoss() {
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
        pl().energy.energyMaster+=2;
        pl().masterHandSize+=3;
        BaseMod.MAX_HAND_SIZE+=2;
    }

    @Override
    public void onUnequip(){
        pl().energy.energyMaster-=2;
        pl().masterHandSize-=3;
        BaseMod.MAX_HAND_SIZE-=2;
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        flash();
        if (!CardModifierManager.hasModifier(card, "Bundle_Of_Peglin:TemporaryCardModRebound")){
            action.exhaustCard = true;
        }
    }

    public boolean canSpawn(){
        return (isBPotionsXPeg() && AbstractDungeon.actNum == 2);
    }
}
