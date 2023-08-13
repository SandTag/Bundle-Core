package bundlecore.powers;

import bundlecore.util.GFL;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * Unfortunately relics trigger before powers and this caused issues.
 */
public class PhysicalTimeWarpPatch {

    @SpirePatch2(clz = GameActionManager.class, method = "callEndOfTurnActions")
    public static class update {
        @SpireInsertPatch(rloc = 0, localvars = {""})
        public static void FixEndOfTurn() {
            for (AbstractPower p : GFL.GAP().powers){
                if (p instanceof PhysicalTimeWarpPower){
                    PhysicalTimeWarpPower.endTurnCall = true;
                    break;
                }
            }
        }
    }
}
