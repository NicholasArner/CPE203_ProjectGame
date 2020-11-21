import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy{
    //private List<Point> closedList = new ArrayList<>();
    private Hashtable<Point, Node> closedList = new Hashtable<>();
    private Node current;

    private Hashtable<Point, Node> allPoints = new Hashtable<>();

    Predicate<Point> notInClosedList = p -> !closedList.containsKey(p);

    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        // canPassThrough could be open list
        // Manhattan distance is simply the sum of the distance in x and the distance in y
        // use streams with canPassThrough, withinReach, and potential neighbors to create openList

        if (current == null){
            current = new Node(start);
        }

        List<Point> neighbors = CARDINAL_NEIGHBORS.apply(current.getPoint()).filter(canPassThrough).collect(Collectors.toList());

        // might need to change this when calculating g val,
        Function<Point, Integer> findG = p -> Math.abs(p.getX() - start.getX()) + Math.abs(p.getY() - start.getY() + 1);
        Function<Point, Integer> findH = p -> Math.abs(p.getX() - end.getX()) + Math.abs(p.getY() - end.getY());
        Function<Point, Integer> findF = p -> findG.apply(p) + findH.apply(p);
        Function<Point, Node> createNode = p -> new Node(p, findG.apply(p), findH.apply(p), findF.apply(p), current);

        Stream<Node> openNodes = CARDINAL_NEIGHBORS.apply(current.getPoint()).filter(canPassThrough).filter(notInClosedList).map(createNode);

        if (openNodes.count() == 0) return null;

        List<Integer> f_vals = openNodes.map(Node::getF).collect(Collectors.toList());

        int min_f_val = Collections.min(f_vals);
        Predicate<Node> lowestF_val = n -> min_f_val == n.getF();

        List<Node> nextNodes = openNodes.filter(lowestF_val).limit(1).collect(Collectors.toList());

        Node nextNode = nextNodes.get(0);

        closedList.put(current.getPoint(), current);

        current = nextNode;

        if (computePath(start, end, canPassThrough, withinReach, potentialNeighbors) != null){
            if (withinReach.test(current.getPoint(), end)) {
                List<Point> Path = new ArrayList<>();
                Node prevNode = current.getParentNode();
                while (prevNode != null){
                    Path.add(prevNode.getPoint());
                    prevNode = prevNode.getParentNode();
                }
                return Path;
            }
        }
        return null;
    }
}
