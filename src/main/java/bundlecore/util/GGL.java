package bundlecore.util;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A functions library for getters.
 */
public class GGL {

    private static HashMap<String, AbstractCard> getCurses = ReflectionHacks.getPrivateStatic(CardLibrary.class, "curses");
    private static HashMap<String, AbstractCard> getCards = ReflectionHacks.getPrivateStatic(CardLibrary.class, "cards");

    public static AbstractCard betterReturnRandomCurse() {
        refreshPool();
        AbstractCard c = betterGetCurse().makeCopy();
        UnlockTracker.markCardAsSeen(c.cardID);
        return c;
    }

    public static void refreshPool(){
        getCurses = ReflectionHacks.getPrivateStatic(CardLibrary.class, "curses");
        getCards = ReflectionHacks.getPrivateStatic(CardLibrary.class, "cards");
    }

    public static AbstractCard betterGetCurse() {
        ArrayList<String> tmp = new ArrayList<>();
        for (Map.Entry<String, AbstractCard> c : getCurses.entrySet()) {
            if (c.getValue().rarity != AbstractCard.CardRarity.SPECIAL){
                tmp.add(c.getKey());
            }
        }
        return getCards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)));
    }

}
