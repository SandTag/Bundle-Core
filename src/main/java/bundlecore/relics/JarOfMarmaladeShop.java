package bundlecore.relics;
import basemod.AutoAdd;
import bundlecore.actions.RandomSupplierAction;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import static bundlecore.BundleCoreMain.makeID;

@AutoAdd.Ignore
public class JarOfMarmaladeShop extends BundleRelic implements BundleChecker {
    private static final String NAME = "JarOfMarmaladeShop";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.SHOP;
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public JarOfMarmaladeShop(){
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
    }

    @Override
    public void onEquip()
    {
        AbstractDungeon.player.heal(6);
        AbstractDungeon.player.increaseMaxHp(1, true);
        this.grayscale = false;
    }

    @Override
    public void atTurnStartPostDraw() {
        GFL.atb(new RandomSupplierAction());
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