/*
package bundlecore.relics;
import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.jankymonsters.Cursed_DecaFinalBoss;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.TimeWarpPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import static bundlecore.BundleCoreMain.makeID;

public class HexacombBoss      extends BundleRelic implements BundleChecker {
    private static final String NAME = "HexacombBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = AbstractRelic.RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    private byte soundPlays = 0;
    private byte soundPlays2 = 0;

    public HexacombBoss() {
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

    @Override
    public void onEquip()
    {
        pl().energy.energyMaster+=2;
        pl().increaseMaxHp(6, true);
        if (Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey) {
            onTrigger();
        }
    }

    @Override
    public void onUnequip(){
        pl().energy.energyMaster-=2;
    }

    public void onTrigger() {
        flash();
        this.grayscale = false;
        soundPlays = 10;
        beginPulse();
        this.pulse = true;
        Burn PermaOuchy = new Burn();
        PermaOuchy.upgrade();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(PermaOuchy.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    @Override
    public void update() {
        super.update();
        if (soundPlays > 0) {
            --soundPlays;
            CardCrawlGame.sound.play("NECRONOMICON", MathUtils.random(1.0F, -1.0F));
        }
        if (soundPlays2 > 0) {
            --soundPlays2;
            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_2", MathUtils.random(1.0F, -1.0F));
        }
    }

    public void onEnterRoom(AbstractRoom room) {
        if(AbstractDungeon.actNum == 4 && room instanceof com.megacrit.cardcrawl.rooms.ShopRoom){
            beginPulse();
            flash();
            soundPlays2 = 10;
            this.pulse = true;
        }
        if (room instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite) {
            beginPulse();
            this.pulse = true;
        }
        else if (AbstractDungeon.actNum != 4){
            this.pulse = false;
        }
    }

    @Override
    public void atTurnStart(){
        Burn Ouchy = new Burn();
        Ouchy.upgrade();
        addToTop(new MakeTempCardInDrawPileAction(Ouchy, 1, false, true));
    }

    @Override
    public void atPreBattle() {
        if((AbstractDungeon.getCurrRoom()).eliteTrigger && AbstractDungeon.actNum == 4 && !Settings.isEndless) {
            Cursed_DecaFinalBoss Decaer = new Cursed_DecaFinalBoss(-666.0F, 10.0F);
            addToBot(new SpawnMonsterAction(Decaer, false));
            addToBot(new RelicAboveCreatureAction(Decaer, this));
            this.grayscale = true;
        }
    }

    @Override
    public boolean canSpawn(){
        return (isCollectDied() && !hasQuestRelic() && isBPotions() && isMazalethUpdate());
    }}
*/