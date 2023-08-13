package bundlecore.potions;

import basemod.ReflectionHacks;
import bundlecore.BundleCoreMain;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.FlashPotionEffect;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * One of the most useful tools currently in bundlecore, it allows the creation of a potion that renders an image instead of a bottle.
 */
public abstract class AbstractCustomImagePotion extends AbstractForbiddenPotion {
    protected Texture potionImg;

    protected Texture potionOutlineImg;

    public Color potionImageColor = Color.WHITE.cpy();

    /**
     * A potion type that can generate with an image rather than a potion bottle.
     * @param id - the full ID of the potion.
     * @param sourcemod - The string name of the assets folder for your mod.
     * @param potionImg - [CLASSNAME].class.getSimpleName() + ".png"
     * @param rarity - Common, Uncommon, Rare or Placeholder
     * @param ForbiddenValue - True when the rarity is Placeholder
     */
    public AbstractCustomImagePotion(String id, String sourcemod, String potionImg, AbstractPotion.PotionRarity rarity, boolean ForbiddenValue) {
        super(id, rarity, AbstractPotion.PotionSize.S);
        this.potionImg = ImageMaster.loadImage(sourcemod + "/potions/" + potionImg);
        this.potionOutlineImg = ImageMaster.loadImage(sourcemod + "/potions/outline/" + potionImg);
        IsForbiddenAfter = ForbiddenValue;
    }

    @Dont_Use_This_Externally
    public AbstractCustomImagePotion(String id, String potionImg, AbstractPotion.PotionRarity rarity, boolean ForbiddenValue) {
        super(id, rarity, AbstractPotion.PotionSize.S);
        this.potionImg = ImageMaster.loadImage(BundleCoreMain.assetPath("") + "potions/" + potionImg);
        this.potionOutlineImg = ImageMaster.loadImage(BundleCoreMain.assetPath("") + "potions/outline/" + potionImg);
        IsForbiddenAfter = ForbiddenValue;
    }

    public void render(SpriteBatch sb) {
        if (this.potionImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(this.potionImageColor);
            sb.draw(this.potionImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }
        ArrayList<FlashPotionEffect> effects = ReflectionHacks.getPrivate(this, AbstractPotion.class, "effect");
        for (FlashPotionEffect e : effects)
            e.render(sb, this.posX, this.posY);
        if (this.hb != null)
            this.hb.render(sb);
    }

    public void renderLightOutline(SpriteBatch sb) {
        if (this.potionOutlineImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }
    }

    public void renderOutline(SpriteBatch sb) {
        if (this.potionOutlineImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(Settings.HALF_TRANSPARENT_BLACK_COLOR);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }
    }

    public void renderOutline(SpriteBatch sb, Color c) {
        if (this.potionOutlineImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(c);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }
    }

    public void renderShiny(SpriteBatch sb) {}

    public void labRender(SpriteBatch sb) {
        try {
            Method updateFlash = AbstractPotion.class.getDeclaredMethod("updateFlash");
            updateFlash.setAccessible(true);
            Method updateEffect = AbstractPotion.class.getDeclaredMethod("updateEffect");
            updateEffect.setAccessible(true);
            updateFlash.invoke(this);
            updateEffect.invoke(this);
        } catch (NoSuchMethodException|IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
        }
        if (this.hb.hovered) {
            TipHelper.queuePowerTips(150.0F * Settings.scale, 800.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }
        renderOutline(sb, this.labOutlineColor);
        render(sb);
    }

    public void shopRender(SpriteBatch sb) {
        try {
            Method updateFlash = AbstractPotion.class.getDeclaredMethod("updateFlash");
            updateFlash.setAccessible(true);
            Method updateEffect = AbstractPotion.class.getDeclaredMethod("updateEffect");
            updateEffect.setAccessible(true);
            updateFlash.invoke(this);
            updateEffect.invoke(this);
        } catch (NoSuchMethodException|IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
        }
        if (this.hb.hovered) {
            TipHelper.queuePowerTips(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY + 50.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }
        renderOutline(sb);
        render(sb);
    }

}
