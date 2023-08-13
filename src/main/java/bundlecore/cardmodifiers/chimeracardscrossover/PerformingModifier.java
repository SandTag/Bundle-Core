package bundlecore.cardmodifiers.chimeracardscrossover;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * A modifier that adds opener and higher card values.
 */
public class PerformingModifier extends AbstractAugment {
    public static final String ID = BundleCoreMain.makeID(PerformingModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean modified;
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
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(TEXT[2]) + rawDescription;
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
    public boolean canPlayCard (AbstractCard card){
        return AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 0;
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.RARE;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseDamage >= 2 || card.baseBlock >=2 || card.baseMagicNumber >=2) && (cardCheck(card, c -> noShenanigans(c)));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PerformingModifier();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0) {
            return damage * 1.5f;
        }
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0) {
            return block * 1.5f;
        }
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (modMagic) {
            return magic * 1.5f;
        }
        return magic;
    }

}
