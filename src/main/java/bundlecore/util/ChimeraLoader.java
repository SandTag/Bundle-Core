package bundlecore.util;
import CardAugments.CardAugmentsMod;
import bundlecore.cardmodifiers.chimeracardscrossover.*;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import static bundlecore.BundleCoreMain.makeID;

@Dont_Use_This_Externally
public class ChimeraLoader {
    @Dont_Use_This_Externally
    public static void initialiseCardAugments(){
        //Register mods
        if (Loader.isModLoaded("CardAugments")) {
            CardAugmentsMod.registerMod("bundlecore", CardCrawlGame.languagePack.getUIString(makeID("ModConfigs")).TEXT[0]);
            CardAugmentsMod.registerAugment(new BootyModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new FartModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new ScreensModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new UnstableModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new OverextendedModifier(), "bundlecore");

            CardAugmentsMod.registerAugment(new BottledMod(), "bundlecore");
            CardAugmentsMod.registerAugment(new CantrippedModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new ShoppingModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new SlimyModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new BongModifier(), "bundlecore");

            CardAugmentsMod.registerAugment(new FriedChickenModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new BreweryModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new FlashbangModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new NormalisedModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new ChokingModifier(), "bundlecore");

            CardAugmentsMod.registerAugment(new VenomousModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new ChaoticOrbsModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new PerformingModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new SoupModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new FractalModifier(), "bundlecore");

            CardAugmentsMod.registerAugment(new ScaldingModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new PainfulModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new RepulsiveModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new FoxyModifier(), "bundlecore");
            CardAugmentsMod.registerAugment(new ConclusiveModifier(), "bundlecore");

            if (Loader.isModLoaded("Bundle_Of_Peglin")) {
                CardAugmentsMod.registerAugment(new OrbModifier(), "bundlecore");
            }
        }
    }

}
