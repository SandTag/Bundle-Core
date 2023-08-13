package bundlecore.cardmodifiers.chimeracardscrossover;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * Singularity's Cardmod, raises values, exhaust is handled by the relic.
 */
@Dont_Use_This_Externally
public class UnstableModifier extends AbstractAugment {
    public static final String ID = BundleCoreMain.makeID(UnstableModifier.class.getSimpleName());
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
    public void onInitialApplication(AbstractCard card) {
        if (cardCheck(card, c -> doesntDowngradeMagic() && c.baseMagicNumber >= 1)) {
            modMagic = true;
        }
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0) {
            return Math.round(damage * 1.65f);
        }
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0) {
            return Math.round(block * 1.65f);
        }
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (modMagic) {
            return Math.round(magic * 1.65f);
        }
        return magic;
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.SPECIAL;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseBlock >= 1 || card.baseDamage >= 1 || card.baseMagicNumber >= 1) && card.cost != -2 && cardCheck(card, c -> doesntDowngradeMagic());
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new UnstableModifier();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

}