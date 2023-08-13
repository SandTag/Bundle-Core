package bundlecore.relics;
import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Objects;

import static bundlecore.BundleCoreMain.makeID;

public class StimulantRShop extends BundleRelic implements BundleChecker {
    private static final String NAME = "StimulantRShop";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.SHOP;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public StimulantRShop() {
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

    public void atBattleStart() {
        if (pl().hasRelic("aspiration:Mageblood")) {
            int Quantity = 0;
            int Filled = 0;
            Quantity += pl().potions.size();
            for (AbstractPotion p : AbstractDungeon.player.potions) {
                if (p != null){
                    if (!Objects.equals(p.ID, PotionSlot.POTION_ID)){
                        Filled ++;
                    }
                }
            }
            if (Filled == Quantity){
                flash();
                addToTop(new RelicAboveCreatureAction(pl(), this));
                addToBot(new ChangeStanceAction("Divinity"));
            }
        }
    }

    public void onUsePotion() {
        if (AbstractDungeon.getCurrRoom() != null) {
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                flash();
                addToBot(new ChangeStanceAction("Wrath"));
            }
        }
    }

    public boolean canSpawn(){
        return (isTimeDied() && isQuestRelicEnabled());
    }
}