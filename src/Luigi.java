import processing.core.PImage;

import java.util.List;

public class Luigi extends GoalFinder{
    protected Luigi(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point goalPoint = new Point(5, 0); // location of the right goal
        Entity endGoal = new EndGoal("endGoal", goalPoint, imageStore.getImageList("endGoal"));
        if (!moveTo(world, endGoal, scheduler)){
            scheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    getActionPeriod());
        }
    }
}
