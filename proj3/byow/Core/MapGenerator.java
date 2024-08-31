package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class MapGenerator {
    // SIZE of the world
    private int WIDTH;
    private int HEIGHT;

    // Seed that would be used to generate the word
    private String seed;

    /* Constructor for the class using a seed, requiere a string seed, and the size of the world
    * */
    public MapGenerator(int WIDTH, int HEIGHT, String seed){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.seed = seed;
    }

    public TETile[][] generateWorld(){
        // Wold will be store here
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeTiles(world);
        return world;
    }

    private void initializeTiles(TETile[][] world){
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

}
