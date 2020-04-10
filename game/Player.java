package game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int coins, bribe, bonus, totalPoints;
    private List<Assets> assetsInHand, assetsOnMerchantStand;
    private List<Assets> assetsInBag, confiscatedAssests;
    private String state, declaredType, type;

    public Player() {
        final int initialCoins = 50;
        coins = initialCoins;
        bribe = 0;
        bonus = 0;
        totalPoints = 0;
        this.assetsInHand = new ArrayList<>();
        this.assetsInBag = new ArrayList<>();
        this.confiscatedAssests = new ArrayList<>();
        this.assetsOnMerchantStand = new ArrayList<>();
        state = null;
        declaredType = null;
        type = null;
    }

    public final void setTotalPoints(final int points) {
        this.totalPoints = points;
    }

    public final int getTotalPoints() {
        return totalPoints;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final void addBonus(final int bonusCoins) {
        this.bonus += bonusCoins;
    }

    public final int getBonus() {
        return bonus;
    }

    public final String getType() {
        return type;
    }

    public final void setBribe(final int bribe) {
        this.bribe = bribe;
    }

    public final int getBribe() {
        return bribe;
    }

    public final List<Assets> getConfiscatedAssests() {
        return confiscatedAssests;
    }
    public final void addConfiscatedAssets(final Assets good) {
        confiscatedAssests.add(good);
    }

    public final void setState(final String state) {
        this.state = state;
    }

    public final String getState() {
        return state;
    }

    public final void setDeclaredType(final String declaredType) {
        this.declaredType = declaredType;
    }

    public final String getDeclaredType() {
        return declaredType;
    }

    public final int getCoins() {
        return coins;
    }

    public final void addCoins(final int profit) {
        coins += profit;
    }

    public final void giveCoins(final int coin) {
        this.coins -= coin;
    }

    public final void addAssetsInHand(final Assets good) {
        assetsInHand.add(good);
    }

    //sterg toate bunurile de acel tip din mana
    public final void removeAssetsInHand(final Assets good) {
        int count = 0;
        while (count < assetsInHand.size()) {
            if (assetsInHand.get(count).getName().equals(good.getName())) {
                assetsInHand.remove(count);
                count = 0;
            } else {
                count++;
            }
        }
    }

    // sterg un singur bun
    public final void removeAssetsInHandI(final Assets good) {
        int count = 0;
        while (count < assetsInHand.size()) {
            if (assetsInHand.get(count).getName().equals(good.getName())) {
                assetsInHand.remove(count);
                count = assetsInHand.size();
            } else {
                count++;
            }
        }
    }

    public final List<Assets> getAssetsInHand() {
        return assetsInHand;
    }

    public final void setAssetsInBag(final List<Assets> assetsInBag) {
        this.assetsInBag = assetsInBag;
    }

    public final void addAssestsInBag(final Assets good) {
        assetsInBag.add(good);
    }

    public final List<Assets> getAssetsInBag() {
        return assetsInBag;
    }

    public final void addAssetsInBag(final Assets good) {
        assetsInBag.add(good);
    }

    public final void addAssetsOnMerchantStand(final Assets good) {
        assetsOnMerchantStand.add(good);
    }

    public final List<Assets> getAssetsOnMerchantStand() {
        return assetsOnMerchantStand;
    }

    public Assets getGoodByName(final String name) {
        for (Assets good : getAssetsInHand()) {
            if (good.getName().equals(name)) {
                return good;
            }
        }

        return null;
    }
}
