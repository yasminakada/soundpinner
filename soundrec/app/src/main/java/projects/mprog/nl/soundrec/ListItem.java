package projects.mprog.nl.soundrec;

import java.io.File;

/**
 * Created by yasmina on 28-1-2015.
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
