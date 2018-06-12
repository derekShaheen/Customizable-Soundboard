package soundboard;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 * @Author (Derek Shaheen)
 * @Author (Adam Bailey)
 * @since 4/26/18
 */
public class SoundboardModel {

    private Sound[] sounds; //array to store the sound objects
    private File soundDirectory = new File("./"); //location of the sound files
    final JFileChooser fc = new JFileChooser(soundDirectory);

    /*
    SoundboardModel the constructor for the array of sounds
    
    @param none
    */
    public SoundboardModel() {
        JOptionPane.showMessageDialog(null, "Please select a folder that contains .wav sounds!");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int fcReturn = fc.showOpenDialog(null);
        try {
            soundDirectory = fc.getSelectedFile();
            sounds = new Sound[returnSize()]; // resize array to fit all sounds
            File[] fileList = soundDirectory.listFiles(); // create a list of files in the directory
            for (int i = 0; i < fileList.length; i++) { // loop through each
                sounds[i] = new Sound(fileList[i].getName()); // create object, place into array at index, and init with the file name
            }
        } catch (NullPointerException ex) {
            System.out.println("Error " + ex);
        }
    }

    /*
    getSoundName a method that simply returns the name of the sound file based on the index given
    
    @param int index
    @return the name of the sound object at the given index in the array
    */
    public String getSoundName(int index) {
        return sounds[index].getSoundName(); // filename.wav
    }

    /*
    getDirectory a method that simply returns the directory file object
    
    @param none
    @return the directory of the sound files
    */
    public File getDirectory() {
        return this.soundDirectory;
    }

    /*
    returnSize Simply creates a file object using the relative path to the sounds, and then runs .list().length on it, which reads how many files are in it and returns that number as int
    
    @param none
    @return an int of how many files are in the soundDirectory folder
     */
    public final int returnSize() {
        try {
            int fileCount = soundDirectory.list().length;
            return fileCount;
        } catch (NullPointerException ex) {
            System.out.println("No sound directory seems to exist. Please verify the existance of directory: '" + this.soundDirectory + "' || " + ex);
            System.exit(-1);
            return -1;
        }
    }

}
