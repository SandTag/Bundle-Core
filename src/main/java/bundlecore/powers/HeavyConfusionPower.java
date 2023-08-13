package bundlecore.powers;
import basemod.interfaces.CloneablePowerInterface;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import static bundlecore.BundleCoreMain.makeID;


public class HeavyConfusionPower extends BundlePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("HeavyConfusionPower");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    /**
     * Used by a relic in Bundle of Content.
     */
    @Dont_Use_This_Externally
    public HeavyConfusionPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }
    public void onCardDraw(AbstractCard card) {
        if (card.cost >= 0) {
            int newCost = AbstractDungeon.cardRandomRng.random(3);
            if (newCost < card.makeCopy().cost){
                newCost = card.makeCopy().cost;
            }
            if (card.cost != newCost) {
                card.cost = newCost;
                card.costForTurn = card.cost;
                card.isCostModified = true;
            }
            card.freeToPlayOnce = false;
        }
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    @Override
    public AbstractPower makeCopy() {
        return new HeavyConfusionPower(owner, amount);
    }
}