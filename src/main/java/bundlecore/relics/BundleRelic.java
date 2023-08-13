package bundlecore.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import bundlecore.util.TextureLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import static bundlecore.BundleCoreMain.relicPath;

public abstract class BundleRelic extends CustomRelic {
    public AbstractCard.CardColor pool = null;
    public RelicType relicType = RelicType.SHARED;
    protected String imageName;
    public String originLocationE;
    public String requiredMod = null;
    public boolean canUnlock = false;

    //for internal character specific relics
    @Dont_Use_This_Externally
    public BundleRelic(String id, String imageName, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx) {
        super(id, TextureLoader.getTexture(relicPath(imageName + ".png")), tier, sfx);
        setPool(pool);
        setPrice(tier);
        this.imageName = imageName;
        loadOutlineInternal();
    }

    //for character effect relics that have dependancies
    @Dont_Use_This_Externally
    public BundleRelic(String id, String imageName, String requiredModID, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx) {
        super(id, TextureLoader.getTexture(relicPath(imageName + ".png")), tier, sfx);
        this.requiredMod = requiredModID;
        setPool(pool);
        setPrice(tier);
        this.imageName = imageName;
        loadOutlineInternal();
    }

    // for internal standard relics
    @Dont_Use_This_Externally
    public BundleRelic(String id, String imageName, RelicTier tier, LandingSound sfx) {
        super(id, TextureLoader.getTexture(relicPath(imageName + ".png")), tier, sfx);
        setPrice(tier);
        this.imageName = imageName;
        loadOutlineInternal();
    }

    // for internal standard relics that require other mods
    @Dont_Use_This_Externally
    public BundleRelic(String id, String imageName, String requiredModID, RelicTier tier, LandingSound sfx) {
        super(id, TextureLoader.getTexture(relicPath(imageName + ".png")), tier, sfx);
        this.requiredMod = requiredModID;
        setPrice(tier);
        this.imageName = imageName;
        loadOutlineInternal();
    }
    protected void loadOutlineInternal() {
        outlineImg = TextureLoader.getTextureNull(relicPath(imageName + "Outline.png"));
        if (outlineImg == null)
            outlineImg = img;
    }





    //for external character specific relics

    /**
     * For character specific relics.
     * @param originLocation Your mods resource folder name.
     */
    public BundleRelic(String id, String imageName, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx, String originLocation) {
        super(id, TextureLoader.getTexture(originLocation+(imageName + ".png")), tier, sfx);
        setPool(pool);
        setPrice(tier);
        this.imageName = imageName;
        originLocationE = originLocation;
        loadOutlineExternal(originLocationE);
    }

    /**
     * For character specific relics that can be enabled for all characters.
     * @param canUnlocked This should be fetched by a config bool in your main mod file.
     * @param originLocation Your mods resource folder name.
     */
    public BundleRelic(String id, String imageName, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx, String originLocation, boolean canUnlocked) {
        super(id, TextureLoader.getTexture(originLocation+(imageName + ".png")), tier, sfx);
        setPool(pool);
        setPrice(tier);
        this.imageName = imageName;
        originLocationE = originLocation;
        this.canUnlock = canUnlocked;
        loadOutlineExternal(originLocationE);
    }

    /**
     * For global pool relics.
     * @param originLocation Your mods resource folder name.
     */
    public BundleRelic(String id, String imageName, RelicTier tier, LandingSound sfx, String originLocation) {
        super(id, TextureLoader.getTexture(originLocation+(imageName + ".png")), tier, sfx);
        setPrice(tier);
        this.imageName = imageName;
        originLocationE = originLocation;
        loadOutlineExternal(originLocationE);
    }

    /**
     * For global pool relics that require other mods to be loaded (Soft Dependencies).
     * @param originLocation Your mods resource folder name.
     */
    public BundleRelic(String id, String imageName, String requiredModID, RelicTier tier, LandingSound sfx, String originLocation) {
        super(id, TextureLoader.getTexture(originLocation+(imageName + ".png")), tier, sfx);
        this.requiredMod = requiredModID;
        setPrice(tier);
        this.imageName = imageName;
        originLocationE = originLocation;
        loadOutlineExternal(originLocationE);
    }

    protected void loadOutlineExternal(String originLocation) {
        outlineImg = TextureLoader.getTextureNull(originLocation+(imageName + "Outline.png"));
        if (outlineImg == null) {
            outlineImg = img;
        }
    }

    @Override
    public void loadLargeImg() {
        if (largeImg == null) {
            if (originLocationE != null){
                this.largeImg = ImageMaster.loadImage(originLocationE+("large/" + imageName + ".png"));
                return;
            }
            else {
                this.largeImg = ImageMaster.loadImage(relicPath("large/" + imageName + ".png"));
            }
        }
    }

    private void setPool(AbstractCard.CardColor pool) {
        switch (pool) { //Basegame pools are handled differently
            case RED:
                relicType = RelicType.RED;
                break;
            case GREEN:
                relicType = RelicType.GREEN;
                break;
            case BLUE:
                relicType = RelicType.BLUE;
                break;
            case PURPLE:
                relicType = RelicType.PURPLE;
                break;
            default:
                this.pool = pool;
                break;
        }
    }

    private void setPrice(AbstractRelic.RelicTier Tier) {
        switch (Tier) {
            //Bundles manually handle relic cost to ensure boss relics can be purchased,
            //with mods that enable the purchasing of such relics in shops.
            //For reference, basegame uses a cost of 999.
            case COMMON:
                cost = 134;
                break;
            case UNCOMMON:
                cost = 223;
                break;
            case RARE:
                cost = 267;
                break;
            case BOSS:
                cost = 555;
                break;
            case SHOP:
                cost = 126;
                break;
            case DEPRECATED:
                cost = 1337;
                break;
            default:
                cost = 65536;
                break;
        }
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

    public void texFetch(String Pathing){}

    public void sPulse() {
        this.pulse = false;
    }

}