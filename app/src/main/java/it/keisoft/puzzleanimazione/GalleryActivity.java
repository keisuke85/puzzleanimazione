package it.keisoft.puzzleanimazione;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by mmarcheselli on 16/11/2015.
 */
public class GalleryActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private ArrayList<ImageItem> imageItems = new ArrayList<>();
//    private Context context;
//    private LoginButton loginButton;
//    private CallbackManager callbackManager;
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);
        gridView = (GridView) findViewById(R.id.gridView);

        // Pull to Refresh inizio
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (gridView != null && gridView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = gridView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = gridView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                mSwipeRefreshLayout.setEnabled(enable);
            }
        });
        // Pull to Refresh fine



//            context = this;

//        updateUI();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                ImageView imageView = (ImageView) v.findViewById(R.id.image);

                //Create intent
                Intent intent = new Intent(GalleryActivity.this, GalleryDetailActivity.class);
                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);

                //Pass the image title and url to DetailsActivity
                intent.putExtra("left", screenLocation[0]).
                        putExtra("top", screenLocation[1]).
                        putExtra("width", imageView.getWidth()).
                        putExtra("height", imageView.getHeight()).
                        putExtra("title", item.getTitle()).
                        putExtra("image", item.getImage());

                //Start details activity
                startActivity(intent);
            }
        });

        updateUI();
    }

    private void refresh(){
        getData();
    }

    private void updateUI(){
        if(imageItems.size() > 0) {
            gridAdapter = new GridViewAdapter(this, R.layout.gallery_grid_item_layout, imageItems);
            gridView.setAdapter(gridAdapter);
        }else{
            refresh();
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // Prepare some dummy data for gridview
    private void getData() {
        loading = true;
    /* make the API call */
        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/329909403739109/photos/uploaded",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
        /* handle the result */
                        try {
                            if(response.getError() != null){
                                Toast.makeText(GalleryActivity.this, "Per visualizzare le foto di Facebook è necessario autorizzare l'applicazione", Toast.LENGTH_SHORT).show();
                            }else {
                                ImageItem imageItem;
                                JSONArray array = response.getJSONObject().getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
//                                    String source = array.getJSONObject(i).getString("source");
//                                    String title = Integer.toString(i);//array.getJSONObject(i).getString("name");
                                    imageItem = new ImageItem(array.getJSONObject(i));
                                    imageItems.add(imageItem);
                                }
                            }
                            if (imageItems.size() > 0) {
                                loading = false;
                                updateUI();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "images,name,id,width,height");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
