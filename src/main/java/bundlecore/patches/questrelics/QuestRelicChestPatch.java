package bundlecore.patches.questrelics;
import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.relics.BundleRelic;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import java.io.IOException;
import java.util.ArrayList;
import javassist.CtBehavior;
import static bundlecore.BundleCoreMain.questRelicFtue3;

/**
 * The patch in which quest relics are added to the pool.
 */
@Dont_Use_This_Externally
public class QuestRelicChestPatch {

    @SpirePatch(clz = BossRelicSelectScreen.class, method = "open")
    public static class Open {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(BossRelicSelectScreen __instance, ArrayList<AbstractRelic> chosenRelics) {
            if (BundleCoreMain.QuestRelics) {

                if (AbstractDungeon.actNum == 1) {
                    int ascbonus = AbstractDungeon.ascensionLevel * 4;
                    int roll1 = AbstractDungeon.miscRng.random(0, 100);
                    roll1 += ascbonus;
                    if (roll1 >= 80) {

                        if(!questRelicFtue3){
                            setValue();
                            QuestRelicTip.spawn();
                        }

                        float BCR_SLOT_1_Y = ((AbstractRelic) __instance.relics.get(0)).currentY;
                        float BCR_SLOT_2_X = ((AbstractRelic) __instance.relics.get(1)).currentX;
                        float BCR_SLOT_2_Y = ((AbstractRelic) __instance.relics.get(1)).currentY;
                        float BCR_SLOT_3_X = ((AbstractRelic) __instance.relics.get(2)).currentX;
                        BundleRelic BCR_relic;
                        if (BundleCoreMain.CurrentRunsFourthBossRelicStorage == null) {
                            BCR_relic = QuestRelicPoolMaker.returnRandomRelic();
                            BundleCoreMain.CurrentRunsFourthBossRelicStorage = BCR_relic;
                        }
                        else{
                            BCR_relic = BundleCoreMain.CurrentRunsFourthBossRelicStorage;
                        }
                        BCR_relic.spawn((((BCR_SLOT_3_X - BCR_SLOT_2_X) / 2) + BCR_SLOT_2_X), (((BCR_SLOT_1_Y - BCR_SLOT_2_Y) / 3) + (BCR_SLOT_2_Y)));
                        BCR_relic.hb.move(BCR_relic.currentX, BCR_relic.currentY);
                        __instance.relics.add(BCR_relic);
                    }
                }
            }
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(BossRelicSelectScreen.class, "relics");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, (Matcher)fieldAccessMatcher);
                return new int[] { found[found.length - 1] };
            }
        }
    }

    public static void setValue(){
        questRelicFtue3 = true;
        try {
            SpireConfig ftueFig = new SpireConfig("bundlecore", "BundleCoreConfigFile");
            ftueFig.setBool("questRelicFtue3", true);
            ftueFig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
