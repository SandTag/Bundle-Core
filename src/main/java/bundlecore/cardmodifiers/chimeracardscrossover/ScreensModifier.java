package bundlecore.cardmodifiers.chimeracardscrossover;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.DynvarCarrier;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

/**
 * A cursed modifier that was probably a mistake.
 */
@Dont_Use_This_Externally
public class ScreensModifier extends AbstractAugment implements DynvarCarrier {
    public static final String ID = BundleCoreMain.makeID(ScreensModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean modified;
    public boolean upgraded;

    //====================================================================-
    //When using a dynamic variable
    public static final String DESCRIPTION_KEY = "!"+ID+"!";
    private static final int EFFECT = 1;
    private static final int UPGRADE_EFFECT = 1;

    public int getBaseVal(AbstractCard card) {
        if (card.upgraded){
            return EFFECT + UPGRADE_EFFECT;
        }
        else {
            return EFFECT;
        }
    }
    //====================================================================-

    @Override
    public String getPrefix() {
        return TEXT[0];
    }

    @Override
    public String getSuffix() {
        return TEXT[1];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(TEXT[3], DESCRIPTION_KEY) + rawDescription + String.format(TEXT[2], DESCRIPTION_KEY);
    }

    @Override
    public String getAugmentDescription() {
        return TEXT2[0];
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.RARE;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL) && card.cost >= 1 && cardCheck(card, c -> notExhaust(c) && doesntUpgradeCost());
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ScreensModifier();
    }

    @Override
    public void onUse (AbstractCard card, AbstractCreature target, UseCardAction action){
        if (card.upgraded){
            GFL.att(new ScryAction(2));
            GFL.atb(new DrawCardAction(2));
            GFL.atb(new DiscardAction(GFL.GAP(), GFL.GAP(), 2, false));
            GFL.atb(new BetterDiscardPileToHandAction(1));
        }
        else {
            GFL.att(new ScryAction(1));
            GFL.atb(new DrawCardAction(1));
            GFL.atb(new DiscardAction(GFL.GAP(), GFL.GAP(), 1, false));
            GFL.atb(new BetterDiscardPileToHandAction(1));
        }
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    //=====================================-
    //DYNVAR STUFF
    @Override
    public String key() {
        return ID;
    }

    @Override
    public int val(AbstractCard card) {
        return getBaseVal(card);
    }

    @Override
    public int baseVal(AbstractCard card) {
        return getBaseVal(card);
    }

    @Override
    public boolean modified(AbstractCard card) {
        return modified;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        modified = card.timesUpgraded != 0 || card.upgraded;
        upgraded = card.timesUpgraded != 0 || card.upgraded;
        return upgraded;
    }
    //=====================================-

}