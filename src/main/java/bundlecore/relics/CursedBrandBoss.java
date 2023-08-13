package bundlecore.relics;
import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.jankymonsters.BigGothCollector;
import bundlecore.jankymonsters.StonksTorchhead;
import bundlecore.util.GFL;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static bundlecore.BundleCoreMain.makeID;

public class CursedBrandBoss extends BundleRelic implements BundleChecker{
    private static final String NAME = "CursedBrandBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    private byte soundPlays = 0;
    private byte soundPlays2 = 0;

    public CursedBrandBoss() {
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
    public void onUnequip(){
        pl().energy.energyMaster--;
    }

    @Override
    public void onEquip()
    {
        pl().energy.energyMaster++;
        if (Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey) {
            onTrigger();
        }
    }

    public void onTrigger() {
        flash();
        this.grayscale = false;
        soundPlays = 10;
        beginPulse();
        this.pulse = true;
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Burn(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
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
            CardCrawlGame.sound.play("MONSTER_COLLECTOR_SUMMON", MathUtils.random(1.0F, -1.0F));
        }
    }

    public void onEnterRoom(AbstractRoom room) {
        if(AbstractDungeon.actNum == 4 && room instanceof com.megacrit.cardcrawl.rooms.ShopRoom){
            beginPulse();
            flash();
            soundPlays2 = 10;
            this.pulse = true;
        }
        if (room instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite && AbstractDungeon.actNum == 4) {
            beginPulse();
            this.pulse = true;
        }
        else if (AbstractDungeon.actNum != 4){
            this.pulse = false;
        }
    }

    @Override
    public void atTurnStart(){
    }

    public boolean bossCheck(){
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void atPreBattle() {
        if((AbstractDungeon.getCurrRoom()).eliteTrigger && AbstractDungeon.actNum == 4 && !Settings.isEndless) {
            BigGothCollector GothLady = new BigGothCollector(-666.0F, 10.0F);
            addToBot(new SpawnMonsterAction(GothLady, false));
            addToBot(new RelicAboveCreatureAction(GothLady, this));
            this.grayscale = true;
        }
        else if(bossCheck()){
            flash();
            StonksTorchhead Stonkyboi = new StonksTorchhead(300.0F, 10.0F);
            addToBot(new SpawnMonsterAction(Stonkyboi, false));
            addToTop(new ApplyPowerAction(pl(), pl(), new WeakPower(pl(), 1, false), 1));
            addToTop(new ApplyPowerAction(pl(), pl(), new FrailPower(pl(), 1, false), 1));
            addToTop(new ApplyPowerAction(pl(), pl(), new VulnerablePower(pl(), 1, false), 1));
            addToBot(new RelicAboveCreatureAction(GFL.GAP(), this));
        }
        else{
            addToTop(new ApplyPowerAction(pl(), pl(), new WeakPower(pl(), 1, false), 1));
            addToTop(new ApplyPowerAction(pl(), pl(), new FrailPower(pl(), 1, false), 1));
            addToTop(new ApplyPowerAction(pl(), pl(), new VulnerablePower(pl(), 1, false), 1));
            addToBot(new RelicAboveCreatureAction(GFL.GAP(), this));
        }
    }

    @Override
    public boolean canSpawn(){
        return (isTimeDied() && !hasQuestRelic() && isQuestRelicEnabled() && (AbstractDungeon.actNum > 1 || AbstractDungeon.floorNum == 0));
    }

}