package bundlecore.powers;

import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import static bundlecore.BundleCoreMain.makeID;

public class OnFirePower extends BundlePower implements CloneablePowerInterface, HealthBarRenderPower {

    public static final String POWER_ID = makeID("OnFirePower");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    /**
     * Used by the Lava Potion.
     */
    @Dont_Use_This_Externally
    public OnFirePower(AbstractCreature owner, AbstractCreature source, int amount){
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }
    public void updateDescription() {this.description = DESCRIPTIONS[0] + (this.amount+1) + DESCRIPTIONS[1] + ((this.amount+1)*2) + DESCRIPTIONS[2];}

    public void atStartOfTurn() {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            addToTop(new ApplyPowerAction(this.owner, this.owner, new OnFirePower(this.owner, this.owner, 1), 1));
            if (this.owner.hasPower("Vulnerable")) {
                addToBot(new LoseHPAction(this.owner, this.source, ((this.amount+1)*2), AbstractGameAction.AttackEffect.FIRE));
            } else {
                addToBot(new LoseHPAction(this.owner, this.source, (this.amount+1), AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new OnFirePower(owner, source, amount);
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("ATTACK_FLAME_BARRIER", 0.05F);
    }

    public int getHealthBarAmount() {
        if (this.owner.hasPower("Intangible") || this.owner.hasPower("IntangiblePlayer"))
            return 1;
        else if (this.owner.hasPower("Vulnerable"))
            return ((this.amount+1)*2);
        return (this.amount+1);
    }

    public Color getColor() {
        return new Color(243/255f,118/255f,46/255f,1.0f);
    }
}
