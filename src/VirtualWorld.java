import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import processing.core.*;

/*
VirtualWorld is our main wrapper
It keeps track of data necessary to use Processing for drawing but also keeps track of the necessary
components to make our world run (eventScheduler), the data in our world (WorldModel) and our
current view (think virtual camera) into that world (WorldView)
 */

public final class VirtualWorld
   extends PApplet
{
   private static final int TIMER_ACTION_PERIOD = 100;

   private static final int VIEW_WIDTH = 960;
   private static final int VIEW_HEIGHT = 720;
   private static final int TILE_WIDTH = 32;
   private static final int TILE_HEIGHT = 32;
   private static final int WORLD_WIDTH_SCALE = 1;
   private static final int WORLD_HEIGHT_SCALE = 1;

   private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
   private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
   private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
   private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

   private static final String IMAGE_LIST_FILE_NAME = "imagelist";
   private static final String DEFAULT_IMAGE_NAME = "background_default";
   private static final int DEFAULT_IMAGE_COLOR = 0x808080;

   private static final String LOAD_FILE_NAME = "world.sav";

   private static final String FAST_FLAG = "-fast";
   private static final String FASTER_FLAG = "-faster";
   private static final String FASTEST_FLAG = "-fastest";
   private static final double FAST_SCALE = 0.5;
   private static final double FASTER_SCALE = 0.25;
   private static final double FASTEST_SCALE = 0.10;

   private static double timeScale = 1.0;

   private ImageStore imageStore;
   private WorldModel world;
   private WorldView view;
   private EventScheduler scheduler;

   private long next_time;
   int minionCount = 0;
   boolean checkSpace = false;
   boolean start= true;

   private static Entity brow; // playable character
   private static Entity deadMario;
   private static boolean killedMario = false;

   public void settings()
   {
      size(VIEW_WIDTH, VIEW_HEIGHT);
   }

   /*
      Processing entry point for "sketch" setup.
   */

   public void setup()
   {
      if (key == ' '){

      this.imageStore = new ImageStore(
         createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
         createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
         TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
      checkSpace = true;
      }
   }

   public void draw()
   {
      if (start){
         textSize(40);
         fill(0, 102, 153);
         text("Welcome to:", 325, 300);
         fill(	40, 100, 10);
         text("Browser Showdown!", 275, 350);
         textSize(20);
         fill(100, 0, 0);
         text("Press Spacebar to Begin", 325, 390);
         start= false;
      }
      if (killedMario){
         fill(100, 0, 0);
         rect(275, 350, 30, 30);
         textSize(20);
         fill(100, 0, 0);
         text("BROWSER DEFEATED... VICTORY!", 285, 355);
         noLoop();
      }
      if (checkSpace){

      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         this.scheduler.updateOnTime(time);
         next_time = time + TIMER_ACTION_PERIOD;
      }

      view.drawViewport();
      }
      Predicate<Entity> checkMario = e -> e instanceof GoalFinder;
      if (world != null){
      deadMario = world.getEntities().stream().filter(checkMario).limit(1).collect(Collectors.toList()).get(0);
      if (((GoalFinder)deadMario).isOutOfLives()){
         killedMario = true;
      }}

      //update
   }


   public void mousePressed() {

      if (checkSpace){
      if (minionCount < 3){
      Point minionNew = new Point(mouseX/TILE_HEIGHT,mouseY/TILE_HEIGHT);
      MovingEntity specialMinion = new PowerMinion("minion2", minionNew, imageStore.getImageList("minion2"),
              0, 100, 0);
      world.addEntity(specialMinion);
      specialMinion.scheduleActions(scheduler, world, imageStore);

      ActiveEntity quake4 = new Quake(new Point(minionNew.getX(), minionNew.getY()-1),
              imageStore.getImageList("quake4"));
      ActiveEntity quake5 = new Quake(new Point(minionNew.getX(), minionNew.getY()+1),
              imageStore.getImageList("quake4"));
         world.setBackground(new Point(minionNew.getX(), minionNew.getY()-1),
                 new Background("eventSquare", imageStore.getImageList("eventSquare")));
         world.setBackground(new Point(minionNew.getX(), minionNew.getY()+1),
                 new Background("eventSquare", imageStore.getImageList("eventSquare")));



         world.addEntity(quake4);
      quake4.scheduleActions(scheduler, world, imageStore);
      world.addEntity(quake5);
      quake5.scheduleActions(scheduler, world, imageStore);
      minionCount++;
      redraw();}
      else{
         checkSpace = true;
         redraw();
      }}
   }

   public void keyPressed()
   {
     if (key == CODED)
      {
         int dx = 0;
         int dy = 0;



         switch (keyCode)
         {
            case UP:

              dy = -1;
               break;
            case DOWN:
              dy = 1;
               break;
            case LEFT:
               dx = -1;
               break;
            case RIGHT:
               dx = 1;
               break;
         }
         int prevX = brow.getPosition().getX();
         int prevY = brow.getPosition().getY();

         Point newPos = new Point(prevX + dx, prevY);

         world.moveEntity(brow, newPos);

         view.drawViewport();

      }
      if (key == 'm'){
         RegularMinion newMin = new RegularMinion("minion1", new Point(brow.getPosition().getX(),
                 brow.getPosition().getY()), imageStore.getImageList("minion1"), 0,0, brow.getPosition().getX());
         world.addEntity(newMin);
         newMin.scheduleActions(scheduler, world, imageStore);
      }
      setup();

   }

   private static Background createDefaultBackground(ImageStore imageStore)
   {
      return new Background(DEFAULT_IMAGE_NAME,
              imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   private static PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private static void loadImages(String filename, ImageStore imageStore,
      PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         imageStore.loadImages(in, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private static void loadWorld(WorldModel world, String filename,
      ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         world.load(in, imageStore);
         Predicate<Entity> checkBrowser = e -> e instanceof Browser;

         brow = world.getEntities().stream().filter(checkBrowser).limit(1).collect(Collectors.toList()).get(0);

      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage()); }
   }

   private static void scheduleActions(WorldModel world,
      EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.getEntities())
      {
         //Only start actions for entities that include action (not those with just animations)

            if (entity instanceof ActiveEntity){
               ActiveEntity ae = (ActiveEntity) entity;
               if (ae.getActionPeriod() > 0){
                  ae.scheduleActions(scheduler, world, imageStore);
               }

            }

      }
   }

   private static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }

   public static void main(String [] args)
   {
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
   }
}
