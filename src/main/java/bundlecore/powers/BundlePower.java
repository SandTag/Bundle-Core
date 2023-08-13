package bundlecore.powers;

import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import java.util.Iterator;
import static bundlecore.BundleCoreMain.*;

public abstract class BundlePower extends AbstractPower {
    private ArrayList<AbstractGameEffect> effect = new ArrayList<>();
    private Color color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    private static PowerStrings getPowerStrings(String ID){return CardCrawlGame.languagePack.getPowerStrings(ID);}
    protected AbstractCreature source;
    protected String[] DESCRIPTIONS;
    @Dont_Use_This_Externally
    public BundlePower(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
        this(id, powerType, isTurnBased, owner, null, amount);
    }
    @Dont_Use_This_Externally
    public BundlePower(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount) {
        this(id, powerType, isTurnBased, owner, source, amount, true);
    }
    @Dont_Use_This_Externally
    public BundlePower(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount, boolean initDescription) {
        this(id, powerType, isTurnBased, owner, source, amount, initDescription, true);
    }
    @Dont_Use_This_Externally
    public BundlePower(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount, boolean initDescription, boolean loadImage) {
        this.ID = id;
        this.isTurnBased = isTurnBased;

        PowerStrings strings = getPowerStrings(this.ID);
        this.name = strings.NAME;
        this.DESCRIPTIONS = strings.DESCRIPTIONS;

        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.type = powerType;

        if (loadImage)
        {
            String unPrefixed = id.substring(id.indexOf(":") + 1);
            Texture normalTexture = TextureLoader.getPowerTexture(unPrefixed);
            Texture hiDefImage = TextureLoader.getHiDefPowerTexture(unPrefixed);
            if (hiDefImage != null)
            {
                region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
                if (normalTexture != null)
                    region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
            }
            else if (normalTexture != null)
            {
                this.img = normalTexture;
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
            }
        }

        if (initDescription)
            this.updateDescription();
    }


    /**
     * A simple power class.
     * @param thisMod The string name of the resources folder for the mod, it should contain "powers" and be a "png".
     * @throws GdxRuntimeException If it cant find the image, it throws.
     */
    public BundlePower(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount, String thisMod) throws GdxRuntimeException {
        this.ID = id;
        this.isTurnBased = isTurnBased;
        PowerStrings strings = getPowerStrings(this.ID);
        this.name = strings.NAME;
        this.DESCRIPTIONS = strings.DESCRIPTIONS;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.type = powerType;
        this.updateDescription();
        if (thisMod != null)
        {
            String unPrefixed = id.substring(id.indexOf(":") + 1);
            Texture normalTexture = new Texture (thisMod + "/powers/" + unPrefixed + ".png");
            Texture hiDefImage = new Texture (thisMod + "/powers/large/" + unPrefixed + ".png");
            if (hiDefImage != null)
            {
                region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
                if (normalTexture != null)
                    region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
            }
            else if (normalTexture != null)
            {
                this.img = normalTexture;
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
            }
            logger.info("Loaded texture " + unPrefixed);
        }
        else throw new NullPointerException("Mod Source Cannot be \"null\"");
    }

    protected static AbstractPlayer pl(){
        return AbstractDungeon.player;
    }
    protected void addToQ(AbstractGameEffect action){
        AbstractDungeon.effectList.add(action);
    }
    protected void addToList(AbstractGameEffect action){
        AbstractDungeon.effectsQueue.add(action);
    }

}