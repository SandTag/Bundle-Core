package bundlecore.bundleloadedbools;
import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.relics.*;
import bundlecore.util.GFL;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Objects;

/**
 * A class containing a wide array of checks used internally by Bundlecore and all the other bundles.
 * <p>
 * Not intended for non-bundle use, although you may need to patch the quest relic system if you are adding such relics.
 */
@Dont_Use_This_Externally
public interface BundleChecker {
    default boolean isBFood(){
        return Loader.isModLoaded("bundle_of_food");
    }

    default boolean isBPotions(){
        return Loader.isModLoaded("bundle_of_potions");
    }

    default boolean isBPeg(){
        return Loader.isModLoaded("Bundle_Of_Peglin");
    }

    default boolean isBTerra(){
        return Loader.isModLoaded("Bundle_Of_Terra");
    }

    default boolean isQuestRelicEnabled(){
        return BundleCoreMain.QuestRelics;
    }

    default boolean isBFoodXPotions(){
        return isBFood() && isBPotions() && BundleCoreMain.bundleCoreConfig.getBool("foodBundleCrossPotions");
    }

    default boolean isBFoodXPeg(){
        return isBFood() && isBPeg() && BundleCoreMain.bundleCoreConfig.getBool("foodBundleCrossPeglin");
    }

    default boolean isBPotionsXPeg(){
        return isBPotions() && isBPeg() && BundleCoreMain.bundleCoreConfig.getBool("potionBundleCrossPeglin");
    }

    default boolean isBFoodXTerra(){
        return isBFood() && isBTerra() && BundleCoreMain.bundleCoreConfig.getBool("terraBundleFood");
    }

    default boolean isBPotionsXTerra(){
        return isBPotions() && isBTerra() && BundleCoreMain.bundleCoreConfig.getBool("terraBundlePotions");
    }

    default boolean isBPegXTerra(){
        return isBPeg() && isBTerra() && BundleCoreMain.bundleCoreConfig.getBool("terraBundlePeg");
    }

    default boolean isFoxMoment(){
        if (BundleCoreMain.ModCreatorButton){
            return Loader.isModLoaded("downfall") || Loader.isModLoaded("RandomCharacterButton") || Loader.isModLoaded("TheJungle");
        }
        return false;
    }

    default boolean isVexMoment(){
        if (BundleCoreMain.ModCreatorButton){
            return Loader.isModLoaded("downfall") || Loader.isModLoaded("thorton") || Loader.isModLoaded("permNeow") || Loader.isModLoaded("anniv5");
        }
        return false;
    }

    default boolean isChimeraMoment(){
        return BundleCoreMain.ChimericExpansion;
    }

    default boolean isStarlightMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return (Loader.isModLoaded("Starlight"));
        }
        return false;
    }

    default boolean isSneckoMoment(){
        if (BundleCoreMain.ModCreatorButton){
            return (Loader.isModLoaded("TrophyHunter"));
        }
        return false;
    }

    default boolean isOceanMoment() {
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("downfall") || Loader.isModLoaded("oceanmod") || Loader.isModLoaded("downfallmodwhereeverythingisthesamebuthermitshathasearholes");
        }
        return false;
    }

    default boolean isBryanMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("BattleTowers") || Loader.isModLoaded("garticmod"); // || Loader.isModLoaded("Idk what fireblade is");
        }
        return false;
    }

    default boolean isGeeKayMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("BattleTowers") || Loader.isModLoaded("mintyspire") || Loader.isModLoaded("anniv5") || Loader.isModLoaded("spicyShops");
        }
        return false;
    }

    default boolean isPandeMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("bigcards") || Loader.isModLoaded("PansTrinkets") || Loader.isModLoaded("spirelocations") || Loader.isModLoaded("penitence") || Loader.isModLoaded("relictweaks");
        }
        return false;
    }

    default boolean isBoomerMoment() {
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("battletimer") || Loader.isModLoaded("anniv5");
        }
        return false;
    }

    default boolean isSilverMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("DragonkinMod");
        }
        return false;
    }

    default boolean isKioMomentOhWaitHoldOnAMinuite(){
        return BundleCoreMain.ModCreatorButton;
    }

    default boolean isAlchyrMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("downfall") || Loader.isModLoaded("BattleTowers") || Loader.isModLoaded("minigames") || Loader.isModLoaded("wanderingMiniBosses") || Loader.isModLoaded("Astrologer") || Loader.isModLoaded("versiontwocompatible");
        }
        return false;
    }

    default boolean isJediMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("jedi") || Loader.isModLoaded("keyreminder") || Loader.isModLoaded("gremlinskip");
        }
        return false;
    }

    default boolean isJohnnyMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("StSModEnergizedSpire") || Loader.isModLoaded("StSModEnergyAddict") || Loader.isModLoaded("sts-mod-the-blackbeard") || Loader.isModLoaded("minigames");
        }
        return false;
    }

    default boolean isJerryMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("force-key") || Loader.isModLoaded("shopping_list") || Loader.isModLoaded("relic-reminders") || Loader.isModLoaded("smilegirya");
        }
        return false;
    }

    default boolean isModargoMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("anniv5") || Loader.isModLoaded("CorruptTheSpire") || Loader.isModLoaded("Elementarium");
        }
        return false;
    }

    default boolean isDarkgladeMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("minigames")  || Loader.isModLoaded("BattleTowers") || Loader.isModLoaded("wanderingMiniBosses") || Loader.isModLoaded("ruina") || Loader.isModLoaded("Gensokyo");
        }
        return false;
    }

    default boolean isMichealMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("downfall") || Loader.isModLoaded("BattleTowers") || Loader.isModLoaded("minigames") || Loader.isModLoaded("TheJungle");
        }
        return false;
    }

    default boolean isLaugicMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("anniv5") || Loader.isModLoaded("theVacant") || Loader.isModLoaded("Snowpunk");
        }
        return false;
    }

    default boolean isLightMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Loader.isModLoaded("LastLightMod") || Loader.isModLoaded("dragonmod");
        }
        return false;
    }

    default boolean isBlankMoment(){
        if (BundleCoreMain.ModCreatorButton) {
            return Settings.isEndless;
        }
        return false;
    }

    default boolean isEnbeonMoment(){
        return Loader.isModLoaded("anniv5") || Loader.isModLoaded("britishspire");
    }

    default boolean isrinMMoment(){
        return Loader.isModLoaded("klkmod") || Loader.isModLoaded("subterfuge");
    }

    default boolean isFleetMoment(){
        return Loader.isModLoaded("Library of Ruina") || Loader.isModLoaded("Elena");
    }

    default boolean isLobbienMoment(){
//        return Loader.isModLoaded("BattleTowers") || Loader.isModLoaded("a") || Loader.isModLoaded("b");
        return true;
    }
    default boolean isSandMomentHmmYes(){
        return BundleCoreMain.ModCreatorButton;
    }


    // Quest Relics
    default boolean isDeliciousDonuDied(){
        return Objects.equals(BundleCoreMain.DonuProgressValue, "Fried");
    }
    default boolean isAscension20DonuDied(){
        return Objects.equals(BundleCoreMain.DonuProgressValueA20, "DeepFried");
    }
    default boolean isDeliciousDecaDied(){
        return Objects.equals(BundleCoreMain.DecaProgressValue, "Mixed");
    }
    default boolean isAscension20DecaDied(){
        return Objects.equals(BundleCoreMain.DecaProgressValueA20, "WellMixed");
    }
    default boolean isTimeDied(){
        return Objects.equals(BundleCoreMain.TimussyProgressValue, "Moist");
    }
    default boolean isAscension20TimeDied(){
        return Objects.equals(BundleCoreMain.TimussyProgressValueA20, "Moister");
    }
    default boolean isCollectDied(){
        return Objects.equals(BundleCoreMain.CollectProgressValue, "Vintage");
    }
    default boolean isAscension20CollectDied(){
        return Objects.equals(BundleCoreMain.CollectProgressValueA20, "Heritage");
    }

    default boolean isGuardianDied(){
        return Boolean.TRUE.equals(BundleCoreMain.guardianDefeated);
    }

    default boolean isGuardianDiedA20(){
        return Boolean.TRUE.equals(BundleCoreMain.guardianDefeatedA20);
    }
    default boolean isMazalethUpdate(){
        return false; //Todo: Next again update
    }

    /**
     * This is the section of BundleChecker that might be important to learn of if you wish to add relics
     * to the quest pool.
     */
    default boolean hasQuestRelic(){
        if (GFL.GAP().hasRelic(DeliciousDecaBoss.ID)){
            return true;
        }
        if (GFL.GAP().hasRelic(MenacingRavenBoss.ID)){
            return true;
        }
        if (GFL.GAP().hasRelic(TemporalTimepieceBoss.ID)){
            return true;
        }
        if (GFL.GAP().hasRelic(CursedBrandBoss.ID)){
            return true;
        }
        if (GFL.GAP().hasRelic("bundle_of_food:DeliciousdonuBoss")){
            return true;
        }
        if (GFL.GAP().hasRelic("bundle_of_content:InsultingNoteQuest")){
            return true;
        }
        return false;
    }

/*
 default boolean isMazalethDefeated(){return Objects.equals(BundleCoreMain.AwakenedProgressValue, "Exhumed");}
    default boolean isMazalethA20(){return Objects.equals(BundleCoreMain.AwakenedProgressValueA20, "Ascended");}
*/

    // Ascenders Bane Replacements
    // Todo: Thesaurosus quest. (Bundle of Peglin).
    default boolean isPeglinSwaps(){
        return isBPeg() && BundleCoreMain.bundleCoreConfig.getBool("terraBundlePeg");
    }

    /**
     * This is irrelevant, use the GFL check for this instead.
     */
    @Deprecated
    static AbstractPlayer pll(){
        return AbstractDungeon.player;
    }

}