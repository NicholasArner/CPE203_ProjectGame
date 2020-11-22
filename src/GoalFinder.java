import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class GoalFinder extends MovingEntity{
    private int num_lives = 3;
    private final PathingStrategy strategy = new AStarPathingStrategy();
    private List<Point> currentPath;

    protected GoalFinder(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public int getNum_lives() {
        return num_lives;
    }

    public Point nextPosition(WorldModel world, Point destPos){
        // recalculate here if next point is occupied, use world.isoccupied
       //List<Point> points = strategy.computePath(getPosition(), destPos)
        if (currentPath == null) currentPath = calculatePath(world, destPos);
        if (world.isOccupied(currentPath.get(0))) return calculatePath(world, destPos).get(0); // recalculate path if previous path is blocked
        return currentPath.get(0); // otherwise continue on current path

    }

    private List<Point> calculatePath(WorldModel world, Point destPos){

        List<Point> path = strategy.computePath(getPosition(), destPos, p-> !world.isOccupied(p) & world.withinBounds(p), GoalFinder::neighbors, PathingStrategy.CARDINAL_NEIGHBORS);
        return path;
    }

    private static boolean neighbors(Point p1, Point p2)
    {
        return p1.getX()+1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()+1 == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()-1 == p2.getY();
    }
}
