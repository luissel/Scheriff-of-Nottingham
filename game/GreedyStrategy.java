package game;

import java.util.List;

public final class GreedyStrategy extends BaseStrategy {
    private int round = 1;

    public GreedyStrategy() {
        super.setType("GREEDY");
    }
    @Override
    public void merchantAction() {
        super.merchantAction();

        if (round % 2 == 0) {
            doIllegalCase();
        }
        round++;
    }

    private void doIllegalCase() {
        List<Assets> bag = getAssetsInBag();
        List<Assets> hand = getAssetsInHand();
        final int maxSize = 5;

        if (bag.size() < maxSize) {
            if (getIllegalGood(hand) != null) {
                Assets good = getIllegalGood(hand);
                addAssestsInBag(good);
                removeAssetsInHandI(good);
                setState("liar");
            }
        }
    }

    @Override
    public void scheriffAction(final Player merchant) {
        if (merchant.getBribe() > 0) {
            addCoins(merchant.getBribe());
            merchant.giveCoins(merchant.getBribe());
            merchant.setBribe(0);
        } else {
            scheriffInspection(merchant);
        }
    }
}
