package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    public static final double CONCERT_A = 440.0;
    public static final int NUMBER_STRINGS = 37;

    public static void main(String[] args) {
        GuitarString[] guitar = new GuitarString[NUMBER_STRINGS];
        /* create two guitar strings, for concert A and C */
        for (int i = 0; i < NUMBER_STRINGS; i++) {
            double frecuency = CONCERT_A * Math.pow(2, (i - 24) / 12.0);
            System.out.println(frecuency);
            GuitarString string = new GuitarString(frecuency);
            guitar[i] = string;
        }

        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int indexKey = keyboard.indexOf(key);
                if (indexKey != -1) {
                    guitar[indexKey].pluck();
                }
            }
            double sample = 0.0;
            for (int i = 0; i < guitar.length; i++) {
               sample = sample + guitar[i].sample();
            }
            StdAudio.play(sample);
            for (int j = 0; j < guitar.length; j++) {
                guitar[j].tic();
            }
        }
        }
}
