package bundlecore.relics;
import basemod.AutoAdd;
import basemod.helpers.CardModifierManager;
import bundlecore.bundleloadedbools.BundleChecker;
import bundlecore.util.GFL;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import static bundlecore.BundleCoreMain.makeID;

@AutoAdd.Ignore
public class ModificationKitCommon extends BundleRelic implements BundleChecker {
    private static final String NAME = "ModificationKitCommon";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.COMMON;
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public ModificationKitCommon(){
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        if (CardModifierManager.modifiers(c).size() != 0 && c.type == AbstractCard.CardType.ATTACK)
            return damage + 1.0F;
        return damage;
    }

    @Override
    public void onUseCard(final AbstractCard c, final UseCardAction action) {
        if (CardModifierManager.modifiers(c).size() != 0 && c.type == AbstractCard.CardType.SKILL) {
            GFL.att(new GainBlockAction(GFL.GAP(), 1, true));
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