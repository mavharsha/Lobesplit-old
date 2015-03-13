/*
 * Copyright (c) Sree Harsha Mamilla.
 */

package lobesplit.maverick.harsha.lobesplit;

import android.app.ListActivity;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;


public class MainActivity extends ListActivity {

    private String choice = "left";
    private final String TAG = "lobesplit.maverick.harsha.lobesplit.MainActivity";
    String str;
    final String MEDIA_PATH = "/sdcard/Music";
    Switch side_switch;
    ImageButton play, pause, stop;
    MediaPlayer leftPlayer, rightPlayer;
    ListView listview1;
    ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> songs = new ArrayList<String>();
    private ArrayList<String> path = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Stream
        side_switch = (Switch) findViewById(R.id.switch1);

        //Playing, Pausing and Stopping
        pause = (ImageButton) findViewById(R.id.pause);
        play = (ImageButton) findViewById(R.id.play);
        stop = (ImageButton) findViewById(R.id.stop);


        //ListView for playlist
        listview1 = getListView();
        //Update Playlist
        updateList();

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songs);
        /*INFLATE LIST VIEW*/
        listview1.setAdapter(itemsAdapter);



        /* SELECT SIDE ONCLICK LISTENER*/

        side_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {

                if(on){
                    choice = "right";
                    Toast.makeText(getApplicationContext(), "Right Stream", Toast.LENGTH_SHORT).show();
                }
                else{
                    choice = "left";
                    Toast.makeText(getApplicationContext(), "Left Stream", Toast.LENGTH_SHORT).show();
                }
            }
        });



        /* PLAY ONCLICK LISTENER */
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Playing", Toast.LENGTH_SHORT).show();


                if(choice.equalsIgnoreCase("left")){
                    if(leftPlayer!=null) {
                        // Toast.makeText(getApplicationContext(), "Playing left"+ str, Toast.LENGTH_SHORT).show();
                        leftPlayer.start();
                    }
                }
                else if(choice.equalsIgnoreCase("right")){

                    if(rightPlayer!= null) {
                        //Toast.makeText(getApplicationContext(), "right"+ str, Toast.LENGTH_SHORT).show();

                        rightPlayer.start();
                    }
                }

            }
        });


        /* PAUSE ONCLICK LISTENER*/
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(choice.equalsIgnoreCase("left")){

                    if(leftPlayer!=null) {
                        //Toast.makeText(getApplicationContext(), "Pause left"+ str, Toast.LENGTH_SHORT).show();
                        leftPlayer.pause();
                    }
                }
                else if(choice.equalsIgnoreCase("right")){

                   if(rightPlayer!=null) {
                       // Toast.makeText(getApplicationContext(), "Pause right"+ str, Toast.LENGTH_SHORT).show();
                       rightPlayer.pause();
                   }
                }


                Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_SHORT).show();

            }
        });



        /* PAUSE ONCLICK LISTENER*/

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(choice.equalsIgnoreCase("left")){

                    if (leftPlayer!= null) {
                        // Toast.makeText(getApplicationContext(), "Playing left"+ str, Toast.LENGTH_SHORT).show();
                        leftPlayer.stop();
                    }
 }
                else if(choice.equalsIgnoreCase("right")){

                    if(rightPlayer!=null) {
                        //Toast.makeText(getApplicationContext(), "right"+ str, Toast.LENGTH_SHORT).show();
                        rightPlayer.stop();
                    }
                }

                Toast.makeText(getApplicationContext(), "Stop", Toast.LENGTH_SHORT).show();
            }
        });






        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Log.i(TAG, "Onitem click listener");
                str = path.get(position);

                if(choice.equalsIgnoreCase("left")){


                    try {

                        if(leftPlayer!=null) {
                           leftPlayer.stop();
                           leftPlayer.reset();
                           leftPlayer.release();

                        }
                        leftPlayer = new MediaPlayer();
                        leftPlayer.setAudioStreamType(AudioAttributes.USAGE_MEDIA);

                        leftPlayer.setDataSource(str);
                        leftPlayer.setVolume(1,0);


                        leftPlayer.prepare();
                        leftPlayer.start();
                        Toast.makeText(getApplicationContext(), "Playing left" + str, Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception e){

                        Log.v(TAG,"Select song(try block)");
                    }



                }
                else if(choice.equalsIgnoreCase("right")){


                    try {
                        if(rightPlayer!=null) {
                           rightPlayer.stop();
                           rightPlayer.reset();

                           rightPlayer.release();

                        }
                        rightPlayer = new MediaPlayer();
                        rightPlayer.setAudioStreamType(AudioAttributes.USAGE_MEDIA);
                        rightPlayer.setVolume(0,1);
                        rightPlayer.setDataSource(str);
                        rightPlayer.prepare();
                        rightPlayer.start();
                        Toast.makeText(getApplicationContext(), "right"+ str, Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception IOException){

                        Log.v(TAG,"Select song(try block)");
                    }

                }



            }
        });

    } //End of onCreate


    @Override
    protected void onPause() {
        if(leftPlayer != null)
            leftPlayer.pause();

        if (rightPlayer != null)
            rightPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {

        if(leftPlayer != null)
        leftPlayer.start();

        if (rightPlayer != null)
        rightPlayer.start();

        super.onResume();
    }

    /*FUNCTION DEFINITION FOR TO UPDATE THE LIST OF SONGS */
    private void updateList() {

        File home = new File(MEDIA_PATH);

        try {


           //File[] temp = home.listFiles();

            if (home.listFiles(new fileFilter()).length > 0) {

                for (File file : home.listFiles(new fileFilter())) {

                    songs.add(file.getName());
                    path.add(file.getPath());

                }
            }

          }catch (NullPointerException e)
        {

            Log.v(TAG,"Null pointer exception");
        }





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Toast.makeText(getApplicationContext(), "Dream Team: Sree Harsha", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

        class fileFilter implements FilenameFilter{

            @Override
            public boolean accept(File dir, String filename) {

                return (filename.endsWith(".mp3")|| filename.endsWith(".MP3"));
            }

        }