package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.util.GFL;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * ModSoup's cardmod, it slaps random mods on everything else in hand after use.
 */
@Dont_Use_This_Externally
public class SoupModifier extends AbstractAugment {
    public static final String ID = BundleCoreMain.makeID(SoupModifier.class.getSimpleName());
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
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() {
        return AugmentRarity.SPECIAL;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost >= 0 && card.rarity != AbstractCard.CardRarity.BASIC && cardCheck(card, AbstractAugment::noShenanigans);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SoupModifier();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new AbstractGameAction(){
            @Override
            public void update() {
                CardCrawlGame.sound.play("GHOST_FLAMES", 0.25F);
                for (AbstractCard card : GFL.GAP().hand.group) {
                    ArrayList<AbstractCardModifier> mods = CardAugmentsMod.modMap.values().stream().filter(mod -> mod.canApplyTo(card)).collect(Collectors.toCollection(ArrayList::new));
                    mods.removeIf(next -> CardModifierManager.hasModifier(card, next.toString()));
                    if (!mods.isEmpty()) {
                        CardModifierManager.addModifier(card, mods.get(AbstractDungeon.cardRng.random(mods.size() - 1)).makeCopy());
                        card.applyPowers();
                        card.superFlash();
                    }
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

}
