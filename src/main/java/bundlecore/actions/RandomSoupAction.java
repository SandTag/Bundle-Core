package bundlecore.actions;
import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.common.SupplyMod;
import basemod.helpers.CardModifierManager;
import bundlecore.cardmodifiers.chimeracardscrossover.SoupModifier;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import static CardAugments.cardmods.AbstractAugment.cardCheck;

public class RandomSoupAction  extends AbstractGameAction {

    /**
     * This cursed little thing is used by a relic called ModSoup and it applies a random chimera cards modifier to every card in hand.
     */
    public RandomSoupAction() {
        this.setValues(null, null, 0);
        this.actionType = ActionType.SPECIAL;
        this.isDone = false;
    }

    public void update() {
        int count;
        CardGroup validcards = GFL.GAP().hand;
        CardGroup validcardspruned = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : validcards.group) {
            if (card.cost >= 0 && cardCheck(card, AbstractAugment::noShenanigans)){
                validcardspruned.addToTop(card);
            }
        }
        if (validcardspruned.size() != 0) {
            count = validcardspruned.size();
            if (count != 0) {
                AbstractCard cardSelected = validcardspruned.getRandomCard(true);
                CardModifierManager.addModifier(cardSelected, new SoupModifier());
            }
        }
        this.isDone = true;
    }
}
