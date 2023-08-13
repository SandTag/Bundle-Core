package bundlecore.relics;
/*
import basemod.AutoAdd;
import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.relics.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.HashSet;
import java.util.List;

import static bundlecore.BundleCoreMain.makeID;

@AutoAdd.Ignore
public class TestRelic extends BundleRelic implements OnChannelRelic, BeforeRenderIntentRelic, BetterOnLoseHpRelic, BetterOnSmithRelic, BetterOnUsePotionRelic,
        OnReceivePowerRelic, OnAnyPowerAppliedRelic, OnAfterUseCardRelic, OnSkipCardRelic, OnRemoveCardFromMasterDeckRelic, OnLoseTempHpRelic, OnLoseBlockRelic,
        OnPlayerDeathRelic, DamageModApplyingRelic, OnCreateBlockInstanceRelic, CardRewardSkipButtonRelic{

    private static final String NAME = "TestRelic";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.SPECIAL;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    public TestRelic() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public boolean canSpawn(){
        return false;
    }

    @Override
    public void onChannel(AbstractOrb abstractOrb) {

    }

    @Override
    public boolean beforeRenderIntent(AbstractMonster abstractMonster) {
        return false;
    }

    @Override
    public int betterOnLoseHp(DamageInfo damageInfo, int i) {
        return 0;
    }

    @Override
    public void betterOnSmith(AbstractCard abstractCard) {

    }

    @Override
    public void betterOnUsePotion(AbstractPotion abstractPotion) {

    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature) {
        return false;
    }

    @Override
    public boolean onAnyPowerApply(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return false;
    }

    @Override
    public int onAnyPowerApplyStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        return OnAnyPowerAppliedRelic.super.onAnyPowerApplyStacks(power, target, source, stackAmount);
    }

    @Override
    public void onAfterUseCard(AbstractCard abstractCard, UseCardAction useCardAction) {

    }

    @Override
    public void onSkipSingingBowl(RewardItem rewardItem) {

    }

    @Override
    public void onSkipCard(RewardItem rewardItem) {

    }

    @Override
    public void onRemoveCardFromMasterDeck(AbstractCard abstractCard) {

    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        return 0;
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int i) {
        return 0;
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        return false;
    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return false;
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return null;
    }

    @Override
    public void onCreateBlockInstance(HashSet<AbstractBlockModifier> hashSet, Object o) {

    }

    @Override
    public void onClickedButton() {

    }

    @Override
    public String getButtonLabel() {
        return null;
    }
}
 */
