package ua.nure.pharmacy.util;

import ua.nure.pharmacy.entity.Pack;

import java.util.Comparator;

public class PriceComparator implements Comparator<Pack> {
    @Override
    public int compare(Pack m1, Pack m2) {
        return m1.getPrice().compareTo(m2.getPrice());
    }
}