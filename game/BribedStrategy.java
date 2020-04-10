package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BribedStrategy extends BaseStrategy {
    public BribedStrategy() {
        super.setType("BRIBED");
    }
    @Override
    public void scheriffAction(final Player merchant) {
        super.scheriffAction(merchant);
    }

    public void illegalGoodsFrequency() {
        List<Assets> assetsInHand = getAssetsInHand();
        Map<String, Integer> list = new HashMap<>();

        for (Assets good : assetsInHand) {
            if (!good.isLegal()) {
                if (list.containsKey(good.getName())) {
                    Integer frequency = list.get(good.getName());
                    frequency++;
                    list.put(good.getName(), frequency);
                } else {
                    list.put(good.getName(), 1);
                }
            }
        }

        if (list.size() == 0) {
            setBribe(0);
            merchant();
        } else {
            getGoods(list);
        }
    }

    private void getGoods(final Map<String, Integer> list) {
        List<Assets> orderedGoods = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : list.entrySet()) {
           for (int cnt = 0; cnt < entry.getValue(); cnt++) {
               orderedGoods.add(getGoodByName(entry.getKey()));
           }
        }

        addIllegalGoods(orderedGoods);
    }

    public void addIllegalGoods(final List<Assets> assets) {
        final int maximum = 10;
        final int minimum = 5;

        if (assets.size() > 0) {
            if (assets.size() <= 2 || getCoins() < maximum) {
                setBribe(minimum);
                int cnt = 0;

                while (cnt < 2) {
                    addAssetsInBag(assets.get(0));
                    addAssetsOnMerchantStand(assets.get(0));
                    getAssetsInHand().remove(assets.get(0));
                    assets.remove(0);

                    if (assets.size() == 0) {
                        cnt = 2;
                    } else {
                        cnt++;
                    }
                }
            } else {
                // daca are mai mult de 2 bunuri ilegale si destui bani, da mita
                if (getCoins() >= maximum) {
                    setBribe(maximum);
                    int cnt = 0;

                    while (cnt < minimum) {
                        addAssetsInBag(assets.get(0));
                        addAssetsOnMerchantStand(assets.get(0));
                        removeAssetsInHand(assets.get(0));
                        assets.remove(0);

                        if (assets.size() == 0) {
                            cnt = minimum;
                        } else {
                            cnt++;
                        }
                    }
                }
            }
            setDeclaredType("Apple");
            setState("liar");
        }
    }
    @Override
    public void merchantAction() {
        final int minimumCoins = 5;

        if (getCoins() > minimumCoins) {
            illegalGoodsFrequency();
        } else {
            merchant();
        }
    }
}
