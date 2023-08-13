package bundlecore.relics.bottledrelics.bottledmods;
import basemod.AutoAdd;
import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomSavable;
import basemod.helpers.CardModifierManager;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.cardmodifiers.chimeracardscrossover.BottledMod;
import bundlecore.relics.BundleRelic;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import static bundlecore.BundleCoreMain.makeID;

@Dont_Use_This_Externally
@AutoAdd.Ignore
public class BottledModsUncommon extends BundleRelic implements BundleChecker, CustomBottleRelic, CustomSavable<Integer> {
    private static final String NAME = "BottledModsUncommon";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.UNCOMMON;
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;
    private AbstractCard card = null;
    private boolean cardSelected = true;
    private static final List<String> NaughtyCards = Arrays.asList("BecomeAlmighty", "Calm", "Wrath", "FameAndFortune", "LiveForever");

    /**
     * A relic that applies a special cardmod and is a bottle relic.
     */
    @Dont_Use_This_Externally
    public BottledModsUncommon(){
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
    }








    public Predicate<AbstractCard> isOnCard() {
        return BottledModsField.inBottleMods::get;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractCard getCard() {
        return this.card.makeCopy();
    }

    public Integer onSave() {
        return Integer.valueOf(AbstractDungeon.player.masterDeck.group.indexOf(this.card));
    }

    public void onLoad(Integer cardIndex) {
        if (cardIndex == null)
            return;
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            this.card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (this.card != null) {
                BottledModsField.inBottleMods.set(this.card, Boolean.TRUE);
                setDescriptionAfterLoading();
            }
        }
    }

    public void onEquip() {
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(bruh(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck)), 1, this.DESCRIPTIONS[1] + this.name + ".", false, false, false, false);
    }

   public static CardGroup bruh(CardGroup group) {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : group.group) {
            if (!c.exhaust && ExhaustiveField.ExhaustiveFields.baseExhaustive.get(c) == -1 && c.type != AbstractCard.CardType.CURSE
                    && c.type != AbstractCard.CardType.STATUS && !SoulboundField.soulbound.get(c) && c.cost >= -1 && !NaughtyCards.contains(c.cardID)){
                retVal.addToTop(c);
            }
        }
        return retVal;
    }

    public void onUnequip() {
        if (this.card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(this.card);
            if (cardInDeck != null)
                BottledModsField.inBottleMods.set(cardInDeck, Boolean.FALSE);
        }
    }

    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            this.card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottledModsField.inBottleMods.set(this.card, Boolean.TRUE);
            CardModifierManager.addModifier(this.card, new BottledMod());
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            setDescriptionAfterLoading();
        }
    }

    private void setDescriptionAfterLoading() {
        this.description = FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }









    @Override
    public boolean canSpawn(){
        return (isChimeraMoment() && BottledMod.checkMasterDeckValidity());
    }
}