package bundlecore.cardmodifiers.chimeracardscrossover;
import CardAugments.damagemods.PerniciousDamage;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.ChokePower;

/**
 * The mechanism of action for the Venomous modifier.
 */
public class VenomousDamage  extends AbstractDamageModifier {
    @Dont_Use_This_Externally
    public VenomousDamage() {
    }

    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (lastDamageTaken > 0) {
            this.addToBot(new ApplyPowerAction(target, info.owner, new ChokePower(target, lastDamageTaken), lastDamageTaken));
        }
    }

    public boolean isInherent() {
        return true;
    }

    public AbstractDamageModifier makeCopy() {
        return new PerniciousDamage();
    }
}
