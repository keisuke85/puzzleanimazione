package it.keisoft.puzzleanimazione;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by mmarcheselli on 05/11/2015.
 */
public class HomeActivity extends BaseActivity{

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.home_layout, frameLayout);

        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        initializeActivity();
    }

    private void initializeActivity(){
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new HomeScreenGridViewAdapter(this, _items));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openActivity(i+1);
            }
        });
    }
}
