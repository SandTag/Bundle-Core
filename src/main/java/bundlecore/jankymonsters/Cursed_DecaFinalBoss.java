package bundlecore.jankymonsters;
import bundlecore.actions.FixedWaitAction;
import bundlecore.actions.deliciousdecastuff.DieAfterNextTurnPowerr;
import bundlecore.actions.deliciousdecastuff.LaterEffect;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.powers.PhysicalTimeWarpPower;
import bundlecore.relics.DeliciousDecaBoss;
import bundlecore.util.GFL;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.HeartAnimListener;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SpiritPoop;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.io.IOException;
import java.util.Objects;
import static bundlecore.BundleCoreMain.bundleCoreConfig;

public class Cursed_DecaFinalBoss extends AbstractMonster {
    public static final String ID = "bundlecore:Cursed_DecaFinalBoss";
    public static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("bundlecore:Cursed_DecaFinalBoss");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private final HeartAnimListener animListener = new HeartAnimListener();
    public int beamDmg;
    public static final String CIRCLE_NAME = MOVES[0];
    public static final String LIMIT_BREAK = MOVES[1];

    private int turncounterdecabossform = 0;
    public boolean isAttacking;

    /**
     * Quest boss #2
     * @param x - Spawned X Pos.
     * @param y - Spawned Y Pos.
     */
    @Dont_Use_This_Externally
    public Cursed_DecaFinalBoss(float x, float y){
        super(NAME, "bundlecore:Cursed_DecaFinalBoss", 100, 0.0F, -10.0F, 185.0F, 185.0F, null, x, y);
        initializeAnimation();
        this.stateData.setMix("Hit", "Idle", 0.15F);
        this.stateData.setMix("Attack_2", "Idle", 0.15F);
        this.type = AbstractMonster.EnemyType.ELITE;

        if (AbstractDungeon.ascensionLevel != 69420) {
            if  (AbstractDungeon.ascensionLevel <= 8) {
                this.type = EnemyType.BOSS;
                setHp(1620);
                GFL.atb(new ApplyPowerAction(this, this, new ArtifactPower(this, 2)));
                GFL.atb(new ApplyPowerAction(this, this, new BarricadePower(this)));
                GFL.atb(new ApplyPowerAction(this, this, new PlatedArmorPower(this,5), 5));
                if (Loader.isModLoaded("CorruptTheSpire")) {
                    GFL.atb(new ApplyPowerAction(this, this, new InvinciblePower(this, 810)));
                }
                else {
                    GFL.atb(new ApplyPowerAction(this, this, new InvinciblePower(this, 540)));
                }
//                if (!GFL.GAP().hasRelic(FasterClockBoss.ID)) { //TODO: Fix faster clock interaction
                    GFL.atb(new ApplyPowerAction(GFL.GAP(), GFL.GAP(), new PhysicalTimeWarpPower(GFL.GAP(), 35, 0)));
//                }
            }
            else if  (AbstractDungeon.ascensionLevel <= 18) {
                this.type = EnemyType.BOSS;
                setHp(2430);
                GFL.atb(new ApplyPowerAction(this, this, new ArtifactPower(this, 3)));
                GFL.atb(new ApplyPowerAction(this, this, new BarricadePower(this)));
                GFL.atb(new ApplyPowerAction(this, this, new BufferPower(this, 1), 1));
                GFL.atb(new ApplyPowerAction(this, this, new PlatedArmorPower(this,7), 7));

                if (Loader.isModLoaded("CorruptTheSpire")) {
                    GFL.atb(new ApplyPowerAction(this, this, new InvinciblePower(this, 900)));
                }
                else {
                    GFL.atb(new ApplyPowerAction(this, this, new InvinciblePower(this, 600)));
                }
//                if (!GFL.GAP().hasRelic(FasterClockBoss.ID)) {
                    GFL.atb(new ApplyPowerAction(GFL.GAP(), GFL.GAP(), new PhysicalTimeWarpPower(GFL.GAP(), 33, 0)));
//                }
            }
            else {
                this.type = EnemyType.BOSS;
                setHp(3240);
                GFL.atb(new ApplyPowerAction(this, this, new ArtifactPower(this, 4)));
                GFL.atb(new ApplyPowerAction(this, this, new BarricadePower(this)));
                GFL.atb(new ApplyPowerAction(this, this, new BufferPower(this, 2), 2));
                GFL.atb(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 32), 32));
                GFL.atb(new ApplyPowerAction(this, this, new ThornsPower(this, 10), 10));
                if (Loader.isModLoaded("CorruptTheSpire")) {
                    GFL.atb(new ApplyPowerAction(this, this, new InvinciblePower(this, 2160)));
                }
                else {
                    GFL.atb(new ApplyPowerAction(this, this, new InvinciblePower(this, 1080)));
                }
//                if (!GFL.GAP().hasRelic(FasterClockBoss.ID)) {
                    GFL.atb(new ApplyPowerAction(GFL.GAP(), GFL.GAP(), new PhysicalTimeWarpPower(GFL.GAP(), 30, 0)));
//               }
            }
        } else throw new IndexOutOfBoundsException("Unexpected Ascension Value");

        if (TheEnding.ID.equals(AbstractDungeon.id)) {
            if (AbstractDungeon.ascensionLevel <= 3) {
                this.beamDmg = 5;
            }
            else if (AbstractDungeon.ascensionLevel <= 8){
                this.beamDmg = 6;
            }
            else if (AbstractDungeon.ascensionLevel <= 18){
                this.beamDmg = 7;
            }
            else{
                this.beamDmg = 8;
            }
        }
        this.damage.add(new DamageInfo(this, this.beamDmg));
        this.isAttacking = true;
        this.state.addListener(this.animListener);
    }

    public void initializeAnimation() {
        updateHitbox(0, 5, 325, 480);
        this.drawX = Settings.xScale*1725.0F;
        this.drawY = Settings.yScale*170.0F;
        this.flipHorizontal = false;
        loadAnimation("images/monsters/theForest/deca/skeleton.atlas", "images/monsters/theForest/deca/skeleton.json", 0.60F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_ENDING");
    }

    public void changeState(String stateName) {
        if ("ATTACK".equals(stateName)) {
            this.state.setAnimation(0, "Attack_2", false);
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

    public void takeTurn() {
        turncounterdecabossform++;
        if (turncounterdecabossform == 2) {
            GFL.atb(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
            GFL.atb(new VFXAction(new HeartMegaDebuffEffect()));
            addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
            AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
            GFL.atb(new VFXAction(new HeartMegaDebuffEffect()));
            addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
            AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new FrailPower(GFL.GAP(), 1, true), 1));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new FrailPower(GFL.GAP(), 1, true), 1));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new DexterityPower(GFL.GAP(), 1), 1));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new FocusPower(GFL.GAP(), 1), 1));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new StrengthPower(GFL.GAP(), 1), 1));
            GFL.atb(new MakeTempCardInDrawPileAction(new Shame(), 1, true, false, false, Settings.WIDTH * 0.5F, Settings.HEIGHT / 2.0F));
            GFL.atb(new MakeTempCardInDiscardAction(new Shame(), 1));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new BarricadePower(GFL.GAP())));
            GFL.atb(new ApplyPowerAction(this, this, new PainfulStabsPower(this)));
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                GFL.atb(new ApplyPowerAction(m, this, new BarricadePower(m)));
                GFL.atb(new ApplyPowerAction(m, this, new StrengthPower(m, 1),1));
                if (AbstractDungeon.ascensionLevel >= 20){
                    GFL.atb(new ApplyPowerAction(m, this, new StrengthPower(m, 2),2));
                }
                GFL.atb(new ApplyPowerAction(m, this, new BufferPower(m, 1),1));
                GFL.atb(new ApplyPowerAction(m, this, new CuriosityPower(m, (1 + (AbstractDungeon.ascensionLevel / 20))), (1 + (AbstractDungeon.ascensionLevel / 20))));
                if (AbstractDungeon.ascensionLevel >= 20){
                    GFL.atb(new ApplyPowerAction(m, this, new PainfulStabsPower(m)));
                }
            }
        }
        if (turncounterdecabossform == 4){
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
                GFL.atb(new ApplyPowerAction(m, this, new RitualPower(m, 2, false),2));
                GFL.atb(new ApplyPowerAction(m, this, new ThornsPower(m, 1+(AbstractDungeon.ascensionLevel / 4)), 1+(AbstractDungeon.ascensionLevel / 4)));
            }
        }
        if (turncounterdecabossform == 6){
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
                GFL.atb(new ApplyPowerAction(m, this, new ThornsPower(m, 5+(AbstractDungeon.ascensionLevel / 4)), 5+(AbstractDungeon.ascensionLevel / 4)));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    GFL.atb(new ApplyPowerAction(m, this, new BufferPower(m, 1), 1));
                }
            }
        }
        if (turncounterdecabossform == 8){
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
                GFL.atb(new ApplyPowerAction(m, this, new MetallicizePower(m, 20), 20));
                GFL.atb(new ApplyPowerAction(m, this, new RitualPower(m, 1, false), 1));
                GFL.atb(new ApplyPowerAction(m, this, new FocusPower(m, 1), 1));
                GFL.atb(new ApplyPowerAction(m, this, new ShiftingPower(m)));
            }
        }
        if (turncounterdecabossform == 10){
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
                GFL.atb(new ApplyPowerAction(m, this, new RitualPower(m, 2, false), 2));
                GFL.atb(new ApplyPowerAction(m, this, new MetallicizePower(m, 5), 5));
                GFL.atb(new ApplyPowerAction(m, this, new ThornsPower(m, 5), 5));
            }
        }
        if (turncounterdecabossform == 12){
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (m.hasPower(StrengthPower.POWER_ID)) {
                    int thisStr = m.getPower(StrengthPower.POWER_ID).amount;
                    if (thisStr >= 1){
                        GFL.atb(new ApplyPowerAction(m, this, new StrengthPower(m, thisStr),thisStr));
                        GFL.atb(new ApplyPowerAction(m, this, new MetallicizePower(m, thisStr*2),thisStr*2));
                    }
                }
                addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
                GFL.atb(new ApplyPowerAction(m, this, new MalleablePower(m, 10), 10));
            }
            AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
            GFL.atb(new VFXAction(new HeartMegaDebuffEffect()));
            AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new FrailPower(GFL.GAP(), 1, true), 1));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new WeakPower(GFL.GAP(), 1, true), 1));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new VulnerablePower(GFL.GAP(), 1, true), 1));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new StrengthPower(GFL.GAP(), -9), -9));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new GainStrengthPower(GFL.GAP(), 9), 9));
            GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new HexPower(GFL.GAP(), 2), 2));
        }
        if (turncounterdecabossform == 14){
            boolean isPacifism = true;
            int monsterCount = 0;
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters){
                if (m.currentHealth < (int)(m.maxHealth*0.5)){
                    isPacifism = false;
                }
                monsterCount++;
            }
            if(monsterCount <= 2){
                isPacifism = false;
            }
            if (isPacifism){
                pacifism();
            }
            else{
                AbstractDungeon.actionManager.addToTop(new SFXAction("MONSTER_CHAMP_CHARGE"));
                GFL.att(new ShoutAction(this, DIALOG[2], 1.0F, 2.0F));
                AbstractDungeon.effectsQueue.add(new LaterEffect(() -> {
                    GFL.att(new ShoutAction(this, DIALOG[3], 1.0F, 2.0F));
                    GFL.att(new ApplyPowerAction(this, this, new StrengthPower(this, 49)));
                }, 2.25f));
                GFL.atb(new VFXAction(new HeartMegaDebuffEffect()));
                addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
                addToBot(new VFXAction(new LightningEffect(GFL.GAP().hb.cX, GFL.GAP().hb.cY)));
                GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new VulnerablePower(GFL.GAP(), 1, true), 1));
                addToBot(new FixedWaitAction(0.2f));
                addToBot(new VFXAction(new LightningEffect(GFL.GAP().hb.cX, GFL.GAP().hb.cY)));
                GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new WeakPower(GFL.GAP(), 1, true), 1));
                addToBot(new FixedWaitAction(0.2f));
                addToBot(new VFXAction(new LightningEffect(GFL.GAP().hb.cX, GFL.GAP().hb.cY)));
                GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new FrailPower(GFL.GAP(), 1, true), 1));
                addToBot(new FixedWaitAction(0.2f));
                GFL.atb(new VFXAction(new HeartMegaDebuffEffect()));
                addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
                addToBot(new VFXAction(new LightningEffect(GFL.GAP().hb.cX, GFL.GAP().hb.cY)));
                GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new FrailPower(GFL.GAP(), 1, true), 1));
                addToBot(new FixedWaitAction(0.2f));
                addToBot(new VFXAction(new LightningEffect(GFL.GAP().hb.cX, GFL.GAP().hb.cY)));
                GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new WeakPower(GFL.GAP(), 1, true), 1));
                addToBot(new FixedWaitAction(0.2f));
                addToBot(new VFXAction(new LightningEffect(GFL.GAP().hb.cX, GFL.GAP().hb.cY)));
                GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new VulnerablePower(GFL.GAP(), 1, true), 1));
                addToBot(new FixedWaitAction(0.2f));
                GFL.atb(new VFXAction(new HeartMegaDebuffEffect()));
                addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new LaserBeamEffect(this.hb.cX-25.0f, this.hb.cY+30.0F * Settings.scale), 1.65F));
                GFL.atb(new ApplyPowerAction(GFL.GAP(), this, new DieAfterNextTurnPowerr(GFL.GAP()), 1));
            }
        }

        int i;
        switch (this.nextMove) {
            case 0:
                GFL.atb(new ApplyPowerAction(this, this, new ArtifactPower(this, 1 + (turncounterdecabossform / 4))));
                GFL.atb(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 1 + (turncounterdecabossform))));
                GFL.atb(new ChangeStateAction(this, "ATTACK"));
                GFL.atb(new WaitAction(0.5F));
                for (i = 0; i < 2; i++)
                    GFL.atb(new DamageAction(GFL.GAP(), this.damage
                            .get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                this.isAttacking = false;
                break;
            case 2:
                if (!this.isEscaping) {
                    GFL.atb(new ApplyPowerAction(this, this, new ThornsPower(this, (1 + (turncounterdecabossform / 4))), (1 + (turncounterdecabossform / 4))));
                    for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                        addToBot(new VFXAction(new SanctityEffect(m.hb.cX, m.hb.cY), 0.1F));
                        GFL.atb(new SFXAction("GUARDIAN_ROLL_UP"));
                        GFL.atb(new GainBlockAction(m, (10 + (turncounterdecabossform * 2))));
                        addToBot(new VFXAction(new BorderFlashEffect(GFL.DecaOrange, true)));
                        GFL.atb(new VFXAction(new HeartBuffEffect(this.hb.cX, this.hb.cY)));
                        GFL.atb(new ApplyPowerAction(m, this, new PlatedArmorPower(m, (3 + (turncounterdecabossform / 2))), (3 + (turncounterdecabossform / 2))));
                        GFL.atb(new ApplyPowerAction(m, this, new BarricadePower(m)));
                    }
                    this.isAttacking = true;
                    break;
                }
        }
        GFL.atb(new RollMoveAction(this));
    }
    public void getMove(int num) {
        if (this.isAttacking) {
            setMove((byte)0, AbstractMonster.Intent.ATTACK, this.damage.get(0).base, 2, true);
        } else {
            if (turncounterdecabossform == 11){
                setMove(LIMIT_BREAK, (byte) 2, Intent.MAGIC);
            }
            else {
                setMove(CIRCLE_NAME, (byte) 2, AbstractMonster.Intent.BUFF);
            }
        }
    }

    @Override
    public void die() {
        super.die();
        useFastShakeAnimation(10.0F);
        for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!m.isDead && !m.isDying && !m.isEscaping) {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
            }
        }
        for (AbstractRelic relic : GFL.GAP().relics){
            if (relic instanceof DeliciousDecaBoss){
                relic.usedUp = true;
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
            bundleCoreConfig.setString("DecaProgressValue", "Mixed");
            if (AbstractDungeon.ascensionLevel >= 20) {
                bundleCoreConfig.setString("DecaProgressValueA20", "WellMixed");
            }
            bundleCoreConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!m.isDead && !m.isDying && !m.isEscaping && !Objects.equals(m.id, this.id)){
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new EscapeAction(m));
            }
        }
        AbstractDungeon.effectsQueue.add(new LaterEffect(() -> {
            AbstractDungeon.actionManager.addToTop(new TalkAction(this, DIALOG[1], 1.0F, 2.0F));
        }, 5.0f));
        AbstractDungeon.effectsQueue.add(new LaterEffect(() -> {
            AbstractDungeon.actionManager.addToTop(new TalkAction(this, DIALOG[4], 1.0F, 2.0F));
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