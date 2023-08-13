package bundlecore.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SneckoHand extends AbstractGameAction {

    /**
     * Used to reroll the cost of your entire hand, can be used by potions, relics, powers...
     */
    @Override
    public void update() {
        for (AbstractCard card2 : AbstractDungeon.player.hand.group) {
            if (card2.cost >= 0) {
                int newCost = AbstractDungeon.cardRandomRng.random(3);
                if (card2.cost != newCost) {
                    card2.cost = newCost;
                    card2.costForTurn = card2.cost;
                    card2.isCostModified = true;}
                card2.freeToPlayOnce = false;
            }
        }
        this.isDone = true;
    }

}
