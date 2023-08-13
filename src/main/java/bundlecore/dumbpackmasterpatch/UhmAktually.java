package bundlecore.dumbpackmasterpatch;

import basemod.ReflectionHacks;
import bundlecore.bundleloadedbools.BundleChecker;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import thePackmaster.cards.pinnaclepack.*;

/**
 * It would have been much better that this just be included as a PR to improve the (my) pack, however I cannot be arsed with the BM from the devs of PM.
 */
public class UhmAktually implements BundleChecker {

        @SpirePatch2(requiredModId ="anniv5", clz = ElectromagneticBurstRarePower.class, method = "<ctor>")
        public static class NoThanks {
            private static final int MAGICFIX = 9;
            @SpirePostfixPatch
            public static void patch(AbstractPinnacleCard __instance) {
                __instance.magicNumber = __instance.baseMagicNumber = MAGICFIX;
            }
        }

    @SpirePatch2(requiredModId ="anniv5", clz = ElectromagneticBurstRarePower.class, method = "upp")
    public static class NoThankser {
        private static final int UPGRADE_MAGICFIX = 1;
        @SpireInsertPatch(rloc = 2)
        public static SpireReturn<?> patch(AbstractPinnacleCard __instance) {
            ReflectionHacks.privateMethod(AbstractCard.class, "upgradeMagicNumber", int.class).invoke(__instance, UPGRADE_MAGICFIX);
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(requiredModId ="anniv5", clz = MarvelousFeastUncommonPower.class, method = "upp")
    public static class FixCroquette1 {
        private static final int NEWCOST = 3;
        @SpireInsertPatch(rloc = 2)
        public static SpireReturn<?> patch(AbstractPinnacleCard __instance) {
            __instance.cost = NEWCOST;
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(requiredModId ="anniv5", clz = FriendshipCroquettesSpecialColourless.class, method = "upp")
    public static class FixCroquette2 {
        private static final int UPGRADE_BLOCKFIX = 3;
        private static final int UPGRADE_MAGICFIX = 1;
        @SpireInsertPatch(rloc = 2)
        public static SpireReturn<?> patch(AbstractPinnacleCard __instance) {
            ReflectionHacks.privateMethod(AbstractCard.class, "upgradeBlock", int.class).invoke(__instance, UPGRADE_BLOCKFIX);
            ReflectionHacks.privateMethod(AbstractCard.class, "upgradeMagicNumber", int.class).invoke(__instance, UPGRADE_MAGICFIX);
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(requiredModId ="anniv5", clz = FishyCroquettesSpecialColourless.class, method = "upp")
    public static class FixCroquette3 {
        private static final int UPGRADE_MAGICFIX = 1;
        @SpireInsertPatch(rloc = 2)
        public static SpireReturn<?> patch(AbstractPinnacleCard __instance) {
            ReflectionHacks.privateMethod(AbstractCard.class, "upgradeMagicNumber", int.class).invoke(__instance, UPGRADE_MAGICFIX);
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(requiredModId ="anniv5", clz = MeatyCroquettesSpecialColourless.class, method = "upp")
    public static class FixCroquette4 {
        private static final int UPGRADE_MAGICFIX = 1;
        @SpireInsertPatch(rloc = 2)
        public static SpireReturn<?> patch(AbstractPinnacleCard __instance) {
            ReflectionHacks.privateMethod(AbstractCard.class, "upgradeMagicNumber", int.class).invoke(__instance, UPGRADE_MAGICFIX);
            return SpireReturn.Return();
        }
    }

}
