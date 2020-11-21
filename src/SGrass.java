import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class SGrass extends StaticEntity{
    private static final String FISH_KEY = "fish";
    private static final String FISH_ID_PREFIX = "fish -- ";
    private static final int FISH_CORRUPT_MIN = 20000;
    private static final int FISH_CORRUPT_MAX = 30000;

//    public SGrass(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
//        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
//    }

//    // these replace parse methods?
//    public SGrass(String[] properties, ImageStore imageStore){
//        super(properties[SGRASS_ID], imageStore.getImageList(SGRASS_KEY), 0, 0, Integer.parseInt(properties[SGRASS_ACTION_PERIOD]), 0, properties, SGRASS_NUM_PROPERTIES, SGRASS_ROW, SGRASS_COL);
//    }

    protected SGrass(String id, Point position, int actionPeriod, List<PImage> images){
        super(id, position, images, 0, 0, actionPeriod, 0);
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Point> openPt = world.findOpenAround(super.getPosition());

        if (openPt.isPresent())
        {
            ActiveEntity fish = new Fish(FISH_ID_PREFIX + super.getId(),
                    openPt.get(), FISH_CORRUPT_MIN +
                            rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
                    imageStore.getImageList(FISH_KEY));
            world.addEntity(fish);
            fish.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                createActivityAction(world, imageStore),
                super.getActionPeriod());
    }

//    @Override
//    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
//        scheduler.scheduleEvent(this,
//                createActivityAction(world, imageStore),
//                this.getActionPeriod());
//    }
}
