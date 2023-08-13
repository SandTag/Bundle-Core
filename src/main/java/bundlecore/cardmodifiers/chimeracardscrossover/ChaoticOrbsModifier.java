package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

/**
 * Card exhausts, gives an orb slot and a random orb.
 */
public class ChaoticOrbsModifier extends AbstractAugment {
    public static final String ID = BundleCoreMain.makeID(ChaoticOrbsModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean upgraded;

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
        return rawDescription + String.format(TEXT[2]);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.RARE;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.rarity != AbstractCard.CardRarity.BASIC && card.type != AbstractCard.CardType.POWER && card.cost >= 0 && cardCheck(card, c -> noShenanigans(c) && allowOrbMods() && notExhaust(c) && doesntUpgradeExhaust());
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ChaoticOrbsModifier();
    }

    @Override
    public void onUse (AbstractCard card, AbstractCreature target, UseCardAction action){
        GFL.atb(new IncreaseMaxOrbAction(1));
        GFL.atb(new ChannelAction(AbstractOrb.getRandomOrb(true)));
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
