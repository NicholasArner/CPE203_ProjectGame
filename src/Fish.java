import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Fish extends StaticEntity{
    private static final String CRAB_KEY = "crab";
    private static final String CRAB_ID_SUFFIX = " -- crab";
    private static final int CRAB_PERIOD_SCALE = 4;
    private static final int CRAB_ANIMATION_MIN = 50;
    private static final int CRAB_ANIMATION_MAX = 150;


//    public Fish(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
//        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
//    }
//
//    public Fish(String[] properties, ImageStore imageStore){
//        super(properties[FISH_ID], imageStore.getImageList(FISH_KEY), 0, 0, Integer.parseInt(properties[FISH_ACTION_PERIOD]), 0, properties, FISH_NUM_PROPERTIES, FISH_ROW, FISH_COL);
//    }


    protected Fish(String id, Point position, int actionPeriod, List<PImage> images){
        super(id, position, images, 0, 0, actionPeriod, 0);
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Point pos = super.getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        ActiveEntity crab = new Crab(super.getId() + CRAB_ID_SUFFIX,
                pos, super.getActionPeriod() / CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN +
                        rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
                imageStore.getImageList(CRAB_KEY));

        world.addEntity(crab);
        crab.scheduleActions(scheduler, world, imageStore);
    }

//    @Override
//    protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
//        scheduler.scheduleEvent(this,
//                createActivityAction(world, imageStore),
//                super.getActionPeriod());
//    }

//    public static boolean parse(String[] properties, ImageStore imageStore, WorldModel world) {
//        return generalParse(properties, imageStore, world, FISH_NUM_PROPERTIES, FISH_COL, FISH_ROW, FISH_ID, FISH_KEY);
//    }

}
