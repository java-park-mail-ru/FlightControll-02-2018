package ru.technopark.flightcontrol.comparators;

import ru.technopark.flightcontrol.models.User;

import java.util.Comparator;

public class RatingsComparator<T> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        if(o1 instanceof User && o2 instanceof User){
            final User u1 = (User) o1;
            final User u2 = (User) o2;
            final int rateCompare = Integer.compare(u1.getRate(), u2.getRate());
            if (rateCompare == 0) {
                return u1.getName().compareTo(u2.getName());
            }
            return rateCompare;
        }
        return 0;
    }
}
