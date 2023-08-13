package bundlecore.relics;
import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;
import static bundlecore.BundleCoreMain.makeID;

public class EmeraldFeastBoss extends BundleRelic implements BundleChecker {
    private static final String NAME = "EmeraldFeastBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public EmeraldFeastBoss() {
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
        pl().energy.energyMaster+=2;
    }

    @Override
    public void onUnequip(){
        pl().energy.energyMaster-=2;
    }

    public void atBattleStart() {
        flash();
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            addToBot(new RelicAboveCreatureAction(mo, this));
            addToBot(new VFXAction(new BorderFlashEffect(Color.CHARTREUSE, true)));
            addToBot(new VFXAction(new SanctityEffect(mo.hb.cX, mo.hb.cY)));
            addToBot(new SFXAction("MONSTER_DONU_DEFENSE"));
            addToBot(new ApplyPowerAction(mo, pl(), new StrengthPower(mo, 3), 3, true));
            addToBot(new ApplyPowerAction(mo, pl(), new PlatedArmorPower(mo, 3), 3, true));
            addToBot(new GainBlockAction(mo, pl(), 16, true));
        }
    }

    public boolean canSpawn(){
        return (isAscension20DonuDied() && isQuestRelicEnabled());
    }
}