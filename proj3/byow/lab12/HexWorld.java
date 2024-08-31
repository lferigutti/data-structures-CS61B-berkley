package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import com.sun.tools.internal.xjc.reader.dtd.bindinfo.BIAttribute;

import java.util.Arrays;
import java.util.Random;


/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int SIZE = 3;
    private static final int[] HEXPERCOL = new int[] {3,4,5,4,3};
    private static final int HEXROWS = 5;
    private static final int HEXCOLS = HEXPERCOL.length;
    private static Hexagon hexagon;
    private static int WIDTH;
    private static int HEIGHT;
    private static final long SEED = 2123;
    private static final Random RANDOM = new Random(SEED);



    private static class Hexagon {

        private static int size;
        private static int hexagonWidth;
        private static int hexagonHeight;

        // Constructor for the Class Hexagon This class could be draw in the map more than one.
        // It required a size, and it will calculate by itself the rest of the parameters
        public Hexagon(int sizeHexagon){
            size = sizeHexagon;
            hexagonWidth = calculateHexagonWidth();
            hexagonHeight = calculateHexagonHeight();
        }

        public void addHexagon(TETile[][] world, int originX, int originY, TETile teTileStyle) {

            for (int row = 0; row < size; row++) {
                drawHexRow(world, row, originX, originY, teTileStyle);
            }
            for (int row = size; row < hexagonHeight; row++) {
                drawHexRowTop(world, row, originX, originY, teTileStyle);
            }
        }

        // Todo: Re write this method to be able to combine it in one. It is not DRY at all
        private static void drawHexRow(TETile[][] world, int currentRow, int originX, int originY, TETile teTile) {
            int firstTile = size - currentRow - 1;
            int lastTile = hexagonWidth - firstTile;
            for (int x = firstTile; x < lastTile; x++) {
                world[x + originX][currentRow + originY] = teTile;
            }
        }

        private static void drawHexRowTop(TETile[][] world, int currentRow, int originX, int originY, TETile teTile) {
            int fakeRow = hexagonHeight - currentRow - 1;
            int firstTile = size - fakeRow - 1;
            int lastTile = hexagonWidth - firstTile;
            for (int x = firstTile; x < lastTile; x++) {
                world[x + originX][currentRow + originY] = teTile;
            }
        }

        public static int calculateHexagonWidth() {
            return 3 * size - 2;
        }

        public static int calculateHexagonHeight(){
            return  2 * size;
        }

        public int getWidth(){
            return hexagonWidth;
        }

        public int getHeight(){
            return hexagonHeight;
        }
    }

    private static class Coordenate{
        int x;
        int y;

        public Coordenate(int posX, int posY){
            x = posX;
            y = posY;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }
    }

    public static void initializeTiles(TETile[][] world){
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.TREE;
            case 1: return Tileset.SAND;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.GRASS;
            case 4: return Tileset.FLOWER;
            default: return Tileset.WATER;
        }
    }

    private static int calculateMapHeight(){
        return hexagon.getHeight() * HEXROWS;
    }

    private static int calculateMapWidth(){
        return calculateMapHeight();
    }

    private static Coordenate getCoordenates (int col, int row, int hexHeight){
        int posX;
        int posY;

        posX = 2 * col * SIZE - col;

        if (col % 2 == 0) {
            posY = row * hexHeight;
        } else {
            posY = SIZE + row * hexHeight;
        }
        return new Coordenate(posX, posY);
    }

    private static int getFirstRow(int col){
        if (col == 0 || col == HEXCOLS -1){
            return 1;
        } else return 0;
    }

    private static int getLastRow(int col){
        if (col == 0 || col == HEXCOLS -1){
            return HEXPERCOL[col] + 1;
        } else return HEXPERCOL[col];
    }

    private static void drawHexColumMap(TETile[][] world, int col){
        int firstRow = getFirstRow(col);
        int lastRow = getLastRow(col);
        for (int row = firstRow; row < lastRow ; row++){
            Coordenate coordenate = getCoordenates(col, row, hexagon.getHeight());
            TETile teTile = randomTile();
            hexagon.addHexagon(world, coordenate.getX(), coordenate.getY(), teTile);

        }
    }

    public static void drawHexMap(TETile[][] world){
        for (int col = 0; col < HEXCOLS ;col++){
            drawHexColumMap(world, col);
            }
        }

    public static void main(String[] args) {
        hexagon = new Hexagon(SIZE);
        HEIGHT = calculateMapHeight();
        WIDTH = calculateMapWidth();
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeTiles(world);
        drawHexMap(world);


        ter.renderFrame(world);
    }
}
