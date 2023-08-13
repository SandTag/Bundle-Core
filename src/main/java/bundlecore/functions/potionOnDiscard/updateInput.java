package bundlecore.functions.potionOnDiscard;
import bundlecore.util.GFL;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CtBehavior;

/**
 * This is the first hook added by bundlecore, it adds detection for potion discards.
 */
@SpirePatch(clz = PotionPopUp.class, method = "updateInput")
public class updateInput {
    @SpireInsertPatch(locator = Locator1.class)
    public static void onDiscardPotion(PotionPopUp __instance) {
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic instanceof DiscardPotionRelic)
                ((DiscardPotionRelic) relic).onDiscardPotion();
        }
    }

    private static class Locator1 extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(TopPanel.class, "destroyPotion");
            int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, (Matcher) methodCallMatcher);
            return new int[]{matches[1]};
        }
    }

    @SpirePatch2(clz = TopPanel.class, method = "destroyPotion")
    static class fetchOutput {
        @SpireInsertPatch(rloc = 0, localvars = {"slot"})
        public static void interceptPotion(int slot) {
            AbstractPotion fetchedPotion = GFL.GAP().potions.get(slot);
            GFL.GAP().relics.stream().filter(r -> r instanceof DiscardPotionRelic).forEach(r -> ((DiscardPotionRelic) r).onDiscardPotionAdvanced(fetchedPotion));
            GFL.GAP().blights.stream().filter(r -> r instanceof DiscardPotionRelic).forEach(r -> ((DiscardPotionRelic) r).onDiscardPotionAdvanced(fetchedPotion));
            if (AbstractDungeon.getCurrRoom() != null) {
                if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                    GFL.GAP().powers.stream().filter(r -> r instanceof DiscardPotionRelic).forEach(r -> ((DiscardPotionRelic) r).onDiscardPotionAdvanced(fetchedPotion));
                    GFL.GAP().hand.group.stream().filter(card -> card instanceof DiscardPotionRelic).forEach(card -> ((DiscardPotionRelic) card).onDiscardPotionAdvanced(fetchedPotion));
                    GFL.GAP().discardPile.group.stream().filter(card -> card instanceof DiscardPotionRelic).forEach(card -> ((DiscardPotionRelic) card).onDiscardPotionAdvanced(fetchedPotion));
                    GFL.GAP().drawPile.group.stream().filter(card -> card instanceof DiscardPotionRelic).forEach(card -> ((DiscardPotionRelic) card).onDiscardPotionAdvanced(fetchedPotion));
                    AbstractDungeon.getMonsters().monsters.stream().filter(mon -> !mon.isDeadOrEscaped()).forEach(m -> m.powers.stream().filter(pow -> pow instanceof DiscardPotionRelic).forEach(pow -> ((DiscardPotionRelic) pow).onDiscardPotionAdvanced(fetchedPotion)));
                }
            }
        }
    }
}

