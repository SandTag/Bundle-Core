package bundlecore.patches.pegmenu.ActPatch;

import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.patches.AbstractMenuPriority;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

/**
 * Can be used as an example of what is required to change the music theme.
 */
@Dont_Use_This_Externally
public class PeglinPoglinMusicPatch extends AbstractMenuPriority {

    @SpirePatch(clz = TempMusic.class, method = "getSong")
    public static class BossMusicExordiumReplacer {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, TempMusic __instance, String key) {
            if (checkingPriorityBoss() && priorityBoss == 1) {
                if (key.equals("BOSS_BOTTOM"))
                    return MainMusic.newMusic("bundlecore/music/peg/PegBoss1.mp3");
            }
            return __result;
        }
    }

    @SpirePatch(clz = TempMusic.class, method = "getSong")
    public static class BossMusicCityReplacer {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, TempMusic __instance, String key) {
            if (checkingPriorityBoss() && priorityBoss == 1) {
                if (key.equals("BOSS_CITY"))
                    return MainMusic.newMusic("bundlecore/music/peg/PegBoss2.mp3");
            }
            return __result;
        }
    }

    @SpirePatch(clz = TempMusic.class, method = "getSong")
    public static class BossMusicBeyondReplacer {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, TempMusic __instance, String key) {
            if (checkingPriorityBoss() && priorityBoss == 1) {
                if (key.equals("BOSS_BEYOND"))
                    return MainMusic.newMusic("bundlecore/music/peg/PegBoss3.mp3");
            }
            return __result;
        }
    }



      //
    //////
      //



    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class HallAct1 {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, MainMusic __instance, String key) {
            if (checkingPriorityAct() && priorityAct == 1) {
                if (key.equals("Exordium"))
                    return MainMusic.newMusic("bundlecore/music/peg/PegAct1.mp3");
            }
            return __result;
        }
    }

    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class HallAct2 {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, MainMusic __instance, String key) {
            if (checkingPriorityAct() && priorityAct == 1) {
                if (key.equals("TheCity"))
                    return MainMusic.newMusic("bundlecore/music/peg/PegAct2.mp3");
            }
            return __result;
        }
    }

    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class HallAct3 {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, MainMusic __instance, String key) {
            if (checkingPriorityAct() && priorityAct == 1) {
                if (key.equals("TheBeyond"))
                    return MainMusic.newMusic("bundlecore/music/peg/PegAct3.mp3");
            }
            return __result;
        }
    }

}