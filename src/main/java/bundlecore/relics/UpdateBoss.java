package bundlecore.relics;
import CardAugments.cardmods.rare.MK2Mod;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.util.Interfaces.patches.OnCreateMidCombatCards;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.Arrays;
import java.util.List;
import static bundlecore.BundleCoreMain.makeID;

@AutoAdd.Ignore
public class UpdateBoss extends BundleRelic implements BundleChecker, OnCreateMidCombatCards {
    private static final String NAME = "UpdateBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    private final List<String> NaughtyCards = Arrays.asList("BecomeAlmighty", "Calm", "Wrath", "FameAndFortune", "LiveForever");
    private boolean firstTurn;

    public UpdateBoss() {
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
            if (!CardModifierManager.hasModifier(c, MK2Mod.ID) && !NaughtyCards.contains(c.cardID)) {
                if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                    CardModifierManager.addModifier(c, new MK2Mod());
                }
            }
        }
    }

    @Override
    public void onPreviewObtainCard(AbstractCard c) {
        onObtainCard(c);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        for (AbstractCard c : pl().masterDeck.group) {
            if (!CardModifierManager.hasModifier(c, MK2Mod.ID) && !NaughtyCards.contains(c.cardID)) {
                if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                    CardModifierManager.addModifier(c, new MK2Mod());
                }
            }
        }
    }

    @Override
    public void atPreBattle() {
        this.firstTurn = true;
        for (AbstractCard c : pl().masterDeck.group) {
            if (!CardModifierManager.hasModifier(c, MK2Mod.ID) && !NaughtyCards.contains(c.cardID)) {
                if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                    CardModifierManager.addModifier(c, new MK2Mod());
                }
            }
        }
        for (AbstractCard c : pl().drawPile.group) {
            if (!CardModifierManager.hasModifier(c, MK2Mod.ID) && !NaughtyCards.contains(c.cardID)) {
                if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                    CardModifierManager.addModifier(c, new MK2Mod());
                }
            }
        }
        for (AbstractCard c : pl().hand.group) {
            if (!CardModifierManager.hasModifier(c, MK2Mod.ID) && !NaughtyCards.contains(c.cardID)) {
                if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                    CardModifierManager.addModifier(c, new MK2Mod());
                }
            }
        }
        for (AbstractCard c : pl().discardPile.group) {
            if (!CardModifierManager.hasModifier(c, MK2Mod.ID) && !NaughtyCards.contains(c.cardID)) {
                if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                    CardModifierManager.addModifier(c, new MK2Mod());
                }
            }
        }
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (!CardModifierManager.hasModifier(c, MK2Mod.ID) && !NaughtyCards.contains(c.cardID)) {
            if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                CardModifierManager.addModifier(c, new MK2Mod());
            }
        }
    }

    @Override
    public void onCreateCard(AbstractCard c) {
        if (!CardModifierManager.hasModifier(c, MK2Mod.ID) && !NaughtyCards.contains(c.cardID)) {
            if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                CardModifierManager.addModifier(c, new MK2Mod());
            }
        }
    }

    @Override
    public void atBattleStart() {
        this.counter = -1;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public boolean canSpawn() {
        return (isChimeraMoment());
    }
}