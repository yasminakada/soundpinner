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
public class FileConstruct {
    // new filename should be rec-25jan2015-1.3gpp
    public static String getDate(){
        Calendar c = Calendar.getInstance();
        Log.d("TEST", "Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy-HHmmss");
        String formattedDate = df.format(c.getTime());
        formattedDate = formattedDate.replace(".","");
        Log.d("TEST", "Formatted time => " + formattedDate);
        return formattedDate;
    }

    public static String getNewFileName(){
        String fileName = "REC-"+ getDate()+ ".3gpp";
        return fileName;
    }

    public static void createMainDirectory(){
        String folder_main = "SoundPinner";

        File f = new File(Environment.getExternalStorageDirectory(),folder_main);
        Log.d("TEST","directory exists: "+ f.isDirectory());
        if (!f.exists()) {
            Log.d("TEST", "+++++ MADE NEW DIRECTORY ++++");
            f.mkdirs();
        }
    }
    public static String getOutputPath(){
       String output = Environment.getExternalStorageDirectory()
               + "/SoundPinner/" + getNewFileName();
       return output;
    }

    // Rename a file.
    // If a name already exists, add a number to the name.
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

    public static void deleteFile(File file){
        file.delete();
    }
}
