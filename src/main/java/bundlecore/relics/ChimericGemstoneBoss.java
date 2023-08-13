package bundlecore.relics;
import CardAugments.cardmods.common.SupplyMod;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.util.GFL;
import bundlecore.util.Interfaces.patches.OnCreateMidCombatCards;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.Arrays;
import java.util.List;
import static bundlecore.BundleCoreMain.makeID;

@AutoAdd.Ignore
public class ChimericGemstoneBoss extends BundleRelic implements BundleChecker, OnCreateMidCombatCards {
    private static final String NAME = "ChimericGemstoneBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    private final List<String> NaughtyCards = Arrays.asList("Reflex", "Tactician", "DeusExMachina", "BecomeAlmighty", "Calm", "Wrath", "FameAndFortune", "LiveForever");
    private boolean firstTurn;
    public ChimericGemstoneBoss() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
        setDescriptionWithCard();
        this.firstTurn = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void setDescriptionWithCard() {
        tips.clear();

        description = DESCRIPTIONS[0];
        this.tips.add(new PowerTip(this.name, this.description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(DESCRIPTIONS[1]), BaseMod.getKeywordDescription(DESCRIPTIONS[1])));

        initializeTips();
    }

    @Override
    public void onEquip() {
        for (AbstractCard c : pl().masterDeck.group) {
            if (!CardModifierManager.hasModifier(c, SupplyMod.ID) && !NaughtyCards.contains(c.cardID)) {
                if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS && c.cost != -1) {
                    CardModifierManager.addModifier(c, new SupplyMod());
                    c.cost = 0;
                    c.costForTurn = 0;
                    c.isCostModified = true;
                } else if (c.cost != -2 && c.cost != -1) {
                    CardModifierManager.addModifier(c, new SupplyMod());
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
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS && CardModifierManager.hasModifier(c, SupplyMod.ID)) {
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModified = true;
            }
        }
    }

    @Override
    public void atPreBattle(){
        this.firstTurn = true;
        for (AbstractCard c : pl().masterDeck.group) {
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS && CardModifierManager.hasModifier(c, SupplyMod.ID)) {
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModified = true;
            }
        }
        for (AbstractCard c : pl().drawPile.group) {
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS && CardModifierManager.hasModifier(c, SupplyMod.ID)) {
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModified = true;
            }
        }
        for (AbstractCard c : pl().hand.group) {
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS && CardModifierManager.hasModifier(c, SupplyMod.ID)) {
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModified = true;
            }
        }
        for (AbstractCard c : pl().discardPile.group) {
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS && CardModifierManager.hasModifier(c, SupplyMod.ID)) {
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModified = true;
            }
        }
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (!CardModifierManager.hasModifier(c, SupplyMod.ID) && !NaughtyCards.contains(c.cardID)) {
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS && c.cost != -1) {
                CardModifierManager.addModifier(c, new SupplyMod());
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModified = true;
            } else if (c.cost != -2 && c.cost != -1) {
                CardModifierManager.addModifier(c, new SupplyMod());
            }
        }
    }

    @Override
    public void onCreateCard(AbstractCard c){
        if (!CardModifierManager.hasModifier(c, SupplyMod.ID) && !NaughtyCards.contains(c.cardID)) {
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS && c.cost != -1) {
                CardModifierManager.addModifier(c, new SupplyMod());
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModified = true;
            } else if (c.cost != -2 && c.cost != -1) {
                CardModifierManager.addModifier(c, new SupplyMod());
            }
        }
    }

    @Override
    public void atBattleStart(){
        this.counter = -1;
    }

    @Override
    public void atTurnStart() {
        if (this.firstTurn) {
            addToBot(new GainEnergyAction(1));
            flash();
            this.firstTurn = false;
        }
    }

    @Override
    public void atTurnStartPostDraw(){
        if (this.counter == -1){
            this.counter = 0;
        }
        else{
            this.counter ++;
        }
        GFL.att(new LoseEnergyAction(this.counter));
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public boolean canSpawn(){
        return (isChimeraMoment());
    }
}
