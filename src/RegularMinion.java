import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class RegularMinion extends Minion{
    int bowsX;
    PathingStrategy strategy = new DeterminedPathingStrategy();
    protected RegularMinion(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int bowsX) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.bowsX=bowsX;

    }

    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        List<Point> path = strategy.computePath(getPosition(), destPos, world::withinBounds, Minion::neighbors, PathingStrategy.CARDINAL_NEIGHBORS);
        if (path.size() == 0){
            return null;
        }
        return path.get(0);
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> nearestMario = world.findNearest(getPosition(), Mario.class);
        Point target = new Point(bowsX, 21);
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
}
