package bundlecore.actions;
import CardAugments.cardmods.common.SupplyMod;
import basemod.helpers.CardModifierManager;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import java.util.Arrays;
import java.util.List;

public class RandomSupplierAction extends AbstractGameAction {
    private final List<String> NaughtyCards = Arrays.asList("Reflex", "Tactician", "DeusExMachina", "BecomeAlmighty", "Calm", "Wrath", "FameAndFortune", "LiveForever");

    /**
     * Part of the chimeric expansion, the pot of tea uses this to hit a card in hand with supplier each turn.
     */
    public RandomSupplierAction() {
        this.setValues(null, null, 0);
        this.actionType = ActionType.SPECIAL;
        this.isDone = false;
    }

    public void update() {
        int count;
        CardGroup validcards = GFL.GAP().hand;
        CardGroup validcardspruned = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : validcards.group) {
            if (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && card.cost >= 0 && !NaughtyCards.contains(card.cardID)) {
                validcardspruned.addToTop(card);
            }
        }
        if (validcardspruned.size() != 0) {
            count = validcardspruned.size();
            if (count != 0) {
                AbstractCard cardSelected = validcardspruned.getRandomCard(true);
                CardModifierManager.addModifier(cardSelected, new SupplyMod());
            }
        }
        this.isDone = true;
    }
}
