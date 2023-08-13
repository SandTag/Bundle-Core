package bundlecore.patches;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

/**
 * Fixes orbs.
 */
@Deprecated
//todo: remove after basemod merges it.
@SpirePatch2(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "onModifyPower", paramtypes = {})
public class FixOrbsPatch {

    //This small postfix patch removes an arbitary check for focus when updating the description of orbs, which causes issues with custom orb's failing to update.

    public static void Postfix(){
        if (player != null) {
            player.hand.applyPowers();
            for (AbstractOrb o : player.orbs) {
                o.updateDescription();
            }
        }
        if ((getCurrRoom()).monsters != null) {
            for (AbstractMonster m : (getCurrRoom()).monsters.monsters)
                m.applyPowers();
        }
    }

}