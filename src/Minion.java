import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Minion extends MovingEntity{

    PathingStrategy strategy = new SingleStepPathingStrategy();
    protected Minion(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        return strategy.computePath(getPosition(), destPos, world::withinBounds, Minion::neighbors, PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS).get(0);
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> nearestMario = world.findNearest(getPosition(), Mario.class);
        Point target = new Point(0, 0);
        if (nearestMario.isPresent()){
            target = nearestMario.get().getPosition();
        }
        Entity endGoal = new EndGoal("endGoal", target, imageStore.getImageList("endGoal"));
        if (!moveTo(world, endGoal, scheduler)){
            scheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    getActionPeriod());
        }
    }

    public static boolean neighbors(Point p1, Point p2)
    {
        return p1.getX()+1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX()-1 == p2.getX() && p1.getY() == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()+1 == p2.getY() ||
                p1.getX() == p2.getX() && p1.getY()-1 == p2.getY();
    }

}
