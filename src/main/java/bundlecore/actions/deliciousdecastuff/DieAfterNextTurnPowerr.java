package bundlecore.actions.deliciousdecastuff;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class DieAfterNextTurnPowerr extends AbstractPower {
    public static final String POWER_ID = "DieAfterNextTurnPowerr";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("EndTurnDeath");

    public DieAfterNextTurnPowerr(AbstractCreature owner) {
        this.name = powerStrings.NAME;
        this.ID = "DieAfterNextTurnPowerr";
        this.owner = owner;
        this.amount = -1;
        updateDescription();
        loadRegion("end_turn_death");
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new VFXAction(new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
        AbstractDungeon.player.isDead = true;
        AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
        AbstractDungeon.player.currentHealth = 0;
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "DieAfterNextTurn"));
    }
}
