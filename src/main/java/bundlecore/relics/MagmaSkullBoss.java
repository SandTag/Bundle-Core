package bundlecore.relics;
;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.potions.LavaPotionForbidden;
import bundlecore.powers.OnFirePower;
import bundlecore.relics.BundleRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnUsePotionRelic;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import java.util.Objects;
import static bundlecore.BundleCoreMain.makeID;

public class MagmaSkullBoss extends BundleRelic implements BundleChecker, BetterOnUsePotionRelic {

    private static final String NAME = "MagmaSkullBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;

    public MagmaSkullBoss() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
    }
    public String getUpdatedDescription() {
        if (Loader.isModLoaded("aspiration")){
            return this.DESCRIPTIONS[1];
        }
        else{
            return this.DESCRIPTIONS[0];
        }
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.potionSlots += 2;
        AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 2));
        AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
    }

    public void atPreBattle() {
        if (pl().hasRelic("aspiration:Mageblood")) {
            int Quantity = 0;
            Quantity += pl().potions.size();
            if ((AbstractDungeon.getCurrRoom()).monsters.monsters.size() != 0) {
                for (int i = 0; i < Quantity; i++) {
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                        addToTop(new ApplyPowerAction(mo, pl(), new OnFirePower(mo, pl(), 4), 4, true));
                    }
                }
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    addToTop(new RelicAboveCreatureAction(mo, this));
                }
            }
        }
    }

    @Override
    public void atBattleStart(){
        boolean bossesinroom = false;
        if (!(AbstractDungeon.getCurrRoom()).eliteTrigger) {
            this.grayscale = true;
            bossesinroom = true;
        }
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS) {
                this.grayscale = true;
                bossesinroom = true;
                break;
            }
        }
        if (!bossesinroom){
            this.grayscale = false;
            flash();
            AbstractPotion po = AbstractDungeon.returnRandomPotion();
            AbstractPotion ta = AbstractDungeon.returnRandomPotion();
            AbstractDungeon.getCurrRoom().addPotionToRewards(po);
            AbstractDungeon.getCurrRoom().addPotionToRewards(ta);
        }
    }

    @Override
    public void betterOnUsePotion(AbstractPotion p) {
        if (!Objects.equals(p.ID, LavaPotionForbidden.POTION_ID)){
            AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(new LavaPotionForbidden()));
        }
    }

    public boolean canSpawn(){
        return (isBPotionsXTerra() && AbstractDungeon.actNum == 1 && AbstractDungeon.floorNum >= 2);
    }

}
