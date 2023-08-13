package bundlecore.relics;
import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import static bundlecore.BundleCoreMain.makeID;

public class TinyDewUncommon  extends BundleRelic implements BundleChecker {

    private static final String NAME = "TinyDewUncommon";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.UNCOMMON;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    private static boolean payoutTime = false;
    public TinyDewUncommon() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = 0;
        this.grayscale = false;
        payoutTime = false;
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

    public static void fixConcurrentBS4(){
        if (payoutTime){
            AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, r);
            payoutTime = false;
        }
    }

    @Override
    public void onUsePotion() {
        this.counter++;
        if (this.counter == 3){
            beginLongPulse();
        }
        if (this.counter >= 4){
            sPulse();
            flash();
            this.payoutTime = true;
            this.counter = 0;
        }
    }

    public boolean canSpawn(){
        return (isAscension20CollectDied() && isQuestRelicEnabled());
    }
}
