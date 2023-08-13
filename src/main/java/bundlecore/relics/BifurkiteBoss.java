package bundlecore.relics;
import basemod.AutoAdd;
import basemod.helpers.CardModifierManager;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import static bundlecore.BundleCoreMain.makeID;

@AutoAdd.Ignore
public class BifurkiteBoss extends BundleRelic implements BundleChecker {
    private static final String NAME = "BifurkiteBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public BifurkiteBoss(){
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
    }

    @Override
    public void atBattleStart() {
        this.counter = 1;
        this.grayscale = false;
    }

    @Override
    public void atTurnStart() {
        this.counter = 1;
        this.grayscale = false;
        flash();
    }

    @Override
    public void onUseCard(final AbstractCard c, final UseCardAction action) {
        if (CardModifierManager.modifiers(c).size() != 0 && this.counter >= 1) {
            this.counter = 0;
            GFL.atb(new GainEnergyAction(1));
            GFL.atb(new RelicAboveCreatureAction(GFL.GAP(), this));
            this.grayscale = true;
        }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
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
