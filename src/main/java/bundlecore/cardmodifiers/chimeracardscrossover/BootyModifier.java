package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.DynvarCarrier;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

/**
 * Not horny. Its pirate themed, I promise. Startup 10 gold.
 */
public class BootyModifier extends AbstractAugment implements DynvarCarrier {
    public static final String ID = BundleCoreMain.makeID(BootyModifier.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] TEXT2 = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public boolean modified;
    public boolean upgraded;

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
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + String.format(TEXT[2], DESCRIPTION_KEY);
    }

    @Override
    public String getAugmentDescription() {
        return TEXT2[0];
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost = card.cost + 1;
        card.costForTurn = card.cost;
        card.exhaust = true;
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.UNCOMMON;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL) && card.cost >= 0 && cardCheck(card, c -> notExhaust(c) && doesntUpgradeCost() && noShenanigans(c));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BootyModifier();
    }

    @Override
    public boolean atBattleStartPreDraw(AbstractCard card){
        if (card.upgraded){
            GFL.GAP().gainGold(15);
            GFL.atl(new RainingGoldEffect(30, true));
        }else {
            GFL.GAP().gainGold(10);
            GFL.atl(new RainingGoldEffect(20, true));
        }
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
