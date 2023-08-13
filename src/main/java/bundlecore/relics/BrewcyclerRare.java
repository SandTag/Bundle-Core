package bundlecore.relics;
import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.functions.potionOnDiscard.DiscardPotionRelic;
import bundlecore.potions.LavaPotionForbidden;
import bundlecore.potions.PotionFireForbidden;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Objects;
import static bundlecore.BundleCoreMain.makeID;

public class BrewcyclerRare extends BundleRelic implements BundleChecker, DiscardPotionRelic {
    private static final String NAME = "BrewcyclerRare";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.RARE;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public BrewcyclerRare() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
        setDescriptionWithCard();
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void setDescriptionWithCard() {
        tips.clear();

        description = DESCRIPTIONS[0];
        this.tips.add(new PowerTip(this.name, this.description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(DESCRIPTIONS[1]), BaseMod.getKeywordDescription(DESCRIPTIONS[1])));

        initializeTips();
    }

    public void atPreBattle(){
        this.counter = 2;
        this.grayscale = false;
    }

    @Override
    public void onDiscardPotionAdvanced(AbstractPotion p) {
        if (this.counter >= 1){
            if (AbstractDungeon.getCurrRoom() != null){
                if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
                    flash();
                    int sillyPotion = AbstractDungeon.cardRandomRng.random(100);
                    if (sillyPotion != 69 && sillyPotion <= 95) {
                        if (p.rarity != AbstractPotion.PotionRarity.PLACEHOLDER && (p.rarity == AbstractPotion.PotionRarity.COMMON || p.rarity ==  AbstractPotion.PotionRarity.UNCOMMON || p.rarity ==  AbstractPotion.PotionRarity.RARE)){
                            ArrayList<AbstractPotion> pot = PotionHelper.getPotionsByRarity(p.rarity);
                            AbstractPotion potout = pot.get(AbstractDungeon.potionRng.random(pot.size() - 1)).makeCopy();
                            (AbstractDungeon.getCurrRoom()).rewards.add(new RewardItem(potout));
                        }
                        else{
                            (AbstractDungeon.getCurrRoom()).rewards.add(new RewardItem(new PotionFireForbidden()));
                        }
                    }
                    else if (sillyPotion != 69 && p.rarity == AbstractPotion.PotionRarity.PLACEHOLDER && !Objects.equals(p.ID, PotionFireForbidden.POTION_ID)){
                        int sillyerPotion = AbstractDungeon.cardRandomRng.random(100);
                        if(sillyerPotion <= 80) {
                            ArrayList<AbstractPotion> pot2 = PotionHelper.getPotionsByRarity(AbstractPotion.PotionRarity.PLACEHOLDER);
                            AbstractPotion potout2 = pot2.get(AbstractDungeon.potionRng.random(pot2.size() - 1)).makeCopy();
                            (AbstractDungeon.getCurrRoom()).rewards.add(new RewardItem(potout2));
                        }
                        else if(sillyerPotion <= 95){
                            (AbstractDungeon.getCurrRoom()).rewards.add(new RewardItem(new LavaPotionForbidden()));
                        }
                        else{
                            (AbstractDungeon.getCurrRoom()).rewards.add(new RewardItem(new PotionFireForbidden()));
                        }
                    }
                    else{
                        (AbstractDungeon.getCurrRoom()).rewards.add(new RewardItem(new PotionFireForbidden()));
                    }
                    this.counter--;
                    if (this.counter <= 0) {
                        this.grayscale = true;
                    }
                }
            }
        }
    }

    public void onVictory(){
        this.counter = -1;
        this.grayscale = false;
    }

    public boolean canSpawn(){
        return (isAscension20TimeDied() && isQuestRelicEnabled());
    }

}