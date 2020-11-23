import processing.core.PImage;

import java.util.List;

public class Mario extends GoalFinder{

    protected Mario(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, WorldModel world) {
        super(id, position, images, actionPeriod, animationPeriod, world);
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point goalPoint = new Point(25, 0); // location of the right goal
        Entity endGoal = new EndGoal("endGoal", goalPoint, imageStore.getImageList("endGoal"));
        if (!moveTo(world, endGoal, scheduler)){
            scheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    getActionPeriod());
        }
    }

    public void hit(){
        if (getLives() == 1){

        }
        else setLives(getLives() - 1);
        Point start = new Point(16, 20);
        getWorld().moveEntity(this, start);
    }
}
