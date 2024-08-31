package gitlet;

// TODO: any imports you need here

import java.util.Date; // TODO: You'll likely use this in this class

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit {
    /*
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;

    /** The datetime of this Commit. */
    private Date datetime;

    /** The parent of this Commit. */
    private Commit parent;

    /** The SHA-1 hash of this Commit. */
    private String sha1;


    public void Commit() {
        message = "initial commit";
        datetime = new Date();
    }
}

