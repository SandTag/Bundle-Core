package bundlecore.relics;

import basemod.BaseMod;
import bundlecore.bundleloadedbools.BundleChecker;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

import static bundlecore.BundleCoreMain.makeID;

public class MenacingRavenBoss extends BundleRelic implements BundleChecker {

    private static final String NAME = "MenacingRavenBoss";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.DEPRECATED; //todo: Next Update
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;
    private byte soundPlays = 0;
    private byte soundPlays2 = 0;
    public MenacingRavenBoss() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = -1;
        this.grayscale = false;
        setDescriptionWithCard();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void setDescriptionWithCard() {
        tips.clear();
        description = DESCRIPTIONS[0];
        this.tips.add(new PowerTip(this.name, this.description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(DESCRIPTIONS[1]), BaseMod.getKeywordDescription(DESCRIPTIONS[1])));
        initializeTips();
    }

    @Override
    public void onEquip() {
        BaseMod.MAX_HAND_SIZE++;
        pl().energy.energyMaster++;
        pl().masterHandSize++;
        AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.BLUE));
        AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.GREEN));
        AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.RED));
        refreshSuperElite();
    }

    @Override
    public void onUnequip() {
        BaseMod.MAX_HAND_SIZE--;
        pl().energy.energyMaster--;
        pl().masterHandSize--;
    }
    @Override
    public void onTrigger() {
        flash();
        this.grayscale = false;
        soundPlays = 10;
        beginPulse();
        this.pulse = true;
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new VoidCard(), Settings.WIDTH / 1.333333F, Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new VoidCard(), Settings.WIDTH / 4F, Settings.HEIGHT / 2.0F));
    }

    private static void refreshSuperElite() {
        for (ArrayList<MapRoomNode> nodes : (Iterable<ArrayList<MapRoomNode>>)AbstractDungeon.map) {
            for (MapRoomNode node : nodes) {
                if (node.getRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite)
                    node.hasEmeraldKey = false;
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (soundPlays > 0) {
            --soundPlays;
            CardCrawlGame.sound.play("NECRONOMICON", MathUtils.random(1.0F, -1.0F));
        }
        if (soundPlays2 > 0) {
            --soundPlays2;
            CardCrawlGame.sound.play("MONSTER_COLLECTOR_SUMMON", MathUtils.random(1.0F, -1.0F));
        }
    }

    @Override
    public boolean canSpawn() {
        return (isDeliciousDecaDied() && !hasQuestRelic() && isMazalethUpdate() && (AbstractDungeon.actNum > 1 || AbstractDungeon.floorNum == 0));
    }

}