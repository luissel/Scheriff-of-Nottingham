package game;

import java.util.Comparator;

public final class PlayerComparator implements Comparator<Player> {
    @Override
    public int compare(final Player p1, final Player p2) {
        return p2.getTotalPoints() - p1.getTotalPoints();
    }
}
