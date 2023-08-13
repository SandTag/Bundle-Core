package bundlecore.util.Interfaces.patches;

import com.megacrit.cardcrawl.cards.AbstractCard;

/**
 * Impliment this hook if you want to make an item that functions with cards created mid-combat.
 */
public interface OnCreateMidCombatCards {
    /**
     * For something that isn't a card.
     * @param card The input.
     */
    default void onCreateCard(AbstractCard card) {}

    /**
     * For something that is a card.
     * @param card The input.
     */
    default void onCreateCardCard(AbstractCard card) {}
}
