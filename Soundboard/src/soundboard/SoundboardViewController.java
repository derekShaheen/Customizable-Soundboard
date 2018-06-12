/*
 * @Author  (Derek Shaheen)
 * @Author  (Adam Bailey)
 * @since   4/26/18
 */
package soundboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JButton;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class SoundboardViewController extends javax.swing.JFrame {

    private final SoundboardModel model = new SoundboardModel();

    /*
    SoundboardViewController the constructor that calls the initComponents and generateBoard methods
    
    @param none
    @return none
     */
    public SoundboardViewController() {
        initComponents(); // Create jFrame
        generateBoard(); // Generate buttons based on number of sound files in the model
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dynamic Soundboard (Bailey / Shaheen)");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("frame"); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(900, 500));
        getContentPane().setLayout(new java.awt.GridLayout(3, 3, 50, 50));

        setBounds(0, 0, 416, 339);
    }// </editor-fold>//GEN-END:initComponents

    /*
    generateBoard the method that generates the gui for the soundboard
    
    @param none
    @return none
     */
    private void generateBoard() { // Generate soundboard based on number of sound files returned by the model
        int buttonActuallyCreated = 0;
        int numButtons = model.returnSize(); // returns number of sound files. This tells us how many buttons we need
        if (numButtons > 1 && numButtons < 100) { // sound files were detected // don't display if more than 100 files detected
            JButton[] genButton = new JButton[numButtons]; // allocate new buttons
            for (int i = 0; i < numButtons; i++) { // for each sound file create new button
                String soundName = model.getSoundName(i); // set this so we don't need to call the model over and over
                if (!soundName.equals("-1")) { // Button was marked to be deleted as it did not have the correct file ext
                    buttonActuallyCreated++;
                    genButton[i] = new JButton();
                    if (soundName.length() > 11) {
                        genButton[i].setFont(new java.awt.Font("Calibri", 0, 38 - soundName.length())); // Adjust the font if name is too long. Doesn't always work great
                    } else {
                        genButton[i].setFont(new java.awt.Font("Calibri", 0, 38));
                    }
                    String[] strSplit = soundName.split("\\."); // split the filename by the period // Here we are removing the file ext from the name if there is one to display on the button
                    if (strSplit.length > 1) { // This name includes a file extension
                        if (strSplit[1].equals("wav")) { // Make sure the ext is .wav
                            genButton[i].setText(strSplit[0]); // Set the text of each button without the file ext
                        }
                    } else {
                        genButton[i].setText(soundName); // no file ext, set it anyway. Could still be a sound file?
                    }
                    genButton[i].setPreferredSize(new java.awt.Dimension(150, 100));
                    genButton[i].addActionListener((java.awt.event.ActionEvent evt) -> {
                        // register ActionEvent for button
                        jButtonActionPerformed(evt); // Button clicked!
                    });
                    getContentPane().add(genButton[i]); // add newly generated button to the pane
                    genButton[i].getAccessibleContext().setAccessibleName(String.valueOf(i)); // return the index of sound file requested
                }
            }
            if (buttonActuallyCreated > 2) { // less than 2 throws error on the next line. No need to reformat for less than 2 anyway 
                getContentPane().setLayout(new java.awt.GridLayout(buttonActuallyCreated / 3, 0)); // set grid based on number of sound files  // usually looks pretty good
            }
        } else { // no files detected or too many files were detected, show close button
            getContentPane().setLayout(new java.awt.GridLayout(1, 1));
            JButton genButton; // allocate exit button
            genButton = new javax.swing.JButton();
            genButton.setFont(new java.awt.Font("Calibri", 0, 15));
            genButton.setText("Error, see sout"); // Set the text of button
            genButton.setPreferredSize(new java.awt.Dimension(250, 100));
            genButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                // register ActionEvent for button
                System.out.println("Error was found with numButtons = " + numButtons);
                System.exit(-1); // return error -1
            });
            getContentPane().add(genButton); // add newly generated button to the pane
        }
        pack(); // resize window based on new button layout
        //repaint(); // paint updates
        this.setLocationRelativeTo(null); // Center the window
        this.setVisible(true); // set the window to visible after generating the buttons
    }

    /*
    jButtonActionPerformed the method that generates the gui for the soundboard
    
    @return none
     */
    private void jButtonActionPerformed(java.awt.event.ActionEvent evt) { // Respond to any button click                                        
        JButton selButton = (JButton) evt.getSource(); // Which button was clicked?
        int index = Integer.parseInt(selButton.getAccessibleContext().getAccessibleName()); // index of the button selected, 0 to total number of files
        if (!playSound(index)) { // if the sound fails to play, let's remove the button all together
            getContentPane().remove(selButton);
            pack();
            repaint(); // paint updates
        }
    }

    /*
    playSound the method that generates the audiostream in order to play the sound
    
    @param int index the index of the sound in the sounds array, giving the location of the file
    @return boolean true if the sound played, false if it didn't
     */
    private boolean playSound(int index) {
        InputStream sound;
        try {
            sound = new FileInputStream(new File(model.getDirectory() + "\\" + model.getSoundName(index)));
            AudioStream audio = new AudioStream(sound);
            AudioPlayer.player.start(audio);
            return true; // sound played!
        } catch (IOException ex) {
            return false; // sound did not play // no need to log this, lets just remove the button...
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SoundboardViewController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SoundboardViewController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SoundboardViewController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SoundboardViewController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SoundboardViewController soundboardViewController = new SoundboardViewController();
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
