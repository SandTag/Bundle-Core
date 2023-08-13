package bundlecore.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.BlackStar;
import javassist.*;
import javassist.bytecode.DuplicateMemberException;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnRandomRelicTier;

/**
 * Fixes a basegame bug with Black Star.
 */
public class FixBlackStar {
    @SpirePatch2(clz = BlackStar.class, method = SpirePatch.CONSTRUCTOR)
    public static class AddUpdateOverride {
        @SpireRawPatch
        public static void addMethod(CtBehavior ctMethodToPatch) throws CannotCompileException, NotFoundException {
            CtClass ctNestClass = ctMethodToPatch.getDeclaringClass();
            CtClass superClass = ctNestClass.getSuperclass();
            CtMethod superMethod = superClass.getDeclaredMethod("atBattleStart");
            CtMethod updateMethod = CtNewMethod.delegator(superMethod, ctNestClass);
            try {
                ctNestClass.addMethod(updateMethod);
            } catch (DuplicateMemberException ignored) {
                updateMethod = ctNestClass.getDeclaredMethod("atBattleStart");
            }
            updateMethod.insertAfter(FixBlackStar.class.getName()+".addToEliteInstances($0);");
        }
    }

    public static void addToEliteInstances(BlackStar __instance) {
        if (AbstractDungeon.getCurrRoom() != null && !(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite) && AbstractDungeon.getCurrRoom().eliteTrigger){
            __instance.beginLongPulse();
            AbstractDungeon.getCurrRoom().addNoncampRelicToRewards(returnRandomRelicTier());
        }
    }
}