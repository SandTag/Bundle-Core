package bundlecore.relics;
import CardAugments.CardAugmentsMod;
import basemod.AutoAdd;
import basemod.helpers.CardModifierManager;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.util.GFL;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

import static bundlecore.BundleCoreMain.makeID;

@AutoAdd.Ignore
public class FanMailCommon extends BundleRelic implements BundleChecker {
    private static final String NAME = "FanMailCommon";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.COMMON;
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;
    private static boolean IsRelicTime = false;

    public FanMailCommon(){
        super(ID, NAME, RARITY, SOUND);
        this.counter = 137;
    }

    @Override
    public void onEquip() {
        int value = CardAugmentsMod.modProbabilityPercent;
        this.counter = (10+(value));
    }

    @Override
    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter <= 0) {
            usedUp();
        } else if (setCounter == 1) {
            this.getUpdatedDescription();
        }
    }

    public static void fixConcurrentBS3(){
        if (IsRelicTime) {
            int RandomValue = AbstractDungeon.relicRng.random(99);
            if (RandomValue != 69) {
                CardCrawlGame.sound.play("POWER_PLATED", 0.25F);
                AbstractRelic abstractRelic = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.RARE);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5F, Settings.HEIGHT / 0.5F, abstractRelic);
            } else {
                GFL.atb(new VFXAction(new GrandFinalEffect(), 2.0F));
                AbstractDungeon.effectList.add(new RainingGoldEffect(666, true));
                GFL.atb(new VFXAction(new GrandFinalEffect(), 2.0F));
                AbstractDungeon.effectList.add(new RainingGoldEffect(666, true));
                GFL.atb(new VFXAction(new GrandFinalEffect(), 2.0F));
                AbstractDungeon.effectList.add(new RainingGoldEffect(666, true));
                GFL.atb(new VFXAction(new GrandFinalEffect(), 2.0F));
                AbstractDungeon.effectList.add(new RainingGoldEffect(666, true));
                GFL.atb(new VFXAction(new GrandFinalEffect(), 2.0F));
                AbstractDungeon.effectList.add(new RainingGoldEffect(666, true));
                AbstractRelic abstractRelic = new UpdateBoss();
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5F, Settings.HEIGHT / 0.5F, abstractRelic);
            }
        }
        IsRelicTime = false;
    }

    @Override
    public void onUseCard(final AbstractCard c, final UseCardAction action) {
        if (CardModifierManager.modifiers(c).size() != 0) {
            this.counter --;
        }
        if (this.counter == 1){
            flash();
            beginLongPulse();
        }
        if (this.counter <= 0 && !this.usedUp){
            GFL.atb(new RelicAboveCreatureAction(GFL.GAP(), this));
            sPulse();
            flash();
            this.usedUp();
            IsRelicTime = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn(){
        return (isChimeraMoment());
    }
}