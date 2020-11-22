import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class GoalFinder extends MovingEntity{
    private int lives = 3;
    private final PathingStrategy strategy = new AStarPathingStrategy();
    private List<Point> currentPath;
    private final WorldModel world;

    protected GoalFinder(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, WorldModel world) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.world = world;
    }

    public int getLives() {
        return lives;
    }

    public Point nextPosition(WorldModel world, Point destPos){
        // recalculate here if next point is occupied, use world.isoccupied
       //List<Point> points = strategy.computePath(getPosition(), destPos)
        if (currentPath == null) currentPath = calculatePath(world, destPos);
        if (world.isOccupied(currentPath.get(0)))
            currentPath = calculatePath(world, destPos);// recalculate path if previous path is blocked
        Point ret_point = currentPath.get(0); // otherwise continue on current path
        currentPath.remove(0); // move onto the next point
        return ret_point;
    }

    private List<Point> calculatePath(WorldModel world, Point destPos){

        return strategy.computePath(getPosition(), destPos, p-> !world.isOccupied(p) & world.withinBounds(p), GoalFinder::neighbors, PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
    }

    private static boolean neighbors(Point p1, Point p2)
    {
        return p1.getX()+1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()+1 == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()-1 == p2.getY();
    }

    public void hit(){
        if (lives == 1){
            world.removeEntity(this);
            return;
        }
        else lives -= 1;
        Point start = new Point(15, 20);
        world.moveEntity(this, start);
    }
}
