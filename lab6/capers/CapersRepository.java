package capers;

import java.io.File;

import static capers.Dog.fromFile;
import static capers.Utils.*;
import static  capers.Dog.DOG_FOLDER;

/** A repository for Capers 
 * @LeonardoFerigutti
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = join(CWD,".carpers");
    static File StoryFile = join(CAPERS_FOLDER, "story");

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {

        if(!CAPERS_FOLDER.exists()){
            CAPERS_FOLDER.mkdir();
            DOG_FOLDER.mkdir();
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        if(!StoryFile.exists()){
            writeContents(StoryFile, text);
        } else {
            String textToStory = readContentsAsString(StoryFile) + "\n"  +text;
            writeContents(StoryFile, textToStory);
        }
        String StoryAsString = readContentsAsString(StoryFile);
        System.out.println(StoryAsString);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        // TODO
        Dog dog = new Dog(name, breed, age);
        dog.saveDog();
        System.out.println(dog.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // TODO
        Dog dog = fromFile(name);
        dog.haveBirthday();
        dog.saveDog();
    }
}
