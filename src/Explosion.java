import processing.core.PImage;

import java.util.List;

public class Explosion extends AnimatedEntity{
    private static final int EXPLOSION_ANIMATION_REPEAT_COUNT = 10;
    private static final String EXPLOSION_ID = "explosion";
    private static final int EXPLOSION_ACTION_PERIOD = 1100;
    private static final int EXPLOSION_ANIMATION_PERIOD = 500;

    protected Explosion(Point position, List<PImage> images){
        super(EXPLOSION_ID, position, images, EXPLOSION_ACTION_PERIOD, EXPLOSION_ANIMATION_PERIOD);
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    @Override
    protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                createActivityAction(world, imageStore),
                super.getActionPeriod());
        scheduler.scheduleEvent(this,
                createAnimationAction(EXPLOSION_ANIMATION_REPEAT_COUNT),
                this.getAnimationPeriod());
    }
}
