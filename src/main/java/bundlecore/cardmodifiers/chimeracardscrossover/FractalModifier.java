package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import bundlecore.BundleCoreMain;
import bundlecore.util.GFL;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * Slightly reduced values, card produces a copy of itself without the modifier in hand.
 */
public class FractalModifier  extends AbstractAugment {
    public static final String ID = BundleCoreMain.makeID(FractalModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean upgraded;
    boolean modMagic;

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
        if (cardCheck(card, c -> doesntDowngradeMagic() && c.baseMagicNumber >= 5)) {
            modMagic = true;
        }
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() {
        return AugmentRarity.RARE;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage >= 5) {
            return MathUtils.round(damage * 0.8f);
        }
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock >= 5) {
            return MathUtils.round(block * 0.8f);
        }
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (modMagic) {
            return MathUtils.round(magic * 0.8f);
        }
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseDamage >= 5 || card.baseBlock >= 5 || card.baseMagicNumber >= 5) && card.cost >= 0 && card.rarity != AbstractCard.CardRarity.BASIC && cardCheck(card, AbstractAugment::noShenanigans);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FractalModifier();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractCard Impostor = card.makeStatEquivalentCopy();
        CardModifierManager.removeModifiersById(Impostor, FractalModifier.ID, false);
        GFL.atb(new MakeTempCardInHandAction(Impostor));
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

}