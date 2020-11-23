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

        end = new Point(start.getX(), 21);
        Point finalEnd = end;
        return potentialNeighbors.apply(start)
                .filter(canPassThrough)
                .filter(pt ->
                        !pt.equals(start)
                                && !start.equals(finalEnd)
                                && Math.abs(finalEnd.getY() - pt.getY()) <= Math.abs(finalEnd.getY() - start.getY()))
                .limit(1)
                .collect(Collectors.toList());
    }
}
