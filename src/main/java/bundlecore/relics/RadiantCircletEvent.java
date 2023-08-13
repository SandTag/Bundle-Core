package bundlecore.relics;
import bundlecore.bundleloadedbools.BundleChecker;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import static bundlecore.BundleCoreMain.makeID;

public class RadiantCircletEvent extends BundleRelic implements BundleChecker {

    private static final String NAME = "RadiantCircletEvent";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.SPECIAL;
    private static final AbstractRelic.LandingSound SOUND = LandingSound.FLAT;

    public RadiantCircletEvent() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        pl().masterHandSize++;
    }

    @Override
    public void onUnequip() {
        pl().masterHandSize--;
    }

    public boolean canSpawn() {
        return isQuestRelicEnabled();
    }

}
