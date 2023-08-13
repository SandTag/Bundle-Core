package bundlecore.jankymonsters;

import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.powers.PressureBossPower;
import bundlecore.util.GFL;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.TorchHeadFireEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.ArrayList;

public class StonksTorchhead extends AbstractMonster {
    public static final String ID = "bundlecore:StonksTorchhead";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("bundlecore:StonksTorchhead");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    private float fireTimer = 0.0F;
    private int baseAttackDamage;
    private int attack1;
    private int attack2;
    private int attack3;
    private int attack4;
    public ArrayList<DamageInfo> damage = new ArrayList<>();

    /**
     * A miniboss used in Quest #4.
     * @param x - Spawned X Pos.
     * @param y - Spawned Y Pos.
     */
    @Dont_Use_This_Externally
    public StonksTorchhead(float x, float y) {
        super(NAME, "bundlecore:StonksTorchhead", 160, -5.0F, 20.0F, 145.0F, 240.0F, (String)null, x, y);
        this.loadAnimation("images/monsters/theCity/torchHead/skeleton.atlas", "images/monsters/theCity/torchHead/skeleton.json", 0.75F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if (AbstractDungeon.ascensionLevel >= 20) {
            this.setHp(190, 215);
            this.baseAttackDamage = 10;
            this.attack1 = baseAttackDamage + 6;
            this.attack2 = baseAttackDamage + 4;
            this.attack3 = baseAttackDamage + 2;
            addToBot(new ApplyPowerAction(this, this, new PressureBossPower(this, this, 0), 0));
        } else {
            this.setHp(160, 180);
            this.baseAttackDamage = 8;
            this.attack1 = baseAttackDamage + 3;
            this.attack2 = baseAttackDamage + 2;
            this.attack3 = baseAttackDamage + 1;
        }
        this.attack4 = baseAttackDamage;
        this.damage.add(new DamageInfo(this, this.attack1, DamageInfo.DamageType.NORMAL)); //0
        this.damage.add(new DamageInfo(this, this.attack2, DamageInfo.DamageType.NORMAL)); //1
        this.damage.add(new DamageInfo(this, this.attack3, DamageInfo.DamageType.NORMAL)); //2
        this.damage.add(new DamageInfo(this, this.attack4, DamageInfo.DamageType.THORNS)); //3
        this.type = EnemyType.BOSS;
        getMove(AbstractDungeon.aiRng.random(100));
    }



    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new VFXAction(this, new FlameBarrierEffect(this.hb.cX, this.hb.cY), 0.1F));
                addToBot(new AnimateSlowAttackAction(this));
                addToBot(new DamageAction(GFL.GAP(), this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                if(AbstractDungeon.ascensionLevel >= 20){
                    addToBot(new ApplyPowerAction(this, this, new StrengthPower(GFL.GAP(), 3), 3));
                }
                getMove(AbstractDungeon.aiRng.random(100));
                break;
            case 2:
                addToBot(new VFXAction(new ExplosionSmallEffect(this.hb.cX, this.hb.cY), 0.1F));
                addToBot(new AnimateSlowAttackAction(this));
                addToBot(new DamageAction(GFL.GAP(), this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new ApplyPowerAction(GFL.GAP(), this, new VulnerablePower(GFL.GAP(), 1, true), 1));
                if(AbstractDungeon.ascensionLevel >= 20){
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new StrengthPower(GFL.GAP(), -1), -1));
                    addToBot(new ApplyPowerAction(this, this, new StrengthPower(GFL.GAP(), 1), 1));
                }
                getMove(AbstractDungeon.aiRng.random(100));
                break;
            case 3:
                addToBot(new VFXAction(this, new InflameEffect(this), 1.0F));
                addToBot(new AnimateSlowAttackAction(this));
                addToBot(new DamageAction(GFL.GAP(), this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                if(AbstractDungeon.ascensionLevel >= 20) {
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new FrailPower(GFL.GAP(), 1, true), 1));
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new FocusPower(GFL.GAP(), -1), -1));
                }
                addToBot(new ApplyPowerAction(GFL.GAP(), this, new WeakPower(GFL.GAP(), 1, true), 1));
                getMove(AbstractDungeon.aiRng.random(100));
                break;
            case 4:
                addToBot(new VFXAction(new FireballEffect(this.hb.cX, this.hb.cY+25, GFL.GAP().hb.cX, GFL.GAP().hb.cY), 0.5F));
                addToBot(new DamageAction(GFL.GAP(), this.damage.get(3), AbstractGameAction.AttackEffect.FIRE));
                Burn burn = new Burn();
                burn.upgrade();
                addToBot(new MakeTempCardInDiscardAction(new Burn(), 1));
                if(AbstractDungeon.ascensionLevel >= 20){
                    addToBot(new MakeTempCardInDiscardAction(burn, 1));
                    addToBot(new ApplyPowerAction(GFL.GAP(), this, new DexterityPower(GFL.GAP(), -1), -1));
                }
                getMove(AbstractDungeon.aiRng.random(100));
                break;
            case 5:
                addToBot(new VFXAction(this, new VerticalAuraEffect(Color.BLACK.cpy(), this.hb.cX, this.hb.cY), 0.33F));
                addToBot(new VFXAction(this, new VerticalAuraEffect(Color.RED.cpy(), this.hb.cX, this.hb.cY), 0.25F));
                addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 3), 3));
                addToBot(new ApplyPowerAction(this, this, new MetallicizePower(this, 2), 2));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
                if (AbstractDungeon.ascensionLevel >= 20){
                    addToBot(new GainBlockAction(this, this, 30));
                }
                getMove(AbstractDungeon.aiRng.random(100));
                break;
            default: throw new IndexOutOfBoundsException("Intent value out of bounds");
        }
    }

    protected void getMove(int num) {
        byte numfm;
        if (num <= 20){
            numfm = 1;
        }
        else if (num <= 40){
            numfm = 2;
        }
        else if (num <= 60){
            numfm = 3;
        }
        else if (num <= 80){
            numfm = 4;
        }
        else{
            numfm = 5;
        }
        switch (numfm) {
            case 1:
                if (AbstractDungeon.ascensionLevel >= 20) {
                    this.setMove(MOVES[0], (byte) 1, Intent.MAGIC, (this.damage.get(0)).base);
                }
                else{
                    this.setMove(MOVES[0], (byte) 1, Intent.ATTACK, (this.damage.get(0)).base);
                }
                break;

            case 2:
                if (AbstractDungeon.ascensionLevel >= 20) {
                    this.setMove(MOVES[1], (byte) 2, Intent.MAGIC, (this.damage.get(1)).base);
                }
                else{
                    this.setMove(MOVES[1], (byte) 2, Intent.ATTACK_DEBUFF, (this.damage.get(1)).base);
                }
                break;

            case 3:
                if (AbstractDungeon.ascensionLevel >= 20) {
                    this.setMove(MOVES[2], (byte) 3, Intent.STRONG_DEBUFF, (this.damage.get(2)).base);
                }
                else{
                    this.setMove(MOVES[2], (byte) 3, Intent.ATTACK_DEBUFF, (this.damage.get(2)).base);
                }
                break;

            case 4:
                if (AbstractDungeon.ascensionLevel >= 20) {
                    this.setMove(MOVES[3], (byte) 4, Intent.STRONG_DEBUFF, (this.damage.get(3)).base);
                }
                else{
                    this.setMove(MOVES[3], (byte) 4, Intent.ATTACK_DEBUFF, (this.damage.get(3)).base);
                }
                break;

            case 5:
                this.setMove(MOVES[4], (byte)5, Intent.BUFF);
                if (AbstractDungeon.ascensionLevel >= 20) {
                    this.setMove(MOVES[4], (byte)5, Intent.DEFEND_BUFF);
                }
                break;

            default: throw new IndexOutOfBoundsException("Intent value out of bounds");
        }

    }

    public void update() {
        super.update();
        if (!this.isDying) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.04F;
                AbstractDungeon.effectList.add(new TorchHeadFireEffect(this.skeleton.getX() + this.skeleton.findBone("fireslot").getX() + 7.0F * Settings.scale, this.skeleton.getY() + this.skeleton.findBone("fireslot").getY() + 140.0F * Settings.scale));
                AbstractDungeon.effectList.add(new TorchHeadFireEffect(this.skeleton.getX() + this.skeleton.findBone("fireslot").getX() + 7.0F * Settings.scale, this.skeleton.getY() + this.skeleton.findBone("fireslot").getY() + 160.0F * Settings.scale));
                AbstractDungeon.effectList.add(new TorchHeadFireEffect(this.skeleton.getX() + this.skeleton.findBone("fireslot").getX() + 10.0F * Settings.scale, this.skeleton.getY() + this.skeleton.findBone("fireslot").getY() + 160.0F * Settings.scale));
                AbstractDungeon.effectList.add(new TorchHeadFireEffect(this.skeleton.getX() + this.skeleton.findBone("fireslot").getX() + 13.0F * Settings.scale, this.skeleton.getY() + this.skeleton.findBone("fireslot").getY() + 160.0F * Settings.scale));
                AbstractDungeon.effectList.add(new TorchHeadFireEffect(this.skeleton.getX() + this.skeleton.findBone("fireslot").getX() + 13.0F * Settings.scale, this.skeleton.getY() + this.skeleton.findBone("fireslot").getY() + 180.0F * Settings.scale));
            }
        }
    }

    public void die() {
        super.die();
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            AbstractDungeon.scene.fadeInAmbiance();
            onBossVictoryLogic();
        }
    }

}
