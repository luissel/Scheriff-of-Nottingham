package game;

import java.util.*;

public final class FinalScore {
    private List<BaseStrategy> players;
    private List<Map<String, Integer>> list;
    private List<Assets> assets;

    public FinalScore(final List<BaseStrategy> myPlayers, final List<Assets> assets) {
        players = new ArrayList<>();
        players.addAll(myPlayers);
        list = new ArrayList<>();
        this.assets = new ArrayList<>();
        this.assets.addAll(assets);
    }

    private void setMap(final Map<String, Integer> map) {
        for (Assets good : assets) {
            map.put(good.getName(), 0);
        }
    }
    public Assets getGoodByName(final String name) {
        for (Assets good : assets) {
            if (good.getName().equals(name)) {
                return good;
            }
        }
        return null;
    }

    // adaug in lista bunurile bonus de la cartile ilegale
    private void addBonusForIllegalGoods(final BaseStrategy player) {
        List<Assets> asset = new ArrayList<>();
        List<Assets> l = new ArrayList<>();
        asset.addAll(player.getAssetsOnMerchantStand());

        for (Assets good : asset) {
            if (!good.isLegal()) {
                String bonusName = good.getBonusAsstesName();
                int numberOfGoods = good.getBonusAssetsCount();
                for (int i = 0; i < numberOfGoods; i++) {
                    l.add(getGoodByName(bonusName));
                }
            }
        }
        player.getAssetsOnMerchantStand().addAll(l);
    }

    // map ul de cu frecventa bunurilor pentru un jucator
    private Map<String, Integer> getFrequnecyList(final Player p) {
        final int capacity = 4;
        List<Assets> asset = p.getAssetsOnMerchantStand();
        Map<String, Integer> assetsFreq = new LinkedHashMap<>(capacity);

        // initializez hash ul de bunuri
        setMap(assetsFreq);

        for (Assets good : asset) {
            if (good.isLegal()) {
                if (assetsFreq.containsKey(good.getName())) {
                    Integer frequency = assetsFreq.get(good.getName());
                    frequency++;
                    assetsFreq.put(good.getName(), frequency);
                }
            }
        }
        return assetsFreq;
    }

    private void createFrequencyList() {
        for (BaseStrategy p : players) {
            addBonusForIllegalGoods(p);
            list.add(getFrequnecyList(p));
        }
    }

    private void getBonus() {
        List<String> assetsName = new ArrayList<>();
        assetsName.add("Apple");
        assetsName.add("Cheese");
        assetsName.add("Bread");
        assetsName.add("Chicken");

        if (list.size() == 2) {
            for (String s : assetsName) {
                if (list.get(0).get(s) > list.get(1).get(s)) {
                    players.get(0).addBonus(getGoodByName(s).getKingBonus());
                    players.get(1).addBonus(getGoodByName(s).getQueenBonus());
                } else if (list.get(0).get(s) == list.get(1).get(s)) {
                    players.get(0).addBonus(getGoodByName(s).getKingBonus());
                    players.get(1).addBonus(getGoodByName(s).getKingBonus());
                } else if (list.get(0).get(s) < list.get(1).get(s)) {
                    players.get(1).addBonus(getGoodByName(s).getKingBonus());
                    players.get(0).addBonus(getGoodByName(s).getQueenBonus());
                }
            }
        } else {
            final int capacity = 3;
            for (String s : assetsName) {
                List<Integer> freq = new ArrayList<>(capacity);
                int max1 = 0, max2 = 0;

                for (Map<String, Integer> m : list) {
                    freq.add(m.get(s));
                    if (m.get(s) > max1) {
                        max1 = m.get(s);
                    }
                }

                for (Map<String, Integer> m : list) {
                    if (m.get(s) > max2 && m.get(s) < max1) {
                        max2 = m.get(s);
                    }
                }

                for (int i = 0; i < capacity; i++) {
                    if (list.get(i).get(s).equals(max1)) {
                        players.get(i).addBonus(getGoodByName(s).getKingBonus());
                    } else if (list.get(i).get(s).equals(max2)) {
                        players.get(i).addBonus(getGoodByName(s).getQueenBonus());
                    }
                }
            }
        }
    }

    private int getProfitOnMerchantStand(final Player p) {
        int profit = 0;
        for (Assets good : p.getAssetsOnMerchantStand()) {
            profit += good.getProfit();
        }
        return profit;
    }

    private int score(final Player p) {
        int profitOnMerchantStand = getProfitOnMerchantStand(p);
        int coins = p.getCoins();
        int total = coins + profitOnMerchantStand;

        return total;
    }

    public void printScore() {
        createFrequencyList();
        getBonus();
        PlayerComparator playerComparator = new PlayerComparator();
        List<Player> scoreBoard = new LinkedList<>();

        for (Player p : players) {
            int total = score(p) + p.getBonus();
            p.setTotalPoints(total);
            scoreBoard.add(p);
        }

        Collections.sort(scoreBoard, playerComparator);

        for (Player player : scoreBoard) {
            System.out.println(player.getType() + ": " + player.getTotalPoints());
        }
    }
}
