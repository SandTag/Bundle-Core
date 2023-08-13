package bundlecore.potions;

import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.powers.OnFirePower;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

/**
 * Used by the magma skull.
 */
@Dont_Use_This_Externally
public class LavaPotionForbidden extends AbstractForbiddenPotion {

    final AbstractPlayer pl = AbstractDungeon.player;
    public static final String POTION_ID = BundleCoreMain.makeID(LavaPotionForbidden.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final int EFFECT = 15;
    /**
     * Used by the magma skull.
     */
    @Dont_Use_This_Externally
    public LavaPotionForbidden(){
        super(NAME, POTION_ID, PotionRarity.PLACEHOLDER, AbstractPotion.PotionSize.SPHERE, AbstractPotion.PotionColor.WHITE, true);
        FlavorText.PotionFlavorFields.flavorBoxType.set(this, FlavorText.boxType.TRADITIONAL);
        FlavorText.PotionFlavorFields.textColor.set(this, new Color(152/255f,255/255f,252/255f,1));
        isThrown = true;
        targetRequired = true;
    }

    @Override
    public void use(AbstractCreature target){
        DamageInfo info = new DamageInfo(pl, this.potency, DamageInfo.DamageType.THORNS);
        info.applyEnemyPowersOnly(target);
        addToBot(new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(target, pl, new OnFirePower(target, pl, this.potency/5), this.potency/5));
    }

    @Override
    public int getPotency(final int ascensionLevel){
        return EFFECT;
    }

    @Override
    public void initializeData(){
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1] + potency/5 + potionStrings.DESCRIPTIONS[2];
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new LavaPotionForbidden();
    }

}