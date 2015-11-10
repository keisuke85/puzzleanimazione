package it.keisoft.puzzleanimazione.old;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.webkit.WebView;

import it.keisoft.puzzleanimazione.R;
import it.keisoft.puzzleanimazione.ShowPage;


public class HomeActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private PlaceholderFragment fragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static String urlPuzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bkpactivity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
/*        if(position +1 == 7){
            ShowPage showPageFragment = ShowPage.newInstance(urlPuzzle);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, showPageFragment)
                    .commit();
        }else {
*/            fragment = PlaceholderFragment.newInstance(position + 1);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
//        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                urlPuzzle = "http://www.puzzleanimazione.it/?page_id=158";
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                urlPuzzle = "http://www.puzzleanimazione.it/?page_id=363";
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                urlPuzzle = "http://www.puzzleanimazione.it/?page_id=41";
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                urlPuzzle = "http://www.puzzleanimazione.it/?page_id=12";
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                urlPuzzle = "http://www.puzzleanimazione.it/?page_id=21";
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                urlPuzzle = "http://www.puzzleanimazione.it/?page_id=268";
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                //urlPuzzle = "http://www.puzzleanimazione.it/?page_id=26";
                urlPuzzle = "26";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String getUrlPuzzle(){
        return urlPuzzle;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(fragment != null) {
            if (fragment.myOnKeyDown(keyCode)) {
                return true;
            } else {
                finish();
                // finish the activity
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private WebView wv;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);

            wv = (WebView) rootView.findViewById(R.id.webPage);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.loadUrl(getUrlPuzzle());
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        public WebView getWebView(){
            return wv;
        }

        public boolean myOnKeyDown(int key_code){
            if ((key_code == KeyEvent.KEYCODE_BACK) && getWebView().canGoBack()) {
                //if Back key pressed and webview can navigate to previous page
                wv.goBack();
                // go back to previous page
                return true;
            }else{
                return false;
            }
        }
    }

}
