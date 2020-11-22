import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy{
    private final Hashtable<Point, Node> closedList = new Hashtable<>();
    private final PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF).thenComparing(Node::getG));
    private Node current;

    private final Hashtable<Point, Node> allPoints = new Hashtable<>();

    Predicate<Point> notInClosedList = p -> !closedList.containsKey(p);


    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        // canPassThrough could be open list
        // Manhattan distance is simply the sum of the distance in x and the distance in y
        // use streams with canPassThrough, withinReach, and potential neighbors to create openList

        openList.clear();
        closedList.clear();
        allPoints.clear();

        current = createNode(start, start, end);
        openList.add(current);

        boolean found = false;

        Function<Point, Node> createNodes = p -> createNode(p, start, end);
        Consumer<Node> addNodes = openList::add;

        while (!found){
            List<Point> adjacentPoints = potentialNeighbors.apply(current.getPoint()).filter(canPassThrough).filter(notInClosedList).collect(Collectors.toList());

            List<Node> nodes = adjacentPoints.stream().map(createNodes).collect(Collectors.toList());

            nodes.forEach(addNodes);

            openList.remove(current);
            closedList.put(current.getPoint(), current);

            current = openList.peek();

            if (current == null) return new ArrayList<>(); // return empty list if stuck
            found = withinReach.test(current.getPoint(), end);
        }

        List<Point> Path = new ArrayList<>();
        Path.add(current.getPoint());
        Node prevNode = current.getParentNode();
        while (prevNode != null) {
            if (!prevNode.getPoint().equals(start))
                Path.add(prevNode.getPoint());
            prevNode = prevNode.getParentNode();
        }
        Collections.reverse(Path);
        return Path;
    }

    private Node createNode(Point p, Point start, Point end){
        int h = Math.abs(p.getX() - end.getX()) + Math.abs(p.getY() - end.getY()) * 10;
        int f = h;

        int g;

        if (p.equals(start)){
            return new Node(p, 0, h, f, null);
        }
        if (isDiagonal(p, current.getPoint())) g = current.getG() + 14;
        else g = current.getG() + 10;

        Node node_created = allPoints.get(p);

        if (node_created != null){
            // reinsert this new node into the openlist
            openList.remove(node_created);
            int g2 = node_created.getG();
            g = Math.min(g, g2);
            if (g < g2){
                return new Node(p, g, h, g+h, current);
            }
            return node_created;

        }

        f = h + g;
        Node new_Node = new Node(p, g, h, f, current);

        allPoints.put(new_Node.getPoint(), new_Node);
        return new_Node;
    }

    private boolean isDiagonal(Point p1, Point p2){
        return p1.getX()+1 == p2.getX() && p1.getY()+1 == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY()-1 == p2.getY() ||
                p1.getX()+1 == p2.getX() && p1.getY()-1 == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY()+1 == p2.getY();
    }
}
