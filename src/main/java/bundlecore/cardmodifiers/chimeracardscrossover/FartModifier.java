package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.DynvarCarrier;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.NoxiousFumesPower;

/**
 * Card gives fumes but exhausts.
 */
public class FartModifier extends AbstractAugment implements DynvarCarrier {
    public static final String ID = BundleCoreMain.makeID(FartModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean modified;
    public boolean upgraded;

    //====================================================================-
    //When using a dynamic variable
    public static final String DESCRIPTION_KEY = "!"+ID+"!";
    private static final int EFFECT = 1;
    private static final int UPGRADE_EFFECT = 1;

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
        card.exhaust = true;
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.UNCOMMON;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL) && card.cost != -2 && cardCheck(card, c -> notExhaust(c) && notEthereal(c));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FartModifier();
    }

    @Override
    public void onUse (AbstractCard card, AbstractCreature target, UseCardAction action){
        if (card.upgraded) {
            GFL.atb(new ApplyPowerAction(GFL.GAP(), GFL.GAP(), new NoxiousFumesPower(GFL.GAP(), 2), 2));
        }
        else{
            GFL.atb(new ApplyPowerAction(GFL.GAP(), GFL.GAP(), new NoxiousFumesPower(GFL.GAP(), 1), 1));
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
