package bundlecore.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;

import static bundlecore.BundleCoreMain.makeID;
public class PressureBossPower extends BundlePower {
    public static final String POWER_ID = makeID("PressureBossPower");
    private static final AbstractPower.PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    public static final boolean TURN_BASED = false;

    /**
     * To be used on any quest boss.
     */
    public PressureBossPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        updateDescription();
    }

    public void atEndOfRound() {
        this.amount = 0;
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[1];
        if (this.amount != 0)
            this.description += DESCRIPTIONS[2] + (30 - this.amount) + DESCRIPTIONS[3];
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        flashWithoutSound();
        this.amount++;
        updateDescription();
        if (this.amount >= 30) {
            this.amount = 0;
            playApplyPowerSfx();
            AbstractDungeon.actionManager.callEndTurnEarlySequence();
            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD.cpy(), true));
            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
        }
        updateDescription();
    }

}
