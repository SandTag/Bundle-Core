package bundlecore.cardmodifiers.chimeracardscrossover;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.DynvarCarrier;
import CardAugments.cardmods.util.PreviewedMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import bundlecore.BundleCoreMain;
import bundlecore.util.GFL;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * A modifier that produces a startup curse of the bell and doubles card values.
 */
public class BongModifier  extends AbstractAugment implements DynvarCarrier {
    public static final String ID = BundleCoreMain.makeID(BongModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean modified;
    public boolean upgraded;

    boolean modMagic;

    //====================================================================-
    //When using a dynamic variable
    public static final String DESCRIPTION_KEY = "!"+ID+"!";
    private static final int EFFECT = 10;
    private static final int UPGRADE_EFFECT = 5;

    public int getBaseVal(AbstractCard card) {
        if (card.upgraded){
            return EFFECT + UPGRADE_EFFECT;
        }
        else {
            return EFFECT;
        }
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
    public String getAugmentDescription() {
        return TEXT2[0];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + String.format(TEXT[2], DESCRIPTION_KEY);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        AbstractCard preview = new CurseOfTheBell();
        CardModifierManager.addModifier(preview, new PreviewedMod());
        MultiCardPreview.add(card, new AbstractCard[]{preview});
        if (cardCheck(card, c -> doesntDowngradeMagic() && c.baseMagicNumber >= 1)) {
            modMagic = true;
        }
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0) {
            return Math.round(damage * 2.0f);
        }
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0) {
            return Math.round(block * 2.0f);
        }
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (modMagic) {
            return Math.round(magic * 2.0f);
        }
        return magic;
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.RARE;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && (card.baseDamage >= 1 || card.baseBlock >= 1 || card.baseMagicNumber >= 1) && cardCheck(card, c -> notReshuffle(c) && noShenanigans(c) && doesntDowngradeMagic() && c.baseMagicNumber > 0 && card.cost <= c.baseMagicNumber);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BongModifier();
    }

    @Override
    public void onUse (AbstractCard card, AbstractCreature target, UseCardAction action) {
        CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
    }

    @Override
    public boolean atBattleStartPreDraw(AbstractCard card){
        CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
        GFL.atb(new MakeTempCardInDrawPileAction(new CurseOfTheBell(), 1, true, false, false, Settings.WIDTH * 0.5F, Settings.HEIGHT / 2.0F));
        return true;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
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
