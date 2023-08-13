package bundlecore.relics;

import basemod.AutoAdd;
import basemod.helpers.CardModifierManager;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.cardmodifiers.chimeracardscrossover.UnstableModifier;
import bundlecore.util.Interfaces.patches.OnCreateMidCombatCards;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.Arrays;
import java.util.List;

import static bundlecore.BundleCoreMain.makeID;

@AutoAdd.Ignore
public class SingularityBoss extends BundleRelic implements BundleChecker, OnCreateMidCombatCards {
    private static final String NAME = "SingularityBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    private final List<String> NaughtyCards = Arrays.asList("BecomeAlmighty", "Calm", "Wrath", "FameAndFortune", "LiveForever");

    public SingularityBoss() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        for (AbstractCard c : pl().masterDeck.group) {
            if (!CardModifierManager.hasModifier(c, UnstableModifier.ID) && !NaughtyCards.contains(c.cardID)) {
                if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                    CardModifierManager.addModifier(c, new UnstableModifier());
                }
            }
        }
    }
    @Override
    public void onPreviewObtainCard(AbstractCard c) {
        onObtainCard(c);
    }

    @Override
    public void onEnterRoom(AbstractRoom room){
        for (AbstractCard c : pl().masterDeck.group) {
            if (!CardModifierManager.hasModifier(c, UnstableModifier.ID) && !NaughtyCards.contains(c.cardID)) {
                if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                    CardModifierManager.addModifier(c, new UnstableModifier());
                }
            }
        }
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (!CardModifierManager.hasModifier(c, UnstableModifier.ID) && !NaughtyCards.contains(c.cardID)) {
            if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                CardModifierManager.addModifier(c, new UnstableModifier());
            }
        }
    }

    @Override
    public void onCreateCard(AbstractCard c){
        if (!CardModifierManager.hasModifier(c, UnstableModifier.ID) && !NaughtyCards.contains(c.cardID)) {
            if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                CardModifierManager.addModifier(c, new UnstableModifier());
            }
        }
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        flash();
        if (!CardModifierManager.hasModifier(card, "Bundle_Of_Peglin:TemporaryCardModRebound")){
            action.exhaustCard = true;
        }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public boolean canSpawn(){
        return (isChimeraMoment() && AbstractDungeon.actNum == 2);
    }
}
