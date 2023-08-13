package bundlecore.jankymonsters;

import bundlecore.actions.deliciousdecastuff.LaterEffect;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.powers.PressureBossPower;
import bundlecore.powers.SuffocationPower;
import bundlecore.util.GFL;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.HeartAnimListener;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.GlowyFireEyesEffect;
import com.megacrit.cardcrawl.vfx.StaffFireEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.io.IOException;
import static bundlecore.BundleCoreMain.bundleCoreConfig;

@Dont_Use_This_Externally
public class BigGothCollector extends AbstractMonster  {
    public static final String ID = "bundlecore:BigGothCollector";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("bundlecore:BigGothCollector");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private final HeartAnimListener animListener = new HeartAnimListener();
    private int turnsTaken = 0;
    private float fireTimer = 0.0F;
    private boolean ultUsed = false;
    private boolean initialSpawn = true;
    private int finalAttack;
    private boolean gossip;

    /**
     * Quest boss #4
     * @param x - Spawned X Pos.
     * @param y - Spawned Y Pos.
     */
    @Dont_Use_This_Externally
    public BigGothCollector(float x, float y) {
        super(NAME, "bundlecore:BigGothCollector", 2500, 15.0F, -40.0F, 300.0F, 565.0F, (String)null, x, y);
        this.dialogX = -90.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.type = AbstractMonster.EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 20) {
            this.setHp(4360);
            addToBot(new ApplyPowerAction(this, this, new InvinciblePower(this, 1540), 1540));
            addToBot(new ApplyPowerAction(this, this, new PressureBossPower(this, this, 0), 0));
        } else {
            this.setHp(2640);
            addToBot(new ApplyPowerAction(this, this, new InvinciblePower(this, 1320), 1320));
        }
        if (AbstractDungeon.ascensionLevel >= 20) {
            this.finalAttack = 99;
        } else {
            this.finalAttack = 49;
        }
        this.damage.add(new DamageInfo(this, this.finalAttack));
        this.loadAnimation("images/monsters/theCity/collector/skeleton.atlas", "images/monsters/theCity/collector/skeleton.json", 0.6F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);

        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addListener(this.animListener);

        this.drawX = Settings.xScale*1700.0F;
        this.drawY = Settings.yScale*350.0F;

        this.gossip = true;
        this.turnsTaken = 0;
        getMove(this.turnsTaken);

        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_ENDING");
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");
        UnlockTracker.markBossAsSeen("COLLECTOR");
    }

    public void takeTurn() {
        switch (this.turnsTaken){
            case 0:{
                //Potions 1
                if (this.gossip) {
                    GFL.atb(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
                    addToBot(new WaitAction(0.5f));
                }
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY), 0.4F));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    addToBot(new ApplyPowerAction(this, this, new MetallicizePower(this, 2), 2));
                }
                else{
                    addToBot(new ApplyPowerAction(this, this, new BeatOfDeathPower(this, 1), 1));
                }
                addToBot(new WaitAction(0.5f));
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY), 0.4F));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    addToBot(new ApplyPowerAction(this, this, new CuriosityPower(this, 2), 2));
                }
                else{
                    addToBot(new ApplyPowerAction(this, this, new CuriosityPower(this, 3), 3));
                }
                addToBot(new WaitAction(0.5f));
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY), 0.4F));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    addToBot(new ApplyPowerAction(this, this, new RegenPower(this, 10), 10));
                }
                else{
                    addToBot(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 12), 12));
                }
                turnsTaken += 1;
                getMove(this.turnsTaken);
                break;
            }

            case 1:{
                //Summoning 1
                addToBot(new VFXAction(this, new InflameEffect(this), 0.5F));
                addToBot(new VFXAction(this, new FlameBarrierEffect(this.hb.cX, this.hb.cY), 0.1F));
                addToBot(new WaitAction(0.5f));
                StonksTorchhead Torch1 = new StonksTorchhead(Settings.xScale*475.0F, Settings.yScale*-270.0F);
                addToBot(new SpawnMonsterAction(Torch1, true));
                turnsTaken = 2;
                getMove(this.turnsTaken);
                break;
            }

            case 2:{
                //Potions 2
                if (this.gossip) {
                    GFL.atb(new TalkAction(this, DIALOG[1], 0.5F, 2.0F));
                    addToBot(new WaitAction(0.5f));
                }
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY), 0.4F));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    addToBot(new ApplyPowerAction(this, this, new MalleablePower(this, 8), 8));
                    addToBot(new ApplyPowerAction(this, this, new BlurMonsterPower(this, 2, false), 2));
                }
                else{
                    addToBot(new ApplyPowerAction(this, this, new MalleablePower(this, 9), 9));
                    addToBot(new ApplyPowerAction(this, this, new BlurMonsterPower(this, 3, false), 3));
                }
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY), 0.4F));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    addToBot(new ApplyPowerAction(this, this, new FixedAfterImagePower(this, 2), 2));
                    addToBot(new ApplyPowerAction(this, this, new BlurMonsterPower(this, 2, false), 2));
                    addToBot(new ApplyPowerAction(this, this, new DexterityPower(this, 2), 2));
                }
                else{
                    addToBot(new ApplyPowerAction(this, this, new FixedAfterImagePower(this, 3), 3));
                    addToBot(new ApplyPowerAction(this, this, new BlurMonsterPower(this, 3, false), 3));
                    addToBot(new ApplyPowerAction(this, this, new DexterityPower(this, 3), 3));
                }
                turnsTaken += 1;
                getMove(this.turnsTaken);
                break;
            }

            case 3:{
                //Summoning 2
                addToBot(new VFXAction(this, new InflameEffect(this), 0.5F));
                addToBot(new VFXAction(this, new FlameBarrierEffect(this.hb.cX, this.hb.cY), 0.1F));
                addToBot(new WaitAction(0.5f));
                StonksTorchhead Torch2 = new StonksTorchhead(Settings.xScale*250.0F, Settings.yScale*-270.0F);
                StonksTorchhead Torch3 = new StonksTorchhead(Settings.xScale*50.0F, Settings.yScale*-270.0F);
                addToBot(new SpawnMonsterAction(Torch2, true));
                addToBot(new SpawnMonsterAction(Torch3, true));

                turnsTaken += 1;
                getMove(this.turnsTaken);
                break;
            }

            case 4:{
                //Potions 3
                if (this.gossip) {
                    GFL.atb(new TalkAction(this, DIALOG[2], 0.5F, 2.0F));
                    addToBot(new WaitAction(0.5f));
                }
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, GFL.GAP().hb.cX, GFL.GAP().hb.cY), 0.4F));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    StrengthPower strkek = new StrengthPower(GFL.GAP(), -6);
                    strkek.type = AbstractPower.PowerType.BUFF;
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, strkek, -6, true));
                }
                else{
                    StrengthPower strkek = new StrengthPower(GFL.GAP(), -7);
                    strkek.type = AbstractPower.PowerType.BUFF;
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, strkek, -7, true));
                }
                addToBot(new WaitAction(0.5f));
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, GFL.GAP().hb.cX, GFL.GAP().hb.cY), 0.4F));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new WeakPower(GFL.GAP(), 4, true), 4));
                }
                else{
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new WeakPower(GFL.GAP(), 5, true), 5));
                }
                turnsTaken += 1;
                getMove(this.turnsTaken);
                break;
            }

            case 5:{
                //Summoning 3
                addToBot(new VFXAction(this, new InflameEffect(this), 0.5F));
                addToBot(new VFXAction(this, new FlameBarrierEffect(this.hb.cX, this.hb.cY), 0.1F));
                addToBot(new WaitAction(0.5f));
                StonksTorchhead Torch4 = new StonksTorchhead(Settings.xScale*-150.0F, Settings.yScale*-270.0F);
                StonksTorchhead Torch5 = new StonksTorchhead(Settings.xScale*-350.0F, Settings.yScale*-270.0F);
                addToBot(new WaitAction(0.5f));
                StonksTorchhead Torch6 = new StonksTorchhead(Settings.xScale*-550.0F, Settings.yScale*-270.0F);
                Torch6.flipHorizontal = true;
                addToBot(new SpawnMonsterAction(Torch4, true));
                addToBot(new SpawnMonsterAction(Torch5, true));
                addToBot(new SpawnMonsterAction(Torch6, true));

                turnsTaken += 1;
                getMove(this.turnsTaken);
                break;
            }

            case 6:{
                //potions 4
                if (this.gossip) {
                    GFL.atb(new TalkAction(this, DIALOG[3], 0.5F, 2.0F));
                    addToBot(new WaitAction(0.5f));
                }
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY), 0.4F));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    addToBot(new ApplyPowerAction(this, this, new OmegaMonsterPower(this, 20, false), 20));
                }
                else{
                    addToBot(new ApplyPowerAction(this, this, new OmegaMonsterPower(this, 24, false), 24));
                }
                addToBot(new WaitAction(0.5f));
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY), 0.4F));
                addToBot(new ApplyPowerAction(this, this, new IntangiblePower(this, 1), 1));
                addToBot(new ApplyPowerAction(this, this, new PhantasmalPower(this, 1), 1));
                turnsTaken += 1;
                getMove(this.turnsTaken);
                break;
            }

            case 7:{
                //Summoning 4
                addToBot(new VFXAction(this, new InflameEffect(this), 0.5F));
                addToBot(new VFXAction(this, new FlameBarrierEffect(this.hb.cX, this.hb.cY), 0.1F));
                addToBot(new WaitAction(0.5f));
                StonksTorchhead Torch7 = new StonksTorchhead(Settings.xScale*-750.0F, Settings.yScale*-270.0F);
                StonksTorchhead Torch8 = new StonksTorchhead(Settings.xScale*-950.0F, Settings.yScale*-270.0F);
                addToBot(new WaitAction(0.5f));
                StonksTorchhead Torch9 = new StonksTorchhead(Settings.xScale*-1150.0F, Settings.yScale*-270.0F);
                StonksTorchhead Torch10 = new StonksTorchhead(Settings.xScale*-1350.0F, Settings.yScale*-270.0F);
                Torch7.flipHorizontal = true;
                Torch8.flipHorizontal = true;
                Torch9.flipHorizontal = true;
                Torch10.flipHorizontal = true;
                addToBot(new SpawnMonsterAction(Torch7, true));
                addToBot(new SpawnMonsterAction(Torch8, true));
                addToBot(new SpawnMonsterAction(Torch9, true));
                addToBot(new SpawnMonsterAction(Torch10, true));

                turnsTaken += 1;
                getMove(this.turnsTaken);
                break;
            }

            case 8:{
                //Potions 5
                if (this.gossip) {
                    GFL.atb(new TalkAction(this, DIALOG[4], 0.5F, 2.0F));
                    addToBot(new WaitAction(0.5f));
                }
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, GFL.GAP().hb.cX, GFL.GAP().hb.cY), 0.4F));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new SlowPower(GFL.GAP(), 2), 2));
                }
                else{
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new SlowPower(GFL.GAP(), 3), 3));
                }
                addToBot(new WaitAction(0.5f));
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY), 0.4F));
                int Dexcount;
                if (this.hasPower(DexterityPower.POWER_ID)){
                    Dexcount = this.getPower(DexterityPower.POWER_ID).amount;
                } else{Dexcount = 0;}
                if (AbstractDungeon.ascensionLevel <= 19) {
                    addToBot(new GainBlockAction(this, (34 + Dexcount)));
                    addToBot(new ApplyPowerAction(this, this, new NextTurnBlockPower(this, (30 + Dexcount)), (30 + Dexcount)));
                }
                else{
                    addToBot(new GainBlockAction(this, (99 + Dexcount)));
                    addToBot(new ApplyPowerAction(this, this, new NextTurnBlockPower(this, (99 + Dexcount)), (99 + Dexcount)));
                }
                turnsTaken += 1;
                getMove(this.turnsTaken);
                break;
            }

            case 9:{
                //Potions 6 (final one)
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, GFL.GAP().hb.cX, GFL.GAP().hb.cY), 0.4F));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new SuffocationPower(GFL.GAP(), this, 1), 1));
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new WeakPower(GFL.GAP(), 8, true), 8));
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new VulnerablePower(GFL.GAP(), 8, true), 8));
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new FrailPower(GFL.GAP(), 8, true), 8));
                }
                else{
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new SuffocationPower(GFL.GAP(), this, 2), 2));
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new WeakPower(GFL.GAP(), 9, true), 9));
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new VulnerablePower(GFL.GAP(), 9, true), 9));
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new FrailPower(GFL.GAP(), 9, true), 9));
                }
                addToBot(new WaitAction(0.5f));
                addToBot(new VFXAction(new PotionBounceEffect(this.hb.cX, this.hb.cY, GFL.GAP().hb.cX, GFL.GAP().hb.cY), 0.4F));
                addToBot(new VFXAction(new SmokeBombEffect(GFL.GAP().hb.cX, GFL.GAP().hb.cY)));
                addToBot(new WaitAction(0.5f));
                if(GFL.GAP().hasPower("IntangiblePlayer")){
                    AbstractDungeon.effectsQueue.add(new LaterEffect(() -> {yeetPlayer(true);}, 2.5f));
                }
                addToBot(new ApplyPowerAction(GFL.GAP(), this, new PoisonPower(GFL.GAP(), this, 3), 3));
                turnsTaken += 1;
                getMove(this.turnsTaken);
                break;
            }

            case 10:{
                //Rest Turn
                if (this.gossip) {
                    GFL.atb(new TalkAction(this, DIALOG[5], 0.5F, 2.0F));
                    addToBot(new WaitAction(0.5f));
                }
                addToBot(new VFXAction(this, new InflameEffect(this), 0.5F));
                if (AbstractDungeon.ascensionLevel <= 19) {
                    addToBot(new ApplyPowerAction(this, this, new StrengthPower(GFL.GAP(), 5), 5));
                }
                else{
                    addToBot(new ApplyPowerAction(this, this, new StrengthPower(GFL.GAP(), 12), 12));
                }
                turnsTaken += 1;
                getMove(this.turnsTaken);
                break;
            }

            case 11: {
                //Nuke Turn
                int i;
                if (AbstractDungeon.ascensionLevel <= 19){
                    i = 5;
                }
                else{
                    i = 11;
                }
                for (int j = i; j > 0; j--) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ScreenOnFireEffect(), 0.69F));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.CHARTREUSE.cpy())));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(GFL.GAP(),
                            this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                }
                turnsTaken += 1;
                getMove(this.turnsTaken);
                break;
            }

            case 12:{
                //Stunned Turn
                if (this.gossip) {
                    GFL.atb(new TalkAction(this, DIALOG[6], 0.5F, 2.0F));
                }
                this.gossip = false;
                turnsTaken = 1;
                getMove(this.turnsTaken);
                break;
            }

            default: {throw new IndexOutOfBoundsException("Big Collector move fixing intent out of bounds.");}
        }
    }

    public void yeetPlayer(boolean yesyes) {
        if (yesyes) {
            GFL.GAP().isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
    }

    protected void getMove(int num) {
        switch (this.turnsTaken) {
            case 0: {
                this.setMove(MOVES[0], (byte) 1, Intent.BUFF);//pot 1
                break;
            }
            case 1: {
                this.setMove(MOVES[7], (byte) 2, Intent.MAGIC);//S1
                break;
            }
            case 2: {
                this.setMove(MOVES[1], (byte) 3, Intent.BUFF);//pot 2
                break;
            }
            case 3: {
                this.setMove(MOVES[7], (byte) 4, Intent.MAGIC);//S2
                break;
            }
            case 4: {
                this.setMove(MOVES[2], (byte) 5, Intent.DEBUFF);//pot 3
                break;
            }
            case 5: {
                this.setMove(MOVES[7], (byte) 6, Intent.MAGIC);//S3
                break;
            }
            case 6: {
                this.setMove(MOVES[3], (byte) 7, Intent.BUFF);//pot 4
                break;
            }
            case 7: {
                this.setMove(MOVES[7], (byte) 8, Intent.MAGIC);//S4
                break;
            }
            case 8: {
                this.setMove(MOVES[4], (byte) 9, Intent.STRONG_DEBUFF);//pot 5
                break;
            }
            case 9: {
                this.setMove(MOVES[5], (byte) 10, Intent.STRONG_DEBUFF);//pot 6
                break;
            }
            case 10: {
                this.setMove((byte) 11, Intent.UNKNOWN);//rest & Dialogue
                break;
            }
            case 11: {
                if (AbstractDungeon.ascensionLevel <= 19) {
                    this.setMove(MOVES[6], (byte) 12, Intent.ATTACK, (this.damage.get(0)).base, 5, true);//Funny Yeet
                }
                else{
                    this.setMove(MOVES[6], (byte) 12, Intent.ATTACK, (this.damage.get(0)).base, 11, true);//Funny Yeet
                }
                break;
            }
            case 12: {
                this.setMove((byte) 12, Intent.STUN);//Stunned Turn
                break;
            }
            default: {throw new IndexOutOfBoundsException("Big Collector roll for intent out of bounds.");}
        }
    }

    public void update() {
        super.update();
        if (!this.isDying) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.07F;
                AbstractDungeon.effectList.add(new GlowyFireEyesEffect(this.skeleton.getX() + this.skeleton.findBone("lefteyefireslot").getX(), this.skeleton.getY() + this.skeleton.findBone("lefteyefireslot").getY() + 235.0F * Settings.scale));
                AbstractDungeon.effectList.add(new GlowyFireEyesEffect(this.skeleton.getX() + this.skeleton.findBone("righteyefireslot").getX(), this.skeleton.getY() + this.skeleton.findBone("righteyefireslot").getY() + 235.0F * Settings.scale));
                AbstractDungeon.effectList.add(new StaffFireEffect(this.skeleton.getX() + this.skeleton.findBone("fireslot").getX() - 200.0F * Settings.scale, this.skeleton.getY() + this.skeleton.findBone("fireslot").getY() + 635.0F * Settings.scale));
            }
        }
    }

    public void die() {
        this.useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        ++this.deathTimer;
        super.die();
        try {
            bundleCoreConfig.setString("DecaProgressValue", "Mixed");
            if (AbstractDungeon.ascensionLevel >= 20) {
                bundleCoreConfig.setString("DecaProgressValueA20", "WellMixed");
            }
            bundleCoreConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.onBossVictoryLogic();
        onFinalBossVictoryLogic();
        CardCrawlGame.stopClock = true;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDead && !m.isDying) {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));
            }
        }
        win();
    }

    private void win(){
        (AbstractDungeon.getCurrRoom()).cannotLose = false;
        try {
            bundleCoreConfig.setString("CollectProgressValue", "Vintage");
            if (AbstractDungeon.ascensionLevel >= 20) {
                bundleCoreConfig.setString("CollectProgressValueA20", "Heritage");
            }
            bundleCoreConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbstractDungeon.effectsQueue.add(new LaterEffect(() -> {
            AbstractDungeon.currMapNode.room = new TrueVictoryRoom();
            AbstractDungeon.currMapNode.room.onPlayerEntry();
        }, 3.0f));
    }

}
