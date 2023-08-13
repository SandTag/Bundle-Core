package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.util.PreviewedMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import bundlecore.BundleCoreMain;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

/**
 * A modifier that reduces cost, but gives a wound after.
 */
public class PainfulModifier extends AbstractAugment {
    public static final String ID = BundleCoreMain.makeID(PainfulModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean upgraded;

    //====================================================================-
    //When using a dynamic variable
    public static final String DESCRIPTION_KEY = "!"+ID+"!";
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
    public String getAugmentDescription() {
        return TEXT2[0];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + String.format(TEXT[2], DESCRIPTION_KEY);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        AbstractCard preview = new Wound();
        CardModifierManager.addModifier(preview, new PreviewedMod());
        MultiCardPreview.add(card, new AbstractCard[]{preview});
        card.cost = card.cost - 1;
        card.costForTurn = card.cost;
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.COMMON;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost >= 1 && cardCheck(card, c -> doesntUpgradeCost());
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PainfulModifier();
    }

    @Override
    public void onUse (AbstractCard card, AbstractCreature target, UseCardAction action){
        GFL.atb(new MakeTempCardInDrawPileAction(new Wound(), 1, true, true, false));
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

}
