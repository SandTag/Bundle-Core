package bundlecore.powers;
import basemod.interfaces.CloneablePowerInterface;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import static bundlecore.BundleCoreMain.makeID;

public class SuffocationPower extends BundlePower implements CloneablePowerInterface, HealthBarRenderPower {
    public static final String POWER_ID = makeID("SuffocationPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    /**
     * Used by both the Sandgun and Kublai.
     */
    @Dont_Use_This_Externally
    public SuffocationPower(AbstractCreature owner, AbstractCreature source, int amount){
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (this.amount*5) + DESCRIPTIONS[1] + ((this.amount*5)*2) + DESCRIPTIONS[2];
    }

    public void atStartOfTurn() {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            if (this.owner.hasPower("Frail")) {
                CardCrawlGame.sound.play("POWER_CONSTRICTED", 0.125F);
                addToBot(new LoseHPAction(this.owner, this.source, ((5*this.amount)*2), AbstractGameAction.AttackEffect.POISON));
            } else {
                CardCrawlGame.sound.play("POWER_CONSTRICTED", 0.035F);
                addToBot(new LoseHPAction(this.owner, this.source, (5*this.amount), AbstractGameAction.AttackEffect.POISON));
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new SuffocationPower(owner, source, amount);
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_CONSTRICTED", 0.075F);
    }

    public int getHealthBarAmount() {
        if (this.owner.hasPower("Intangible") || this.owner.hasPower("IntangiblePlayer"))
            return 1;
        else if (this.owner.hasPower("Frail"))
            return (10*this.amount);
        return (5*this.amount);
    }

    public Color getColor() {
        return new Color(69/255f,69/255f,69/255f,1.0f);
    }
}
