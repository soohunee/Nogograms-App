package edu.skku.map.pa22016314364parksoohun;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GridAdapter extends BaseAdapter {

    private Context context;
    Bitmap bitmap[];
    String wlist[];
    public GridAdapter(Context ctx, Bitmap arr[]){
        context = ctx;
        bitmap = arr;
    }

    @Override
    public int getCount() {
        return bitmap.length;
    }

    @Override
    public Object getItem(int position) {
        return bitmap[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.gridview_layout, viewGroup, false);
        }
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap[i]);

        return view;
    }
}