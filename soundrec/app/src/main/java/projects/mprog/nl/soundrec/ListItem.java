package projects.mprog.nl.soundrec;

import java.io.File;


/**
 * Yasmina Kada
 * Programming Project 2015
 * 10001567
 */

public class ListItem{
    File file;
    String fileName;
    String fileDuration;
    ListItem(File file,String fileName,String fileDuration){
        this.file=file;
        this.fileName=fileName;
        this.fileDuration=fileDuration;

    }
}
