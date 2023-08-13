package bundlecore.patches;

import basemod.ReflectionHacks;
import bundlecore.BundleCoreMain;
import com.evacipated.cardcrawl.modthespire.Loader;

/**
 * Currently only supports manual implementation, although will later on have dynamic support.
 */
public abstract class AbstractMenuPriority {
    public static int priorityAct = 0;
    public static int priorityBoss = 0;
    public static int priorityMenu = 0;
    public static boolean enabledAct = false;
    public static boolean enabledBoss = false;
    public static boolean enabledMenu = false;

    public static boolean checkingPriorityAct(){
        if (BundleCoreMain.bundleCoreConfig.getBool("pegBundleEnableHallwayMusicSwap") && Loader.isModLoaded("Bundle_Of_Peglin")){
            priorityAct = 1;
            enabledAct = true;
        }
        if (BundleCoreMain.bundleCoreConfig.getBool("terraBundleActMusic") && Loader.isModLoaded("BundleOfTerra")){
            priorityAct = 2;
            enabledAct = true;
        }
        return enabledAct;
    }

    public static boolean checkingPriorityBoss(){
        if (BundleCoreMain.bundleCoreConfig.getBool("pegBundleEnableMusicSwap") && Loader.isModLoaded("Bundle_Of_Peglin")){
            priorityBoss = 1;
            enabledBoss = true;
        }
        if (BundleCoreMain.bundleCoreConfig.getBool("terraBundleBossMusic") && Loader.isModLoaded("BundleOfTerra")){
            priorityBoss = 2;
            enabledBoss = true;
        }
        return enabledBoss;
    }

    public static boolean checkingPriorityMenu(){
        //Hubris, also known as the first content mod.
        if (BundleCoreMain.bundleCoreConfig.getBool("IsHubrisConfig") && Loader.isModLoaded("hubris")){
            priorityMenu = 0;
            enabledMenu = false;
        }
        //Downfall, the most successful STS mod at the time of writing.
        if (BundleCoreMain.bundleCoreConfig.getBool("IsDownfallConfig") && Loader.isModLoaded("downfall")){
            priorityMenu = 1;
            enabledMenu = false;
        }
        //Ruina, my personal favourite out of the act mods.
        if (BundleCoreMain.bundleCoreConfig.getBool("IsRuinaConfig") && Loader.isModLoaded("ruina")){
            priorityMenu = 2;
            enabledMenu = false;
        }
        //Horny mod, I mean Shion mod.
        if (BundleCoreMain.bundleCoreConfig.getBool("IsShionConfig") && Loader.isModLoaded("VUPShionMod")){
            priorityMenu = 3;
            enabledMenu = false;
        }
        //Bundle Of Peglin (Bundle 3) - I don't really like this one anymore.
        if (BundleCoreMain.bundleCoreConfig.getBool("pegBundleEnableTitle") && Loader.isModLoaded("Bundle_Of_Peglin")){
            priorityMenu = 4;
            enabledMenu = true;
        }
        //Dead Cells Mod -> It was buggy, but it was still fun.
        if (BundleCoreMain.bundleCoreConfig.getBool("IsDeadConfig") && Loader.isModLoaded("DeadCells")){
            priorityMenu = 5;
            enabledMenu = false;
        }
        // Bundle Of Terra (Bundle 5) - Other than being Dev Hell, It's pretty great.
        if (BundleCoreMain.bundleCoreConfig.getBool("terraBundleMainMenu") && Loader.isModLoaded("Bundle_Of_Terra")){
            priorityMenu = 6;
            enabledMenu = true;
        }



        //
        //
        ////
        ////
        ////
        ////
        ////
        ///////
        //////
        /////
        ////
        ///
        //
//      /


        //hubris
        if (priorityMenu > 0 && AbstractMenuPriority.priorityMenu != 69420){
            if (Loader.isModLoaded("hubris")) {
                BundleCoreMain.bundleCoreConfig.setBool("IsHubrisConfig", false);
            }
        }
        //downfall
        if (priorityMenu > 1 && AbstractMenuPriority.priorityMenu != 69420){
            if (Loader.isModLoaded("downfall")) {
                BundleCoreMain.bundleCoreConfig.setBool("IsDownfallConfig", false);
                try {
                    ReflectionHacks.setPrivateStatic(Class.forName("downfall.downfallMod"), "noMusic", true);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //Ruina
        if (priorityMenu > 2 && AbstractMenuPriority.priorityMenu != 69420){
            if (Loader.isModLoaded("ruina")) {
                BundleCoreMain.bundleCoreConfig.setBool("IsRuinaConfig", false);
                try {
                    ReflectionHacks.setPrivateStatic(Class.forName("ruina.RuinaMod"), "disableAltTitleArt", true);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //VUPShion
        if (priorityMenu > 3 && AbstractMenuPriority.priorityMenu != 69420){
            if (Loader.isModLoaded("VUPShionMod")) {
                BundleCoreMain.bundleCoreConfig.setBool("IsShionConfig", false);
                try {
                    ReflectionHacks.setPrivateStatic(Class.forName("VUPShionMod.util.SaveHelper"), "notReplaceTitle", true);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (priorityMenu > 5 && AbstractMenuPriority.priorityMenu != 69420){
            if (Loader.isModLoaded("DeadCells")) {
                BundleCoreMain.bundleCoreConfig.setBool("IsDeadConfig", false);
            }
        }
        return enabledMenu;
    }

}