package it.keisoft.puzzleanimazione;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by mmarcheselli on 16/11/2015.
 */
public class ImageItem {
    private JSONObject obj;
    private Bitmap image;
    private String title;
    private String urlSmall;
    private String urlBig;
    private boolean bigLoaded = false;

    public ImageItem(JSONObject obj){
        this.obj = obj;
        fillFromJSON();
    }

    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public ImageItem(String url, String title) {
        super();
        this.urlSmall = url;
        this.title = title;
        //getImageFromUrl(this.urlSmall);
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlSmall() {
        return urlSmall;
    }

    public void setUrlSmall(String url) {
        this.urlSmall = url;
    }

    public JSONObject getObj() {
        return obj;
    }

    public void setObj(JSONObject obj) {
        this.obj = obj;
    }

    public String getUrlBig() {
        return urlBig;
    }

    public void setUrlBig(String urlBig) {
        this.urlBig = urlBig;
    }

    private void fillFromJSON(){
        if(this.obj != null){
            try {
                setTitle(this.obj.getString("name"));
                JSONArray array = this.obj.getJSONArray("images");
//                String width = this.obj.getString("width");
                String height = this.obj.getString("height");
                for(int i = 0; i<array.length();i++) {
                /*if (array.getJSONObject(i).getString("height").equalsIgnoreCase("130") || array.getJSONObject(i).getString("width").equalsIgnoreCase("130")) {
                    setUrlSmall(array.getJSONObject(i).getString("source"));
                    getImageFromUrl(getUrlSmall());
                } else if (array.getJSONObject(i).getString("height").equalsIgnoreCase("480") || array.getJSONObject(i).getString("width").equalsIgnoreCase("480")) {
                 */   setUrlBig(array.getJSONObject(i).getString("source"));
                    getImageFromUrl(this.urlBig);
               /* }*/
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getBigOne(){
        if(!bigLoaded) {
            bigLoaded = true;
            getImageFromUrl(this.getUrlBig());
        }
    }

    private void getImageFromUrl(String url){
        new LoadImage().execute(url);
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                InputStream is = url.openConnection().getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                //bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                setImage(bitmap);
            }
        }
    }
}
