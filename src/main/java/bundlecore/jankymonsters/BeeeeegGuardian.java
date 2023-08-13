package bundlecore.jankymonsters;

import bundlecore.actions.deliciousdecastuff.LaterEffect;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.powers.PressureBossPower;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.vfx.combat.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import static bundlecore.BundleCoreMain.bundleCoreConfig;

@Dont_Use_This_Externally
public class BeeeeegGuardian extends AbstractMonster {
    private static final Logger logger = LogManager.getLogger(BeeeeegGuardian.class.getName());
    public static final String ID = "bundlecore:BeeeeegGuardian";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("bundlecore:BeeeeegGuardian");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public static final int HP = 240;
    private int dmgThreshold;
    private int dmgThresholdIncrease = 0;
    private int dmgTaken;
    private final int fierceBashDamage;
    private final int whirlwindDamage = 7;
    private final int twinSlamDamage = 28;
    private int gemstoneCannonDamage = 0;
    private final int rollDamage;
    private int whirlwindCount;
    private final int DEFENSIVE_BLOCK = 20;
    private final int blockAmount = 9;
    private final int VENT_DEBUFF = 1;
    private boolean sleepTurn = true;
    private boolean isOpen = false;
    private boolean closeUpTriggered = false;
    private boolean initialForm = true;
    private static final String CLOSEUP_NAME = MOVES[0], FIERCEBASH_NAME = MOVES[1], TWINSLAM_NAME = MOVES[3];
    private static final String WHIRLWIND_NAME = MOVES[4], CHARGEUP_NAME = MOVES[5], VENTSTEAM_NAME = MOVES[6], GEMSTONE_NAME = MOVES[7], SLEEP_NAME = MOVES[8];

    /**
     * Quest boss #5
     * @param x - Spawned X Pos.
     * @param y - Spawned Y Pos.
     */
    @Dont_Use_This_Externally
    public BeeeeegGuardian(float x, float y) {
        super(NAME, "bundlecore:BeeeeegGuardian", 1240, 0.0F, 95.0F, 440.0F, 350.0F, null, x, y);
        this.drawX = Settings.xScale*1640.0F;
        this.drawY = Settings.yScale*65.0F;
        this.flipHorizontal = false;
        this.type = AbstractMonster.EnemyType.BOSS;
        this.dialogX = -100.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;

        if(AbstractDungeon.ascensionLevel >= 20){
            setHp(2160);
            this.fierceBashDamage = 65;
            this.dmgThreshold = 300;
            dmgThresholdIncrease = 300;
            this.rollDamage = 45;
            whirlwindCount = 9;
            addToBot(new ApplyPowerAction(this, this, new PressureBossPower(this, this, 0), 0));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 6), 6));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 4), 4));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 12), 12));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 38), 38));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MalleablePower(this, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RitualPower(this, 1, false), 1));
            addToBot(new ApplyPowerAction(this, this, new InvinciblePower(this, 999), 999));
        }
        else if (AbstractDungeon.ascensionLevel == 19) {
            setHp(1420);
            this.fierceBashDamage = 59;
            this.dmgThreshold = 200;
            this.rollDamage = 35;
            dmgThresholdIncrease = 100;
            whirlwindCount = 8;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 3), 3));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 3), 3));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 10), 10));

        } else if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(1260);
            this.fierceBashDamage = 54;
            this.dmgThreshold = 160;
            this.rollDamage = 27;
            dmgThresholdIncrease = 80;
            whirlwindCount = 7;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 2), 2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2), 2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 5), 5));
        } else if (AbstractDungeon.ascensionLevel >= 7){
            setHp(1140);
            this.fierceBashDamage = 51;
            this.dmgThreshold = 120;
            this.rollDamage = 22;
            dmgThresholdIncrease = 60;
            whirlwindCount = 6;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 3), 3));
        }
        else if (AbstractDungeon.ascensionLevel >= 4) {
            setHp(1100);
            this.fierceBashDamage = 47;
            this.rollDamage = 15;
            dmgThresholdIncrease = 40;
            whirlwindCount = 5;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 1), 1));
        } else {
            setHp(1035);
            this.fierceBashDamage = 42;
            this.rollDamage = 11;
            dmgThresholdIncrease = 20;
            whirlwindCount = 4;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 1), 1));
        }
        this.damage.add(new DamageInfo(this, this.fierceBashDamage));//0
        this.damage.add(new DamageInfo(this, this.rollDamage));//1
        this.damage.add(new DamageInfo(this, this.whirlwindDamage));//2
        this.damage.add(new DamageInfo(this, this.twinSlamDamage));//3
        gemstoneCannonDamage = 50;
        this.damage.add(new DamageInfo(this, this.gemstoneCannonDamage));//4?
        loadAnimation("images/monsters/theBottom/boss/guardian/skeleton.atlas", "images/monsters/theBottom/boss/guardian/skeleton.json", 1.5F);
        this.state.setTimeScale(2.0F);
        this.state.setAnimation(0, "transition", false);
        this.state.addAnimation(0, "defensive", true, 0.0F);
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Defensive Mode"));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                useCloseUp();
                return;
            case 2:
                useFierceBash();
                return;
            case 7:
                useVentSteam();
                return;
            case 3:
                useRollAttack();
                return;
            case 4:
                useTwinSmash();
                return;
            case 5:
                useWhirlwind();
                return;
            case 6:
                useChargeUp();
                return;
            case 8:
                useGemstoneCannon();
                return;
            case 9:
                useSleeping();
                return;
        }
        logger.info("ERROR");
    }

    private void useFierceBash() {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        addToBot(new VFXAction(new WeightyImpactEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
        if (AbstractDungeon.ascensionLevel >= 20){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 5), 5));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -3), -3));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                .get(0), AbstractGameAction.AttackEffect.NONE));
        setMove(VENTSTEAM_NAME, (byte)7, AbstractMonster.Intent.STRONG_DEBUFF);
    }

    private void useVentSteam() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.VENT_DEBUFF, true), this.VENT_DEBUFF));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, this.VENT_DEBUFF, true), this.VENT_DEBUFF));
        if (AbstractDungeon.ascensionLevel >= 20){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.VENT_DEBUFF, true), this.VENT_DEBUFF));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -1), -1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FocusPower(AbstractDungeon.player, -1), -1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, this.VENT_DEBUFF, true), this.VENT_DEBUFF));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -1), -1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FocusPower(AbstractDungeon.player, -1), -1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.VENT_DEBUFF, true), this.VENT_DEBUFF));
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new MantraPower(AbstractDungeon.player, -1), -1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 5), 5));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_GUARDIAN_DESTROY"));
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[6], 1.0F, 2.5F));
            setMove(GEMSTONE_NAME, (byte)8, AbstractMonster.Intent.ATTACK, this.gemstoneCannonDamage, this.whirlwindCount*2, true);
        }
        else{
            setMove(WHIRLWIND_NAME, (byte)5, AbstractMonster.Intent.ATTACK, this.whirlwindDamage, this.whirlwindCount, true);
        }
    }

    private void useCloseUp() {
        AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, DIALOG[1]));
        int thornsDamage = 7;
        if (AbstractDungeon.ascensionLevel >= 20) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SharpHidePower(this, thornsDamage + 5)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 3), 3));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 15), 15));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2), 2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -1), -1));
        }
        else if (AbstractDungeon.ascensionLevel == 19) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SharpHidePower(this, thornsDamage + 3)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 2), 2));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SharpHidePower(this, thornsDamage)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 1), 1));
        }
        setMove((byte)3, AbstractMonster.Intent.ATTACK, this.damage.get(1).base);
    }

    private void useTwinSmash() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Offensive Mode"));
        if (AbstractDungeon.ascensionLevel >= 20) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 5), 5));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FocusPower(AbstractDungeon.player, -1), -1));
        }
        addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                .get(3), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (AbstractDungeon.ascensionLevel >= 20){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -1), -1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player, -1), -1));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                .get(3), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (AbstractDungeon.ascensionLevel >= 20){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -1), -1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FocusPower(AbstractDungeon.player, -1), -1));
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Sharp Hide"));
        setMove(WHIRLWIND_NAME, (byte)5, AbstractMonster.Intent.ATTACK, this.whirlwindDamage, this.whirlwindCount, true);
    }

    private void useRollAttack() {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        if (AbstractDungeon.ascensionLevel >= 20) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 5), 5));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -1), -1));
        }
        addToTop(new VFXAction(new SanctityEffect(this.hb.cX, this.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                .get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        setMove(TWINSLAM_NAME, (byte)4, AbstractMonster.Intent.ATTACK_BUFF, this.twinSlamDamage, 2, true);
    }

    private void useWhirlwind() {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        if (AbstractDungeon.ascensionLevel >= 20) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 5), 5));
        }
        for (int i = 0; i < this.whirlwindCount; i++) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new CleaveEffect(true), 0.15F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                    .get(2), AbstractGameAction.AttackEffect.NONE, true));
        }
        setMove(CHARGEUP_NAME, (byte)6, AbstractMonster.Intent.DEFEND);
    }

    private void useChargeUp() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.blockAmount));
        addToBot(new VFXAction(this, new InflameEffect(this), 1.0F));
        if (AbstractDungeon.ascensionLevel >= 20) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 5), 5));
        }
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_GUARDIAN_DESTROY"));
        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 1.0F, 2.5F));
        setMove(FIERCEBASH_NAME, (byte)2, AbstractMonster.Intent.ATTACK, this.damage.get(0).base);
    }

    private void useGemstoneCannon() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.blockAmount));
        if (AbstractDungeon.ascensionLevel >= 20) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BufferPower(this, 10), 10));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 50), 50));
        }
        addToBot(new VFXAction(new BlizzardEffect(50, true), 1.0F));
        for (int i = 0; i < this.whirlwindCount*2; i++) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -10), -10));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                    .get(4), AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
        }
        setMove(CHARGEUP_NAME, (byte)6, AbstractMonster.Intent.ATTACK, this.damage.get(0).base);
    }

    private void useSleeping(){
        if (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite) {
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_ENDING");
        }
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Offensive Mode"));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_GUARDIAN_DESTROY"));
        this.sleepTurn = false;
        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[4], 1.0F, 2.5F));
        setMove(CHARGEUP_NAME, (byte)6, AbstractMonster.Intent.DEFEND);
    }

    protected void getMove(int num) {
        if (this.sleepTurn){
            setMove(CHARGEUP_NAME, (byte)9, Intent.SLEEP);
            this.sleepTurn = false;
        }
        else if (this.isOpen) {
            setMove(CHARGEUP_NAME, (byte)6, AbstractMonster.Intent.DEFEND);
        } else {
            setMove((byte)3, AbstractMonster.Intent.ATTACK, (this.damage.get(1)).base);
        }
    }

    public void changeState(String stateName) {
        switch (stateName) {
            case "Defensive Mode":
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Mode Shift"));
                CardCrawlGame.sound.play("GUARDIAN_ROLL_UP");
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.DEFENSIVE_BLOCK));
                this.stateData.setMix("idle", "transition", 0.1F);
                this.state.setTimeScale(2.0F);
                this.state.setAnimation(0, "transition", false);
                this.state.addAnimation(0, "defensive", true, 0.0F);
                this.dmgThreshold += this.dmgThresholdIncrease;
                if (this.sleepTurn) {
                    setMove(SLEEP_NAME, (byte) 9, Intent.SLEEP);
                    this.sleepTurn = false;
                }else{
                    setMove(CLOSEUP_NAME, (byte) 1, AbstractMonster.Intent.BUFF);
                }
                createIntent();
                this.isOpen = false;
                updateHitbox(0.0F, 95.0F, 440.0F, 250.0F);
                healthBarUpdatedEvent();
                this.sleepTurn = false;
                break;

            case "Offensive Mode":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ModeShiftPower(this, this.dmgThreshold)));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Reset Threshold"));
                if (this.currentBlock != 0)
                    AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
                this.stateData.setMix("defensive", "idle", 0.2F);
                this.state.setTimeScale(1.0F);
                this.state.setAnimation(0, "idle", true);
                this.isOpen = true;
                this.closeUpTriggered = false;
                updateHitbox(0.0F, 95.0F, 440.0F, 350.0F);
                healthBarUpdatedEvent();
                break;

            case "Reset Threshold":
                this.dmgTaken = 0;
                break;
        }
    }

    public void damage(DamageInfo info) {
        int tmpHealth = this.currentHealth;
        super.damage(info);
        if (this.isOpen && !this.closeUpTriggered &&
                tmpHealth > this.currentHealth && !this.isDying) {
            this.dmgTaken += tmpHealth - this.currentHealth;
            if (getPower("Mode Shift") != null) {
                (getPower("Mode Shift")).amount -= tmpHealth - this.currentHealth;
                getPower("Mode Shift").updateDescription();
            }
            if (this.dmgTaken >= this.dmgThreshold) {
                this.dmgTaken = 0;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, false), 0.05F, true));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Defensive Mode"));
                this.closeUpTriggered = true;
            }
        }
    }

    public void render(SpriteBatch sb) {
        super.render(sb);
    }

    public void die() {
        (AbstractDungeon.getCurrRoom()).cannotLose = false;
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        onBossVictoryLogic();
        onFinalBossVictoryLogic();
        try {
            bundleCoreConfig.setBool("guardianDefeated", true);
            if (AbstractDungeon.ascensionLevel >= 20) {
                bundleCoreConfig.setBool("guardianDefeatedA20", true);
            }
            bundleCoreConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbstractDungeon.effectsQueue.add(new LaterEffect(() -> {
            AbstractDungeon.currMapNode.room = new TrueVictoryRoom();
            AbstractDungeon.currMapNode.room.onPlayerEntry();
        }, 2.0f));
    }

}
