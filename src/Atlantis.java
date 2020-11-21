import processing.core.PImage;

import java.util.List;

public class Atlantis extends AnimatedEntity{
    private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

//    public Atlantis(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
//        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
//    }

//    public Atlantis(String[] properties, ImageStore imageStore){
//        super(properties[ATLANTIS_ID], imageStore.getImageList(ATLANTIS_KEY), 0, 0, 0, 0, properties, ATLANTIS_NUM_PROPERTIES, ATLANTIS_ROW, ATLANTIS_COL);
//    }

    protected Atlantis(String id, Point position, List<PImage> images){
        super(id, position, images, 0, 0);
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    @Override
    protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                createAnimationAction(ATLANTIS_ANIMATION_REPEAT_COUNT),
                this.getAnimationPeriod());
    }

//    public static boolean parse(String[] properties, ImageStore imageStore, WorldModel world){
//        return generalParse(properties, imageStore, world, ATLANTIS_NUM_PROPERTIES, ATLANTIS_COL, ATLANTIS_ROW, ATLANTIS_ID, ATLANTIS_KEY);
//    }
}
