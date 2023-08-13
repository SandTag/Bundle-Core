package bundlecore.patches.terramenu;

import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.patches.AbstractMenuPriority;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.Objects;

/**
 * Can be used as an example of what is required to change the music theme.
 */
@Dont_Use_This_Externally
public class TerrariaMusicPatches extends AbstractMenuPriority {

    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class TerraHallAct1 {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, MainMusic __instance, String key) {
            if (checkingPriorityAct() && priorityAct == 2) {
                if (key.equals("Exordium")) {
                    if (AbstractDungeon.miscRng.random(1) == 0) {
                        return MainMusic.newMusic("bundlecore/music/terra/Act1A_side.mp3");
                    }
                    return MainMusic.newMusic("bundlecore/music/terra/Act1B_side.mp3");
                }
            }
            return __result;
        }
    }

    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class TerraHallAct2 {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, MainMusic __instance, String key) {
            if (checkingPriorityAct() && priorityAct == 2) {
                if (key.equals("TheCity")) {
                    if (AbstractDungeon.miscRng.random(1) == 0) {
                        return MainMusic.newMusic("bundlecore/music/terra/Act2A_side.mp3");
                    }
                    return MainMusic.newMusic("bundlecore/music/terra/Act2B_side.mp3");
                }
            }
            return __result;
        }
    }

    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class TerraHallAct3 {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, MainMusic __instance, String key) {
            if (checkingPriorityAct() && priorityAct == 2) {
                if (key.equals("TheBeyond")) {
                    if (AbstractDungeon.miscRng.random(1) == 0) {
                        return MainMusic.newMusic("bundlecore/music/terra/Act3A_side.mp3");
                    }
                    return MainMusic.newMusic("bundlecore/music/terra/Act3B_side.mp3");
                }
            }
            return __result;
        }
    }

    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class Act4S_S {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, MainMusic __instance, String key) {
            if (checkingPriorityAct() && priorityAct == 2) {
                if (key.equals("TheEnding"))
                    return MainMusic.newMusic("bundlecore/music/terra/Act4.mp3");
            }
            return __result;
        }
    }



    //
    /////
    //////////
    // Complex Specific Boss Patches Below
    /////////
    /////
    //



    @SpirePatch(clz = TempMusic.class, method = "getSong")
    public static class BossMusicExordiumReplacer {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, TempMusic __instance, String key) {
            if (checkingPriorityBoss() && priorityBoss == 2) {
                if (key.equals("BOSS_BOTTOM"))
                    if (AbstractDungeon.getCurrRoom() != null) {
                        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                            if (!m.isDeadOrEscaped()) {
                                if (Objects.equals(m.id, "TheGuardian")) {
                                    return MainMusic.newMusic("bundlecore/music/terra/MechanicalBoss.mp3");
                                }
                                if (Objects.equals(m.id, "Hexaghost")) {
                                    return MainMusic.newMusic("bundlecore/music/terra/MagicalBoss.mp3");
                                }
                                if (Objects.equals(m.id, "SlimeBoss")) {
                                    return MainMusic.newMusic("bundlecore/music/terra/RoyalBoss.mp3");
                                }
                            }
                        }
                    }
            }
            return __result;
        }
    }

    @SpirePatch(clz = TempMusic.class, method = "getSong")
    public static class BossMusicCityReplacer {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, TempMusic __instance, String key) {
            if (checkingPriorityBoss() && priorityBoss == 2) {
                if (key.equals("BOSS_CITY"))
                    if (AbstractDungeon.getCurrRoom() != null) {
                        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                            if (!m.isDeadOrEscaped()) {
                                if (Objects.equals(m.id, "BronzeAutomaton")) {
                                    return MainMusic.newMusic("bundlecore/music/terra/MechanicalBoss.mp3");
                                }
                                if (Objects.equals(m.id, "TheCollector")) {
                                    return MainMusic.newMusic("bundlecore/music/terra/MagicalBoss.mp3");
                                }
                                if (Objects.equals(m.id, "Champ")) {
                                    return MainMusic.newMusic("bundlecore/music/terra/RoyalBoss.mp3");
                                }
                            }
                        }
                    }
            }
            return __result;
        }
    }

    @SpirePatch(clz = TempMusic.class, method = "getSong")
    public static class BossMusicBeyondReplacer {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, TempMusic __instance, String key) {
            if (checkingPriorityBoss() && priorityBoss == 2) {
                if (key.equals("BOSS_BEYOND"))
                    if (AbstractDungeon.getCurrRoom() != null) {
                        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                            if (!m.isDeadOrEscaped()) {
                                if (Objects.equals(m.id, "Donu")) {
                                    return MainMusic.newMusic("bundlecore/music/terra/MechanicalBoss.mp3");
                                }
                                if (Objects.equals(m.id, "TimeEater")) {
                                    return MainMusic.newMusic("bundlecore/music/terra/MagicalBoss.mp3");
                                }
                                if (Objects.equals(m.id, "AwakenedOne")) {
                                    return MainMusic.newMusic("bundlecore/music/terra/Boss3Awake.mp3");
                                }
                            }
                        }
                    }
            }
            return __result;
        }
    }

    @SpirePatch(clz = TempMusic.class, method = "getSong")
    public static class Act4Heart {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, TempMusic __instance, String key) {
            if (checkingPriorityBoss() && priorityBoss == 2) {
                if (key.equals("BOSS_ENDING"))
                    return MainMusic.newMusic("bundlecore/music/terra/Boss4.mp3");
            }
            return __result;
        }
    }

    @SpirePatch(clz = TempMusic.class, method = "getSong")
    public static class Act3Bloom {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, TempMusic __instance, String key) {
            if (checkingPriorityBoss() && priorityBoss == 2) {
                if (key.equals("MINDBLOOM"))
                    return MainMusic.newMusic("bundlecore/music/terra/MindBloom.mp3");
            }
            return __result;
        }
    }

    @SpirePatch(clz = TempMusic.class, method = "getSong")
    public static class LagaV {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, TempMusic __instance, String key) {
            if (checkingPriorityBoss() && priorityBoss == 2) {
                if (key.equals("ELITE"))
                    return MainMusic.newMusic("bundlecore/music/terra/Laga.mp3");
            }
            return __result;
        }
    }

}