package bundlecore.util.PotionFixes;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.random.Random;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import java.util.ArrayList;
import java.util.stream.Collectors;
@Dont_Use_This_Externally
public class removeForbiddenPotions {
    @SpirePatch(clz = PotionHelper.class, method = "getRandomPotion", paramtypez = {})
    @SpirePatch(clz = PotionHelper.class, method = "getRandomPotion", paramtypez = {Random.class})
    public static class FilterOutForbiddenPotions extends ExprEditor {
        @Override
        public void edit(FieldAccess fieldAccess) throws CannotCompileException {
            if (fieldAccess.getClassName().equals(PotionHelper.class.getName()) && fieldAccess.getFieldName().equals("potions")) {
                fieldAccess.replace(String.format("{ $_ = %1$s.filterOutForbiddenPotions(potions); }", removeForbiddenPotions.class.getName()));
            }
        }

        @SpireInstrumentPatch
        public static ExprEditor filterOutForbiddenRarityPotions() {
            return new FilterOutForbiddenPotions();
        }
    }

    public static ArrayList<String> filterOutForbiddenPotions(ArrayList<String> potions) {
        if (!potionsUtil.hasFilledPotions) {
            potionsUtil.fillPotions();
        }
        return new ArrayList<>(potions).stream()
                .filter(potionId -> !potionsUtil.forbiddenPotionsIDs.contains(potionId))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
