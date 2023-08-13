package bundlecore.actions;

import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.ArrayList;

public class FixStunAction extends AbstractGameAction {
    AbstractMonster m;

    /**
     * Used to allow stun to be applied without stun action and without breaking things.
     * @param monster = the monster having stun fixed for.
     */
    public FixStunAction(AbstractMonster monster) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.m = monster;
    }
    public void update() {
        ArrayList<AbstractPower> powers = m.powers;
        for (AbstractPower selection : powers) {
            if (selection instanceof StunMonsterPower) {
                selection.type = AbstractPower.PowerType.DEBUFF;
            }
        }
        addToTop(new WaitAction(0.05f));
        this.isDone = true;
    }

}
