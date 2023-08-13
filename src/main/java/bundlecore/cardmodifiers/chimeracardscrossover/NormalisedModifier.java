package bundlecore.cardmodifiers.chimeracardscrossover;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.DynvarCarrier;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * Card is like FTL with higher values, but cant be played after the conditional expires.
 */
public class NormalisedModifier extends AbstractAugment implements DynvarCarrier {
    public static final String ID = BundleCoreMain.makeID(NormalisedModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean modified;
    public boolean upgraded;

    //====================================================================-
    //When using a dynamic variable
    public static final String DESCRIPTION_KEY = "!"+ID+"!";
    private static final int EFFECT = 3;
    private static final int UPGRADE_EFFECT = 1;
    boolean modMagic;

    public int getBaseVal(AbstractCard card) {
        return EFFECT + (this.getEffectiveUpgrades(card) * UPGRADE_EFFECT);
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
        return rawDescription + String.format(TEXT[2], DESCRIPTION_KEY);
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
        return AbstractDungeon.actionManager.cardsPlayedThisTurn.size() <= getBaseVal(card);
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.UNCOMMON;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseDamage >= 3 || card.baseBlock >=3 || card.baseMagicNumber >=3) && (cardCheck(card, AbstractAugment::noShenanigans));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new NormalisedModifier();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0) {
            return damage * 1.35f;
        }
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0) {
            return block * 1.35f;
        }
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (modMagic) {
            return magic * 1.35f;
        }
        return magic;
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
