import processing.core.PImage;

import java.util.*;

/*
WorldModel ideally keeps track of the actual size of our grid world and what is in that world
in terms of entities and background elements
 */

final class WorldModel
{
   private static final int FISH_REACH = 1;
   private static final String FISH_KEY = "fish";
   private static final int FISH_NUM_PROPERTIES = 5;
   private static final int FISH_ID = 1;
   private static final int FISH_COL = 2;
   private static final int FISH_ROW = 3;
   private static final int FISH_ACTION_PERIOD = 4;

   private static final String OCTO_KEY = "octo";
   private static final int OCTO_NUM_PROPERTIES = 7;
   private static final int OCTO_ID = 1;
   private static final int OCTO_COL = 2;
   private static final int OCTO_ROW = 3;
   private static final int OCTO_LIMIT = 4;
   private static final int OCTO_ACTION_PERIOD = 5;
   private static final int OCTO_ANIMATION_PERIOD = 6;

   private static final String OBSTACLE_KEY = "obstacle";
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_ID = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;

   private static final String ATLANTIS_KEY = "atlantis";
   private static final int ATLANTIS_NUM_PROPERTIES = 4;
   private static final int ATLANTIS_ID = 1;
   private static final int ATLANTIS_COL = 2;
   private static final int ATLANTIS_ROW = 3;

   private static final String SGRASS_KEY = "seaGrass";
   private static final int SGRASS_NUM_PROPERTIES = 5;
   private static final int SGRASS_ID = 1;
   private static final int SGRASS_COL = 2;
   private static final int SGRASS_ROW = 3;
   private static final int SGRASS_ACTION_PERIOD = 4;

   private static final int PROPERTY_KEY = 0;

   private static final String BGND_KEY = "background";
   private static final int BGND_NUM_PROPERTIES = 4;
   private static final int BGND_ID = 1;
   private static final int BGND_COL = 2;
   private static final int BGND_ROW = 3;

   private static final String QUAKE_ID = "quake";
   private static final int QUAKE_ACTION_PERIOD = 1100;
   private static final int QUAKE_ANIMATION_PERIOD = 100;

   private static final String MARIO_KEY = "mario";
   private static final String LUIGI_KEY = "luigi";
   private static final int MARIO_LUIGI_NUM_PROPERTIES = 4;
   private static final int MARIO_LUIGI_ID = 1;
   private static final int MARIO_LUIGI_COL = 2;
   private static final int MARIO_LUIGI_ROW = 3;
   private static final int MARIO_LUIGI_ACTION_PERIOD = 1100;
   private static final int MARIO_LUIGI_ANIMATION_PERIOD = 100;

   private static final String BROWSER_KEY = "browser";
   private static final int BROWSER_NUM_PROPERTIES = 4;
   private static final int BROWSER_ID = 1;
   private static final int BROWSER_COL = 2;
   private static final int BROWSER_ROW = 3;
   private static final int BROWSER_ACTION_PERIOD = 1100;
   private static final int BROWSER_ANIMATION_PERIOD = 100;

   private final int numRows;
   private final int numCols;
   private final Background[][] background;
   private final Entity[][] occupancy;
   private final Set<Entity> entities;

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public int getNumRows(){ return numRows;}
   public int getNumCols(){ return numCols;}
   public Set<Entity> getEntities(){ return entities;}

   public Optional<Point> findOpenAround(Point pos)
   {
      for (int dy = -FISH_REACH; dy <= FISH_REACH; dy++)
      {
         for (int dx = -FISH_REACH; dx <= FISH_REACH; dx++)
         {
            Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
            if (withinBounds(newPt) &&
                    !isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }

   public void load(Scanner in, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!processLine(in.nextLine(), imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }

   private boolean processLine(String line, ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      //boolean test = Entity.parse();
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return parseBackground(properties, imageStore);
            case OBSTACLE_KEY:
               return parseObstacle(properties, imageStore);

            case MARIO_KEY:
               return parseMario(properties, imageStore);

            case LUIGI_KEY:
               return parseLuigi(properties, imageStore);

            case BROWSER_KEY:
               return parseBrowser(properties, imageStore);

            case OCTO_KEY:
               return parseOcto(properties, imageStore);
         }
      }

      return false;
   }

   private boolean parseBackground(String [] properties, ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         setBackground(pt,
                 new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   private boolean parseBrowser(String[] properties, ImageStore imageStore){
      if (properties.length == BROWSER_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BROWSER_COL]),
                 Integer.parseInt(properties[BROWSER_ROW]));
         Entity entity = new Mario(properties[BROWSER_ID],
                 pt,
                 imageStore.getImageList(BROWSER_KEY),
                 BROWSER_ACTION_PERIOD,
                 BROWSER_ANIMATION_PERIOD
         );
         tryAddEntity(entity);
      }

      return properties.length == BROWSER_NUM_PROPERTIES;
   }

   private boolean parseMario(String[] properties, ImageStore imageStore){
      if (properties.length == MARIO_LUIGI_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[MARIO_LUIGI_COL]),
                 Integer.parseInt(properties[MARIO_LUIGI_ROW]));
         Entity entity = new Mario(properties[MARIO_LUIGI_ID],
                 pt,
                 imageStore.getImageList(MARIO_KEY),
                 MARIO_LUIGI_ACTION_PERIOD,
                 MARIO_LUIGI_ANIMATION_PERIOD
                 );
         tryAddEntity(entity);
      }

      return properties.length == MARIO_LUIGI_NUM_PROPERTIES;
   }

   private boolean parseLuigi(String[] properties, ImageStore imageStore){
      if (properties.length == MARIO_LUIGI_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[MARIO_LUIGI_COL]),
                 Integer.parseInt(properties[MARIO_LUIGI_ROW]));
         Entity entity = new Luigi(properties[MARIO_LUIGI_ID],
                 pt,
                 imageStore.getImageList(LUIGI_KEY),
                 MARIO_LUIGI_ACTION_PERIOD,
                 MARIO_LUIGI_ANIMATION_PERIOD
         );
         tryAddEntity(entity);
      }

      return properties.length == MARIO_LUIGI_NUM_PROPERTIES;
   }

   private boolean parseOcto(String [] properties, ImageStore imageStore)
   {
      if (properties.length == OCTO_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[OCTO_COL]),
                 Integer.parseInt(properties[OCTO_ROW]));
         Entity entity = new Octo_Not_Full(properties[OCTO_ID],
                 pt,
                 imageStore.getImageList(OCTO_KEY),
                 Integer.parseInt(properties[OCTO_ACTION_PERIOD]),
                 Integer.parseInt(properties[OCTO_ANIMATION_PERIOD])
                 );
         tryAddEntity(entity);
      }

      return properties.length == OCTO_NUM_PROPERTIES;
   }

   private boolean parseObstacle(String [] properties, ImageStore imageStore)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Entity entity =  new Obstacle(properties[OBSTACLE_ID],
                 pt, imageStore.getImageList(OBSTACLE_KEY));
         tryAddEntity(entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   private void tryAddEntity(Entity entity)
   {
      if (isOccupied(entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      addEntity(entity);
   }

   private boolean withinBounds(Point pos)
   {
      return pos.getY() >= 0 && pos.getY() < this.numRows &&
              pos.getX() >= 0 && pos.getX() < this.numCols;
   }

   public boolean isOccupied(Point pos)
   {
      return withinBounds(pos) &&
              getOccupancyCell(pos) != null;
   }

   private static Optional<Entity> nearestEntity(List<Entity> entities,
                                                 Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = nearest.getPosition().distanceSquared( pos);

         for (Entity other : entities)
         {
            int otherDistance = other.getPosition().distanceSquared( pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   public Optional<Entity> findNearest(Point pos,
                                       Class kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : this.entities)
      {
         if (entity.getClass().equals(kind))
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }

   /*
    Assumes that there is no entity currently occupying the
    intended destination cell.
 */
   public void addEntity(Entity entity)
   {
      if (this.withinBounds( entity.getPosition()))
      {
         setOccupancyCell(entity.getPosition(), entity);
         this.entities.add(entity);
      }
   }

   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (withinBounds(pos) && !pos.equals(oldPos))
      {
         setOccupancyCell(oldPos, null);
         removeEntityAt(pos);
         setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (this.withinBounds(pos))
      {
         return Optional.of(getBackgroundCell(pos).getCurrentImage());
      }
      else
      {
         return Optional.empty();
      }
   }

   private void setBackground(Point pos,
                              Background background)
   {
      if (withinBounds(pos))
      {
         setBackgroundCell(pos, background);
      }
   }

   public Optional<Entity> getOccupant(Point pos)
   {
      if (isOccupied(pos))
      {
         return Optional.of(getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   private Entity getOccupancyCell(Point pos)
   {
      return this.occupancy[pos.getY()][pos.getX()];
   }

   private void setOccupancyCell(Point pos,
                                 Entity entity)
   {
      this.occupancy[pos.getY()][pos.getX()] = entity;
   }

   private Background getBackgroundCell(Point pos)
   {
      return this.background[pos.getY()][pos.getX()];
   }

   private void setBackgroundCell(Point pos,
                                  Background background)
   {
      this.background[pos.getY()][pos.getX()] = background;
   }

   public void removeEntity(Entity entity)
   {
      removeEntityAt(entity.getPosition());
   }

   private void removeEntityAt(Point pos)
   {
      if (withinBounds(pos)
              && getOccupancyCell(pos) != null)
      {
         Entity entity = getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         entities.remove(entity);
         setOccupancyCell(pos, null);
      }
   }

}