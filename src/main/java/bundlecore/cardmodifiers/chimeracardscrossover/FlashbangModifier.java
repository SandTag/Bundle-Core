package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import bundlecore.BundleCoreMain;
import bundlecore.util.GFL;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * Card applies stun, costs more and exhausts.
 */
public class FlashbangModifier extends AbstractAugment {
        public static final String ID = BundleCoreMain.makeID(FlashbangModifier.class.getSimpleName());
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
        public String modifyDescription(String rawDescription, AbstractCard card) {
            return rawDescription + String.format(TEXT[2]);
        }

        @Override
        public void onInitialApplication(AbstractCard card) {
            card.cost = card.cost + 1;
            card.costForTurn = card.cost;
            PurgeField.purge.set(card, true);
        }

        @Override
        public AbstractAugment.AugmentRarity getModRarity() {
            return AugmentRarity.RARE;
        }

        @Override
        public boolean validCard(AbstractCard card) {
            return card.type == AbstractCard.CardType.ATTACK && card.cost >= 2 && card.baseDamage >= 1 && card.rarity != AbstractCard.CardRarity.COMMON && card.rarity != AbstractCard.CardRarity.BASIC && cardCheck(card, c -> noShenanigans(c) && doesntUpgradeCost() && doesntUpgradeRetain() && notRetain(c) && usesVanillaTargeting(c));
        }

        @Override
        public AbstractCardModifier makeCopy() {
            return new FlashbangModifier();
        }

        @Override
        public void onUse (AbstractCard card, AbstractCreature target, UseCardAction action){
            if (target instanceof AbstractMonster) {
                GFL.atb(new StunMonsterAction((AbstractMonster)target, GFL.GAP()));
            }
        }

        @Override
        public String identifier(AbstractCard card) {
            return ID;
        }

}
