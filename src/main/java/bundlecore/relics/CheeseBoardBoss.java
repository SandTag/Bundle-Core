package bundlecore.relics;

import bundlecore.bundleloadedbools.BundleChecker;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import static bundlecore.BundleCoreMain.makeID;

public class CheeseBoardBoss extends BundleRelic implements BundleChecker {

    private static final String NAME = "CheeseBoardBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public CheeseBoardBoss() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
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
        pl().potionSlots ++;
        pl().potions.add(new PotionSlot(pl().potionSlots - 1));
        for (AbstractPotion p : pl().potions){
            p.initializeData();}
    }

    public void onEnterRoom(AbstractRoom room) {
        this.pulse = false;
    }

    @Override
    public void atBattleStart() {

        boolean bossesinroom = false;

        if ((AbstractDungeon.getCurrRoom()).eliteTrigger) {
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

        if (!bossesinroom) {
            this.grayscale = false;
            flash();

            AbstractPotion po = AbstractDungeon.returnRandomPotion();
            AbstractDungeon.getCurrRoom().addPotionToRewards(po);
        }

        if (pl().hasRelic("aspiration:Mageblood")) {
            this.grayscale = false;
            this.pulse = true;
            AbstractPotion popo = AbstractDungeon.returnRandomPotion();
            AbstractDungeon.getCurrRoom().addPotionToRewards(popo);
        }
    }

    @Override
    public void onVictory() {
        this.pulse = false;
        this.grayscale = false;
    }

    public void onUsePotion() {
        flash();
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new RelicAboveCreatureAction(pl(), this));
            pl().increaseMaxHp(1, true);
        } else {
            pl().increaseMaxHp(1, false);
        }
    }

    public boolean canSpawn() {
        return isBFoodXPotions();
    }

}
