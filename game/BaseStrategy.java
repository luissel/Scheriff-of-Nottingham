package game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BaseStrategy extends Player {
    public BaseStrategy() {
        super.setType("BASIC");
    }

    private int getProfit(final String name) {
        for (Assets good : getAssetsInHand()) {
            if (good.getName().equals(name)) {
                return good.getProfit();
            }
        }
        return 0;
    }

    public final Assets getIllegalGood(final List<Assets> assets) {
        int max = -1;
        Assets good = null;
        for (Assets g : assets) {
            if (g.getProfit() >= max) {
                max = g.getProfit();
                good = g;
            }
        }
        return good;
    }

    private void illegalCase(final List<Assets> assets) {
        Assets good = getIllegalGood(assets);
        addAssestsInBag(good);
        setDeclaredType("Apple");
        setState("liar");
    }

    public final void merchant() {
        List<Assets> assetsInHand = getAssetsInHand();
        boolean isAllIllegal = true;

        for (Assets good : assetsInHand) {
            if (good.isLegal()) {
                isAllIllegal = false;
            }
        }

        if (isAllIllegal) {
            illegalCase(assetsInHand);
        } else {
            goodFrequency();
        }
    }

    public void merchantAction() {
        merchant();
    }

    private void goodFrequency() {
        List<Assets> assetsInHand = getAssetsInHand();
        Map<String, Integer> list = new LinkedHashMap<>();

        for (Assets good : assetsInHand) {
            if (good.isLegal()) {
                if (list.containsKey(good.getName())) {
                    Integer frequency = list.get(good.getName());
                    frequency++;
                    list.put(good.getName(), frequency);
                } else {
                    list.put(good.getName(), 1);
                }
            }
        }
        getFrequency(list);
    }

    private void getFrequency(final Map<String, Integer> list) {
        Map.Entry<String, Integer> maxEntry = null;
        List<Map.Entry<String, Integer>> maxim = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : list.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        for (Map.Entry<String, Integer> entry : list.entrySet()) {
            if (entry.getValue().equals(maxEntry.getValue())) {
                maxim.add(entry);
            }
        }

        if (maxim.size() != 0) {
            int max = -1, freq = 0, count = 0;
            String good = null;

            for (Map.Entry<String, Integer> currentGood : maxim) {
                if (getProfit(currentGood.getKey()) > max) {
                    max = getProfit(currentGood.getKey());
                    good = currentGood.getKey();
                    freq = currentGood.getValue();
                }
            }

            Assets obj = getGoodByName(good);
            while (count < freq) {
                addAssestsInBag(obj);
                removeAssetsInHand(obj);
                count++;
            }

            setDeclaredType(good);
            this.setState("honest");
        }
    }

    public Assets getGoodByName(final String name) {
        for (Assets good : getAssetsInHand()) {
            if (good.getName().equals(name)) {
                return good;
            }
        }
        return null;
    }

    private int getPenaltyy(final Player merchant) {
        String name = merchant.getDeclaredType();

        for (Assets good : merchant.getAssetsInBag()) {
            if (good.getName().equals(name)) {
               return good.getPenalty();
            }
        }

        return 0;
    }

    public void scheriffAction(final Player merchant) {
        if (merchant.getBribe() > 0) {
            setBribe(0);
        }

        scheriffInspection(merchant);
    }

    public final void scheriffInspection(final Player merchant) {
        // da bani comerciantilor onesti
        if (merchant.getState().equals("honest")) {
            int coins = merchant.getAssetsInBag().size() * getPenaltyy(merchant);
            merchant.addCoins(coins);
            this.giveCoins(coins);

            for (Assets good : merchant.getAssetsInBag()) {
                merchant.addAssetsOnMerchantStand(good);
            }
        } else {
            int coins = 0;

            // castiga seriful bani pentru fiecare bun nedeclarat
            for (Assets good : merchant.getAssetsInBag()) {
                if (!good.isLegal()) {
                    coins += good.getPenalty();
                    addConfiscatedAssets(good);

                    if (merchant.getAssetsOnMerchantStand().size() > 0) {
                        merchant.getAssetsOnMerchantStand().remove(good);
                    }
                } else {
                    merchant.addAssetsOnMerchantStand(good);
                }
            }

            if (merchant.getBribe() != 0) {
                merchant.setBribe(0);
            }

            merchant.giveCoins(coins);
            this.addCoins(coins);
        }
    }
}
