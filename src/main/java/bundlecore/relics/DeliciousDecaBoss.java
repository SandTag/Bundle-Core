package bundlecore.relics;
import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.jankymonsters.Cursed_DecaFinalBoss;
import bundlecore.util.GFL;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;
import static bundlecore.BundleCoreMain.makeID;

public class DeliciousDecaBoss extends BundleRelic implements BundleChecker {
    private static final String NAME = "DeliciousDecaBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = AbstractRelic.RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;

    private byte soundPlays = 0;
    private byte soundPlays2 = 0;

    public DeliciousDecaBoss() {
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
        pl().energy.energyMaster++;
        pl().decreaseMaxHealth(1);
        if (Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey) {
            onTrigger();
        }
    }

    @Override
    public void onUnequip(){
        pl().energy.energyMaster--;
    }

    public void onTrigger() {
        flash();
        this.grayscale = false;
        soundPlays = 10;
        beginPulse();
        this.pulse = true;
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wound(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    @Override
    public void usedUp(){
        if(AbstractDungeon.ascensionLevel >= 20) {
            pl().energy.energyMaster--;
            pl().increaseMaxHp(1, true);
        }
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
            CardCrawlGame.sound.play("TINGSHA", MathUtils.random(1.0F, -1.0F));
        }
    }

    @Override
    public void atTurnStart() {
        if (!this.usedUp) {
            flash();
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (!mo.isDead) {
                    addToBot(new RelicAboveCreatureAction(mo, this));
                    addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
                    addToBot(new VFXAction(new SanctityEffect(mo.hb.cX, mo.hb.cY)));
                    addToBot(new SFXAction("MONSTER_DONU_DEFENSE"));
                    addToBot(new ApplyPowerAction(mo, pl(), new PlatedArmorPower(mo, 2), 2, true));
                }
            }
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
    public void atPreBattle() {
        if((AbstractDungeon.getCurrRoom()).eliteTrigger && AbstractDungeon.actNum == 4 && !Settings.isEndless) {
            Cursed_DecaFinalBoss Decaer = new Cursed_DecaFinalBoss(-666.0F, 10.0F);
            addToTop(new SpawnMonsterAction(Decaer, false));
            addToTop(new RelicAboveCreatureAction(Decaer, this));
            this.grayscale = true;
        }
    }

    @Override
    public boolean canSpawn(){
        return (isDeliciousDonuDied() && !hasQuestRelic() && isQuestRelicEnabled() && (AbstractDungeon.actNum > 1 || AbstractDungeon.floorNum == 0));
    }

}