package soundboard;
/**
 * @Author (Derek Shaheen)
 * @Author (Adam Bailey)
 * @since 4/26/18
 */
public class Sound {

    private String soundFilename = "-1"; //data member for the soundfilename 

    /*
    Sound the constructor for the sound objects
    
    @param name A String of the name of the sound
    */
    public Sound(String name) {
        String[] strSplit = name.split("\\."); // split the filename by the period
        if (strSplit.length > 1) { // This name includes a file extension
            if (strSplit[1].equals("wav")) { // Make sure the ext is .wav
                this.soundFilename = name; // set to the file name
            }
        } else {
            this.soundFilename = "-1"; // no file ext, lets not add it
        }
    }

    /*
    getSoundName a method that simply returns the name of the sound file
    
    @param none
    @return soundFilename
    */
    public String getSoundName() {
        return this.soundFilename;
    }
}
