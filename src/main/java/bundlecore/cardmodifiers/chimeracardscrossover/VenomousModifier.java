package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * Halves damage, applies choke equal to new unblocked damage.
 */
public class VenomousModifier extends AbstractAugment {
    public static final String ID = BundleCoreMain.makeID(VenomousModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    public VenomousModifier() {}

    public void onInitialApplication(AbstractCard card) {
        DamageModifierManager.addModifier(card, new VenomousDamage());
    }

    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage * 0.5F;
    }

    public boolean validCard(AbstractCard card) {
        return card.type == AbstractCard.CardType.ATTACK && card.baseDamage > 1;
    }

    public String getPrefix() {
        return TEXT[0];
    }

    public String getSuffix() {
        return TEXT[1];
    }

    public String getAugmentDescription() {
        return TEXT2[0];
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, TEXT[2]);
    }

    public AbstractAugment.AugmentRarity getModRarity() {
        return AugmentRarity.UNCOMMON;
    }

    public AbstractCardModifier makeCopy() {
        return new VenomousModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

}
