package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.Assets;
import game.Player;
import game.BaseStrategy;
import game.FinalScore;
import game.GreedyStrategy;
import game.BribedStrategy;
import main.GameInput;

public final class Main {

    private static final class GameInputLoader {
        private final String mInputPath;

        private GameInputLoader(final String path) {
            mInputPath = path;
        }

        public GameInput load() {
            List<Integer> assetsIds = new ArrayList<>();
            List<String> playerOrder = new ArrayList<>();

            try {
                BufferedReader inStream = new BufferedReader(new FileReader(mInputPath));
                String assetIdsLine = inStream.readLine().replaceAll("[\\[\\] ']", "");
                String playerOrderLine = inStream.readLine().replaceAll("[\\[\\] ']", "");

                for (String strAssetId : assetIdsLine.split(",")) {
                    assetsIds.add(Integer.parseInt(strAssetId));
                }

                for (String strPlayer : playerOrderLine.split(",")) {
                    playerOrder.add(strPlayer);
                }
                inStream.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return new GameInput(assetsIds, playerOrder);
        }
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0]);
        GameInput gameInput = gameInputLoader.load();
        // TODO Implement the game logic.

        List assets = gameInput.getAssetIds();
        List players = gameInput.getPlayerNames();
        List<Assets> assetsName = new ArrayList<>();
        final int kingBonusApple = 20, queenBonusApple = 10;
        final int kingBonusCheese = 15, queenBonusCheese = 10;
        final int kingBonusBread = 15, queenBonusBread = 10;
        final int kingBonusChicken = 10, queenBonusChicken = 5;
        final int profitApple = 2, profitCheese = 3, profitBread = 4, profitChicken = 4;
        final int penaltyApple = 2, penaltyCheese = 2, penaltyBread = 2, penaltyChicken = 2;
        final int profitSilk = 9, profitPepper = 8, profitBarrel = 7;
        final int penaltyIllegal = 4;
        final int idApple = 0, idCheese = 1, idBread = 2, idChicken = 3;
        final int idSilk = 10, idPepper = 11, idBarrel = 12;
        final int bonus = 3;
        int id;

        for (id = 0; id < assets.size(); id++) {
            Assets good;

            if (assets.get(id).equals(idApple)) {
                good = new Assets("Apple", "legal", profitApple, penaltyApple, kingBonusApple,
                        queenBonusApple);
                assetsName.add(good);
            }

            if (assets.get(id).equals(idCheese)) {
                good = new Assets("Cheese", "legal", profitCheese, penaltyCheese, kingBonusCheese,
                        queenBonusCheese);
                assetsName.add(good);
            }

            if (assets.get(id).equals(idBread)) {
                good = new Assets("Bread", "legal", profitBread, penaltyBread, kingBonusBread,
                        queenBonusBread);
                assetsName.add(good);
            }

            if (assets.get(id).equals(idChicken)) {
                good = new Assets("Chicken", "legal", profitChicken, penaltyChicken,
                        kingBonusChicken, queenBonusChicken);
                assetsName.add(good);
            }

            if (assets.get(id).equals(idSilk)) {
                good = new Assets("Silk", "illegal", profitSilk, penaltyIllegal, bonus,
                        "Cheese");
                assetsName.add(good);
            }

            if (assets.get(id).equals(idPepper)) {
                good = new Assets("Pepper", "illegal", profitPepper, penaltyIllegal, 2,
                        "Chicken");
                assetsName.add(good);
            }

            if (assets.get(id).equals(idBarrel)) {
                good = new Assets("Barrel", "illegal", profitBarrel, penaltyIllegal, 2,
                        "Bread");
                assetsName.add(good);
            }
        }

        int round = 1;
        List<BaseStrategy> myPlayers = new ArrayList<>();

        for (int j = 0; j < players.size(); j++) {
            if (players.get(j).equals("basic")) {
                myPlayers.add(new BaseStrategy());
            } else if (players.get(j).equals("greedy")) {
                myPlayers.add(new GreedyStrategy());
            } else {
                myPlayers.add(new BribedStrategy());
            }
        }

        int numberOfPlayers = players.size();
        final int numberAssetsInHand = 6;

        while (round <= numberOfPlayers * 2) {
            for (Player player : myPlayers) {
                while (player.getAssetsInHand().size() < numberAssetsInHand) {
                    player.addAssetsInHand(assetsName.get(0));
                    assetsName.remove(0);
                }
            }

            if (round % numberOfPlayers == 1) {
                for (int j = 1; j < numberOfPlayers; j++) {
                    ((BaseStrategy) myPlayers.get(j)).merchantAction();
                    ((BaseStrategy) myPlayers.get(0)).scheriffAction(myPlayers.get(j));

                    myPlayers.get(j).getAssetsInBag().clear();
                }

                // adaug bunurile confiscate de serif in pachetul initial
                if (myPlayers.get(0).getConfiscatedAssests().size() != 0) {
                    assetsName.addAll(myPlayers.get(0).getConfiscatedAssests());
                }
            } else if (round % numberOfPlayers == 2) {
                for (int j = 0; j < numberOfPlayers; j++) {
                    if (j != 1) {
                        ((BaseStrategy) myPlayers.get(j)).merchantAction();
                        ((BaseStrategy) myPlayers.get(1)).scheriffAction(myPlayers.get(j));
                    }

                    myPlayers.get(j).getAssetsInBag().clear();
                }

                // adaug bunurile confiscate de serif in pachetul initial
                if (myPlayers.get(1).getConfiscatedAssests().size() != 0) {
                    assetsName.addAll(myPlayers.get(1).getConfiscatedAssests());
                }
            } else {
                for (int j = 0; j < numberOfPlayers; j++) {
                    if (j != numberOfPlayers - 1) {
                        ((BaseStrategy) myPlayers.get(j)).merchantAction();
                        ((BaseStrategy) myPlayers.get(numberOfPlayers - 1)).
                                scheriffAction(myPlayers.get(j));
                    }

                    myPlayers.get(j).getAssetsInBag().clear();
                }

                // adauga bunurile confiscate de serif in pachetul initial
                if (myPlayers.get(numberOfPlayers - 1).getConfiscatedAssests().size() != 0) {
                    assetsName.addAll(myPlayers.get(numberOfPlayers - 1).
                            getConfiscatedAssests());
                }
            }
            round++;
        }

        FinalScore score = new FinalScore(myPlayers, assetsName);
        score.printScore();
    }
}
