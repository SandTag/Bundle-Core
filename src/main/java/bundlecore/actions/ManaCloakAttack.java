package bundlecore.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class ManaCloakAttack extends AbstractGameAction  {

    /**
     * This is used by a crossover relic in Bundle of Terra.
     * @param source (This is the player)
     */
    public ManaCloakAttack(AbstractCreature source) {
        this.source = source;
    }

    public void update() {
        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(5, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, null, new WeakPower(mo, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
        }
        this.isDone = true;
    }

}
