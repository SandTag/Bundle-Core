package bundlecore.relics;
import basemod.AutoAdd;
import basemod.helpers.CardModifierManager;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import static bundlecore.BundleCoreMain.makeID;

@AutoAdd.Ignore
public class CursedMirrorRare extends BundleRelic implements BundleChecker {
    private static final String NAME = "CursedMirrorRare";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.RARE;
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public CursedMirrorRare(){
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
    }

    @Override
    public void onUseCard(final AbstractCard c, final UseCardAction action) {
        if (CardModifierManager.modifiers(c).size() != 0 && this.counter >= 1) {
            this.counter = 0;
            AbstractCard duplicated = c.makeSameInstanceOf();
            GFL.atb(new MakeTempCardInDrawPileAction(duplicated, 1, false, true, true));
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
