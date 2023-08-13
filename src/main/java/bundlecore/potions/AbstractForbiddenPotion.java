package bundlecore.potions;

import basemod.abstracts.CustomPotion;
import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FloatyEffect;

public abstract class AbstractForbiddenPotion extends CustomPotion {
    public static boolean IsForbiddenAfter;
    public boolean isDone = false;
    public boolean isAnimating = false;
    private float glowTimer = 0.0F;
    private FloatyEffect f_effect = new FloatyEffect(10.0F, 0.2F);

    /**
     * The same as a regular potion, except with an additional bool for potions intended to be generated directly from relics or cards.
     * @param IsForbidden Set to true to make the potion not show up in potion rewards.
     * @param name the resources folder of your mod.
     */
    public AbstractForbiddenPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionColor color, boolean IsForbidden) {
        super(name, id, rarity, size, color);
        IsForbiddenAfter = IsForbidden;
    }

    @Dont_Use_This_Externally
    public AbstractForbiddenPotion(String id, AbstractPotion.PotionRarity rarity, AbstractPotion.PotionSize size) {
        super((CardCrawlGame.languagePack.getPotionString(id)).NAME, id, rarity, size, AbstractPotion.PotionColor.STRENGTH);
    }

    @Dont_Use_This_Externally
    public AbstractForbiddenPotion(String id, AbstractPotion.PotionRarity rarity, AbstractPotion.PotionSize size, boolean ForbiddenValue) {
        super((CardCrawlGame.languagePack.getPotionString(id)).NAME, id, rarity, size, AbstractPotion.PotionColor.STRENGTH);
        IsForbiddenAfter = ForbiddenValue;
    }

    protected void addToQ(AbstractGameEffect action){
        AbstractDungeon.effectList.add(action);
    }

    protected void addToList(AbstractGameEffect action){
        AbstractDungeon.effectsQueue.add(action);
    }
}
