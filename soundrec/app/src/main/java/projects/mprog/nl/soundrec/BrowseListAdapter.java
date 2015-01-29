package projects.mprog.nl.soundrec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by yasmina on 28-1-2015.
 */
public class BrowseListAdapter extends BaseAdapter {
    ArrayList<ListItem> list;
    Context context;

    BrowseListAdapter(Context c) throws IOException {
        context = c;
        list = new ArrayList<ListItem>();
        File[] files = ListUtilities.getAllFilesStored();
        String[] fileNames = ListUtilities.getAllFileNames(files);
        String[] fileDurations = ListUtilities.getAllDurations(files);
        for (int i=0; i < files.length; i++)
        {
            list.add(new ListItem(files[i],fileNames[i],fileDurations[i]));
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.list_item,parent,false);

        TextView fileName = (TextView) item.findViewById(R.id.fileName);
        TextView fileDuration = (TextView) item.findViewById(R.id.fileDuration);
        ImageView img = (ImageView) item.findViewById(R.id.image);

        ListItem temp = list.get(position);
        fileName.setText(temp.fileName);
        fileDuration.setText(temp.fileDuration);
        img.setImageResource(R.drawable.music_icon_64);
        return item;
    }
}
