package bundlecore.patches;

import bundlecore.BundleCoreMain;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

/**
 * Makes "DeadCells" AMP compliant.
 */
@SpirePatch(cls = "deadCellsMod.cn.infinite.stsmod.patch.MainMenuPatch$ArtPatch", method = "BackgroundTexturePatch", requiredModId = "DeadCells")
public class PatchPatchPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> ReturnNullKekw() {
        if (AbstractMenuPriority.priorityMenu >= 6 && AbstractMenuPriority.priorityMenu != 69420 && !BundleCoreMain.IsDeadConfig) {
            return SpireReturn.Return();
        }
        else if (BundleCoreMain.IsDeadConfig) {
            return SpireReturn.Continue();
        } else {
            return SpireReturn.Return();
        }
    }
}

/**
 * Part 2 of the same patch.
 */
@SpirePatch(cls = "deadCellsMod.cn.infinite.stsmod.patch.MainMenuPatch", method = "setMainMenuBG", requiredModId = "DeadCells")
class PatchPatchPatchII {
    @SpirePrefixPatch
    public static SpireReturn<Void> ReturnNullKekw() {
        if (AbstractMenuPriority.priorityMenu >= 6 && AbstractMenuPriority.priorityMenu != 69420 && !BundleCoreMain.IsDeadConfig) {
            return SpireReturn.Return();
        }
        else if (BundleCoreMain.IsDeadConfig) {
            return SpireReturn.Continue();
        } else {
            return SpireReturn.Return();
        }
    }
}
