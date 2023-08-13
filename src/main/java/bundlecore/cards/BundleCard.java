package bundlecore.cards;

import basemod.abstracts.CustomCard;
import bundlecore.BundleCoreMain;
import bundlecore.util.CardInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import static bundlecore.BundleCoreMain.makeID;
import static bundlecore.util.TextureLoader.getCardTextureString;
import static bundlecore.util.TextureLoader.getExternalCardTextureString;

/**
 * This entire abstract needs re-written from the ground up, its confusing,
 * harder to use than desired and cannot render in external projects.
 */
@Deprecated
public abstract class BundleCard extends CustomCard {
    public CardStrings cardStrings;
    public boolean upgradesDescription;
    public int baseCost;
    public boolean upgradeCost;
    public boolean upgradeDamage;
    public boolean upgradeBlock;
    public boolean upgradeMagic;
    public int costUpgrade;
    public int damageUpgrade;
    public int blockUpgrade;
    public int magicUpgrade;
    public boolean baseExhaust = false;
    public boolean upgExhaust = false;
    public boolean baseEthereal = false;
    public boolean upgEthereal = false;
    public boolean baseInnate = false;
    public boolean upgInnate = false;
    public boolean baseRetain = false;
    public boolean upgRetain = false;

    public BundleCard(CardInfo cardInfo) {
        this(cardInfo.baseId, cardInfo.baseCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, cardInfo.cardColor);
    }
    public BundleCard(CardInfo cardInfo, boolean upgradesDescription)
    {
        this(cardInfo.baseId, cardInfo.baseCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, cardInfo.cardColor, upgradesDescription);
    }


    //Internal
    public BundleCard(String baseID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color)
    {
        super(makeID(baseID), "", getCardTextureString(baseID, cardType), cost, "", cardType, color, rarity, target);
        loadStrings();
        this.baseCost = cost;
        this.upgradesDescription = cardStrings.UPGRADE_DESCRIPTION != null;
        this.upgradeCost = false;
        this.upgradeDamage = false;
        this.upgradeBlock = false;
        this.upgradeMagic = false;
        this.costUpgrade = cost;
        this.damageUpgrade = 0;
        this.blockUpgrade = 0;
        this.magicUpgrade = 0;
        initializeTitle();
        initializeDescription();
    }

    public BundleCard(String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, boolean upgradesDescription)
    {
        super(makeID(cardName), "", getCardTextureString(cardName, cardType), cost, "", cardType, color, rarity, target);
        loadStrings();
        this.baseCost = cost;
        this.upgradesDescription = upgradesDescription;
        this.upgradeCost = false;
        this.upgradeDamage = false;
        this.upgradeBlock = false;
        this.upgradeMagic = false;
        this.costUpgrade = cost;
        this.damageUpgrade = 0;
        this.blockUpgrade = 0;
        this.magicUpgrade = 0;
        initializeTitle();
        initializeDescription();
    }

    // External
    public BundleCard(CardInfo cardInfo, String OriginLocation) {
        this(cardInfo.baseId, cardInfo.baseCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, cardInfo.cardColor, OriginLocation);
    }
    public BundleCard(CardInfo cardInfo, boolean upgradesDescription, String OriginLocation)
    {
        this(cardInfo.baseId, cardInfo.baseCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, cardInfo.cardColor, upgradesDescription, OriginLocation);
    }

    public BundleCard(String baseID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, String OriginLocation)
    {
        super(makeID(baseID), "", getExternalCardTextureString(baseID, cardType, OriginLocation), cost, "", cardType, color, rarity, target);
        loadStrings();
        this.baseCost = cost;
        this.upgradesDescription = cardStrings.UPGRADE_DESCRIPTION != null;
        this.upgradeCost = false;
        this.upgradeDamage = false;
        this.upgradeBlock = false;
        this.upgradeMagic = false;
        this.costUpgrade = cost;
        this.damageUpgrade = 0;
        this.blockUpgrade = 0;
        this.magicUpgrade = 0;
        initializeTitle();
        initializeDescription();
    }
    public BundleCard(String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, boolean upgradesDescription, String OriginLocation)
    {
        super(makeID(cardName), "", getExternalCardTextureString(cardName, cardType, OriginLocation), cost, "", cardType, color, rarity, target);
        loadStrings();
        this.baseCost = cost;
        this.upgradesDescription = upgradesDescription;
        this.upgradeCost = false;
        this.upgradeDamage = false;
        this.upgradeBlock = false;
        this.upgradeMagic = false;
        this.costUpgrade = cost;
        this.damageUpgrade = 0;
        this.blockUpgrade = 0;
        this.magicUpgrade = 0;
        initializeTitle();
        initializeDescription();
    }

















    private void loadStrings() {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.originalName = cardStrings.NAME;
        this.name = originalName;
    }

    //Methods meant for constructor use
    protected final void setDamage(int damage)
    {
        this.setDamage(damage, 0);
    }
    protected final void setBlock(int block)
    {
        this.setBlock(block, 0);
    }
    protected final void setMagic(int magic)
    {
        this.setMagic(magic, 0);
    }
    protected final void setCostUpgrade(int costUpgrade)
    {
        this.costUpgrade = costUpgrade;
        this.upgradeCost = true;
    }
    protected final void setExhaust(boolean exhaust) { this.setExhaust(exhaust, exhaust); }
    protected final void setEthereal(boolean ethereal) { this.setEthereal(ethereal, ethereal); }
    protected final void setInnate(boolean innate) {this.setInnate(innate, innate); }
    protected final void setSelfRetain(boolean retain) {this.setSelfRetain(retain, retain); }

    protected final void setDamage(int damage, int damageUpgrade)
    {
        this.baseDamage = this.damage = damage;
        if (damageUpgrade != 0)
        {
            this.upgradeDamage = true;
            this.damageUpgrade = damageUpgrade;
        }
    }
    protected final void setBlock(int block, int blockUpgrade)
    {
        this.baseBlock = this.block = block;
        if (blockUpgrade != 0)
        {
            this.upgradeBlock = true;
            this.blockUpgrade = blockUpgrade;
        }
    }
    protected final void setMagic(int magic, int magicUpgrade)
    {
        this.baseMagicNumber = this.magicNumber = magic;
        if (magicUpgrade != 0)
        {
            this.upgradeMagic = true;
            this.magicUpgrade = magicUpgrade;
        }
    }
    protected final void setExhaust(boolean baseExhaust, boolean upgExhaust)
    {
        this.baseExhaust = baseExhaust;
        this.upgExhaust = upgExhaust;
        this.exhaust = baseExhaust;
    }
    protected final void setEthereal(boolean baseEthereal, boolean upgEthereal)
    {
        this.baseEthereal = baseEthereal;
        this.upgEthereal = upgEthereal;
        this.isEthereal = baseEthereal;
    }
    protected void setInnate(boolean baseInnate, boolean upgInnate)
    {
        this.baseInnate = baseInnate;
        this.upgInnate = upgInnate;
        this.isInnate = baseInnate;
    }
    protected void setSelfRetain(boolean baseRetain, boolean upgRetain)
    {
        this.baseRetain = baseRetain;
        this.upgRetain = upgRetain;
        this.selfRetain = baseRetain;
    }


    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();

        if (card instanceof BundleCard)
        {
            card.rawDescription = this.rawDescription;
            ((BundleCard) card).upgradesDescription = this.upgradesDescription;
            ((BundleCard) card).baseCost = this.baseCost;
            ((BundleCard) card).upgradeCost = this.upgradeCost;
            ((BundleCard) card).upgradeDamage = this.upgradeDamage;
            ((BundleCard) card).upgradeBlock = this.upgradeBlock;
            ((BundleCard) card).upgradeMagic = this.upgradeMagic;
            ((BundleCard) card).costUpgrade = this.costUpgrade;
            ((BundleCard) card).damageUpgrade = this.damageUpgrade;
            ((BundleCard) card).blockUpgrade = this.blockUpgrade;
            ((BundleCard) card).magicUpgrade = this.magicUpgrade;
            ((BundleCard) card).baseExhaust = this.baseExhaust;
            ((BundleCard) card).upgExhaust = this.upgExhaust;
            ((BundleCard) card).baseEthereal = this.baseEthereal;
            ((BundleCard) card).upgEthereal = this.upgEthereal;
            ((BundleCard) card).baseInnate = this.baseInnate;
            ((BundleCard) card).upgInnate = this.upgInnate;
            ((BundleCard) card).baseRetain = this.baseRetain;
            ((BundleCard) card).upgRetain = this.upgRetain;
        }

        return card;
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            this.upgradeName();

            if (this.upgradesDescription)
            {
                if (cardStrings.UPGRADE_DESCRIPTION == null)
                {
                    BundleCoreMain.logger.error("Card " + cardID + " upgrades description and has null upgrade description.");
                }
                else
                {
                    this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                }
            }

            if (upgradeCost)
            {
                if (isCostModified && this.cost < this.baseCost && this.cost >= 0) {
                    int diff = this.costUpgrade - this.baseCost; //how the upgrade alters cost
                    this.upgradeBaseCost(this.cost + diff);
                    if (this.cost < 0)
                        this.cost = 0;
                }
                else {
                    upgradeBaseCost(costUpgrade);
                }
            }

            if (upgradeDamage)
                this.upgradeDamage(damageUpgrade);

            if (upgradeBlock)
                this.upgradeBlock(blockUpgrade);

            if (upgradeMagic)
                this.upgradeMagicNumber(magicUpgrade);

            if (baseExhaust ^ upgExhaust)
                this.exhaust = upgExhaust;

            if (baseInnate ^ upgInnate)
                this.isInnate = upgInnate;

            if (baseEthereal ^ upgEthereal)
                this.isEthereal = upgEthereal;

            if (baseRetain ^ upgRetain)
                this.selfRetain = upgRetain;


            this.initializeDescription();
        }
    }

    protected static AbstractPlayer pl(){
        return AbstractDungeon.player;
    }



    //
    //////
    // Below: Tools
    //////
    //



    public static AbstractCard returnMasterCardOfRarity(AbstractCard.CardRarity rarity, Random rng) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.rarity == rarity)
                cards.add(c);
        }
        if (cards.size() == 0){
            return new Madness();
        }
        return cards.get(rng.random(cards.size() - 1));
    }
    protected void addToQ(AbstractGameEffect action){
        AbstractDungeon.effectList.add(action);
    }
    protected void addToList(AbstractGameEffect action){
        AbstractDungeon.effectsQueue.add(action);
    }

}