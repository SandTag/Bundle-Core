package bundlecore.relics;

import basemod.AutoAdd;
import basemod.helpers.CardModifierManager;
import bundlecore.bundleloadedbools.BundleChecker;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import static bundlecore.BundleCoreMain.makeID;

@AutoAdd.Ignore
public class DualityDraughtRare extends BundleRelic implements BundleChecker {
    private static final String NAME = "DualityDraughtRare";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.RARE;
    private static final AbstractRelic.LandingSound SOUND = LandingSound.HEAVY;

    public DualityDraughtRare(){
        super(ID, NAME, RARITY, SOUND);
        this.counter = 0;
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (CardModifierManager.modifiers(card).size() != 0) {
            this.counter++;
        }
        if (this.counter == 9){
            beginPulse();
        }
        if (this.counter >= 10){
            flash();
            addToBot(new RelicAboveCreatureAction(pl(), this));
            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(1));
            sPulse();
            this.counter = 0;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn(){
        return (isChimeraMoment());
    }
}
