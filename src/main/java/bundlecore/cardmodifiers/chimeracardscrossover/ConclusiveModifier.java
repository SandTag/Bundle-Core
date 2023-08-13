package bundlecore.cardmodifiers.chimeracardscrossover;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * Card has higher values but ends your turn.
 */
public class ConclusiveModifier  extends AbstractAugment {
    public static final String ID = BundleCoreMain.makeID(ConclusiveModifier.class.getSimpleName());
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
    public void onInitialApplication(AbstractCard card) {}

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target){
        if (card.baseDamage > 0) {
            return Math.round(damage * 1.35f);
        }
        return damage;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + TEXT[2];
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.SPECIAL;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseDamage >= 4) && cardCheck(card, c -> noShenanigans(c) && notInnate(c));
    }

    @Override
    public void onUse (AbstractCard card, AbstractCreature target, UseCardAction action){
        addToBot(new PressEndTurnButtonAction());
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ConclusiveModifier();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

}