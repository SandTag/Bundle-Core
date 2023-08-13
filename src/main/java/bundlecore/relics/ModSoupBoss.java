package bundlecore.relics;
import basemod.AutoAdd;
import bundlecore.actions.RandomSoupAction;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.util.GFL;
import bundlecore.util.Interfaces.patches.OnCreateMidCombatCards;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.Arrays;
import java.util.List;
import static bundlecore.BundleCoreMain.makeID;

@AutoAdd.Ignore
public class ModSoupBoss extends BundleRelic implements BundleChecker, OnCreateMidCombatCards {
    private static final String NAME = "ModSoupBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;
    private static Boolean SoupTime = false;
    public ModSoupBoss(){
        super(ID, NAME, RARITY, SOUND);
        this.counter = Loader.MODINFOS.length;
    }

    private final List<String> NaughtyCards = Arrays.asList("Reflex", "Tactician", "DeusExMachina", "BecomeAlmighty", "Calm", "Wrath", "FameAndFortune", "LiveForever");

    @Override
    public void onEquip() {
        SoupTime();
        if (SoupTime) {
            pl().increaseMaxHp(14, true);
        }
        pl().energy.energyMaster++;
    }

    @Override
    public void onUnequip(){
        pl().energy.energyMaster--;
    }

    private void SoupTime(){
        if (Loader.MODINFOS.length >= 50) {
            SoupTime = true;
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        GFL.atb(new RandomSoupAction());
    }

    @Override
    public String getUpdatedDescription() {
        SoupTime();
        if (!SoupTime) {
            return this.DESCRIPTIONS[0];
        }
        else{
            return this.DESCRIPTIONS[1];
        }
    }

    @Override
    public boolean canSpawn(){
        return (isChimeraMoment());
    }
}
