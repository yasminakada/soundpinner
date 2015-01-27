package projects.mprog.nl.soundrec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.io.File;

/**
 * Created by yasmina on 26-1-2015.
 */
public class RenameDialogFragment extends DialogFragment {
    String message = "Give this recording a name.";
    EditText editText;
    String oldFilename = "";
    File oldFile;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_rename,null);
        editText = (EditText) view.findViewById(R.id.editTextRenameDialog);
        editText.setText("");
        editText.append(oldFilename);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Rename file")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String filename = editText.getText().toString();
                        FileConstruct.renameFile(oldFile,filename);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
    public void setFile(File file){
        oldFile = file;
        oldFilename = file.getName();
        oldFilename = oldFilename.replace(".3gpp", "");
    }
}