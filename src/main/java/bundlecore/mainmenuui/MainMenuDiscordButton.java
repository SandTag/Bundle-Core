package bundlecore.mainmenuui;

import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Adds the discord button to the main menu.
 */
@Dont_Use_This_Externally
public class MainMenuDiscordButton {
    private static final MainMenuAd invite = new MainMenuAd();
    private static class MainMenuAdInfo {
        private String text;
        private String text2;
        private String text3;
        private String text4;
        private String text5;
        private String textheader;
        private String url;
        private Texture img;
        private MainMenuAdInfo(String text, String text2, String text3, String text4, String text5, String textheader, String url, Texture img) {
            this.text = text;
            this.text2 = text2;
            this.text3 = text3;
            this.text4 = text4;
            this.text5 = text5;
            this.textheader = textheader;
            this.url = url;
            this.img = img;
        }
    }

    private static final ArrayList<MainMenuAdInfo> inviter = new ArrayList<>();
    static {
        inviter.add(new MainMenuAdInfo("", "", "", "", "", "", "https://discord.gg/3GUDmbdCDG", TextureLoader.getTexture("bundlecore/discbutton.png")));
        invite.current = inviter.get(0);
    }

    @SpirePatch(clz = TitleBackground.class, method = "render")
    public static class RenderPatch {
        @SpirePostfixPatch
        public static void renderAd(TitleBackground instance, SpriteBatch sb) {
            MainMenuDiscordButton.invite.render(sb);
        }
    }
    @SpirePatch(clz = TitleBackground.class, method = "update")
    public static class UpdatePatch {
        @SpirePostfixPatch
        public static void updateAd(TitleBackground instance) {
            MainMenuDiscordButton.invite.update();
        }
    }

    private static class MainMenuAd {
        private static final Texture tex = TextureLoader.getTexture("bundlecore/discbutton.png");
        private MainMenuDiscordButton.MainMenuAdInfo current;
        public final Hitbox hb;
        private Color tint = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        private float xPos = Settings.WIDTH * 0.925F;
        private float yPos = (float)(Settings.HEIGHT/10) - (float)(Settings.HEIGHT/9.5);
        private float angle = 0.0F;
        private float scale = 0.5F;
        private float adjustedWidth = tex.getWidth() * Settings.scale * this.scale;
        private float adjustedHeight = tex.getHeight() * Settings.scale * this.scale;
        private ArrayList<AbstractGameEffect> sparkles;

        public void render(SpriteBatch sb) {
            sb.setColor(Color.WHITE.cpy());
            sb.draw(this.current.img, this.xPos, this.yPos, 0.0F, 0.0F, this.current.img.getWidth(), this.current.img.getHeight(), Settings.scale, Settings.scale, this.angle, 0, 0, this.current.img.getWidth(), this.current.img.getHeight(), false, false);
            if (this.tint.a > 0.0F) {
                sb.setBlendFunction(770, 1);
                sb.setColor(this.tint);
                sb.draw(this.current.img, this.xPos, this.yPos, 0.0F, 0.0F, this.current.img.getWidth(), this.current.img.getHeight(), Settings.scale, Settings.scale, this.angle, 0, 0, this.current.img.getWidth(), this.current.img.getHeight(), false, false);
                sb.setBlendFunction(770, 771);
            }
            sb.setColor(Color.WHITE.cpy());
            sb.setColor(Color.WHITE.cpy());
            this.hb.render(sb);
        }

        public MainMenuAd() {
            this.hb = new Hitbox(this.xPos+18, this.yPos+18, (int)(this.adjustedWidth*1.5), (int)(this.adjustedHeight*1.5));
        }

        public void update() {
            if (CardCrawlGame.mainMenuScreen.screen != MainMenuScreen.CurScreen.MAIN_MENU)
                return;
            this.hb.update();
            if (this.hb.hovered) {
                this.tint.a = 0.25F;
                if (InputHelper.justClickedLeft) {
                    CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
                    try {
                        Desktop.getDesktop().browse(new URI(this.current.url));
                    } catch (IOException|java.net.URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                this.tint.a = 0.0F;
            }
        }
    }
}
