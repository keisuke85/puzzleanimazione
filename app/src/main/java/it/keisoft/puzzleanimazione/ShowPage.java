package it.keisoft.puzzleanimazione;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mmarcheselli on 05/11/2015.
 */
public class ShowPage extends BaseActivity {

    public static final String ARG_SECTION_NUMBER = "section_number";
    private static final String urlPuzzle = "http://www.puzzleanimazione.it/?page_id=";

    private HtmlParser hp;
    private WebView wv;
    //post-26
    private String elementName = "div";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         *  We will not use setContentView in this activty
         *  Rather than we will use layout inflater to add view in FrameLayout of our base activity layout*/

        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.show_page, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        Intent intent = getIntent();
        wv = (WebView)findViewById(R.id.webPage);
        wv.getSettings().setJavaScriptEnabled(true);

        String idPost =  intent.getExtras().getString(ARG_SECTION_NUMBER);

//        wv.loadUrl(urlPuzzle + idPost);
        wv.loadUrl("file:///android_asset/" + idPost);
//            new AsyncCaller().execute(urlPuzzle,idPost);


            /* passiamo il risultato alla TextView per visualizzarli. */

//        ((ImageView)findViewById(R.id.image_view)).setBackgroundResource(R.drawable.image1);
    }

    private class AsyncCaller extends AsyncTask<String, Void, Void> {
        String idPost;

        @Override
        protected Void doInBackground(String... params) {

            try {
                idPost = params[1];
                hp = new HtmlParser(params[0] + params[1]);



            } catch (Exception e) {
             /* In caso di errore, passiamo l'errore alla TextView. */
//                tv.setText("Problema nel parsing: \n" + e.getMessage());
                Log.e("Error", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            wv.loadData(hp.Stampa(elementName, "post-" + idPost), "text/html", "UTF-8");
//            wv.loadDataWithBaseURL(null, hp.Stampa(elementName, "post-" + idPost), "text/html", "UTF-8", null);
                    //.setText(hp.Stampa(elementName, "post-" + idPost));
        }
    }

}
