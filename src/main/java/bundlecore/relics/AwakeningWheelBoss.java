package bundlecore.relics;
import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import static bundlecore.BundleCoreMain.makeID;

public class AwakeningWheelBoss extends BundleRelic implements BundleChecker {
    private static final String NAME = "AwakeningWheelBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public AwakeningWheelBoss() {
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
        this.counter = 0;
        addToBot(new ApplyPowerAction(pl(), pl(), new CuriosityPower(pl(), 2), 2));
    }

    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER && card.costForTurn >= 1){
//            addToBot(new ApplyPowerAction(pl(), pl(), new StrengthPower(pl(), card.costForTurn), card.costForTurn));
            this.counter++;
        }
        if (this.counter == 12){
            beginLongPulse();
        }
        if (this.counter >= 13){
            sPulse();
            flash();
            addToTop(new RelicAboveCreatureAction(pl(), this));
            addToTop(new CanLoseAction());
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (!m.isDeadOrEscaped()) {
                    addToTop(new RelicAboveCreatureAction(m, this));
                    for (AbstractPower p : m.powers) {
                        addToTop(new RemoveSpecificPowerAction(m, m, p.ID));
                    }
                    addToBot(new VFXAction(new GrandFinalEffect(), 0.05F));
                    addToBot(new VFXAction(new BlizzardEffect(50, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.05F));
                    addToBot(new VFXAction(new DieDieDieEffect(), 0.05F));
                    addToBot(new VFXAction(new LightningEffect(m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new ShockWaveEffect(m.hb.cX, m.hb.cY, Settings.GREEN_TEXT_COLOR.cpy(), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.05F));
                    addToBot(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.05F));
                    addToBot(new VFXAction(new SweepingBeamEffect(pl().hb.cX, pl().hb.cY, pl().flipHorizontal), 0.05F));
                    addToBot(new VFXAction(new MindblastEffect(pl().hb.cX, pl().hb.cY, pl().flipHorizontal), 0.05F));
                    addToBot(new VFXAction(new CleaveEffect(), 0.05F));
                    addToBot(new VFXAction(new AdrenalineEffect(), 0.05F));
                    addToBot(new VFXAction(new PressurePointEffect(m.hb.cX, m.hb.cY)));
                    addToBot(new VFXAction(new PotionBounceEffect(pl().hb.cX, pl().hb.cY, m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new FlickCoinEffect(pl().hb.cX, pl().hb.cY, m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new HemokinesisEffect(pl().hb.cX, pl().hb.cY, m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new VerticalAuraEffect(Color.RED.cpy(), m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.PINK.cpy()), 0.05F));
                    addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new RipAndTearEffect(m.hb.cX, m.hb.cY, Color.GOLD.cpy(), Color.FIREBRICK.cpy()), 0.05F));
                    addToBot(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
                    addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new ReaperEffect(), 0.05F));
                    addToBot(new VFXAction(new FlyingOrbEffect(m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new SanctityEffect(m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new EmptyStanceEffect(m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new ThirdEyeEffect(m.hb.cX, m.hb.cY)));
                    addToBot(new VFXAction(new AnimatedSlashEffect(m.hb.cX, m.hb.cY - 30.0F * Settings.scale, 500.0F, 200.0F, 290.0F, 3.0F, Color.SCARLET.cpy(), Color.VIOLET.cpy()), 0.05F));
                    addToBot(new VFXAction(new ScrapeEffect(m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY, Settings.PURPLE_COLOR.cpy()), 0.05F));
                    addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY, Settings.GOLD_COLOR.cpy()), 0.05F));
                    addToBot(new VFXAction(new DevotionEffect(), 0.05F));
                    addToBot(new VFXAction(new OfferingEffect(), 0.05F));
                    addToBot(new VFXAction(new IntimidateEffect(m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new VFXAction(new InflameEffect(pl()), 0.05F));
                    addToBot(new VFXAction(new InflameEffect(m), 0.05F));
                    addToBot(new VFXAction(new SearingBlowEffect(m.hb.cX, m.hb.cY, 69), 0.05F));
                    addToBot(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.05F));
                    addToBot(new InstantKillAction(m));
                }
            }
        }
    }

    public void onVictory() {
        this.counter = -1;
        sPulse();
    }

    public boolean canSpawn(){
        return (isAscension20DecaDied() && isQuestRelicEnabled());
    }
}