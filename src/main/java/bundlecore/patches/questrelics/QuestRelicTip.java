package bundlecore.patches.questrelics;


import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.FtueTip;

/**
 * The FTUE used to explain quest relics the first time the player interacts with them.
 */
@Dont_Use_This_Externally
public class QuestRelicTip extends FtueTip {
    public QuestRelicTip(String label, String text, float x, float y, FtueTip.TipType type) {
        super(label, text, x, y, type);
    }

    public static final String ID = BundleCoreMain.makeID("FtueQuestRelics");
    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString(ID);
    public static final String[] LABEL = tutorialStrings.LABEL;

    public static final String[] TEXT = tutorialStrings.TEXT;
    public static void spawn() {
        AbstractDungeon.ftue = new QuestRelicTip(LABEL[0], TEXT[0], Settings.WIDTH / 2.0F - 500.0F * Settings.scale, Settings.HEIGHT / 2.0F, FtueTip.TipType.RELIC);
    }
}