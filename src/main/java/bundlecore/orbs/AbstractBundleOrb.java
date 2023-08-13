package bundlecore.orbs;

import basemod.abstracts.CustomOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * Largely a TODO.
 * <P>
 * Needs vastly increased functionality due to how bare-bones orbs are.
 */
public abstract class AbstractBundleOrb extends CustomOrb {

    public AbstractBundleOrb(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, String passiveDescription, String evokeDescription, String imgPath) {
        super(ID, NAME, basePassiveAmount, baseEvokeAmount, passiveDescription, evokeDescription, imgPath);
    }

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    protected static AbstractPlayer pl(){
        return AbstractDungeon.player;
    }

}
