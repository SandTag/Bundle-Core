package bundlecore.potions;

import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

@Dont_Use_This_Externally
public class PotionFireForbidden extends AbstractForbiddenPotion {

    final AbstractPlayer pl = AbstractDungeon.player;
    public static final String POTION_ID = BundleCoreMain.makeID(PotionFireForbidden.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final int EFFECT = 1;
    @Dont_Use_This_Externally
    public PotionFireForbidden() {
        super(NAME, POTION_ID, PotionRarity.PLACEHOLDER, AbstractPotion.PotionSize.SPHERE, AbstractPotion.PotionColor.FIRE, true);
        this.isThrown = true;
        this.targetRequired = true;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.potency, DamageInfo.DamageType.NORMAL);
        addToBot(new DamageAction(target, info, AbstractGameAction.AttackEffect.SHIELD));
    }

    public int getPotency(int ascensionLevel) {
        return 2;
    }

    public AbstractPotion makeCopy() {
        return new PotionFireForbidden();
    }
}