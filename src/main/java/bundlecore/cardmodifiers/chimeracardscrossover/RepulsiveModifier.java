package bundlecore.cardmodifiers.chimeracardscrossover;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * A modifier that adds purge and higher values.
 */
public class RepulsiveModifier extends AbstractAugment {
    public static final String ID = BundleCoreMain.makeID(RepulsiveModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean upgraded;
    boolean modMagic;

    @Override
    public String getPrefix() {return TEXT[0];}

    @Override
    public String getSuffix() {return TEXT[1];}

    @Override
    public String getAugmentDescription() {return TEXT2[0];}

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {return rawDescription + String.format(TEXT[2]);}

    @Override
    public void onInitialApplication(AbstractCard card){
        if(cardCheck(card, c -> doesntDowngradeMagic() && c.baseMagicNumber >= 3)){modMagic = true;}
        PurgeField.purge.set(card, true);
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage >= 2) {
            return damage * 1.35f;
        }
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock >= 2) {
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

    @Override
    public AugmentRarity getModRarity() {return AugmentRarity.UNCOMMON;}

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseBlock >= 3 || card.baseDamage >= 3 || (card.baseMagicNumber >= 3 && cardCheck(card, c -> doesntDowngradeMagic()))) && card.type != AbstractCard.CardType.POWER;
    }

    @Override
    public AbstractCardModifier makeCopy() {return new RepulsiveModifier();}

    @Override
    public String identifier(AbstractCard card) {return ID;}

}
