package bundlecore.jankymonsters;
import bundlecore.actions.deliciousdecastuff.LaterEffect;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.relics.TemporalTimepieceBoss;
import bundlecore.util.GFL;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import com.megacrit.cardcrawl.monsters.ending.SpireSpear;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SpiritPoop;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import java.io.IOException;
import java.util.Objects;
import static bundlecore.BundleCoreMain.bundleCoreConfig;

public class TimeussuyExpress extends AbstractMonster {
    public static final String ID = "bundlecore:TimeussuyExpress";
    public static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("bundlecore:TimeussuyExpress");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final int HP = 456;
    private final int reverbDmg;
    private final int headSlamDmg;
    private boolean usedHaste = false;
    private boolean usedHaste2 = false;
    private int creaturesRevived = 0;
    private boolean firstTurn = true;

    /**
     * Quest boss #3
     * @param x - Spawned X Pos.
     * @param y - Spawned Y Pos.
     */
    @Dont_Use_This_Externally
    public TimeussuyExpress(float x, float y) {
        super(NAME, "TimeEater", 456, -10.0F, -30.0F, 476.0F, 410.0F, null, x, y);
        initializeAnimation();
        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(4000);
            if (Loader.isModLoaded("CorruptTheSpire")) {
                GFL.atb(new ApplyPowerAction(this, this, new InvinciblePower(this, 1600)));
                GFL.atb(new ApplyPowerAction(this, this, new RitualPower(this, 2, false), 3));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new TimeWarpPower(this)));
            }
            else {
                GFL.atb(new ApplyPowerAction(this, this, new InvinciblePower(this, 800)));
                GFL.atb(new ApplyPowerAction(this, this, new RitualPower(this, 2, false), 2));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new TimeWarpPower(this)));
            }
        } else {
            setHp(3500);
            if (Loader.isModLoaded("CorruptTheSpire")) {
                GFL.atb(new ApplyPowerAction(this, this, new InvinciblePower(this, 1750)));
                GFL.atb(new ApplyPowerAction(this, this, new RitualPower(this, 2, false), 2));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new TimeWarpPower(this)));
            }
            else {
                GFL.atb(new ApplyPowerAction(this, this, new InvinciblePower(this, 875)));
                GFL.atb(new ApplyPowerAction(this, this, new RitualPower(this, 2, false), 1));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new TimeWarpPower(this)));
            }
        }
        if (AbstractDungeon.ascensionLevel >= 18) {
            this.reverbDmg = 11;
            this.headSlamDmg = 39;
        }
        else if (AbstractDungeon.ascensionLevel >= 4) {
            this.reverbDmg = 11;
            this.headSlamDmg = 39;
        } else {
            this.reverbDmg = 9;
            this.headSlamDmg = 31;
        }
        this.damage.add(new DamageInfo(this, this.reverbDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.headSlamDmg, DamageInfo.DamageType.NORMAL));
    }

    public void initializeAnimation() {
        loadAnimation("images/monsters/theForest/timeEater/skeleton.atlas", "images/monsters/theForest/timeEater/skeleton.json", 0.6F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.drawX = Settings.xScale*1800.0F;
        this.drawY = Settings.yScale*340.0F;
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        this.type = AbstractMonster.EnemyType.BOSS;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_ENDING");
    }

    public void takeTurn() {
        int i;
        if (this.firstTurn) {
            if (AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.WATCHER) {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 0.5F, 2.0F));
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            }
            this.firstTurn = false;
        }
        switch (this.nextMove) {
            case 2:
                for (i = 0; i < 2; i++) {
                    addToBot(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.75F));
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage
                            .get(0), AbstractGameAction.AttackEffect.FIRE));
                    addToBot(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, (1 + creaturesRevived))));
                }
                break;
            case 3:
                addToBot(new GainBlockAction(this, this, 20));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                if (AbstractDungeon.ascensionLevel >= 20)
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
                addToBot(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, (1 + creaturesRevived))));
                break;
            case 4:
                addToBot(new ChangeStateAction(this, "ATTACK"));
                addToBot(new WaitAction(0.4F));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage
                        .get(1), AbstractGameAction.AttackEffect.POISON));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, 1)));
                if (AbstractDungeon.ascensionLevel >= 20)
                    addToBot(new MakeTempCardInDiscardAction(new Slimed(), 2));
                addToBot(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, (1 + creaturesRevived))));
                break;
            case 5:
                addToBot(new ShoutAction(this, DIALOG[1], 0.5F, 2.0F));
                addToBot(new RemoveDebuffsAction(this));
                int ritualToGain = 2;
                if (this.hasPower(StrengthPower.POWER_ID)) {
                    int curStr = this.getPower(StrengthPower.POWER_ID).amount;
                    if (AbstractDungeon.ascensionLevel <= 19) {
                        ritualToGain = Math.max(2, (2 + curStr / 12));
                    } else {
                        ritualToGain = Math.max(3, (3 + curStr / 10));
                    }
                }
                addToBot(new RemoveSpecificPowerAction(this, this, "Shackled"));
                addToBot(new RemoveSpecificPowerAction(this, this, "Strength"));
                addToBot(new RemoveSpecificPowerAction(this, this, "Ritual"));
                addToBot(new HealAction(this, this, (int) ((this.maxHealth / 1.1) - this.currentHealth)));
                if (AbstractDungeon.ascensionLevel >= 20)
                    addToBot(new GainBlockAction(this, this, this.headSlamDmg));
                addToBot(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, (1 + creaturesRevived))));
                addToBot(new ApplyPowerAction(this, this, new RitualPower(this, ritualToGain, false), ritualToGain));
                break;
            case 6:
                addToBot(new ShoutAction(this, DIALOG[4], 0.5F, 2.0F));
                addToBot(new RemoveDebuffsAction(this));
                int ritualToGainStronger = 2;
                if (this.hasPower(StrengthPower.POWER_ID)) {
                    int curStr = this.getPower(StrengthPower.POWER_ID).amount;
                    if (AbstractDungeon.ascensionLevel <= 19) {
                        ritualToGainStronger = Math.max(2, (2 + curStr / 12));
                    } else {
                        ritualToGainStronger = Math.max(3, (3 + curStr / 10));
                    }
                }
                addToBot(new RemoveSpecificPowerAction(this, this, "Shackled"));
                addToBot(new RemoveSpecificPowerAction(this, this, "Strength"));
                addToBot(new RemoveSpecificPowerAction(this, this, "Ritual"));
                addToBot(new HealAction(this, this, (int) ((this.maxHealth / 1.5) - this.currentHealth)));
                if (AbstractDungeon.ascensionLevel >= 20)
                    addToBot(new GainBlockAction(this, this, this.headSlamDmg * 2));
                addToBot(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, (1 + creaturesRevived))));
                addToBot(new ApplyPowerAction(this, this, new RitualPower(this, ritualToGainStronger, false), ritualToGainStronger));
                break;
            case 7:
                if (creaturesRevived == 0) {
                    addToBot(new ShoutAction(this, DIALOG[5], 0.5F, 2.0F));
                    resurrectCreatureAction();
                    if (AbstractDungeon.ascensionLevel >= 20)
                        addToBot(new GainBlockAction(this, this, this.headSlamDmg * 3));
                    addToBot(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, (1 + creaturesRevived))));
                    for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
                        if (!(monster.isDeadOrEscaped() || monster.isEscaping || monster.isDying) && monster != this){
                            addToBot(new ApplyPowerAction(monster, this, new StrengthPower(monster, creaturesRevived), creaturesRevived));
                        }
                    }
                    break;
                } else if (creaturesRevived <= 5) {
                    addToBot(new ShoutAction(this, DIALOG[6], 0.5F, 2.0F));
                    resurrectCreatureAction();
                    if (AbstractDungeon.ascensionLevel >= 20)
                        addToBot(new GainBlockAction(this, this, this.headSlamDmg * 2));
                    addToBot(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, (1 + creaturesRevived))));
                    for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
                        if (!(monster.isDeadOrEscaped() || monster.isEscaping || monster.isDying) && monster != this){
                            addToBot(new ApplyPowerAction(monster, this, new StrengthPower(monster, creaturesRevived), creaturesRevived));
                            addToBot(new ApplyPowerAction(monster, this, new PlatedArmorPower(monster, creaturesRevived), creaturesRevived));
                        }
                    }
                    break;
                } else if (creaturesRevived <= 10) {
                    addToBot(new ShoutAction(this, DIALOG[7], 0.5F, 2.0F));
                    resurrectCreatureAction();
                    if (AbstractDungeon.ascensionLevel >= 20)
                        addToBot(new GainBlockAction(this, this, this.headSlamDmg));
                    addToBot(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, (1 + creaturesRevived))));
                    for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
                        if (!(monster.isDeadOrEscaped() || monster.isEscaping || monster.isDying) && monster != this){
                            addToBot(new ApplyPowerAction(monster, this, new RitualPower(monster, creaturesRevived, false), creaturesRevived));
                            addToBot(new ApplyPowerAction(monster, this, new PlatedArmorPower(monster, creaturesRevived), creaturesRevived));
                            addToBot(new ApplyPowerAction(monster, this, new MetallicizePower(monster, creaturesRevived), creaturesRevived));
                            addToBot(new ApplyPowerAction(monster, this, new BarricadePower(monster)));
                            addToBot(new ApplyPowerAction(monster, this, new BufferPower(monster, 1), 1));
                        }
                    }
                    break;
                } else {
                    pacifism();
                    break;
                }
        }

        addToBot(new RollMoveAction(this));
    }

    public void changeState(String stateName) {
        if ("ATTACK".equals(stateName)) {
            this.state.setAnimation(0, "Attack", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    private void resurrectCreatureAction() {
        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PURPLE, true));
        AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
        boolean speardeadrightnow = true;
        boolean shielddeadrightnow = true;
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (Objects.equals(monster.id, SpireSpear.ID) && !monster.isDead) {
                speardeadrightnow = false;
            }
            if (Objects.equals(monster.id, SpireShield.ID) && !monster.isDead) {
                shielddeadrightnow = false;
            }
        }
        SpireShield ShieldMinion = new SpireShield();
        SpireSpear SpearMinion = new SpireSpear();
        if (creaturesRevived == 10 && shielddeadrightnow && speardeadrightnow) {
            pacifism();
            return;
        }
        if (creaturesRevived <= 11 && shielddeadrightnow) {
            creaturesRevived++;
            addToTop(new SpawnMonsterAction(ShieldMinion, true));
        }
        if (creaturesRevived <= 11 && speardeadrightnow) {
            creaturesRevived++;
            addToTop(new SpawnMonsterAction(SpearMinion, true));
        }
    }

    protected void getMove(int num) {
        boolean shieldead = true;
        boolean speardead = true;
        if (this.currentHealth < this.maxHealth*0.65  && !this.usedHaste) {
            this.usedHaste = true;
            setMove(MOVES[3], (byte)5, Intent.BUFF);
            return;
        }
        if (this.currentHealth < this.maxHealth*0.4 && !this.usedHaste2) {
            this.usedHaste2 = true;
            setMove(MOVES[4], (byte)6, Intent.BUFF);
            return;
        }
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (Objects.equals(monster.id, SpireSpear.ID) && !(monster.isDeadOrEscaped() || monster.isEscaping || monster.isDying)){
                speardead = false;
            }
            if (Objects.equals(monster.id, SpireShield.ID) && !(monster.isDeadOrEscaped() || monster.isEscaping || monster.isDying)){
                shieldead = false;
            }
        }
        if ((shieldead || speardead) && !lastMove((byte)7)){
            setMove(MOVES[5], (byte)7, Intent.MAGIC);
            return;
        }
        if (num < 45) {
            if (!lastTwoMoves((byte)2)) {
                setMove(MOVES[0], (byte)2, AbstractMonster.Intent.ATTACK, this.damage.get(0).base, 2, true);
                return;
            }
            getMove(AbstractDungeon.aiRng.random(50, 99));
            return;
        }
        if (num < 80) {
            if (!lastMove((byte)4)) {
                setMove(MOVES[1], (byte)4, AbstractMonster.Intent.ATTACK_DEBUFF, this.damage.get(1).base);
                return;
            }
            if (AbstractDungeon.aiRng.randomBoolean(0.66F)) {
                setMove(MOVES[0], (byte)2, AbstractMonster.Intent.ATTACK, this.damage.get(0).base, 2, true);
                return;
            }
            setMove(MOVES[2], (byte)3, AbstractMonster.Intent.DEFEND_DEBUFF);
            return;
        }
        if (!lastMove((byte)3)) {
            setMove(MOVES[2], (byte)3, AbstractMonster.Intent.DEFEND_DEBUFF);
            return;
        }
        getMove(AbstractDungeon.aiRng.random(74));
    }

    public void die() {
        super.die();
        for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!m.isDead && !m.isDying && !m.isEscaping) {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
            }
        }
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        onBossVictoryLogic();
        for (AbstractRelic relic : GFL.GAP().relics) {
            if (relic instanceof TemporalTimepieceBoss) {
                relic.grayscale = true;
                AbstractDungeon.getCurrRoom().addRelicToRewards(new SpiritPoop());
                AbstractDungeon.getCurrRoom().addRelicToRewards(new SpiritPoop());
                AbstractDungeon.getCurrRoom().addRelicToRewards(new SpiritPoop());
            }
        }
    }

    public void pacifism(){
        this.isEscaping = true;
        (AbstractDungeon.getCurrRoom()).cannotLose = false;
        try {
            bundleCoreConfig.setString("TimussyProgressValue", "Moist");
            if (AbstractDungeon.ascensionLevel >= 20) {
                bundleCoreConfig.setString("TimussyProgressValueA20", "Moister");
            }
            bundleCoreConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!m.isDead && !m.isDying && !m.isEscaping && !Objects.equals(m.id, this.id)){
                addToTop(new SuicideAction(m));
                addToTop(new HideHealthBarAction(m));
            }
        }
        AbstractDungeon.effectsQueue.add(new LaterEffect(() -> {
            AbstractDungeon.actionManager.addToTop(new TalkAction(this, DIALOG[8], 1.0F, 2.0F));
        }, 5.0f));
        AbstractDungeon.effectsQueue.add(new LaterEffect(() -> {
            AbstractDungeon.actionManager.addToTop(new TalkAction(this, DIALOG[9], 1.0F, 2.0F));
        }, 7.5f));
        AbstractDungeon.effectsQueue.add(new LaterEffect(() -> {
            onBossVictoryLogic();
            onFinalBossVictoryLogic();
            CardCrawlGame.stopClock = true;
            AbstractDungeon.actionManager.addToTop(new EscapeAction(this));
        }, 10f));
        AbstractDungeon.effectsQueue.add(new LaterEffect(() -> {
            AbstractDungeon.currMapNode.room = new TrueVictoryRoom();
            AbstractDungeon.currMapNode.room.onPlayerEntry();
        }, 12.9f));
    }
}
