package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.util.GFL;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.Arrays;
import java.util.List;

/**
 * Used by the Bottled Concentrated Force relic.
 */
@Dont_Use_This_Externally
public class BottledMod extends AbstractAugment {
    public static final String ID = BundleCoreMain.makeID(BottledMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean upgraded;
    private static final int EXHAUSTIVE = 2;
    private static final List<String> NaughtyCards = Arrays.asList("BecomeAlmighty", "Calm", "Wrath", "FameAndFortune", "LiveForever");

    @Override
    public String getPrefix() {
        return TEXT[0];
    }

    @Override
    public String getSuffix() {
        return TEXT[1];
    }

    @Override
    public String getAugmentDescription() {
        return TEXT2[0];
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        SoulboundField.soulbound.set(card, true);
        ExhaustiveVariable.setBaseValue(card, EXHAUSTIVE);
//            ExhaustiveField.ExhaustiveFields.baseExhaustive.set(card, 2);
        card.selfRetain = true;
        card.initializeDescription();
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.SPECIAL;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(TEXT[2]) + rawDescription + String.format(TEXT[3]);
    }

    @Override
    public void onDrawn(AbstractCard card) {
        GFL.atb(new DrawCardAction(1));
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (!card.exhaust && ExhaustiveField.ExhaustiveFields.baseExhaustive.get(card) == -1 && card.type != AbstractCard.CardType.CURSE
                && card.type != AbstractCard.CardType.STATUS && !SoulboundField.soulbound.get(card) && card.cost >= -1 && !NaughtyCards.contains(card.cardID));
    }

    public static boolean checkMasterDeckValidity(){
        for (AbstractCard card : GFL.GAP().masterDeck.group){
            if (!card.exhaust && ExhaustiveField.ExhaustiveFields.baseExhaustive.get(card) == -1 && card.type != AbstractCard.CardType.CURSE
                    && card.type != AbstractCard.CardType.STATUS && !SoulboundField.soulbound.get(card) && card.cost >= -1 && !NaughtyCards.contains(card.cardID)){
                return true;
            }
        }
        return false;
    }

//    @Override
//    public boolean isInherent(AbstractCard card) {
//        return true;
//    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BottledMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

}