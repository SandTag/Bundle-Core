package bundlecore.relics;

import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.orbs.TerraSlimeOrb;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static bundlecore.BundleCoreMain.makeID;

public class RedRoyalGelBoss extends BundleRelic implements BundleChecker {

    private static final String NAME = "RedRoyalGelBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public RedRoyalGelBoss() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart(){
        addToBot(new ChannelAction(new TerraSlimeOrb()));
        addToBot(new ChannelAction(new TerraSlimeOrb()));
        addToBot(new ChannelAction(new TerraSlimeOrb()));
    }

    public boolean canSpawn(){
        return (isBPegXTerra());
    }

}
