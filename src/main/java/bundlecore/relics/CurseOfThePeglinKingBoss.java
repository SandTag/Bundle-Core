package bundlecore.relics;

import bundlecore.bundleloadedbools.BundleChecker;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static bundlecore.BundleCoreMain.makeID;

public class CurseOfThePeglinKingBoss extends BundleRelic implements BundleChecker {

    private static final String NAME = "CurseOfThePeglinKingBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = AbstractRelic.RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public CurseOfThePeglinKingBoss() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        pl().potionSlots ++;
        pl().potions.add(new PotionSlot(pl().potionSlots - 1));
    }

    public void atTurnStart() {
        flash();
        addToTop(new MakeTempCardInDrawPileAction(new Miracle(), 1, false, true));
    }

    public boolean canSpawn(){
        return (isBPotionsXPeg() && AbstractDungeon.actNum <= 1);
    }
}
