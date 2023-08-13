package bundlecore.cards;
import bundlecore.powers.SuffocationPower;
import bundlecore.util.CardInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;

/**
 * Please ignore this, it uses legacy code.
 */
public class SandgunBossSpecial extends BundleCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SandgunBossSpecial",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.SPECIAL,
            CardColor.COLORLESS
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 5;
    private static final int MAGICNUM = 2;
//    private static final int UPG_MAGICNUM = 0;

    public SandgunBossSpecial() {
        super(cardInfo, false); //Pass the cardInfo to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it increases when upgraded.
        setMagic(MAGICNUM/*, UPG_MAGICNUM*/);
        setInnate(true, true);
        SoulboundField.soulbound.set(this, true);
        this.selfRetain = true;
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new DrawCardAction(1));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new LoseHPAction(pl(), pl(), 3));
            int Atktimes = this.magicNumber;
            while (Atktimes >= 1) {
                Atktimes--;
                addToBot(new VFXAction(new FlickCoinEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.3F));
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
                addToBot(new ApplyPowerAction(m, pl(), new SuffocationPower(m, pl(), 1), 1));
                addToBot(new WaitAction(0.05f));
            }
    }

    public void triggerOnExhaust() {
        addToBot(new MakeTempCardInHandAction(this.makeStatEquivalentCopy()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SandgunBossSpecial();
    }
}