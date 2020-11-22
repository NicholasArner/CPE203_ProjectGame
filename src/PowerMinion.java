import processing.core.PImage;

import java.awt.geom.PathIterator;
import java.util.List;
import java.util.Optional;

public class PowerMinion extends Minion{
    int tryCount;

    PathingStrategy strategy = new SingleStepPathingStrategy();
    protected PowerMinion(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int tryCount) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.tryCount = tryCount;
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
            if (tryCount <= 7){
            scheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    getActionPeriod());
            tryCount++;
            }
        }
    }
}
