package ru.bets.project.utils;

public class Index implements Comparable<Index> {
    public double value;
    public double odds;

    public Index(double value, double odds) {
        this.value = value;
        this.odds = odds;
    }

    @Override
    public int compareTo(Index o) {
        return Double.compare(this.value,o.value);
    }

}
