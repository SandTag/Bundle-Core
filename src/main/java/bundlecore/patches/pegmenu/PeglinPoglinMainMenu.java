package bundlecore.patches.pegmenu;

import basemod.ReflectionHacks;
import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.patches.AbstractMenuPriority;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.scenes.TitleCloud;
import com.megacrit.cardcrawl.vfx.scene.LogoFlameEffect;
import com.megacrit.cardcrawl.vfx.scene.TitleDustEffect;
import bundlecore.util.TextureLoader;
import java.util.ArrayList;

/**
 * Can be used as an example of what is required to change the main menu theme.
 */
@Dont_Use_This_Externally
public class PeglinPoglinMainMenu extends AbstractMenuPriority {

    public static final String assetPath = BundleCoreMain.makeMenuPath("peg/");
    private static String getAsset(String assetName) {
        return assetPath + assetName;
    }

    @SpirePatch(clz = TitleBackground.class, method = "<ctor>")
    public static class TitleBackgroundReplacementPatch {
        @SpirePostfixPatch
        public static void PeglinPoglinMainMenu(TitleBackground __instance) {
                if (priorityMenu == 4) {
                TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(BundleCoreMain.makeMenuPath("peg/title.atlas")));
                PeglinPoglinMainMenu.setTitleBackgroundAtlasRegion(__instance, atlas, "sky", "jpg/sky");
                PeglinPoglinMainMenu.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Bot", "mg3Bot");
                PeglinPoglinMainMenu.setTitleBackgroundAtlasRegion(__instance, atlas, "mg3Top", "mg3Top");
                PeglinPoglinMainMenu.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow", "mg3TopGlow1");
                PeglinPoglinMainMenu.setTitleBackgroundAtlasRegion(__instance, atlas, "topGlow2", "mg3TopGlow2");
                PeglinPoglinMainMenu.setTitleBackgroundAtlasRegion(__instance, atlas, "botGlow", "mg3BotGlow");
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

    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class MusicReplacerMainMenu {
        @SpirePostfixPatch
        public static Music Postfix(Music __result, MainMusic __instance, String key) {
            if (priorityMenu == 4) {
                if (key.equals("MENU"))
                    return MainMusic.newMusic("bundlecore/music/peg/PegMenu.mp3");
            }
            return __result;
        }
    }

    private static void setTitleBackgroundAtlasRegion(TitleBackground menu, TextureAtlas newAtlas, String classVarName, String srcRegionName) {
        if (priorityMenu == 4) {
            ReflectionHacks.setPrivate(menu, TitleBackground.class, classVarName, newAtlas.findRegion(srcRegionName));
        }
    }

    private static void setLogo(TitleBackground menu) {
        Texture empty = TextureLoader.getTexture(getAsset("logo.png"));
        if (priorityMenu == 4) {
            ReflectionHacks.setPrivate(menu, TitleBackground.class, "titleLogoImg", empty);
        }
    }

    @SpirePatch2(clz = LogoFlameEffect.class, method = "render", paramtypez = {SpriteBatch.class, float.class, float.class})
    public static class GoodbyeFlames {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(LogoFlameEffect __instance, SpriteBatch sb, float x, float y) {
            if (priorityMenu == 4) {
                return SpireReturn.Return(null);
            } else return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = TitleDustEffect.class, method = "render", paramtypez = {SpriteBatch.class, float.class, float.class})
    public static class GoodbyeStupidDustyStuff {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TitleDustEffect __instance, SpriteBatch sb, float srcX, float srcY) {
            if (priorityMenu == 4) {
                return SpireReturn.Return(null);
            } else return SpireReturn.Continue();
        }
    }

}