import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */


abstract class Entity
{
   public static final Random rand = new Random();

   //private final EntityKind kind;
   private final String id;
   private Point position;
   private final List<PImage> images;
   private int imageIndex;
   //private final int resourceLimit;
   //private int resourceCount;
   //private final int actionPeriod;
   //private final int animationPeriod;

//   private static final int PROPERTY_KEY = 0;
//   private static final String FISH_KEY = "fish";
//   private static final String OCTO_KEY = "octo";
//   private static final String OBSTACLE_KEY = "obstacle";
//   private static final String ATLANTIS_KEY = "atlantis";
//   private static final String SGRASS_KEY = "seaGrass";

//   private static final String CRAB_KEY = "crab";
//   private static final String CRAB_ID_SUFFIX = " -- crab";
//   private static final int CRAB_PERIOD_SCALE = 4;
//   private static final int CRAB_ANIMATION_MIN = 50;
//   private static final int CRAB_ANIMATION_MAX = 150;

//   private static final String FISH_KEY = "fish";
//   private static final String FISH_ID_PREFIX = "fish -- ";
//   private static final int FISH_CORRUPT_MIN = 20000;
//   private static final int FISH_CORRUPT_MAX = 30000;

//   private static final String QUAKE_KEY = "quake";

//   private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

//   private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

   protected Entity(String id, Point position,
                 List<PImage> images)
   {
      //this.kind = kind;
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      //this.resourceLimit = resourceLimit;
      //this.resourceCount = resourceCount;
      //this.actionPeriod = actionPeriod;
      //this.animationPeriod = animationPeriod;
   }

//   public Entity(String id,
//                 List<PImage> images, int resourceLimit, int resourceCount,
//                 int actionPeriod, int animationPeriod, String[] properties, int desiredLength, int row, int col)
//   {
//      if (properties.length == desiredLength){
//
//      }
//
//      Point pt = new Point(Integer.parseInt(properties[col]),
//              Integer.parseInt(properties[row]));
//      //this.kind = kind;
//      this.id = id;
//      this.position = pt;
//      this.images = images;
//      this.imageIndex = 0;
//      this.resourceLimit = resourceLimit;
//      this.resourceCount = resourceCount;
//      this.actionPeriod = actionPeriod;
//      this.animationPeriod = animationPeriod;
//   }

   //public EntityKind getKind(){ return kind;}
   public Point getPosition(){ return position;}
   public void setPosition(Point p){ position = p;}
   public List<PImage> getImages(){ return images;}
   //public int getActionPeriod(){ return actionPeriod;}
   public String getId(){ return this.id;}
   //public int getResourceLimit(){ return this.resourceLimit;}
   //public int getResourceCount(){ return this.resourceCount;}
   //public void setResourceCount(int i){ resourceCount = i;}
  //public int getAnimationPeriod() { return this.animationPeriod; }
   public int getImageIndex() { return imageIndex; }
   public void setImageIndex(int imageIndex) { this.imageIndex = imageIndex; }
   //   public void nextImage()
//   {
//      this.imageIndex = (this.imageIndex + 1) % this.images.size();
//   }

//   protected abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
//   protected abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

//   public Action createAnimationAction(int repeatCount)
//   {
//      return new Animation(this, null, null, repeatCount);
//   }
//   public Action createActivityAction(WorldModel world, ImageStore imageStore) { return new Activity(this, world, imageStore, 0); }
   public PImage getCurrentImage(){ return (this).images.get((this).imageIndex); }


//   protected static boolean generalParse(String [] properties, ImageStore imageStore, WorldModel world, int num_properties, int col, int row, int ent_id, String key){
//      if (properties.length == num_properties)
//      {
//         Point pt = new Point(
//                 Integer.parseInt(properties[col]),
//                 Integer.parseInt(properties[row]));
//         Entity entity =  new Obstacle(properties[ent_id],
//                 pt, imageStore.getImageList(key));
//         world.tryAddEntity(entity);
//      }
//
//      return properties.length == num_properties;
//   }
//   {
//      switch (this.kind)
//      {
////      case OCTO_FULL:
////         scheduler.scheduleEvent(this,
////            createActivityAction(world, imageStore),
////            this.actionPeriod);
////         scheduler.scheduleEvent(this, createAnimationAction( 0),
////                 this.getAnimationPeriod());
////         break;
//
////      case OCTO_NOT_FULL:
////         scheduler.scheduleEvent(this,
////            createActivityAction(world, imageStore),
////            this.actionPeriod);
////         scheduler.scheduleEvent(this,
////            createAnimationAction(0), this.getAnimationPeriod());
////         break;
//
////      case FISH:
////         scheduler.scheduleEvent(this,
////            createActivityAction(world, imageStore),
////            this.actionPeriod);
////         break;
//
////      case CRAB:
////         scheduler.scheduleEvent(this,
////            createActivityAction(world, imageStore),
////            this.actionPeriod);
////         scheduler.scheduleEvent(this,
////            createAnimationAction(0), this.getAnimationPeriod());
////         break;
//
////      case QUAKE:
////         scheduler.scheduleEvent(this,
////            createActivityAction(world, imageStore),
////            this.actionPeriod);
////         scheduler.scheduleEvent(this,
////            createAnimationAction(QUAKE_ANIMATION_REPEAT_COUNT),
////                 this.getAnimationPeriod());
////         break;
//
////      case SGRASS:
////         scheduler.scheduleEvent(this,
////            createActivityAction(world, imageStore),
////            this.actionPeriod);
////         break;
////      case ATLANTIS:
////         scheduler.scheduleEvent(this,
////                    createAnimationAction(ATLANTIS_ANIMATION_REPEAT_COUNT),
////                 this.getAnimationPeriod());
////            break;
////
////      default:
//      }
//   }



//   public void executeOctoFullActivity(WorldModel world,
//                                              ImageStore imageStore, EventScheduler scheduler)
//   {
//      Optional<Entity> fullTarget = world.findNearest(this.position,
//              EntityKind.ATLANTIS);
//
//      if (fullTarget.isPresent() &&
//              moveToFull(world, fullTarget.get(), scheduler))
//      {
//         //at atlantis trigger animation
//         fullTarget.get().scheduleActions(scheduler, world, imageStore);
//
//         //transform to unfull
//         transformFull(world, scheduler, imageStore);
//      }
//      else
//      {
//         scheduler.scheduleEvent(this,
//                 createActivityAction(world, imageStore),
//                 this.actionPeriod);
//      }
//   }

//   public void executeOctoNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
//   {
//      Optional<Entity> notFullTarget = world.findNearest(this.position,
//              EntityKind.FISH);
//
//      if (!notFullTarget.isPresent() ||
//              !moveToNotFull( world, notFullTarget.get(), scheduler) ||
//              !transformNotFull( world, scheduler, imageStore))
//      {
//         scheduler.scheduleEvent(this,
//                 createActivityAction(world, imageStore),
//                 this.actionPeriod);
//      }
//   }

//   public void executeFishActivity(WorldModel world,
//                                          ImageStore imageStore, EventScheduler scheduler)
//   {
//      Point pos = this.position;  // store current position before removing
//
//      world.removeEntity(this);
//      scheduler.unscheduleAllEvents(this);
//
//      Entity crab = world.createCrab(this.id + CRAB_ID_SUFFIX,
//              pos, this.actionPeriod / CRAB_PERIOD_SCALE,
//              CRAB_ANIMATION_MIN +
//                      rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
//              imageStore.getImageList(CRAB_KEY));
//
//      world.addEntity(crab);
//      crab.scheduleActions(scheduler, world, imageStore);
//   }

//   public void executeCrabActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
//   {
//      Optional<Entity> crabTarget = world.findNearest(
//              this.position, EntityKind.SGRASS);
//      long nextPeriod = this.actionPeriod;
//
//      if (crabTarget.isPresent())
//      {
//         Point tgtPos = crabTarget.get().position;
//
//         if (moveToCrab( world, crabTarget.get(), scheduler))
//         {
//            Entity quake = world.createQuake(tgtPos,
//                    imageStore.getImageList(QUAKE_KEY));
//
//            world.addEntity(quake);
//            nextPeriod += this.actionPeriod;
//            quake.scheduleActions(scheduler, world, imageStore);
//         }
//      }
//
//      scheduler.scheduleEvent(this,
//              createActivityAction(world, imageStore),
//              nextPeriod);
//   }

//   public void executeQuakeActivity(WorldModel world,
//                                           ImageStore imageStore, EventScheduler scheduler)
//   {
//      scheduler.unscheduleAllEvents(this);
//      world.removeEntity(this);
//   }

//   public void executeAtlantisActivity(WorldModel world,
//                                              ImageStore imageStore, EventScheduler scheduler)
//   {
//      scheduler.unscheduleAllEvents(this);
//      world.removeEntity(this);
//   }

//   public void executeSgrassActivity(WorldModel world,
//                                            ImageStore imageStore, EventScheduler scheduler)
//   {
//      Optional<Point> openPt = world.findOpenAround(this.position);
//
//      if (openPt.isPresent())
//      {
//         Entity fish = world.createFish(FISH_ID_PREFIX + this.id,
//                 openPt.get(), FISH_CORRUPT_MIN +
//                         rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
//                 imageStore.getImageList(FISH_KEY));
//         world.addEntity(fish);
//         fish.scheduleActions(scheduler, world, imageStore);
//      }
//
//      scheduler.scheduleEvent(this,
//              createActivityAction(world, imageStore),
//              this.actionPeriod);
//   }

//   boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
//   {
//      if (this.resourceCount >= this.resourceLimit)
//      {
//         Entity octo = world.createOctoFull(this.id, this.resourceLimit,
//                 this.position, this.actionPeriod, this.animationPeriod,
//                 this.images);
//
//         world.removeEntity(this);
//         scheduler.unscheduleAllEvents(this);
//
//         world.addEntity(octo);
//         octo.scheduleActions(scheduler, world, imageStore);
//
//         return true;
//      }
//
//      return false;
//   }

//   void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
//   {
//      Entity octo = world.createOctoNotFull(this.id, this.resourceLimit,
//              this.position, this.actionPeriod, this.animationPeriod,
//              this.images);
//
//      world.removeEntity(this);
//      scheduler.unscheduleAllEvents(this);
//
//      world.addEntity(octo);
//      octo.scheduleActions(scheduler, world, imageStore);
//   }

//   boolean moveToNotFull(WorldModel world,
//                         Entity target, EventScheduler scheduler)
//   {
//      if (this.position.adjacent(target.position))
//      {
//         this.resourceCount += 1;
//         world.removeEntity(target);
//         scheduler.unscheduleAllEvents(target);
//
//         return true;
//      }
//      else
//      {
//         Point nextPos = nextPositionOcto(world, target.position);
//
//         if (!this.position.equals(nextPos))
//         {
//            Optional<Entity> occupant = world.getOccupant(nextPos);
//            if (occupant.isPresent())
//            {
//               scheduler.unscheduleAllEvents(occupant.get());
//            }
//
//            world.moveEntity(this, nextPos);
//         }
//         return false;
//      }
//   }
//   boolean moveToFull(WorldModel world,
//                      Entity target, EventScheduler scheduler)
//   {
//      if (this.position.adjacent(target.position))
//      {
//         return true;
//      }
//      else
//      {
//         Point nextPos = nextPositionOcto( world, target.position);
//
//         if (!this.position.equals(nextPos))
//         {
//            Optional<Entity> occupant = world.getOccupant(nextPos);
//            if (occupant.isPresent())
//            {
//               scheduler.unscheduleAllEvents(occupant.get());
//            }
//
//            world.moveEntity(this, nextPos);
//         }
//         return false;
//      }
//   }

//   boolean moveToCrab(WorldModel world,
//                      Entity target, EventScheduler scheduler)
//   {
//      if (this.position.adjacent(target.position))
//      {
//         world.removeEntity(target);
//         scheduler.unscheduleAllEvents(target);
//         return true;
//      }
//      else
//      {
//         Point nextPos = nextPositionCrab(world, target.position);
//
//         if (!this.position.equals(nextPos))
//         {
//            Optional<Entity> occupant = world.getOccupant(nextPos);
//            if (occupant.isPresent())
//            {
//               scheduler.unscheduleAllEvents(occupant.get());
//            }
//
//            world.moveEntity(this, nextPos);
//         }
//         return false;
//      }
//   }

//   Point nextPositionOcto(WorldModel world,
//                          Point destPos)
//   {
//      int horiz = Integer.signum(destPos.getX() - this.position.getX());
//      Point newPos = new Point(this.position.getX() + horiz,
//              this.position.getY());
//
//      if (horiz == 0 || world.isOccupied(newPos))
//      {
//         int vert = Integer.signum(destPos.getY() - this.position.getY());
//         newPos = new Point(this.position.getX(),
//                 this.position.getY() + vert);
//
//         if (vert == 0 || world.isOccupied(newPos))
//         {
//            newPos = this.position;
//         }
//      }
//
//      return newPos;
//   }

//   private Point nextPositionCrab(WorldModel world,
//                                        Point destPos)
//   {
//      int horiz = Integer.signum(destPos.getX() - this.position.getX());
//      Point newPos = new Point(this.position.getX() + horiz,
//              this.position.getY());
//
//      Optional<Entity> occupant = world.getOccupant(newPos);
//
//      if (horiz == 0 ||
//              (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
//      {
//         int vert = Integer.signum(destPos.getY() - this.position.getY());
//         newPos = new Point(this.position.getX(), this.position.getY() + vert);
//         occupant = world.getOccupant(newPos);
//
//         if (vert == 0 ||
//                 (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
//         {
//            newPos = this.position;
//         }
//      }
//
//      return newPos;
//   }

}