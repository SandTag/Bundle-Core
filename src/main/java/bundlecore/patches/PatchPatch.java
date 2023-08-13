package bundlecore.patches;

import bundlecore.BundleCoreMain;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

/**
 * Makes hubris AMP compliant.
 */
@SpirePatch(cls = "com.evacipated.cardcrawl.mod.hubris.patches.LogoPatch", method = "Postfix", requiredModId = "hubris")
public class PatchPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> ReturnNullKekw() {
        if (AbstractMenuPriority.priorityMenu != 0 && AbstractMenuPriority.priorityMenu != 69420 && !BundleCoreMain.IsHubrisConfig) {
            return SpireReturn.Return();
        }
        else if (BundleCoreMain.IsHubrisConfig) {
            return SpireReturn.Continue();
        } else {
            return SpireReturn.Return();
        }
    }
}