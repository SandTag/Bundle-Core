package bundlecore.patches.questrelics;
import bundle_of_content.relics.InsultingNoteQuest;
import bundlecore.BundleCoreMain;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.relics.*;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import foodbundle.relics.DeliciousdonuBoss;
import java.util.*;


public class QuestRelicPoolMaker implements BundleChecker {
    private static ArrayList<BundleRelic> QuestRelics = new ArrayList<>();
    public static boolean donuUnlocked;
    public static boolean decaUnlocked;
    public static boolean timeUnlocked;
    public static boolean collectUnlocked;
    public static boolean mazalethUnlocked;

    public static void testUnlockStatus(){
        donuUnlocked = Loader.isModLoaded("bundle_of_food");
        decaUnlocked = Objects.equals(BundleCoreMain.DonuProgressValue, "Fried");
        timeUnlocked = Loader.isModLoaded("bundle_of_potions");
        collectUnlocked = Objects.equals(BundleCoreMain.TimussyProgressValue, "Moist");
        mazalethUnlocked = (Objects.equals(BundleCoreMain.DecaProgressValue, "Mixed") && Objects.equals(BundleCoreMain.CollectProgressValue, "Vintage"));
    }

    public static void initializePool() {
        if (QuestRelics.size() != 0) {
            QuestRelics.clear();
        }
        if (AbstractDungeon.actNum == 1) {
            testUnlockStatus();
        }
        if (Loader.isModLoaded("bundle_of_food")) {
            DeliciousdonuBoss TestParameters = new DeliciousdonuBoss();
            if (donuUnlocked) {
                QuestRelics.add(TestParameters);
            }
        }
        DeliciousDecaBoss TestParameters2 = new DeliciousDecaBoss();
        if (decaUnlocked) {
            QuestRelics.add(TestParameters2);
        }
        TemporalTimepieceBoss TestParameters3 = new TemporalTimepieceBoss();
        if (timeUnlocked) {
            QuestRelics.add(TestParameters3);
        }
        CursedBrandBoss TestParameters4 = new CursedBrandBoss();

        if (collectUnlocked) {
            QuestRelics.add(TestParameters4);
        }
        MenacingRavenBoss TestParameters5 = new MenacingRavenBoss();
        if (TestParameters5.canSpawn()) {
            if (mazalethUnlocked) {
                QuestRelics.add(TestParameters5);
            }
        }
        if (Loader.isModLoaded("bundle_of_content")) {
            InsultingNoteQuest TestParameters = new InsultingNoteQuest();
            if (donuUnlocked) {
                QuestRelics.add(TestParameters);
            }
        }
        if (QuestRelics.isEmpty()) {
            QuestRelics.add(new RadiantCircletEvent());
        }
    }

    public static BundleRelic returnRandomRelic() {
        initializePool();
        BundleRelic output = returnRandomQuestRelic();
        if (output != null){
            return output;
        }
        else{
            throw new IllegalStateException("Failsafe Circlet Not Present");
        }
    }
    public static BundleRelic returnRandomQuestRelic() {
        int size = QuestRelics.size();
        int roll = AbstractDungeon.relicRng.random(0, (size-1));
        BundleRelic input = (BundleRelic) QuestRelics.get(roll).makeCopy();
        return (input);
    }

}
