package it.keisoft.puzzleanimazione;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import it.keisoft.puzzleanimazione.old.HomeActivity;

/**
 * Created by mmarcheselli on 04/11/2015.
 */
public class SplashScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){

        }else if(orientation == Configuration.ORIENTATION_PORTRAIT){

        }

/*        Toast.makeText(getApplicationContext(), rotation == 2 ? "Landscape" : rotation == 1 ? "Portrait" : Integer.toString(rotation),
                Toast.LENGTH_LONG).show();
*/
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(SplashScreen.this, BaseActivity.class);
                    startActivity(intent);
                }
            }
        };

        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
