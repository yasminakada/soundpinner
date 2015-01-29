package projects.mprog.nl.soundrec;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Yasmina Kada
 * Programming Project 2015
 * 10001567
 */

public class FileUtilities {

    // Used to auto-generate unique filenames.
    public static String getDate(){
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy-HHmmss");
        String formattedDate = df.format(c.getTime());
        formattedDate = formattedDate.replace(".","");
        return formattedDate;
    }

    // Auto-generate a filename.
    public static String getNewFilename(){
        String filename = "REC-"+ getDate()+ ".3gpp";
        return filename;
    }

    // Returns the path of the main directory.
    public static String getMainDirectory(){
        String path = Environment.getExternalStorageDirectory()
                + "/SoundPinner/";
        return path;
    }

    // Creates a new main directory when it does not exist.
    public static void createMainDirectory(){
        String folder_main = "SoundPinner";

        File f = new File(Environment.getExternalStorageDirectory(),folder_main);
        if (!f.exists()) {
            Log.d("TEST", "+++++ MADE NEW DIRECTORY ++++");
            f.mkdirs();
        }
    }

    public static String getOutputPath(){
       String output = Environment.getExternalStorageDirectory()
               + "/SoundPinner/" + getNewFilename();
       return output;
    }

    // Handles renaming of files. Checks for files so no file will be overwritten with the same name.
    public static void renameFile(File file, String newName){
        String newNameExtended = newName + ".3gpp";
        File newFile = new File(file.getParent(),newNameExtended);
        if (newFile.exists()){
            int counter = 1;
            while(newFile.exists()){
                newFile = new File(file.getParent(),newName +"(" + counter + ")" + ".3gpp");
                counter++;
            }
        }
        file.renameTo(newFile);
    }

    // Removes the ".3gpp" extension from a string.
    public static String stripFilename(String filename){
        return filename.replace(".3gpp", "");
    }
}
