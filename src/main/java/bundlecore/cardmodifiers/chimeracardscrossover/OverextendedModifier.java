package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.DynvarCarrier;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

/**
 * A modifier that gives really big values but slaps base debuffs on after use.
 */
public class OverextendedModifier extends AbstractAugment implements DynvarCarrier {
    public static final String ID = BundleCoreMain.makeID(OverextendedModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean modified;
    public boolean upgraded;

    //====================================================================-
    //When using a dynamic variable
    public static final String DESCRIPTION_KEY = "!"+ID+"!";
    private static final int EFFECT = 99;
    private static final int UPGRADE_EFFECT = -96;
    boolean modMagic;

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
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + String.format(TEXT[2], DESCRIPTION_KEY);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (cardCheck(card, c -> doesntDowngradeMagic() && c.baseMagicNumber >= 1)) {
            modMagic = true;
        }
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.RARE;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost != -2 && (card.baseDamage >= 1 || card.baseBlock >= 1 || card.baseMagicNumber >= 1) && cardCheck(card, c -> notRetain(c) && noShenanigans(c));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new OverextendedModifier();
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0) {
            return damage * 3.0f;
        }
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0) {
            return block * 3.0f;
        }
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (modMagic) {
            return magic * 3.0f;
        }
        return magic;
    }

    @Override
    public String getAugmentDescription() {
        return TEXT2[0];
    }

    @Override
    public void onUse (AbstractCard card, AbstractCreature target, UseCardAction action){
        if (card.upgraded){
            GFL.atb(new ApplyPowerAction(GFL.GAP(), GFL.GAP(), new VulnerablePower(GFL.GAP(), 3, false), 3));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), GFL.GAP(), new WeakPower(GFL.GAP(), 3, false), 3));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), GFL.GAP(), new FrailPower(GFL.GAP(), 3, false), 3));
        }
        else {
            GFL.atb(new ApplyPowerAction(GFL.GAP(), GFL.GAP(), new VulnerablePower(GFL.GAP(), 99, false), 99));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), GFL.GAP(), new WeakPower(GFL.GAP(), 99, false), 99));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), GFL.GAP(), new FrailPower(GFL.GAP(), 99, false), 99));
        }
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