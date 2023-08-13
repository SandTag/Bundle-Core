package bundlecore.jankymonsters;

import bundlecore.util.GFL;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.OmegaFlashEffect;

public class OmegaMonsterPower extends AbstractPower {
    public static final String POWER_ID = "bundlecore:OmegaMonsterPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("OmegaPower");
    private boolean skipFirst = true;
    private boolean onPlayer;

    /**
     * Omega for enemies.
     * @param owner - Monster that has this power
     * @param newAmount - The amount of stacks.
     * @param playerControlled - Legacy Variable, should be false.
     */
    public OmegaMonsterPower(AbstractCreature owner, int newAmount, boolean playerControlled) {
        this.name = powerStrings.NAME;
        this.ID = "bundlecore:OmegaMonsterPower";
        this.owner = owner;
        this.amount = newAmount;
        updateDescription();
        loadRegion("omega");
        this.onPlayer = playerControlled;
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public void atEndOfRound() {
        if (!this.onPlayer)
            if (!this.skipFirst) {
                if (Settings.FAST_MODE) {
                    addToBot(new VFXAction(new OmegaFlashEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1F));
                }
                else {
                    addToBot(new VFXAction(new OmegaFlashEffect(this.owner.hb.cX, this.owner.hb.cY), 0.2F));
                }
                DamageInfo info = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);
                info.applyEnemyPowersOnly(GFL.GAP());
                addToBot(new DamageAction(GFL.GAP(), info, AbstractGameAction.AttackEffect.FIRE));
            } else {
                this.skipFirst = false;
            }
    }

 /*   public void atTurnStart() {
        flash();
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(new OmegaFlashEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1F));
        }
        else {
            addToBot(new VFXAction(new OmegaFlashEffect(this.owner.hb.cX, this.owner.hb.cY), 0.2F));
        }
        DamageInfo info = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);
        info.applyEnemyPowersOnly(GFL.GAP());
        addToBot(new DamageAction(GFL.GAP(), info, AbstractGameAction.AttackEffect.FIRE));
    }*/

}
