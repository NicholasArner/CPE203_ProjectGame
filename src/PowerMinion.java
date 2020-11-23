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
    protected boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
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

    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        return strategy.computePath(getPosition(), destPos, world::withinBounds, Minion::neighbors, PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS).get(0);
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> nearestGoalFinder = world.findNearestGoalFinder(getPosition());
        Point target = new Point(0, 0);
        if (nearestGoalFinder.isPresent()){
            target = nearestGoalFinder.get().getPosition();
        }
        Entity endGoal = new EndGoal("endGoal", target, imageStore.getImageList("endGoal"));
        if (!moveTo(world, endGoal, scheduler)){
            if (tryCount <= 7){
            scheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    getActionPeriod());
            tryCount++;
            }
            else{
                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);
            }
        }
        else{
            // minion should already be there
            GoalFinder goalFinder;
            if (nearestGoalFinder.isPresent()){
                goalFinder = (GoalFinder) nearestGoalFinder.get();
                if (getPosition().adjacent(goalFinder.getPosition()))
                    goalFinder.hit();
                if (goalFinder.isOutOfLives()) scheduler.unscheduleAllEvents(goalFinder);
            }
            //world.moveEntity(this, target);
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
        }
    }
}
