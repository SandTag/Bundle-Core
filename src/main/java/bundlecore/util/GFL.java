package bundlecore.util;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Contains a wide array of useful functions.
 */
public class GFL {

    public static final Color DecaOrange = new Color((233f/255f), (169f/255f), (100f/255f), 0.0f);


    public static AbstractPlayer GAP() {
        return AbstractDungeon.player;
    }

    public static void forAllCardsInList(Consumer<AbstractCard> consumer, ArrayList<AbstractCard> cardsList) {
        for (AbstractCard c : cardsList)
            consumer.accept(c);
    }

    public static ArrayList<AbstractCard> getAllCardsInCardGroups(boolean includeHand, boolean includeExhaust) {
        ArrayList<AbstractCard> masterCardsList = new ArrayList<>();
        masterCardsList.addAll(AbstractDungeon.player.drawPile.group);
        masterCardsList.addAll(AbstractDungeon.player.discardPile.group);
        if (includeHand)
            masterCardsList.addAll(AbstractDungeon.player.hand.group);
        if (includeExhaust)
            masterCardsList.addAll(AbstractDungeon.player.exhaustPile.group);
        return masterCardsList;
    }

    public static void forAllMonstersLiving(Consumer<AbstractMonster> consumer) {
        for (AbstractMonster m : aliveMonsterList())
            consumer.accept(m);
    }

    public static ArrayList<AbstractMonster> aliveMonsterList() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>((AbstractDungeon.getMonsters()).monsters);
        monsters.removeIf(m -> (m.isDead || m.halfDead));
        return monsters;
    }

    public static void forAllMonstersActive(Consumer<AbstractMonster> consumer) {
        for (AbstractMonster m : activeMonsterList())
            consumer.accept(m);
    }

    public static ArrayList<AbstractMonster> activeMonsterList() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>((AbstractDungeon.getMonsters()).monsters);
        monsters.removeIf(m -> (m.isDead || m.isDying || m.escaped || m.halfDead || m.isEscaping));
        return monsters;
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred) {
        return getCardsMatchingPredicate(pred, false);
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred, boolean allcards) {
        if (allcards) {
            ArrayList<AbstractCard> arrayList = new ArrayList<>();
            for (AbstractCard c : CardLibrary.getAllCards()) {
                if (pred.test(c))
                    arrayList.add(c.makeStatEquivalentCopy());
            }
            return arrayList;
        }
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (pred.test(c))
                cardsList.add(c.makeStatEquivalentCopy());
        }
        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (pred.test(c))
                cardsList.add(c.makeStatEquivalentCopy());
        }
        for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (pred.test(c))
                cardsList.add(c.makeStatEquivalentCopy());
        }
        return cardsList;
    }

    public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred, boolean allCards) {
        return getRandomItem(getCardsMatchingPredicate(pred, allCards));
    }

    public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred) {
        return returnTrulyRandomPrediCardInCombat(pred, false);
    }

    public static <T> T getRandomItem(ArrayList<T> list) {
        return list.isEmpty() ? null : list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }

    public static boolean isInCombat() {
        return (CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT);
    }

    public static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public static void atl(AbstractGameEffect action) {
        AbstractDungeon.effectList.add(action);
    }

    public static void atq(AbstractGameEffect action) {
        AbstractDungeon.effectList.add(action);
    }

    public static void vfx(AbstractGameEffect gameEffect) {
        atb((AbstractGameAction)new VFXAction(gameEffect));
    }

    public static void vfx(AbstractGameEffect gameEffect, float duration) {
        atb((AbstractGameAction)new VFXAction(gameEffect, duration));
    }

    public static void makeInHand(AbstractCard c, int i) {
        atb((AbstractGameAction)new MakeTempCardInHandAction(c, i));
    }

    public static void makeInHandSameUUID(AbstractCard c) {
        atb((AbstractGameAction)new MakeTempCardInHandAction(c, true, true));
    }

    public static void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    public static void shuffleIn(AbstractCard c, int i) {
        atb((AbstractGameAction)new MakeTempCardInDrawPileAction(c, i, true, true));
    }

    public static void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    public static void intoDiscard(AbstractCard c, int i) {
        atb((AbstractGameAction)new MakeTempCardInDiscardAction(c, i));
    }

    public static void topDeck(AbstractCard c, int i) {
        atb((AbstractGameAction)new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    public static void topDeck(AbstractCard c) {
        topDeck(c, 1);
    }

    public static void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)AbstractDungeon.player, po, po.amount));
    }

    public static void applyToEnemyTop(AbstractMonster m, AbstractPower po) {
        att((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelf(AbstractPower po) {
        atb((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelfTop(AbstractPower po) {
        att((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, po, po.amount));
    }

    public static void dmg(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
        atb((AbstractGameAction)new DamageAction(target, info, effect));
    }

    public static void dmg(AbstractCreature target, DamageInfo info) {
        dmg(target, info, AbstractGameAction.AttackEffect.NONE);
    }

    public static void block(AbstractCreature target, int amount) {
        atb((AbstractGameAction)new GainBlockAction(target, amount));
    }

}
