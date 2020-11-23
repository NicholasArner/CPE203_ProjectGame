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
    private boolean outOfLives;
    private boolean frozen = false;
    private int frozenCount;

    protected GoalFinder(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, WorldModel world) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.world = world;
    }

    public boolean isOutOfLives() {
        return outOfLives;
    }

    public void setOutOfLives(boolean outOfLives) {
        this.outOfLives = outOfLives;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void freeze() {
        this.frozen = true;
        frozenCount = 0;
    }

    public WorldModel getWorld() {
        return world;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Point nextPosition(WorldModel world, Point destPos){
        // recalculate here if next point is occupied, use world.isoccupied
       //List<Point> points = strategy.computePath(getPosition(), destPos)
        if (frozenCount > 3){ // when frozen, stay this way for 3 activityPeriods
            frozen = false;
        }

        if (!frozen){
            if (currentPath == null)  currentPath = calculatePath(world, destPos);

            if (currentPath.size() == 0)
                return getPosition(); // the entity has reached its goal or is stuck

            if (world.isOccupied(currentPath.get(0))){
                currentPath = calculatePath(world, destPos);// recalculate path if previous path is blocked
            }

            if (!neighbors(currentPath.get(0), getPosition()))
                currentPath = calculatePath(world, destPos); // recalculate path if nextPos is not next to current pos, ie entity was moved by something else

            Point ret_point = currentPath.get(0); // otherwise continue on current path
            currentPath.remove(0); // move onto the next point

            return ret_point;
        }
        frozenCount += 1;
        return getPosition();
    }
    @Override
    protected boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler){
        if (this.getPosition().adjacent(target.getPosition())) return true;
        else
        {
            Point nextPos = nextPosition(world, target.getPosition());
            if (nextPos == null){
                return true;
            }

            if (!this.getPosition().equals(nextPos))
            {
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    private List<Point> calculatePath(WorldModel world, Point destPos){

        return strategy.computePath(getPosition(), destPos, p-> !world.isOccupied(p) & world.withinBounds(p), GoalFinder::neighbors, PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
    }

    private static boolean neighbors(Point p1, Point p2)
    {
        return p1.getX()+1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()+1 == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()-1 == p2.getY() ||
                p1.getX()+1 == p2.getX() && p1.getY()+1 == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY()-1 == p2.getY() ||
                p1.getX()+1 == p2.getX() && p1.getY()-1 == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY()+1 == p2.getY();
    }

    public abstract void hit();
}
