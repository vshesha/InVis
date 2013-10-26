package invis.gl.fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javax.swing.JFileChooser;

/**
 *
 * @author Matt
 */
public class FileIO {

    static public void setContents(File aFile, String aContents) throws FileNotFoundException, IOException {
        if (aFile == null) {
            throw new IllegalArgumentException("File should not be null.");
        }
        if (!aFile.exists()) {
            aFile.createNewFile();
        }
        if (!aFile.isFile()) {
            throw new IllegalArgumentException("Should not be a directory: " + aFile);
        }
        if (!aFile.canWrite()) {
            throw new IllegalArgumentException("File cannot be written: " + aFile);
        }
        //use buffering
        Writer output = new BufferedWriter(new FileWriter(aFile));
        try {//FileWriter always assumes default encoding is OK!
            output.write(aContents);
        } finally {
            output.close();
        }
    }

    static public String getContents(File aFile) {
        StringBuilder contents = new StringBuilder();
        try {
            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK!
            BufferedReader input = new BufferedReader(new FileReader(aFile));
            try {
                String line = null; //not declared within while loop
                /*
                 * readLine is a bit quirky :
                 * it returns the content of a line MINUS the newline.
                 * it returns null only for the END of the stream.
                 * it returns an empty String if two newlines appear in a row.
                 */
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
        }
        return contents.toString();
    }

    static public File OpenFile()
    {
        return (FileIO.FileDialog("Open"));
    }
    
    static public File SaveFile() 
    {
        return (FileIO.FileDialog("Save"));
    }

    static private File FileDialog(String buttonText) 
    {
        String defaultDirectory = System.getProperty("user.dir");
        final JFileChooser jfc = new JFileChooser(defaultDirectory);
        jfc.setMultiSelectionEnabled(false);
        jfc.setApproveButtonText(buttonText);
        if (jfc.showOpenDialog(jfc) != JFileChooser.APPROVE_OPTION) 
        {
            return (null);
        }
        File file = jfc.getSelectedFile();
        return (file);
    }
}
