package bundlecore.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class YoinkRandomCards extends AbstractGameAction {

    final AbstractPlayer pl = AbstractDungeon.player;

    /**
     * Used by Baked Sweet Potato (Bundle of Food), can be used by pretty much anything.
     * @param amount - How many cards you want to bring back.
     */
    public YoinkRandomCards(int amount) {
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        for (int i = 0; i < this.amount; i++) {
            if (pl.discardPile.size() >= 1) {
                AbstractCard c = this.pl.discardPile.getRandomCard(AbstractDungeon.cardRandomRng);
                this.pl.discardPile.moveToHand(c);
            }
            else {
                break;
            }
        }
        this.isDone = true;
    }

}
