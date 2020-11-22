import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeterminedPathingStrategy implements PathingStrategy{

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        /* Does not check withinReach.  Since only a single step is taken
         * on each call, the caller will need to check if the destination
         * has been reached.
         */
        end = new Point(start.getX(), 50);
        Point finalEnd = end;
        return potentialNeighbors.apply(start)
                .filter(canPassThrough)
                .filter(pt ->
                        !pt.equals(start)
                                && !pt.equals(finalEnd)
                                //&& Math.abs(end.getX() - pt.getX()) <= Math.abs(end.getX() - start.getX())
                                && Math.abs(finalEnd.getY() - pt.getY()) <= Math.abs(finalEnd.getY() - start.getY()))
                .limit(1)
                .collect(Collectors.toList());
    }
}
