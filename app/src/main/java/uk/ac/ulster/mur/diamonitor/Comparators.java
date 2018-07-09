package uk.ac.ulster.mur.diamonitor;

import java.util.Comparator;
/**
 * Comparators For sorting ArrayLists by time to sort ArrayLists of each Type into
 * chronological order using the long epoch as the compared value
 */
class BloodComparator implements Comparator<Blood> {
    @Override
    public int compare(Blood b1, Blood b2) {
        return Long.compare(b2.getTime(), b1.getTime());
    }
}
class InsulinComparator implements Comparator<Insulin> {
    @Override
    public int compare(Insulin i1, Insulin i2) {
        return Long.compare(i2.getTime(), i1.getTime());
    }
}
class CarbsComparator implements Comparator<Carbs> {
    @Override
    public int compare(Carbs c1, Carbs c2) {
        return Long.compare(c2.getTime(), c1.getTime());
    }
}
