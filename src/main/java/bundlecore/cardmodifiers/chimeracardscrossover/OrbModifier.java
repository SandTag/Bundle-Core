package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import bundlecore.patches.BundleCoreCardTags;
import bundlecore.util.GFL;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;

/**
 * Bundle of Peglin's modifier. Card contributes to crits.
 */
public class OrbModifier extends AbstractAugment {
    public static final String ID = BundleCoreMain.makeID(OrbModifier.class.getSimpleName());
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
        return String.format(TEXT[2], DESCRIPTION_KEY) + rawDescription + String.format(TEXT[3], DESCRIPTION_KEY);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(BundleCoreCardTags.PEG_ORB);
        card.tags.add(BundleCoreCardTags.PEG_CAN_CRIT);
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.COMMON;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return Loader.isModLoaded("Bundle_Of_Peglin") && card.type != AbstractCard.CardType.CURSE && card.cost != -2 && cardCheck(card, AbstractAugment::noShenanigans);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new OrbModifier();
    }

    @Override
    public void onUse (AbstractCard card, AbstractCreature target, UseCardAction action){
        if (GFL.GAP().hasPower("Bundle_Of_Peglin:OrbCritPower")){
            addToTop(new VFXAction(new SanctityEffect(GFL.GAP().hb.cX, GFL.GAP().hb.cY), 0.1F));
            GFL.atb(new GainEnergyAction(2));
            GFL.atb(new DrawCardAction(4));
            GFL.atb(new RemoveSpecificPowerAction(GFL.GAP(), null, "Bundle_Of_Peglin:OrbCritPower"));
        }
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

}