package bundlecore.patches;

import bundlecore.relics.*;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import javassist.CtBehavior;

/**
 * An essential class that allows quest relics to gain a "hook" for key pieces.
 */
public class DonuDecaNoise {

    @SpirePatch(clz = ObtainKeyEffect.class, method = "update")

    public static class update {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert() {
            if (AbstractDungeon.player.hasRelic("bundle_of_food:DeliciousdonuBoss")) {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey) {
                    AbstractDungeon.player.getRelic("bundle_of_food:DeliciousdonuBoss").onTrigger();
                }

                else if (Settings.hasEmeraldKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic("bundle_of_food:DeliciousdonuBoss").onTrigger();
                }

                else if (Settings.hasRubyKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic("bundle_of_food:DeliciousdonuBoss").onTrigger();
                }
            }

            if (AbstractDungeon.player.hasRelic(DeliciousDecaBoss.ID)) {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey) {
                    AbstractDungeon.player.getRelic(DeliciousDecaBoss.ID).onTrigger();
                }

                else if (Settings.hasEmeraldKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic(DeliciousDecaBoss.ID).onTrigger();
                }

                else if (Settings.hasRubyKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic(DeliciousDecaBoss.ID).onTrigger();
                }
            }

            if (AbstractDungeon.player.hasRelic(TemporalTimepieceBoss.ID)) {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey) {
                    AbstractDungeon.player.getRelic(TemporalTimepieceBoss.ID).onTrigger();
                }

                else if (Settings.hasEmeraldKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic(TemporalTimepieceBoss.ID).onTrigger();
                }

                else if (Settings.hasRubyKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic(TemporalTimepieceBoss.ID).onTrigger();
                }
            }

            if (AbstractDungeon.player.hasRelic(MenacingRavenBoss.ID)) {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey) {
                    AbstractDungeon.player.getRelic(MenacingRavenBoss.ID).onTrigger();
                }

                else if (Settings.hasEmeraldKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic(MenacingRavenBoss.ID).onTrigger();
                }

                else if (Settings.hasRubyKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic(MenacingRavenBoss.ID).onTrigger();
                }
            }

            if (AbstractDungeon.player.hasRelic(CursedBrandBoss.ID)) {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey) {
                    AbstractDungeon.player.getRelic(CursedBrandBoss.ID).onTrigger();
                }

                else if (Settings.hasEmeraldKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic(CursedBrandBoss.ID).onTrigger();
                }

                else if (Settings.hasRubyKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic(CursedBrandBoss.ID).onTrigger();
                }
            }

            if (AbstractDungeon.player.hasRelic("bundle_of_content:InsultingNoteQuest")) {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey) {
                    AbstractDungeon.player.getRelic("bundle_of_content:InsultingNoteQuest").onTrigger();
                }

                else if (Settings.hasEmeraldKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic("bundle_of_content:InsultingNoteQuest").onTrigger();
                }

                else if (Settings.hasRubyKey && Settings.hasSapphireKey) {
                    AbstractDungeon.player.getRelic("bundle_of_content:InsultingNoteQuest").onTrigger();
                }
            }

        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(SoundMaster.class, "playA");
                return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
            }
        }
    }

}
