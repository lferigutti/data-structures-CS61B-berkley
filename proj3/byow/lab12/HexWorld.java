package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private static final int SIZE = 4;


    private static class Hexagon {

        private static int size;
        private static int hexagonWidth;
        private static int hexagonHeight;

        // Constructor for the Class Hexagon.
        // It required a size, and it will calculate by itself the rest of the parameters
        public Hexagon(int sizeHexagon){
            size = sizeHexagon;
            hexagonWidth = calculateHexagonWidth();
            hexagonHeight = calculateHexagonHeight();
        }

        public void addHexagon(TETile[][] world) {

            for (int row = 0; row < size; row++) {
                drawHexRow(world, row, hexagonWidth);
            }
            for (int row = size; row < hexagonHeight; row++) {
                drawHexRowTop(world, row, hexagonWidth, hexagonHeight);
            }
        }

        private static void drawHexRow(TETile[][] world, int currentRow, int hexWidth) {
            int firstTile = size - currentRow - 1;
            int lastTile = hexWidth - firstTile;
            for (int x = firstTile; x < lastTile; x++) {
                world[x][currentRow] = Tileset.MOUNTAIN;
            }
        }

        private static void drawHexRowTop(TETile[][] world, int currentRow, int hexWidth, int hexHeight) {
            int fakeRow = hexHeight - currentRow - 1;
            int firstTile = size - fakeRow - 1;
            int lastTile = hexWidth - firstTile;
            for (int x = firstTile; x < lastTile; x++) {
                world[x][currentRow] = Tileset.MOUNTAIN;
            }
        }

        public static int calculateHexagonWidth() {
            return 3 * size - 2;
        }

        public static int calculateHexagonHeight(){
            return  2 * size;
        }
    }

    public static void initializeTiles(TETile[][] world){
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeTiles(world);

        Hexagon hexagon = new Hexagon(SIZE);
        hexagon.addHexagon(world);


        ter.renderFrame(world);
    }
}
