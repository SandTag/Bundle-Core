package bundlecore.patches;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;

/**
 * This is how cheese board works.
 */
@Dont_Use_This_Externally
@SpirePatch(cls = "com.megacrit.cardcrawl.potions.AbstractPotion", method = "getPotency", paramtypes = {})
public class CheeseBoardPotency {

    public static int Postfix(int __result, AbstractPotion __instance) {
        int v = __result;

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("bundlecore:CheeseBoardBoss")) {
            switch (__instance.ID) {
                case "AttackPotion":
                    __instance.description = "Add a random Upgraded Attack card to your hand, it costs #b0 this turn.";
                    return v;
                case "SkillPotion":
                    __instance.description = "Add a random Upgraded Skill card to your hand, it costs #b0 this turn.";
                    return v;
                case "PowerPotion":
                    __instance.description = "Add a random Upgraded Power card to your hand, it costs #b0 this turn.";
                    return v;
            }
            v = __result * 6 / 5;
            if (v == __result)
                v++;
            return v;
        }
        return __result;
    }
}
