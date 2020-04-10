package game;

public final class Assets {
    private String name, type, bonusGoods;
    private int profit, penalty, bonusNumberGoods;
    private int kingBonus, queenBonus;

    public Assets(final String name, final String type, final int profit, final int penalty,
                  final int bonusNumberGoods, final String bonusGoods) {
        this.name = name;
        this.profit = profit;
        this.penalty = penalty;
        this.bonusNumberGoods = bonusNumberGoods;
        this.bonusGoods = bonusGoods;
        this.type = type;
    }

    public Assets(final String name, final String type, final int profit, final int penalty,
                  final int kingBonus, final int queenBonus) {
        this.name = name;
        this.profit = profit;
        this.penalty = penalty;
        this.kingBonus = kingBonus;
        this.queenBonus = queenBonus;
        this.type = type;
    }

    public int getBonusAssetsCount() {
        return bonusNumberGoods;
    }


    public String getBonusAsstesName() {
        return bonusGoods;
    }

    public String getName() {
        return name;
    }

    public int getPenalty() {
        return this.penalty;
    }

    public int getProfit() {
        return profit;
    }

    public int getKingBonus() {
        return kingBonus;
    }

    public int getQueenBonus() {
        return queenBonus;
    }

    public boolean isLegal() {
        if (type.equals("legal")) {
            return true;
        }
        return false;
    }
}
