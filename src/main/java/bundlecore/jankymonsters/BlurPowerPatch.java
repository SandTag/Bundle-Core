package bundlecore.jankymonsters;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class BlurPowerPatch {

    @SpirePatch2(clz = MonsterGroup.class, method = "applyPreTurnLogic")
    public static class ChangeIf {
        @SpireInstrumentPatch
        public static ExprEditor Foobar() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException{
                    if (m.getMethodName().equals("hasPower")){
                        m.replace("$_ = $proceed($$) || m.hasPower(\"" + BlurMonsterPower.POWER_ID + "\");");
                    }
                }
            };
        }
    }

    /*@SpireInsertPatch(loc = 130, localvars={""})
    public static void Insert(MonsterGroup __instance){
        for (AbstractMonster m : __instance.monsters){
            if (m.hasPower(BlurMonsterPower.POWER_ID)){
            }
        }
    }*/
}

