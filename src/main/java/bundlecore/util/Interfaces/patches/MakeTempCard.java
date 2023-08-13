package bundlecore.util.Interfaces.patches;

import basemod.ReflectionHacks;
import bundlecore.BundleCoreMain;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

/**
 * A hook to detect cards made mid combat.
 */
public class MakeTempCard {

    @SpirePatch(clz = ShowCardAndAddToDiscardEffect.class, method = "update")
    @SpirePatch(clz = ShowCardAndAddToHandEffect.class, method = "update")
    @SpirePatch(clz = ShowCardAndAddToDrawPileEffect.class, method = "update")
    public static class InDraw
    {
        public static void Prefix(AbstractGameEffect __instance)
        {
            if (__instance.duration == (float) ReflectionHacks.getPrivateStatic(__instance.getClass(), "EFFECT_DUR"))
            {
                BundleCoreMain.onGenerateCardMidcombat(ReflectionHacks.getPrivate(__instance, __instance.getClass(), "card"));
            }
        }
    }
}