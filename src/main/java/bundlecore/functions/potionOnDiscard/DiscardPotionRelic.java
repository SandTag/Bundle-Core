package bundlecore.functions.potionOnDiscard;

import com.megacrit.cardcrawl.potions.AbstractPotion;

/**
 * Implement and override to add functionality when discarding potions, for relics.
 * <p>
 * The second interface in spite of this class name is universal.
 */
public interface DiscardPotionRelic {
    @Deprecated
    default void onDiscardPotion() {}
    default void onDiscardPotionAdvanced(AbstractPotion p){}
}
