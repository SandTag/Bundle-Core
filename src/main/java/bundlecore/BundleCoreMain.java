package bundlecore;
import basemod.*;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import bundlecore.cards.BundleCard;
import bundlecore.events.BundleEvent;
import bundlecore.events.enemies.LagavulinCloneEnemy;
import bundlecore.patches.AbstractMenuPriority;
import bundlecore.potions.LavaPotionForbidden;
import bundlecore.potions.PotionFireForbidden;
import bundlecore.relics.*;
import bundlecore.relics.bottledrelics.bottledmods.BottledModsUncommon;
import bundlecore.util.ChimeraLoader;
import bundlecore.util.GFL;
import bundlecore.util.GeneralUtils;
import bundlecore.util.Interfaces.patches.OnCreateMidCombatCards;
import bundlecore.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.deprecated.DEPRECATED_DarkCore;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import java.util.ArrayList;
import java.util.Properties;

@SpireInitializer
public class BundleCoreMain implements EditStringsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, PostInitializeSubscriber, PostUpdateSubscriber, EditCardsSubscriber, PostDungeonInitializeSubscriber, StartGameSubscriber {
    public static ModInfo info;
    public static String modID;

    static {
        loadModInfo();
    }

    public static final Logger logger = LogManager.getLogger(modID);
    private static final String resourcesFolder = "bundlecore";

    public static String makeID(String id) {
        return modID + ":" + id;
    }

    public static void initialize() {
        new BundleCoreMain();
    }

    public static boolean CheckForFood = false;
    public static boolean CheckForPotions = false;
    public static boolean CheckForPeg = false;
    public static boolean CheckForTerra = false;
    public static boolean CheckForanniv5 = false;
    public static int InitialModCount = 0;

    public static String FILE_NAME = "BundleCoreConfigFile";
    public static boolean foodBundleCrossPotions = true;
    public static boolean foodBundleCrossPeglin = true;
    public static boolean pegBundleEnableTitle = true;
    public static boolean pegBundleEnableMusicSwap = true;
    public static boolean pegBundleEnableHallwayMusicSwap = true;
    public static boolean potionBundleCrossPeglin = true;
    public static boolean terraBundleMainMenu = true;
    public static boolean terraBundleActMusic = true;
    public static boolean terraBundleBossMusic = true;
    public static boolean terraBundleFood = true;
    public static boolean terraBundlePotions = true;
    public static boolean terraBundlePeg = true;
    //
    // External Crossover Bools
    //
    public static boolean IsHubrisConfig = false;
    public static boolean IsDownfallConfig = false;
    public static boolean IsRuinaConfig = false;
    public static boolean IsShionConfig = false;
    public static boolean IsDeadConfig = false;
    public static boolean FunButton = false;
    public static boolean ModCreatorButton = true;

    public static boolean QuestRelics = true;

    public static boolean ChimericExpansion = true;
    public static String DonuProgressValue = "raw";
    public static String DonuProgressValueA20 = "raw";
    public static String DecaProgressValue = "Dry";
    public static String DecaProgressValueA20 = "Dry";
    public static String AwakenedProgressValue = "Sleep";
    public static String AwakenedProgressValueA20 = "Sleep";
    public static String TimussyProgressValue = "Crusty";
    public static String TimussyProgressValueA20 = "Crusty";
    public static String CollectProgressValue = "Empty";
    public static String CollectProgressValueA20 = "Empty";
    public static boolean questRelicFtue3 = false;
    public static boolean guardianDefeated = false;
    public static boolean guardianDefeatedA20 = false;
    public static BundleRelic CurrentRunsFourthBossRelicStorage = null;

    //

    public static SpireConfig bundleCoreConfig;
    private ModPanel settingsPanel1;

    public static String makeMenuPath(String resourcePath) {
        return "bundlecore/menustuff/" + resourcePath;
    }

    public BundleCoreMain() {
        BaseMod.subscribe(this);

        logger.info(modID + " subscribed to BaseMod.");
        Properties bundleCoreDefaultSettings = new Properties();
        bundleCoreDefaultSettings.setProperty("foodBundleCrossPotions", Boolean.toString(foodBundleCrossPotions));
        bundleCoreDefaultSettings.setProperty("foodBundleCrossPeglin", Boolean.toString(foodBundleCrossPeglin));
        bundleCoreDefaultSettings.setProperty("pegBundleEnableTitle", Boolean.toString(pegBundleEnableTitle));
        bundleCoreDefaultSettings.setProperty("pegBundleEnableMusicSwap", Boolean.toString(pegBundleEnableMusicSwap));
        bundleCoreDefaultSettings.setProperty("pegBundleEnableHallwayMusicSwap", Boolean.toString(pegBundleEnableHallwayMusicSwap));
        bundleCoreDefaultSettings.setProperty("potionBundleCrossPeglin", Boolean.toString(potionBundleCrossPeglin));
        bundleCoreDefaultSettings.setProperty("terraBundleMainMenu", Boolean.toString(terraBundleMainMenu));
        bundleCoreDefaultSettings.setProperty("terraBundleActMusic", Boolean.toString(terraBundleActMusic));
        bundleCoreDefaultSettings.setProperty("terraBundleBossMusic", Boolean.toString(terraBundleBossMusic));
        bundleCoreDefaultSettings.setProperty("terraBundleFood", Boolean.toString(terraBundleFood));
        bundleCoreDefaultSettings.setProperty("terraBundlePotions", Boolean.toString(terraBundlePotions));
        bundleCoreDefaultSettings.setProperty("terraBundlePeg", Boolean.toString(terraBundlePeg));
        bundleCoreDefaultSettings.setProperty("DonuProgressValue", DonuProgressValue);
        bundleCoreDefaultSettings.setProperty("DonuProgressValueA20", DonuProgressValue);
        bundleCoreDefaultSettings.setProperty("IsHubrisConfig", Boolean.toString(IsHubrisConfig));
        bundleCoreDefaultSettings.setProperty("IsDownfallConfig", Boolean.toString(IsDownfallConfig));
        bundleCoreDefaultSettings.setProperty("IsRuinaConfig", Boolean.toString(IsRuinaConfig));
        bundleCoreDefaultSettings.setProperty("IsShionConfig", Boolean.toString(IsShionConfig));
        bundleCoreDefaultSettings.setProperty("IsDeadConfig", Boolean.toString(IsDeadConfig));
        bundleCoreDefaultSettings.setProperty("FunButton", Boolean.toString(FunButton));
        bundleCoreDefaultSettings.setProperty("ModCreatorButton", Boolean.toString(ModCreatorButton));
        bundleCoreDefaultSettings.setProperty("DecaProgressValue", DecaProgressValue);
        bundleCoreDefaultSettings.setProperty("DecaProgressValueA20", DecaProgressValueA20);
        bundleCoreDefaultSettings.setProperty("AwakenedProgressValue", AwakenedProgressValue);
        bundleCoreDefaultSettings.setProperty("AwakenedProgressValueA20", AwakenedProgressValueA20);
        bundleCoreDefaultSettings.setProperty("TimussyProgressValue", TimussyProgressValue);
        bundleCoreDefaultSettings.setProperty("TimussyProgressValueA20", TimussyProgressValueA20);
        bundleCoreDefaultSettings.setProperty("CollectProgressValue", CollectProgressValue);
        bundleCoreDefaultSettings.setProperty("CollectProgressValueA20", CollectProgressValueA20);
        bundleCoreDefaultSettings.setProperty("questRelicFtue3", Boolean.toString(questRelicFtue3));
        bundleCoreDefaultSettings.setProperty("QuestRelics", Boolean.toString(QuestRelics));
        bundleCoreDefaultSettings.setProperty("ChimericExpansion", Boolean.toString(ChimericExpansion));
        bundleCoreDefaultSettings.setProperty("guardianDefeated", Boolean.toString(guardianDefeated));
        bundleCoreDefaultSettings.setProperty("guardianDefeatedA20", Boolean.toString(guardianDefeatedA20));

        try {
            bundleCoreConfig = new SpireConfig(modID, FILE_NAME, bundleCoreDefaultSettings);
            foodBundleCrossPotions = bundleCoreConfig.getBool("foodBundleCrossPotions");
            foodBundleCrossPeglin = bundleCoreConfig.getBool("foodBundleCrossPeglin");
            pegBundleEnableTitle = bundleCoreConfig.getBool("pegBundleEnableTitle");
            pegBundleEnableMusicSwap = bundleCoreConfig.getBool("pegBundleEnableMusicSwap");
            pegBundleEnableHallwayMusicSwap = bundleCoreConfig.getBool("pegBundleEnableHallwayMusicSwap");
            potionBundleCrossPeglin = bundleCoreConfig.getBool("potionBundleCrossPeglin");
            terraBundleMainMenu = bundleCoreConfig.getBool("terraBundleMainMenu");
            terraBundleActMusic = bundleCoreConfig.getBool("terraBundleActMusic");
            terraBundleBossMusic = bundleCoreConfig.getBool("terraBundleBossMusic");
            terraBundleFood = bundleCoreConfig.getBool("terraBundleFood");
            terraBundlePotions = bundleCoreConfig.getBool("terraBundlePotions");
            terraBundlePeg = bundleCoreConfig.getBool("terraBundlePeg");
            IsHubrisConfig = bundleCoreConfig.getBool("IsHubrisConfig");
            IsDownfallConfig = bundleCoreConfig.getBool("IsDownfallConfig");
            IsRuinaConfig = bundleCoreConfig.getBool("IsRuinaConfig");
            IsShionConfig = bundleCoreConfig.getBool("IsShionConfig");
            IsDeadConfig = bundleCoreConfig.getBool("IsDeadConfig");
            FunButton = bundleCoreConfig.getBool("FunButton");
            ModCreatorButton = bundleCoreConfig.getBool("ModCreatorButton");
            DonuProgressValue = bundleCoreConfig.getString("DonuProgressValue");
            DonuProgressValueA20 = bundleCoreConfig.getString("DonuProgressValueA20");
            DecaProgressValue = bundleCoreConfig.getString("DecaProgressValue");
            DecaProgressValueA20 = bundleCoreConfig.getString("DecaProgressValueA20");
            AwakenedProgressValue = bundleCoreConfig.getString("AwakenedProgressValue");
            AwakenedProgressValueA20 = bundleCoreConfig.getString("AwakenedProgressValueA20");
            TimussyProgressValue = bundleCoreConfig.getString("TimussyProgressValue");
            TimussyProgressValueA20 = bundleCoreConfig.getString("TimussyProgressValueA20");
            CollectProgressValue = bundleCoreConfig.getString("CollectProgressValue");
            CollectProgressValueA20 = bundleCoreConfig.getString("CollectProgressValueA20");
            questRelicFtue3 = bundleCoreConfig.getBool("questRelicFtue3");
            QuestRelics = bundleCoreConfig.getBool("QuestRelics");
            ChimericExpansion = bundleCoreConfig.getBool("ChimericExpansion");
            guardianDefeated = bundleCoreConfig.getBool("guardianDefeated");
            guardianDefeatedA20 = bundleCoreConfig.getBool("guardianDefeatedA20");
        } catch (IOException e) {
            logger.error("BundleCore SpireConfig initialization failed:");
            e.printStackTrace();
        }
    }

    public void SearchForBundles() {
        if (Loader.isModLoaded("bundle_of_food")) {
            CheckForFood = true;
        }
        if (Loader.isModLoaded("bundle_of_potions")) {
            CheckForPotions = true;
        }
        if (Loader.isModLoaded("Bundle_Of_Peglin")) {
            CheckForPeg = true;
        }
        if (Loader.isModLoaded("Bundle_Of_Terra")) {
            CheckForTerra = true;
        }
        if (Loader.isModLoaded("anniv5")) {
            CheckForanniv5 = true;
        }
    }

    public void receiveEditRelics() { //somewhere in the class
        new AutoAdd(modID)
                .packageFilter(BundleRelic.class)
                .any(BundleRelic.class, (info, relic) -> {
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType);
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
        receiveEditConditionalRelics();
    }

    private void receiveEditConditionalRelics() {
        if (Loader.isModLoaded("CardAugments")) {
            BaseMod.addRelic(new JarOfMarmaladeShop(), RelicType.SHARED);
            BaseMod.addRelic(new ChimericGemstoneBoss(), RelicType.SHARED);
            BaseMod.addRelic(new UpdateBoss(), RelicType.SHARED);
            BaseMod.addRelic(new SingularityBoss(), RelicType.SHARED);
            BaseMod.addRelic(new DualityDraughtRare(), RelicType.SHARED);
            BaseMod.addRelic(new BottledModsUncommon(), RelicType.SHARED);
            BaseMod.addRelic(new ModificationKitCommon(), RelicType.SHARED);
            BaseMod.addRelic(new FanMailCommon(), RelicType.SHARED);
            BaseMod.addRelic(new CursedMirrorRare(), RelicType.SHARED);
            BaseMod.addRelic(new BifurkiteBoss(), RelicType.SHARED);
            BaseMod.addRelic(new ModSoupBoss(), RelicType.SHARED);
        }
        BaseMod.addRelic(new DEPRECATED_DarkCore(), RelicType.BLUE);
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID)
                .packageFilter(BundleCard.class)
                .setDefaultSeen(false)
                .cards();
    }

    @Override
    public void receivePostInitialize() {
        if (Loader.isModLoaded("CardAugments")){
            ChimeraLoader.initialiseCardAugments();
        }
        BaseMod.addPotion(LavaPotionForbidden.class, new Color(249 / 255f, 254 / 255f, 106 / 255f, 1), new Color(233 / 255f, 137 / 255f, 78 / 255f, 1), new Color(202 / 255f, 95 / 255f, 101 / 255f, 1), LavaPotionForbidden.POTION_ID);
        BaseMod.addPotion(PotionFireForbidden.class, null, null, null, PotionFireForbidden.POTION_ID);
        SearchForBundles();
        addMonsters();
        countMenuPatchMods();
        AbstractMenuPriority.checkingPriorityMenu();
        if (FunButton && InitialModCount >= 2) {
            randomiseMenuStuff();
        }
        initializeConfig();
    }

    protected void randomiseMenuStuff() {
        if (FunButton) {
            IsHubrisConfig = false;
            IsDownfallConfig = false;
            IsRuinaConfig = false;
            IsShionConfig = false;
            pegBundleEnableTitle = false;
            IsDeadConfig = false;
            terraBundlePeg = false;
            int ChaosBingo = MathUtils.random(InitialModCount - 1);
            ArrayList<String> modIDs = new ArrayList<>(Arrays.asList(
                    "hubris",
                    "downfall",
                    "ruina",
                    "VUPShionMod",
                    "Bundle_Of_Peglin",
                    "DeadCells",
                    "Bundle_Of_Terra"
            ));
            modIDs.removeIf(modId -> !Loader.isModLoaded(modId));
            String Selection = modIDs.get(ChaosBingo);
            if (Selection == null) {
                throw new NullPointerException("Where Menu?");
            }
            switch (Selection) {
                case "hubris": {
                    IsHubrisConfig = true;
                    IsDownfallConfig = false;
                    IsRuinaConfig = false;
                    IsShionConfig = false;
                    pegBundleEnableTitle = false;
                    IsDeadConfig = false;
                    terraBundlePeg = false;
                    break;
                }
                case "downfall": {
                    IsHubrisConfig = false;
                    IsDownfallConfig = true;
                    IsRuinaConfig = false;
                    IsShionConfig = false;
                    pegBundleEnableTitle = false;
                    IsDeadConfig = false;
                    terraBundlePeg = false;
                    try {
                        ReflectionHacks.setPrivateStatic(Class.forName("downfall.downfallMod"), "noMusic", false);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case "ruina": {
                    IsHubrisConfig = false;
                    IsDownfallConfig = false;
                    IsRuinaConfig = true;
                    IsShionConfig = false;
                    pegBundleEnableTitle = false;
                    IsDeadConfig = false;
                    terraBundlePeg = false;
                    try {
                        ReflectionHacks.setPrivateStatic(Class.forName("ruina.RuinaMod"), "disableAltTitleArt", false);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case "VUPShionMod": {
                    IsHubrisConfig = false;
                    IsDownfallConfig = false;
                    IsRuinaConfig = false;
                    IsShionConfig = true;
                    pegBundleEnableTitle = false;
                    IsDeadConfig = false;
                    terraBundlePeg = false;
                    try {
                        ReflectionHacks.setPrivateStatic(Class.forName("VUPShionMod.util.SaveHelper"), "notReplaceTitle", false);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case "Bundle_Of_Peglin": {
                    IsHubrisConfig = false;
                    IsDownfallConfig = false;
                    IsRuinaConfig = false;
                    IsShionConfig = false;
                    pegBundleEnableTitle = true;
                    IsDeadConfig = false;
                    terraBundlePeg = false;
                    try {
                        bundleCoreConfig.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AbstractMenuPriority.priorityMenu = 4;
                    break;
                }
                case "DeadCells": {
                    IsHubrisConfig = false;
                    IsDownfallConfig = false;
                    IsRuinaConfig = false;
                    IsShionConfig = false;
                    pegBundleEnableTitle = false;
                    IsDeadConfig = true;
                    terraBundlePeg = false;
                    break;
                }
                case "Bundle_Of_Terra": {
                    IsHubrisConfig = false;
                    IsDownfallConfig = false;
                    IsRuinaConfig = false;
                    IsShionConfig = false;
                    pegBundleEnableTitle = false;
                    IsDeadConfig = false;
                    terraBundlePeg = true;
                    try {
                        bundleCoreConfig.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AbstractMenuPriority.priorityMenu = 6;
                    break;
                }
                default: {
                    throw new NullPointerException("Valid Main Menu Not Found");
                }
            }
        }
    }

    protected void countMenuPatchMods() {
        if (Loader.isModLoaded("hubris")) {
            InitialModCount++;
        }
        if (Loader.isModLoaded("downfall")) {
            InitialModCount++;
        }
        if (Loader.isModLoaded("ruina")) {
            InitialModCount++;
        }
        if (Loader.isModLoaded("VUPShionMod")) {
            InitialModCount++;
        }
        if (Loader.isModLoaded("Bundle_Of_Peglin")) {
            InitialModCount++;
        }
        if (Loader.isModLoaded("DeadCells")) {
            InitialModCount++;
        }
        if (Loader.isModLoaded("Bundle_Of_Terra")) {
            InitialModCount++;
        }
    }

    protected void initializeConfig() {
        Texture badgeTexture = TextureLoader.getTexture(resourcePath("badge.png"));
        UIStrings configStrings = CardCrawlGame.languagePack.getUIString("bundlecore:ModSettings");
        this.settingsPanel1 = new ModPanel();
        float currentYposition = 740.0F;
        float currentXposition = 360.0F;

        ModLabeledToggleButton foodXPotions = new ModLabeledToggleButton(configStrings.TEXT[0],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("foodBundleCrossPotions"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("foodBundleCrossPotions", button.enabled);
            foodBundleCrossPotions = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentYposition -= 40;

        ModLabeledToggleButton foodXPeg = new ModLabeledToggleButton(configStrings.TEXT[1],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("foodBundleCrossPeglin"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("foodBundleCrossPeglin", button.enabled);
            foodBundleCrossPeglin = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentYposition -= 40;

        ModLabeledToggleButton enableBossMusic = new ModLabeledToggleButton(configStrings.TEXT[3],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("pegBundleEnableMusicSwap"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("pegBundleEnableMusicSwap", button.enabled);
            pegBundleEnableMusicSwap = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentYposition -= 40;

        ModLabeledToggleButton enableHallwayMusic = new ModLabeledToggleButton(configStrings.TEXT[4],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("pegBundleEnableHallwayMusicSwap"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("pegBundleEnableHallwayMusicSwap", button.enabled);
            pegBundleEnableHallwayMusicSwap = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentYposition -= 40;

        ModLabeledToggleButton enablePotionsXPegged = new ModLabeledToggleButton(configStrings.TEXT[5],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("potionBundleCrossPeglin"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("potionBundleCrossPeglin", button.enabled);
            potionBundleCrossPeglin = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentYposition -= 40;

        ModLabeledToggleButton TerrariaActMusic = new ModLabeledToggleButton(configStrings.TEXT[7],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("terraBundleActMusic"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("terraBundleActMusic", button.enabled);
            terraBundleActMusic = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentYposition -= 40;

        ModLabeledToggleButton TerrariaBossMusic = new ModLabeledToggleButton(configStrings.TEXT[8],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("terraBundleBossMusic"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("terraBundleBossMusic", button.enabled);
            terraBundleBossMusic = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentYposition -= 40;

        ModLabeledToggleButton TerrariaFood = new ModLabeledToggleButton(configStrings.TEXT[9],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("terraBundleFood"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("terraBundleFood", button.enabled);
            terraBundleFood = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentYposition -= 40;

        ModLabeledToggleButton TerrariaPotions = new ModLabeledToggleButton(configStrings.TEXT[10],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("terraBundlePotions"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("terraBundlePotions", button.enabled);
            terraBundlePotions = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentYposition -= 40;

        ModLabeledToggleButton TerrariaPeglin = new ModLabeledToggleButton(configStrings.TEXT[11],
                360.0F, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("terraBundlePeg"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("terraBundlePeg", button.enabled);
            terraBundlePeg = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentYposition -= 40;

        ModLabeledToggleButton QuestRelicsButton = new ModLabeledToggleButton(configStrings.TEXT[20],
                360.0F, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("QuestRelics"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("QuestRelics", button.enabled);
            QuestRelics = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentYposition -= 40;

        ModLabeledToggleButton ChimericExpansionButton = new ModLabeledToggleButton(configStrings.TEXT[21],
                360.0F, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("ChimericExpansion"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("ChimericExpansion", button.enabled);
            ChimericExpansion = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //
        /////
        //
        //
        ///// Main Menu Replacements
        //
        //
        /////
        //

        currentYposition = 740.0f;
        currentXposition = 900.0f;

        //#0 - hubris

        ModLabeledToggleButton hubrisMenuButton = new ModLabeledToggleButton(configStrings.TEXT[12],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("IsHubrisConfig"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("IsHubrisConfig", button.enabled);
            IsDownfallConfig = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //#1 - Downfall
        currentYposition -= 40;
        ModLabeledToggleButton downfallMenuButton = new ModLabeledToggleButton(configStrings.TEXT[13],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("IsDownfallConfig"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("IsDownfallConfig", button.enabled);
            IsDownfallConfig = !button.enabled;
            try {
                ReflectionHacks.setPrivateStatic(Class.forName("downfall.downfallMod"), "noMusic", IsDownfallConfig);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                ReflectionHacks.privateStaticMethod(Class.forName("downfall.downfallMod"), "saveData").invoke();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //#2 - Ruina
        currentYposition -= 40;
        ModLabeledToggleButton ruinaMenuButton = new ModLabeledToggleButton(configStrings.TEXT[14],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("IsRuinaConfig"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("IsRuinaConfig", button.enabled);
            IsRuinaConfig = !button.enabled;
            try {
                ReflectionHacks.setPrivateStatic(Class.forName("ruina.RuinaMod"), "disableAltTitleArt", IsRuinaConfig);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                ReflectionHacks.privateStaticMethod(Class.forName("ruina.RuinaMod"), "saveConfig").invoke();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                ReflectionHacks.privateStaticMethod(Class.forName("ruina.RuinaMod"), "saveData").invoke();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //#3 - Shion
        currentYposition -= 40;
        ModLabeledToggleButton shionMenuButton = new ModLabeledToggleButton(configStrings.TEXT[15],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("IsShionConfig"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("IsShionConfig", button.enabled);
            IsShionConfig = !button.enabled;
            try {
                ReflectionHacks.setPrivateStatic(Class.forName("VUPShionMod.util.SaveHelper"), "notReplaceTitle", IsShionConfig);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                ReflectionHacks.privateStaticMethod(Class.forName("VUPShionMod.util.SaveHelper"), "saveSettings").invoke();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //#4 - Bundle Of Peglin
        currentYposition -= 40;
        ModLabeledToggleButton enableTitleScreen = new ModLabeledToggleButton(configStrings.TEXT[2],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("pegBundleEnableTitle"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("pegBundleEnableTitle", button.enabled);
            pegBundleEnableTitle = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //#5 - Deadcells
        currentYposition -= 40;
        ModLabeledToggleButton deadcellsMenuButton = new ModLabeledToggleButton(configStrings.TEXT[16],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("IsDeadConfig"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("IsDeadConfig", button.enabled);
            IsDeadConfig = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //#6 - Bundle Of Terra
        currentYposition -= 40;
        ModLabeledToggleButton terrariaMainMenu = new ModLabeledToggleButton(configStrings.TEXT[6],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("terraBundleMainMenu"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("terraBundleMainMenu", button.enabled);
            terraBundleMainMenu = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //#7 - "Fun Button"
        currentYposition -= 40;
        ModLabeledToggleButton funButtonMenuRandomiser = new ModLabeledToggleButton(configStrings.TEXT[17],
                currentXposition, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, bundleCoreConfig.getBool("FunButton"),
                this.settingsPanel1, label -> {
        }, button -> {
            bundleCoreConfig.setBool("FunButton", button.enabled);
            FunButton = button.enabled;
            try {
                bundleCoreConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        if (BundleCoreMain.CheckForFood && BundleCoreMain.CheckForPotions) {
            settingsPanel1.addUIElement(foodXPotions);
        }
        if (BundleCoreMain.CheckForFood && BundleCoreMain.CheckForPeg) {
            settingsPanel1.addUIElement(foodXPeg);
        }
        if (BundleCoreMain.CheckForPeg) {
            settingsPanel1.addUIElement(enableBossMusic);
            settingsPanel1.addUIElement(enableHallwayMusic);
        }
        if (BundleCoreMain.CheckForPeg && BundleCoreMain.CheckForPotions) {
            settingsPanel1.addUIElement(enablePotionsXPegged);
        }
        if (BundleCoreMain.CheckForTerra) {
            settingsPanel1.addUIElement(TerrariaActMusic);
            settingsPanel1.addUIElement(TerrariaBossMusic);
        }
        if (BundleCoreMain.CheckForTerra && CheckForFood) {
            settingsPanel1.addUIElement(TerrariaFood);
        }
        if (BundleCoreMain.CheckForTerra && CheckForPotions) {
            settingsPanel1.addUIElement(TerrariaPotions);
        }
        if (BundleCoreMain.CheckForTerra && CheckForPeg) {
            settingsPanel1.addUIElement(TerrariaPeglin);
        }

        settingsPanel1.addUIElement(QuestRelicsButton);

        if (Loader.isModLoaded("CardAugments")){
            settingsPanel1.addUIElement(ChimericExpansionButton);
        }

        //0
        if (Loader.isModLoaded("hubris")) {
            settingsPanel1.addUIElement(hubrisMenuButton);
        }
        //1
        if (Loader.isModLoaded("downfall")) {
            settingsPanel1.addUIElement(downfallMenuButton);
        }
        //2
        if (Loader.isModLoaded("ruina")) {
            settingsPanel1.addUIElement(ruinaMenuButton);
        }
        //3
        if (Loader.isModLoaded("VUPShionMod")) {
            settingsPanel1.addUIElement(shionMenuButton);
        }
        //4
        if (BundleCoreMain.CheckForPeg) {
            settingsPanel1.addUIElement(enableTitleScreen);
        }
        //5
        if (Loader.isModLoaded("DeadCells")) {
            settingsPanel1.addUIElement(deadcellsMenuButton);
        }
        //6
        if (BundleCoreMain.CheckForTerra) {
            settingsPanel1.addUIElement(terrariaMainMenu);
        }
        //Final
        if (InitialModCount >= 2) {
            settingsPanel1.addUIElement(funButtonMenuRandomiser);
        }


        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, this.settingsPanel1);
    }

    private static void addMonsters() {
        BaseMod.addMonster(BundleEvent.Three_Lagavulin, () -> new MonsterGroup(new AbstractMonster[]{new LagavulinCloneEnemy(true, -330.0F, 25.0F),
                new LagavulinCloneEnemy(true, -85.0F, 10.0F), new LagavulinCloneEnemy(true, 140.0F, 30.0F)}));
    }

    public static String resourcePath(String file) {
        return resourcesFolder + "/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/relics/" + file;
    }
    public static String makeOrbPath(String file) {
        return resourcesFolder + "/orbs/" + file;
    }
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo) -> {
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(BundleCoreMain.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        } else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }


    // Load localization
    public static String assetPath(String path) {
        return modID + "/" + path;
    }

    private Settings.GameLanguage languageSupport() {
        switch (Settings.language) {
            case ZHS:
                return Settings.language;
//            case KOR:
//                return Settings.language;
        }
        return Settings.GameLanguage.ENG;
    }


    public void receiveEditStrings() {
        Settings.GameLanguage language = languageSupport();
        loadLocStrings(Settings.GameLanguage.ENG);
        if (!language.equals(Settings.GameLanguage.ENG)) {
            loadLocStrings(language);
        }
    }

    private void loadLocStrings(Settings.GameLanguage language) {
        String path = "localization/" + language.toString().toLowerCase() + "/";
        BaseMod.loadCustomStringsFile(CardStrings.class, assetPath(path + "CardStrings.json"));
//      BaseMod.loadCustomStringsFile(CharacterStrings.class, assetPath(path + "CharacterStrings.json"));
//      BaseMod.loadCustomStringsFile(EventStrings.class, assetPath(path + "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class, assetPath(path + "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class, assetPath(path + "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class, assetPath(path + "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class, assetPath(path + "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class, assetPath(path + "UIStrings.json"));
        BaseMod.loadCustomStringsFile(MonsterStrings.class, assetPath(path + "monsters.json"));
        BaseMod.loadCustomStringsFile(TutorialStrings.class, assetPath(path + "TutorialStrings.json"));
    }

    private void loadLocKeywords(Settings.GameLanguage language) {
        String path = "localization/" + language.toString().toLowerCase() + "/";
        Gson gson = new Gson();
        String json = Gdx.files.internal(assetPath(path + "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null)
            for (Keyword keyword : keywords)
                BaseMod.addKeyword("bundlecore", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
    }

    public void receiveEditKeywords() {
        Settings.GameLanguage language = languageSupport();
        loadLocKeywords(Settings.GameLanguage.ENG);
        if (!language.equals(Settings.GameLanguage.ENG))
            loadLocKeywords(language);
    }

    public void receivePostUpdate() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null)
            return;
        if (p.hasRelic(FanMailCommon.ID)) {
            FanMailCommon.fixConcurrentBS3();
        }
        if (p.hasRelic(TinyDewUncommon.ID)) {
            TinyDewUncommon.fixConcurrentBS4();
        }
    }










    public static void onGenerateCardMidcombat(AbstractCard c)
    {
        GFL.GAP().relics.stream().filter(r -> r instanceof OnCreateMidCombatCards).forEach(r -> ((OnCreateMidCombatCards)r).onCreateCard(c));
        GFL.GAP().powers.stream().filter(r -> r instanceof OnCreateMidCombatCards).forEach(r -> ((OnCreateMidCombatCards)r).onCreateCard(c));
        GFL.GAP().hand.group.stream().filter(card -> card instanceof OnCreateMidCombatCards).forEach(card -> ((OnCreateMidCombatCards)card).onCreateCardCard(c));
        GFL.GAP().discardPile.group.stream().filter(card -> card instanceof OnCreateMidCombatCards).forEach(card -> ((OnCreateMidCombatCards)card).onCreateCardCard(c));
        GFL.GAP().drawPile.group.stream().filter(card -> card instanceof OnCreateMidCombatCards).forEach(card -> ((OnCreateMidCombatCards)card).onCreateCardCard(c));
        AbstractDungeon.getMonsters().monsters.stream().filter(mon -> !mon.isDeadOrEscaped()).forEach(m -> m.powers.stream().filter(pow -> pow instanceof OnCreateMidCombatCards).forEach(pow -> ((OnCreateMidCombatCards)pow).onCreateCard(c)));
        GFL.GAP().blights.stream().filter(r -> r instanceof OnCreateMidCombatCards).forEach(r -> ((OnCreateMidCombatCards)r).onCreateCardCard(c));
        if (c instanceof OnCreateMidCombatCards)
        {
            ((OnCreateMidCombatCards)c).onCreateCard(c);
        }
    }

    @Override
    public void receivePostDungeonInitialize() {
//        QuestRelicPoolMaker.initializePool();
    }


    @Override
    public void receiveStartGame() {
        if (!CardCrawlGame.loadingSave){
            CurrentRunsFourthBossRelicStorage = null;
        }
    }

}