package bundlecore.powers;

import basemod.ReflectionHacks;
import basemod.interfaces.CloneablePowerInterface;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import static bundlecore.BundleCoreMain.makeID;
public class PhysicalTimeWarpPower extends BundlePower implements CloneablePowerInterface {
    private float currentTime;
    private float baseTimeLimit;
    private boolean activeTimer = true;
    private float timeCounter = -1.0F;
    private float waitTimer = 0.0F;
    public static final String POWER_ID = makeID("PhysicalTimeWarpPower");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public static boolean endTurnCall = false;

    /**
     * Used by some bosses in bundles and a relic in Bundle of Content.
     */
    @Dont_Use_This_Externally
    public PhysicalTimeWarpPower(AbstractCreature owner, int amount, int startingValue) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.baseTimeLimit = (float)amount;
        this.currentTime = startingValue;
    }
    public void atStartOfTurnPostDraw() {
        this.baseTimeLimit = this.amount;
        this.timeCounter = 0;
        this.currentTime = 0;
        this.activeTimer = true;
        endTurnCall = false;
    }
    public void updateDescription() {
        if (this.currentTime >= 2.0f) {
            this.description = DESCRIPTIONS[0] + (int) this.baseTimeLimit + DESCRIPTIONS[1] + (int) this.currentTime + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }
        else{
            this.description = DESCRIPTIONS[0] + (int) this.baseTimeLimit + DESCRIPTIONS[1] + (int) this.currentTime + DESCRIPTIONS[4] + DESCRIPTIONS[3];
        }
    }

    public void update(int slot) {
        super.update(slot);
        if (this.currentTime < this.baseTimeLimit) {
            if (JankyShit()) {
                if (this.waitTimer <= 0.0F) {
                    this.timeCounter += Gdx.graphics.getRawDeltaTime();
                } else {
                    this.waitTimer -= Gdx.graphics.getRawDeltaTime();
                }
                currentTime = (this.timeCounter);
            }
        } else if (this.currentTime >= this.baseTimeLimit) {
            if (this.waitTimer <= 0.0F && activeTimer && JankyShit()) {
                if (!AbstractDungeon.player.isDead && AbstractDungeon.getCurrRoom().phase.equals(AbstractRoom.RoomPhase.COMBAT)) {
                    activeTimer = false;
                    this.timeCounter = 0;
                    this.currentTime = 0;
                    flash();
                    TimeWarpTurnEndEffect effect = new TimeWarpTurnEndEffect();
                    String unPrefixed = this.ID.substring(ID.indexOf(":") + 1);
                    Texture hiDefImage = TextureLoader.getHiDefPowerTexture(unPrefixed);
                    region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
                    ReflectionHacks.setPrivate(effect, TimeWarpTurnEndEffect.class, "img", region128);
                    AbstractDungeon.topLevelEffectsQueue.add(effect);
                    addToTop(new PressEndTurnButtonAction());
                    this.waitTimer = 1.0F;
                }
            } else {
                this.waitTimer -= Gdx.graphics.getRawDeltaTime();
            }
        }
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        activeTimer = false;
        this.timeCounter = 0;
        this.currentTime = 0;
    }

    private boolean JankyShit(){
        if (CardCrawlGame.stopClock){
            return true;
        }
        else return (!AbstractDungeon.actionManager.turnHasEnded && !endTurnCall);
    }

    @Override
    public AbstractPower makeCopy() {
        return new PhysicalTimeWarpPower(owner, amount, amount);
    }
}
