package bundlecore.util.PotionFixes;
import bundlecore.potions.AbstractCustomImagePotion;
import bundlecore.potions.AbstractForbiddenPotion;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class potionsUtil {
    protected static boolean hasFilledPotions = false;
    protected static ArrayList<String> findPotions = new ArrayList<String>(65535);
    public static void fillPotions(){
        hasFilledPotions = true;
        int counter = 0;
        Iterator potionList = PotionHelper.getPotions(null, true).iterator();
        while (potionList.hasNext()) {
            AbstractPotion potionInList = PotionHelper.getPotion(potionList.next().toString());
            if (potionInList instanceof AbstractForbiddenPotion) {
                if (((AbstractForbiddenPotion) potionInList).IsForbiddenAfter){
                    findPotions.add(potionInList.ID);
                    System.out.println("Removed " + potionInList.ID + " from potion pool");
                    counter++;
                }
            } else if (potionList instanceof AbstractCustomImagePotion) {
                if (((AbstractCustomImagePotion) potionInList).IsForbiddenAfter){
                    findPotions.add(potionInList.ID);
                    System.out.println("Removed " + potionInList.ID + " from potion pool");
                    counter++;
                }
            }
        }
        if (counter == 0) {
            throw new IllegalStateException("No potions found in potionsUtil.java.fillPotions()");
        }
    }

    public static final List<String> forbiddenPotionsIDs = findPotions;

    public static ArrayList<AbstractPotion> getAllCorruptedPotions() {
        return (ArrayList<AbstractPotion>)forbiddenPotionsIDs.stream().map(PotionHelper::getPotion).collect(Collectors.toCollection(ArrayList::new));
    }
}
