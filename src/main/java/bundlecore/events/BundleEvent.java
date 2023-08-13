package bundlecore.events;

import basemod.abstracts.events.PhasedEvent;
import bundlecore.BundleCoreMain;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

/**
 * A Quaternary abstract that uses builders to form events in a simple manner.
 * This abstract greatly extends the functionality of the PhasedEvent although to be fair, this should just be an interface or something.
 */
public abstract class BundleEvent extends PhasedEvent {

    public static final String Three_Lagavulin = BundleCoreMain.makeID("Three Lagavulin");

    public static boolean IsBottom() {
        return (currMapNode != null && currMapNode.y < map.size() / 2);
    }

    public static boolean IsTop() {
        return (currMapNode != null && currMapNode.y > map.size() / 2);
    }

    public static boolean IsEventHealthy() {
        return AbstractDungeon.player.currentHealth >= 13 && AbstractDungeon.player.currentHealth >= (AbstractDungeon.player.maxHealth*0.25) && pl().maxHealth >= 25;
    }

    public static boolean IsEventSuperHealthy() {
        return AbstractDungeon.player.currentHealth >= 22 && AbstractDungeon.player.currentHealth >= (AbstractDungeon.player.maxHealth*0.35) && pl().maxHealth >= 43;
    }

    protected static AbstractPlayer pl(){
        return AbstractDungeon.player;
    }

    public static Boolean hasYeetableCards(){
        return CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() != 0;
    }

    public static Boolean hasValidUpgrade(){
        return AbstractDungeon.player.masterDeck.getUpgradableCards().size() != 0;
    }

    public static Boolean hasTwoUpgradeableAndYeetableCards(){
        return CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getUpgradableCards().getPurgeableCards()).size() >= 2;
    }

    public static boolean hasCardWithTypeUpdated(AbstractCard.CardType type) {
        Iterator var1 = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).getPurgeableCards().group.iterator();
        AbstractCard c;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            c = (AbstractCard)var1.next();
        } while(c.type != type);

        return true;
    }

    public static boolean hasCardWithTypeAndUpgradeableUpdated(AbstractCard.CardType type) {
        Iterator var1 = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getUpgradableCards()).getPurgeableCards().group.iterator();
        AbstractCard c;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            c = (AbstractCard)var1.next();
        } while(c.type != type);
        return true;
    }


    public static AbstractCard returnCardOfTypeUpdated(AbstractCard.CardType type, Random rng) {
        ArrayList<AbstractCard> cards = new ArrayList();
        Iterator var3 = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).getPurgeableCards().group.iterator();
        while(var3.hasNext()) {
            AbstractCard c = (AbstractCard)var3.next();
            if (c.type == type) {
                cards.add(c);
            }
        }
        return cards.remove(rng.random(cards.size() - 1));
    }

    public static AbstractCard returnCardOfTypeAndUpgradeableUpdated(AbstractCard.CardType type, Random rng) {
        ArrayList<AbstractCard> cards = new ArrayList();
        Iterator var3 = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getUpgradableCards()).getPurgeableCards().group.iterator();
        while(var3.hasNext()) {
            AbstractCard c = (AbstractCard)var3.next();
            if (c.type == type) {
                cards.add(c);
            }
        }
        return cards.remove(rng.random(cards.size() - 1));
    }

    public static Boolean hastwoUpgradeableAndYeetableCardsOfWhichOneAttackAndOneSkill(){
        return
                CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getUpgradableCards().getPurgeableCards()).getCardsOfType(AbstractCard.CardType.ATTACK).size() != 0 &&
                        CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getUpgradableCards().getPurgeableCards().getCardsOfType(AbstractCard.CardType.SKILL)).size() != 0;
    }

    public static Boolean hasOneYeetableOfEachType(){
        return
                CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).getCardsOfType(AbstractCard.CardType.ATTACK).size() != 0 &&
                        CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards().getCardsOfType(AbstractCard.CardType.SKILL)).size() != 0 &&
                        CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards().getCardsOfType(AbstractCard.CardType.POWER)).size() != 0;
    }

    public static boolean hasRemoveableStrikes() {
        Iterator var1 = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).getPurgeableCards().group.iterator();
        AbstractCard c;
        do {
            if (!var1.hasNext()) {
                return false;
            }
            c = (AbstractCard)var1.next();
        } while(!c.hasTag(AbstractCard.CardTags.STRIKE));
        return true;
    }

    public static AbstractCard getRandomRemoveableWithTag(AbstractCard.CardType type, Random rng, AbstractCard.CardTags tags) {
        ArrayList<AbstractCard> cards = new ArrayList();
        Iterator var3 = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).getPurgeableCards().group.iterator();
        while(var3.hasNext()) {
            AbstractCard c = (AbstractCard)var3.next();
            if (c.type == type && c.hasTag(tags)) {
                cards.add(c);
            }
        }
        return cards.remove(rng.random(cards.size() - 1));
    }

    public static boolean hasToxEgg (){
        Iterator var1 = AbstractDungeon.player.relics.iterator();
        AbstractRelic r;

        do {
            if (!var1.hasNext()) {
                return false;
            }
            r = (AbstractRelic)var1.next();
        } while(!Objects.equals(r.relicId, ToxicEgg2.ID));
        return true;
    }

    public BundleEvent(String id, String title, String imgUrl) {
        super(id, title, imgUrl);
    }

    public static AbstractRelic.RelicTier returnTrulyRandomRelicTier() {

        int roll = relicRng.random(0, 99);
        if (roll <= 20) {
            return AbstractRelic.RelicTier.COMMON;
        }
        else if (roll < 41) {
            return AbstractRelic.RelicTier.UNCOMMON;
        }
        else if (roll < 61) {
            return AbstractRelic.RelicTier.RARE;
        }
        else if (roll < 81) {
            return AbstractRelic.RelicTier.BOSS;
        }
        else return AbstractRelic.RelicTier.SHOP;
    }






    //
    //
    // // // // // From this point onwards, are intergrations for specific events, rather than tools
    //
    //





    // =-> PegEventsRetiredSlime
    public void removeStrikesRetiredSlime () {
        Iterator SMDR = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).getPurgeableCards().group.iterator();
        while (SMDR.hasNext()) {
            AbstractCard removeStrikesPls = (AbstractCard) SMDR.next();
            if (removeStrikesPls.hasTag(AbstractCard.CardTags.STRIKE)) {
                AbstractDungeon.effectList.add(new PurgeCardEffect(removeStrikesPls.makeStatEquivalentCopy(),
                        MathUtils.random(0.1F, 0.9F) * Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT));
                AbstractDungeon.player.masterDeck.removeCard(removeStrikesPls);
            }
        }
    }

    public void remove_A_L_L_cursesAction () {
        Iterator AMDC = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group.iterator();
        while (AMDC.hasNext()) {
            AbstractCard cursesGIGAyeet = (AbstractCard) AMDC.next();
            if (cursesGIGAyeet.type == AbstractCard.CardType.CURSE) {
                AbstractDungeon.effectList.add(new PurgeCardEffect(cursesGIGAyeet.makeStatEquivalentCopy(),
                        MathUtils.random(0.1F, 0.9F) * Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT));
                AbstractDungeon.player.masterDeck.removeCard(cursesGIGAyeet);
            }
        }
    }

    public void duplicateEntireDeck () {
        Iterator AMDC = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group.iterator();
        while (AMDC.hasNext()) {
            AbstractCard dupeAllNonCurse = (AbstractCard) AMDC.next();
            if (dupeAllNonCurse.type != AbstractCard.CardType.CURSE) {
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(dupeAllNonCurse.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }
        }
    }

    public void yeetEntireDeck () {
        Iterator AMDC = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group.iterator();
        while (AMDC.hasNext()) {
            AbstractCard allcards = (AbstractCard) AMDC.next();
            {
                AbstractDungeon.effectList.add(new PurgeCardEffect(allcards.makeStatEquivalentCopy(),
                        MathUtils.random(0.1F, 0.9F) * Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT));
                AbstractDungeon.player.masterDeck.removeCard(allcards);
            }
        }
    }

}
