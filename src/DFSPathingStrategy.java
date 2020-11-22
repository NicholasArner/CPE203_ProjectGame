import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DFSPathingStrategy implements PathingStrategy {

    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point,
            Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        //down, up, right, left
        boolean found = false;
        HashMap<Point, String> closedList = new HashMap<Point, String>();
        List<Point> path = new LinkedList<>();
        Point current = start;

        while (!(withinReach.test(current, end))){

        //test if this is a valid grid cell
            Point rightN = new Point(current.getX() +1, current.getY()); //right
            Point leftN = new Point(current.getX() -1, current.getY()); //left
            Point downN = new Point(current.getX(), current.getY() +1); //down
            Point upN = new Point(current.getX(), current.getY() -1); //up

            if (canPassThrough.test(downN) && (!closedList.containsKey(downN))) {
                closedList.put(current, "lol");
                path.add(downN);
                current = downN;
            }

        else {
                if (canPassThrough.test(upN) && (!closedList.containsKey(upN))) {
                    closedList.put(current, "lol");
                    path.add(downN);
                    current = downN;
                }
            else {
                if (canPassThrough.test(rightN) && (!closedList.containsKey(rightN)))
                {
                    //check if my right neighbor is the goal

                    //set this value as searched
                    closedList.put(current, "lol");
                    path.add(rightN);
                    current = rightN;
                }
                else {
                    if (canPassThrough.test(leftN) && (!closedList.containsKey(leftN))) {
                        closedList.put(current, "lol");
                        path.add(leftN);
                        current = leftN;
                    }
                    }

            }
        }
        }
        return path;

    }
}
