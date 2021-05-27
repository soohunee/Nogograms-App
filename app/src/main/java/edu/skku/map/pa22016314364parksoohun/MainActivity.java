package edu.skku.map.pa22016314364parksoohun;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    Bitmap naver = null;
    Bitmap galleryImage = null;
    int[] nono = new int[400];
    int correct_length = 0;
    int correct_click = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView row1 = findViewById(R.id.row1);
        TextView row2 = findViewById(R.id.row2);
        TextView row3 = findViewById(R.id.row3);
        TextView row4 = findViewById(R.id.row4);
        TextView row5 = findViewById(R.id.row5);
        TextView row6 = findViewById(R.id.row6);
        TextView row7 = findViewById(R.id.row7);
        TextView row8 = findViewById(R.id.row8);
        TextView row9 = findViewById(R.id.row9);
        TextView row10 = findViewById(R.id.row10);
        TextView row11 = findViewById(R.id.row11);
        TextView row12 = findViewById(R.id.row12);
        TextView row13 = findViewById(R.id.row13);
        TextView row14 = findViewById(R.id.row14);
        TextView row15 = findViewById(R.id.row15);
        TextView row16 = findViewById(R.id.row16);
        TextView row17 = findViewById(R.id.row17);
        TextView row18 = findViewById(R.id.row18);
        TextView row19 = findViewById(R.id.row19);
        TextView row20 = findViewById(R.id.row20);
        TextView col1 = findViewById(R.id.col1);
        TextView col2 = findViewById(R.id.col2);
        TextView col3 = findViewById(R.id.col3);
        TextView col4 = findViewById(R.id.col4);
        TextView col5 = findViewById(R.id.col5);
        TextView col6 = findViewById(R.id.col6);
        TextView col7 = findViewById(R.id.col7);
        TextView col8 = findViewById(R.id.col8);
        TextView col9 = findViewById(R.id.col9);
        TextView col10 = findViewById(R.id.col10);
        TextView col11 = findViewById(R.id.col11);
        TextView col12 = findViewById(R.id.col12);
        TextView col13 = findViewById(R.id.col13);
        TextView col14 = findViewById(R.id.col14);
        TextView col15 = findViewById(R.id.col15);
        TextView col16 = findViewById(R.id.col16);
        TextView col17 = findViewById(R.id.col17);
        TextView col18 = findViewById(R.id.col18);
        TextView col19 = findViewById(R.id.col19);
        TextView col20 = findViewById(R.id.col20);
        EditText search_word = findViewById(R.id.search_word);
        Button search = findViewById(R.id.search);
        Button gallery = findViewById(R.id.gallery);
        GridView gridView = findViewById(R.id.gridView);
        gridView.setVerticalSpacing(10);

        Bitmap blank = BitmapFactory.decodeResource(getResources(), R.drawable.blank);
        Bitmap black = BitmapFactory.decodeResource(getResources(), R.drawable.black);
        Bitmap cropped_black = Bitmap.createBitmap(black, 20, 20, 40, 40);
        Bitmap bitmaps[] = split(blank, 20);
        Bitmap org_bitmaps[] = bitmaps.clone();
        GridAdapter gridAdapter = new GridAdapter(this, bitmaps);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(nono[position] == 0){
                    Toast.makeText(getApplicationContext(), "Wrong Click !!", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < 400; i++) {
                        Arrays.asList(bitmaps).set(i, org_bitmaps[i]);
                        gridAdapter.notifyDataSetChanged();
                    }
                    correct_click = 0;
                }
                else{
                    bitmaps[position] = cropped_black;
                    gridAdapter.notifyDataSetChanged();
                    correct_click++;
                }
                if (correct_length == correct_click){
                    Toast.makeText(getApplicationContext(), "Completed !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = search_word.getText().toString();
                OkHttpClient client = new OkHttpClient();
                Gson gson = new GsonBuilder().create();
                SearchApi api = new SearchApi();

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        String result = api.main(keyword);
                        ImageLink imageLink = gson.fromJson(result, ImageLink.class);
                        String link = imageLink.getItems().get(0).get("link");
                        try {
                            URL url = new URL(link);
                            naver = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            naver = Bitmap.createScaledBitmap(naver, 320, 380, false);
                            naver = grayScale(naver);
                            nono = nonotialize(naver);
                            correct_length = 0;
                            for (int i=0;i<400;i++){
                                if (nono[i] == 1) {
                                    correct_length++;
                                }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String a="";
                                row1.setText(rowCal(0));
                                row2.setText(rowCal(1));
                                row3.setText(rowCal(2));
                                row4.setText(rowCal(3));
                                row5.setText(rowCal(4));
                                row6.setText(rowCal(5));
                                row7.setText(rowCal(6));
                                row8.setText(rowCal(7));
                                row9.setText(rowCal(8));
                                row10.setText(rowCal(9));
                                row11.setText(rowCal(10));
                                row12.setText(rowCal(11));
                                row13.setText(rowCal(12));
                                row14.setText(rowCal(13));
                                row15.setText(rowCal(14));
                                row16.setText(rowCal(15));
                                row17.setText(rowCal(16));
                                row18.setText(rowCal(17));
                                row19.setText(rowCal(18));
                                row20.setText(rowCal(19));
                                col1.setText(colCal(0));
                                col2.setText(colCal(1));
                                col3.setText(colCal(2));
                                col4.setText(colCal(3));
                                col5.setText(colCal(4));
                                col6.setText(colCal(5));
                                col7.setText(colCal(6));
                                col8.setText(colCal(7));
                                col9.setText(colCal(8));
                                col10.setText(colCal(9));
                                col11.setText(colCal(10));
                                col12.setText(colCal(11));
                                col13.setText(colCal(12));
                                col14.setText(colCal(13));
                                col15.setText(colCal(14));
                                col16.setText(colCal(15));
                                col17.setText(colCal(16));
                                col18.setText(colCal(17));
                                col19.setText(colCal(18));
                                col20.setText(colCal(19));


                                for(int i=0;i<400;i++){
                                    Arrays.asList(bitmaps).set(i, org_bitmaps[i]);
                                }
                                gridAdapter.notifyDataSetChanged();
                                correct_click = 0;
                                gridView.setAdapter(gridAdapter);
                            }
                        });
                    }
                };
                thread.start();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 10);
                for(int i=0;i<400;i++){
                    Arrays.asList(bitmaps).set(i, org_bitmaps[i]);
                }
                gridAdapter.notifyDataSetChanged();
                correct_click = 0;
                gridView.setAdapter(gridAdapter);

            }

        });
    }
    private String colCal(int x){
        String a = "";
        int count = 0;
        for(int y = 0;y<20;y++){
            if(nono[x+20*y]==1){
                count++;
            }
            else{
                String count_st = Integer.toString(count);
                if ( count == 0){
                    continue;
                }
                else{
                    a += count_st + '\n';
                    count = 0;
                }
            }
        }
        if (count == 20){
            a += "20";
        }
        else{
            String count_st = Integer.toString(count);
            a += count_st;
        }
        return a;
    }

    private String rowCal(int x){
        String a = "";
        int count = 0;
        for(int y = 0;y<20;y++){
            if(nono[20*x+y]==1){
                count++;
            }
            else{
                String count_st = Integer.toString(count);
                if ( count == 0){
                    continue;
                }
                else{
                    a += count_st + ' ';
                    count = 0;
                }
            }
        }
        if (count == 20){
            a += "20";
        }
        else{
            String count_st = Integer.toString(count);
            a += count_st;
        }
        return a;
    }
    private int[] nonotialize(Bitmap gray){
        int[] nono = new int[400];
        int w = gray.getWidth();
        int h = gray.getHeight();
        int crop_w = w/20;
        int crop_h = h/20;
        int pixel;
        int sum = 0;
        float avg;
        for (int x=0;x<20;x++){
            for (int y=0;y<20;y++){
                pixel = 0;
                sum = 0;
                Bitmap temp = Bitmap.createBitmap(gray, crop_w*x, crop_h*y, crop_w, crop_h);
                for(int xx=0;xx<crop_w;xx++){
                    for(int yy=0;yy<crop_h;yy++){
                        pixel = temp.getPixel(xx,yy);
                        int A = Color.alpha(pixel);
                        int R = Color.red(pixel);
                        int G = Color.green(pixel);
                        int B = Color.blue(pixel);
                        int grayv = (int) (0.2990 * R + 0.5870 * G + 0.1140 * B);
                        sum += grayv;
                        }
                    }
                avg = sum / 304;
                if (avg > 128){
                    nono[(20*x)+y] = 1;
                }
            }
        }
        return nono;
    }
    private Bitmap[] split(Bitmap org, int num) {
        Bitmap[] bitmaps = new Bitmap[num * num];
        int w = org.getWidth();
        int h = org.getHeight();
        for (int i = 0; i < num * num; i++) {
            bitmaps[i] = Bitmap.createBitmap(org, 20, 20, 40, 40);
        }
        return bitmaps;
    }
    private Bitmap grayScale(final Bitmap org) {
        int w = org.getWidth();
        int h = org.getHeight();
        Bitmap orgGray = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        int A, R, G, B;
        int pixel;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                pixel = org.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2990 * R + 0.5870 * G + 0.1140 * B);
                if (gray > 128) {
                    gray = 255;
                } else {
                    gray = 0;
                }
                orgGray.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        return orgGray;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                galleryImage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        galleryImage = grayScale(galleryImage);
        nono = nonotialize(galleryImage);
        correct_length = 0;
        for (int i=0;i<400;i++){
            if (nono[i] == 1) {
                correct_length++;
            }
        }
        runOnUiThread(new Runnable() {
            TextView row1 = findViewById(R.id.row1);
            TextView row2 = findViewById(R.id.row2);
            TextView row3 = findViewById(R.id.row3);
            TextView row4 = findViewById(R.id.row4);
            TextView row5 = findViewById(R.id.row5);
            TextView row6 = findViewById(R.id.row6);
            TextView row7 = findViewById(R.id.row7);
            TextView row8 = findViewById(R.id.row8);
            TextView row9 = findViewById(R.id.row9);
            TextView row10 = findViewById(R.id.row10);
            TextView row11 = findViewById(R.id.row11);
            TextView row12 = findViewById(R.id.row12);
            TextView row13 = findViewById(R.id.row13);
            TextView row14 = findViewById(R.id.row14);
            TextView row15 = findViewById(R.id.row15);
            TextView row16 = findViewById(R.id.row16);
            TextView row17 = findViewById(R.id.row17);
            TextView row18 = findViewById(R.id.row18);
            TextView row19 = findViewById(R.id.row19);
            TextView row20 = findViewById(R.id.row20);
            TextView col1 = findViewById(R.id.col1);
            TextView col2 = findViewById(R.id.col2);
            TextView col3 = findViewById(R.id.col3);
            TextView col4 = findViewById(R.id.col4);
            TextView col5 = findViewById(R.id.col5);
            TextView col6 = findViewById(R.id.col6);
            TextView col7 = findViewById(R.id.col7);
            TextView col8 = findViewById(R.id.col8);
            TextView col9 = findViewById(R.id.col9);
            TextView col10 = findViewById(R.id.col10);
            TextView col11 = findViewById(R.id.col11);
            TextView col12 = findViewById(R.id.col12);
            TextView col13 = findViewById(R.id.col13);
            TextView col14 = findViewById(R.id.col14);
            TextView col15 = findViewById(R.id.col15);
            TextView col16 = findViewById(R.id.col16);
            TextView col17 = findViewById(R.id.col17);
            TextView col18 = findViewById(R.id.col18);
            TextView col19 = findViewById(R.id.col19);
            TextView col20 = findViewById(R.id.col20);
            @Override
            public void run() {
                String a="";
                row1.setText(rowCal(0));
                row2.setText(rowCal(1));
                row3.setText(rowCal(2));
                row4.setText(rowCal(3));
                row5.setText(rowCal(4));
                row6.setText(rowCal(5));
                row7.setText(rowCal(6));
                row8.setText(rowCal(7));
                row9.setText(rowCal(8));
                row10.setText(rowCal(9));
                row11.setText(rowCal(10));
                row12.setText(rowCal(11));
                row13.setText(rowCal(12));
                row14.setText(rowCal(13));
                row15.setText(rowCal(14));
                row16.setText(rowCal(15));
                row17.setText(rowCal(16));
                row18.setText(rowCal(17));
                row19.setText(rowCal(18));
                row20.setText(rowCal(19));
                col1.setText(colCal(0));
                col2.setText(colCal(1));
                col3.setText(colCal(2));
                col4.setText(colCal(3));
                col5.setText(colCal(4));
                col6.setText(colCal(5));
                col7.setText(colCal(6));
                col8.setText(colCal(7));
                col9.setText(colCal(8));
                col10.setText(colCal(9));
                col11.setText(colCal(10));
                col12.setText(colCal(11));
                col13.setText(colCal(12));
                col14.setText(colCal(13));
                col15.setText(colCal(14));
                col16.setText(colCal(15));
                col17.setText(colCal(16));
                col18.setText(colCal(17));
                col19.setText(colCal(18));
                col20.setText(colCal(19));
            }
        });
    }
};