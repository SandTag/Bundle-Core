package bundlecore.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class FixedWaitAction  extends AbstractGameAction {

    /**
     * Basegame wait action is ignored in fast mode, so if you do need a wait action to allow vfx to resolve for example, use this instead.
     * @param setDur = the amount of time (Seconds) the wait action wil occur.
     */
    public FixedWaitAction(float setDur) {
        this.setValues(null, null, 0);
        this.duration = setDur;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        this.tickDuration();
    }
}