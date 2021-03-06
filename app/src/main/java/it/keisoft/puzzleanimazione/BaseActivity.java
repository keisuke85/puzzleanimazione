package it.keisoft.puzzleanimazione;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;

/**
 * Created by mmarcheselli on 05/11/2015.
 */
public class BaseActivity extends AppCompatActivity {

    protected FrameLayout frameLayout;
    protected ListView mDrawerList;
    private DrawerLayout mDrawerLayout;

    protected String[] listArray;
    protected ArrayList<Items> _items;

    protected static int position;

    private static boolean isLaunch = true;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_base_layout);

        listArray = new String[]{getString(R.string.title_section0),
                getString(R.string.title_section1),
                getString(R.string.title_section2),
                getString(R.string.title_section3),
                getString(R.string.title_section4),
                getString(R.string.title_section5),
                getString(R.string.title_section6),
                getString(R.string.title_section7),
                getString(R.string.action_settings)};

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        _items = new ArrayList<>();
        _items.add(new Items(getString(R.string.title_section1), null, R.drawable.ic_drawer));
        _items.add(new Items(getString(R.string.title_section2), null, R.drawable.puzzle_splash));
        _items.add(new Items(getString(R.string.title_section3), null, R.drawable.ic_drawer));
        _items.add(new Items(getString(R.string.title_section4), null, R.drawable.puzzle_splash));
        _items.add(new Items(getString(R.string.title_section5), null, R.drawable.ic_drawer));
        _items.add(new Items(getString(R.string.title_section6), null, R.drawable.puzzle_splash));
        _items.add(new Items(getString(R.string.title_section7), null, R.drawable.ic_drawer));
        _items.add(new Items(getString(R.string.action_settings), null, R.drawable.ic_action_settings));

        View header = getLayoutInflater().inflate(R.layout.list_view_header_layout, null);
        mDrawerList.addHeaderView(header);

        mDrawerList.setAdapter(new NavigationDrawerListAdapter(this, _items));
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openActivity(i);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
//                R.mipmap.ic_launcher,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(listArray[position]);
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        if(isLaunch){
            fbLogin();

            isLaunch = false;
            openActivity(0);
        }
    }


    protected void openActivity(int position){
        mDrawerLayout.closeDrawer(mDrawerList);
        BaseActivity.position = position;
        Intent intent;

        switch (position){
            case 0:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case 1:
                intent = new Intent(this, ShowPage.class);
                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "158");
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, ShowPage.class);
                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "servizi.html");
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, GalleryActivity.class);
//                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "41");
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this, ShowPage.class);
                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "12");
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(this, ShowPage.class);
                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "storia.html");
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(this, ShowPage.class);
                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "268");
                startActivity(intent);
                break;
            case 7:
                intent = new Intent(this, ShowPage.class);
                //intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "26");
                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "contatti.html");
                startActivity(intent);
                break;
            case 8:
//                getFragmentManager().beginTransaction().replace(android.R.id.content,
//                        new SettingsActivity()).commit();
                intent = new Intent(this, SettingsActivity.class);
                //intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "26");
//                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "contatti.html");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(mDrawerList)){
            mDrawerLayout.closeDrawer(mDrawerList);
        }else{
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }

    private void fbLogin() {
        // Facebook Login inizio
        FacebookSdk.sdkInitialize(getApplicationContext());
//            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
//                    refresh();
            }

            @Override
            public void onCancel() {
//                if (pendingAction != PendingAction.NONE) {
//                    showAlert();
//                    pendingAction = PendingAction.NONE;
//                }
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
//                    refresh();
            }

            @Override
            public void onError(FacebookException exception) {
//                    if (/*pendingAction != PendingAction.NONE &&*/ exception instanceof FacebookAuthorizationException) {
//                        showAlert();
//                    pendingAction = PendingAction.NONE;
                //                   }
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
//                   refresh();
            }

            /*    private void showAlert() {
                    new AlertDialog.Builder(BaseActivity.this)
                            .setTitle(R.string.cancelled)
                            .setMessage(R.string.permission_not_granted)
                            .setPositiveButton(R.string.ok, null)
                            .show();
                }*/
        });
        // Facebook Login fine

    }

}

