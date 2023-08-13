package bundlecore.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.MysteriousSphere;

/**
 * Not so much a bugfix more a consistency fix.
 */
public class MakeTwoOrbWalkersElite {

    @SpirePatch2(clz = MysteriousSphere.class, method = "buttonEffect")
    public static class AddEliteTrigger {
        @SpireInsertPatch(rloc = 26)
        public static void Insert() {
            (AbstractDungeon.getCurrRoom()).eliteTrigger = true;
        }
    }

}


