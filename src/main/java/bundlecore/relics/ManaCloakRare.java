package bundlecore.relics;

import bundlecore.actions.ManaCloakAttack;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.relics.BundleRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Objects;

import static bundlecore.BundleCoreMain.makeID;

public class ManaCloakRare extends BundleRelic implements BundleChecker {

    private static final String NAME = "ManaCloakRare";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.RARE;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public ManaCloakRare() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onUsePotion() {
        addToBot(new GainEnergyAction(1));
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != pl() && damageAmount > 0) {
            flash();
           for (AbstractPotion p : pl().potions){
               if (!Objects.equals(p.ID, PotionSlot.POTION_ID)){
                   addToBot(new ManaCloakAttack(pl()));
                   break;
               }
           }
        }
        return damageAmount;
    }

    public boolean canSpawn(){
        return (isBPotionsXTerra());
    }

}