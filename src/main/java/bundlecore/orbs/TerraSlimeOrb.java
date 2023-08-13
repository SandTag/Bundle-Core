package bundlecore.orbs;

import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;
import static bundlecore.BundleCoreMain.makeOrbPath;

/**
 * Used by Bundle of Terra for a boss relic.
 */
@Dont_Use_This_Externally
public class TerraSlimeOrb extends AbstractBundleOrb {
    public static final String ORB_ID = BundleCoreMain.makeID("TerraSlimeOrb");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int PASSIVE_AMOUNT = 1;
    private static final int EVOKE_AMOUNT = 1;
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;

    /**
     * Used by Bundle of Terra for a boss relic.
     */
    @Dont_Use_This_Externally
    public TerraSlimeOrb() {
        super(ORB_ID, orbString.NAME, PASSIVE_AMOUNT, EVOKE_AMOUNT, DESCRIPTIONS[0], DESCRIPTIONS[0], makeOrbPath("TerraSlimeOrb.png"));
        img = TextureLoader.getTexture(makeOrbPath("TerraSlimeOrb.png"));
        updateDescription();
        angle = 0.0f;
        channelAnimTimer = 0.5f;
    }



    // Define orb evoke and passive
    @Override
    public void onEvoke() {
        addToBot(new IncreaseMaxOrbAction(1));
        addToBot(new ChannelAction(new TerraSlimeOrb()));
    }

    @Override
    public void onEndOfTurn() {
        addToBot(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
        updateDescription();
        addToTop(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,passiveAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
    }



    // Define what affects orb values.
    @Override
    public void applyFocus() {
        AbstractPower power = AbstractDungeon.player.getPower("Focus");
        AbstractPower power2 = AbstractDungeon.player.getPower("Strength");
        AbstractPower power3 = AbstractDungeon.player.getPower("Dexterity");
        int sumpowers = 0;
        boolean changedvalue = false;
        if (power != null){
            sumpowers += power.amount;
            changedvalue = true;
        }
        if (power2 != null){
            sumpowers += power2.amount;
            changedvalue = true;
        }
        if (power3 != null){
            sumpowers += power3.amount;
            changedvalue = true;
        }
        if (changedvalue){
            this.passiveAmount = Math.max(0, this.basePassiveAmount + sumpowers);
            this.evokeAmount = this.baseEvokeAmount;
        }
        else{
            this.passiveAmount = this.basePassiveAmount;
            this.evokeAmount = this.baseEvokeAmount;
        }
    }



    //Orb vfx and text.
    @Override
    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.25F);
        AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
    }

    @Override
    public void updateDescription() {
        applyFocus();
        description = DESCRIPTIONS[0] + passiveAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2];
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.15F);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, c.a / 2.0f));
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.45F, this.scale * 1.45F, this.angle / 1.35F, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.5F, this.scale * 1.5F, this.angle / 1.4F, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        renderText(sb);
        hb.render(sb);
    }
    @Override
    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET + 20.0F * Settings.scale, this.c, this.fontScale);
    }



    // copy
    @Override
    public AbstractOrb makeCopy() {
        return new TerraSlimeOrb();
    }

}
