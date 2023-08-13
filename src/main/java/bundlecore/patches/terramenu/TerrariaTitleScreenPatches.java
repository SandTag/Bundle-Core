package bundlecore.patches.terramenu;

import basemod.ReflectionHacks;
import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.patches.AbstractMenuPriority;
import bundlecore.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.scenes.TitleCloud;
import com.megacrit.cardcrawl.vfx.scene.LogoFlameEffect;
import com.megacrit.cardcrawl.vfx.scene.TitleDustEffect;
import java.util.ArrayList;

/**
 * Can be used as an example of what is required to change the main menu theme.
 */
@Dont_Use_This_Externally
public class TerrariaTitleScreenPatches extends AbstractMenuPriority {
    public static final String assetPath = BundleCoreMain.makeMenuPath("terra/");
    private static String getAsset(String assetName) {
        return assetPath + assetName;
    }

    @SpirePatch(clz = TitleBackground.class, method = "<ctor>")
    public static class TitleBackgroundReplacementPatch {
        @SpirePostfixPatch
        public static void TerrariaTitleScreenPatches(TitleBackground __instance) {
            if (priorityMenu == 6) {
                int MenuBingo = MathUtils.random(99);
                if (MenuBingo == 69) {
                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("terra/title2.atlas")));
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
                    ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
                    ArrayList<TitleCloud> newMidClouds = new ArrayList<>();
                    int i;
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("topCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("midCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "topClouds", newTopClouds);
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "midClouds", newMidClouds);
                    setLogo(__instance);
                }
                else if (MenuBingo <= 9){
                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("terra/Corruption.atlas")));
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
                    ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
                    ArrayList<TitleCloud> newMidClouds = new ArrayList<>();
                    int i;
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("topCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("midCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "topClouds", newTopClouds);
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "midClouds", newMidClouds);
                    setLogo(__instance);
                }
                else if (MenuBingo <= 19){
                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("terra/Crimson.atlas")));
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
                    ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
                    ArrayList<TitleCloud> newMidClouds = new ArrayList<>();
                    int i;
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("topCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("midCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "topClouds", newTopClouds);
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "midClouds", newMidClouds);
                    setLogo(__instance);
                }
                else if (MenuBingo <= 29){
                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("terra/Hallow.atlas")));
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
                    ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
                    ArrayList<TitleCloud> newMidClouds = new ArrayList<>();
                    int i;
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("topCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("midCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "topClouds", newTopClouds);
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "midClouds", newMidClouds);
                    setLogo(__instance);
                }
                else if (MenuBingo <= 39){
                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("terra/Jungle.atlas")));
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
                    ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
                    ArrayList<TitleCloud> newMidClouds = new ArrayList<>();
                    int i;
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("topCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("midCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "topClouds", newTopClouds);
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "midClouds", newMidClouds);
                    setLogo(__instance);
                }
                else if (MenuBingo <= 49){
                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("terra/Mushroom.atlas")));
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
                    ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
                    ArrayList<TitleCloud> newMidClouds = new ArrayList<>();
                    int i;
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("topCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("midCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "topClouds", newTopClouds);
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "midClouds", newMidClouds);
                    setLogo(__instance);
                }
                else if (MenuBingo <= 59){
                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("terra/Ocean.atlas")));
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
                    ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
                    ArrayList<TitleCloud> newMidClouds = new ArrayList<>();
                    int i;
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("topCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("midCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "topClouds", newTopClouds);
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "midClouds", newMidClouds);
                    setLogo(__instance);
                }
                else if (MenuBingo <= 69){
                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("terra/Sand.atlas")));
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
                    ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
                    ArrayList<TitleCloud> newMidClouds = new ArrayList<>();
                    int i;
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("topCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("midCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "topClouds", newTopClouds);
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "midClouds", newMidClouds);
                    setLogo(__instance);
                }
                else if (MenuBingo <= 79){
                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("terra/Snow.atlas")));
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
                    ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
                    ArrayList<TitleCloud> newMidClouds = new ArrayList<>();
                    int i;
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("topCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("midCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "topClouds", newTopClouds);
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "midClouds", newMidClouds);
                    setLogo(__instance);
                }
                else if (MenuBingo <= 89){
                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("terra/Underworld.atlas")));
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
                    ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
                    ArrayList<TitleCloud> newMidClouds = new ArrayList<>();
                    int i;
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("topCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("midCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "topClouds", newTopClouds);
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "midClouds", newMidClouds);
                    setLogo(__instance);
                }
                else{
                    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("terra/title.atlas")));
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                    TerrariaTitleScreenPatches.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
                    ArrayList<TitleCloud> newTopClouds = new ArrayList<>();
                    ArrayList<TitleCloud> newMidClouds = new ArrayList<>();
                    int i;
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("topCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    for (i = 0; i < 1; i++)
                        newTopClouds.add(new TitleCloud(atlas
                                .findRegion("midCloud" + (i + 1)),
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale,
                                MathUtils.random(10000.0F, 10000.0F) * Settings.scale));
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "topClouds", newTopClouds);
                    ReflectionHacks.setPrivate(__instance, TitleBackground.class, "midClouds", newMidClouds);
                    setLogo(__instance);
                }
            }
        }
    }

    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class MusicReplacerMainMenu {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, MainMusic __instance, String key) {
            if (priorityMenu == 6) {
                if (key.equals("MENU"))
                    return MainMusic.newMusic("bundlecore/music/terra/MenuTheme.mp3");
            }
            return __result;
        }
    }

    private static void setTitleBackgroundAtlasRegion(TitleBackground menu, TextureAtlas newAtlas, String classVarName, String srcRegionName) {
        if (priorityMenu == 6) {
            ReflectionHacks.setPrivate(menu, TitleBackground.class, classVarName, newAtlas.findRegion(srcRegionName));
        }
    }

    private static void setLogo(TitleBackground menu) {
        Texture empty = TextureLoader.getTexture(getAsset("logo.png"));
        if (priorityMenu == 6) {
            ReflectionHacks.setPrivate(menu, TitleBackground.class, "titleLogoImg", empty);
        }
    }

    @SpirePatch2(clz = LogoFlameEffect.class, method = "render", paramtypez = {SpriteBatch.class, float.class, float.class})
    public static class GoodbyeFlames {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(LogoFlameEffect __instance, SpriteBatch sb, float x, float y) {
            if (priorityMenu == 6) {
                return SpireReturn.Return(null);
            } else return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = TitleDustEffect.class, method = "render", paramtypez = {SpriteBatch.class, float.class, float.class})
    public static class GoodbyeStupidDustyStuff {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TitleDustEffect __instance, SpriteBatch sb, float srcX, float srcY) {
            if (priorityMenu == 6) {
                return SpireReturn.Return(null);
            } else return SpireReturn.Continue();
        }
    }

}