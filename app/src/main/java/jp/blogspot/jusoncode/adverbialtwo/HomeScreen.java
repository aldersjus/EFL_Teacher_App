package jp.blogspot.jusoncode.adverbialtwo;


import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import jp.blogspot.jusoncode.adverbialtwo.database.DataBaseDAO;
import jp.blogspot.jusoncode.adverbialtwo.lessons.LessonAddStudent;
import jp.blogspot.jusoncode.adverbialtwo.lessons.LessonDelete;
import jp.blogspot.jusoncode.adverbialtwo.lessons.LessonMarkOff;
import jp.blogspot.jusoncode.adverbialtwo.lessons.LessonNew;
import jp.blogspot.jusoncode.adverbialtwo.lessons.LessonNewMan;
import jp.blogspot.jusoncode.adverbialtwo.lessons.ReportFragmentLess;
import jp.blogspot.jusoncode.adverbialtwo.school.ReportSchool;
import jp.blogspot.jusoncode.adverbialtwo.students.Grades;
import jp.blogspot.jusoncode.adverbialtwo.students.ReportFragment;
import jp.blogspot.jusoncode.adverbialtwo.students.StudentComment;
import jp.blogspot.jusoncode.adverbialtwo.students.StudentDelete;
import jp.blogspot.jusoncode.adverbialtwo.students.StudentFragment;


public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static FragmentManager fm;
    public static String message;
    public static DataBaseDAO data;
    public int layout = R.layout.activity_home_screen;
    public static HashMap<String,HashMapBitMap> imagesHome = new HashMap<>();
    public static int screenOrientation;
    public static float size;
    public static float sizeTwo;
    public static boolean tablet;

    ////New variables for adding notifications
    private static PendingIntent pendingIntent;
    private static AlarmManager alarmManager;
    static boolean alarmSet = false;
    static boolean alarmDoNotCancel = false;
    private static String ALARM_SET = "alarmSet";

    ///Method to cancel alarm add to settings, will be called from settings when turned on or off.....
    static void cancelAlarm() {

        if (alarmManager != null) {
            if(alarmDoNotCancel) {
                alarmManager.cancel(pendingIntent);
                alarmDoNotCancel = false;
            }else{
                alarmDoNotCancel = true;
                alarmSet = false;
            }
        }
    }

    ///BELOW SETS ALARM TO LAUNCH INTENT TO SEND NOTIFICATION AT SOME TIME
    //Currently being called from the NotificationBootReceived class......
    static void setAlarm(Context context) {
        Intent intent = new Intent(context, NotificationBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);//plus ten second

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP ,System.currentTimeMillis(),(AlarmManager.INTERVAL_DAY),pendingIntent);

        alarmSet = true;

        Toast.makeText(context, "Setting alarm", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fm = getFragmentManager();

        PreferenceManager.setDefaultValues(this,R.xml.settings_screen,true);

        loadSharedPreferences();


        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            tablet = true;
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            if(savedInstanceState == null) {
                TabletTenInch tabletTenInch = new TabletTenInch();
                fm.beginTransaction().add(R.id.tabletView, tabletTenInch).commit();
                ContentHome ch = new ContentHome();
                fm.beginTransaction().add(R.id.here, ch, "contentHome").commit();
            }
        }

        if(savedInstanceState == null & !tablet) {
            ContentHome ch = new ContentHome();
            fm.beginTransaction().add(R.id.here, ch,"contentHome").commit();

        }

        size = getBaseContext().getResources().getDisplayMetrics().widthPixels;
        sizeTwo = getBaseContext().getResources().getDisplayMetrics().heightPixels;
        screenOrientation = getResources().getConfiguration().orientation;

        loadDatabase();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReportFragmentLess rfl = new ReportFragmentLess();
                fm.beginTransaction().replace(R.id.here, rfl).commit();


            }

        });


    }

    private void saveSharedPreferences() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(ALARM_SET, alarmSet);

        editor.apply();

    }


    ////LOAD WHEN APP STARTS....
    public void loadSharedPreferences() {
        final boolean DEFAULT_VALUE = false;
        final String ALARM_DO_NOT_CANCEL = "notifications";

        SharedPreferences sharedPreferencesManager = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        alarmDoNotCancel = sharedPreferencesManager.getBoolean(ALARM_DO_NOT_CANCEL, DEFAULT_VALUE);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        alarmSet = sharedPreferences.getBoolean(ALARM_SET, DEFAULT_VALUE);


        if(alarmDoNotCancel & !alarmSet){
            setAlarm(this);

        }
    }


    public void loadDatabase() {

        AsyncTask<Void, Void, Void> loadDatabase = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                data = new DataBaseDAO(getApplicationContext());

                return null;
            }
        };

        loadDatabase.execute();

    }

    public void onResume(){
        super.onResume();
        load();

    }

    public void onPause(){
        super.onPause();

        data.closeDatabase();
        saveSharedPreferences();
    }

    @SuppressWarnings("unchecked resource")
    public void load(){
        try {

            FileInputStream fis = openFileInput("adverbial_two_data.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            imagesHome = (HashMap<String, HashMapBitMap>) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (ClassNotFoundException e) {
            Toast.makeText(this, "Class Exception, Report to developer." ,Toast.LENGTH_SHORT).show();

        }
        catch (IOException ioe) {
             // Toast.makeText(this, "No photos, file empty." ,Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else if(getFragmentManager().findFragmentById(R.id.here) != getFragmentManager().findFragmentByTag("contentHome")) {
            ContentHome ch = new ContentHome();
            fm.beginTransaction().replace(R.id.here, ch,"contentHome").commit();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Settings settings = new Settings();
            fm.beginTransaction().replace(R.id.here, settings,"settings").commit();
        }
        else if (id == R.id.about){
            AboutApp about = new AboutApp();
            fm.beginTransaction().replace(R.id.here, about,"about").commit();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //New Student...
        if (id == R.id.student_new) {
            StudentFragment sf = new StudentFragment();
            fm.beginTransaction().replace(R.id.here, sf).commit();

        }
        ///Student Comments
        else if (id == R.id.student_comment) {
            StudentComment sc = new StudentComment();
            fm.beginTransaction().replace(R.id.here, sc).commit();
        }
        ///Student Grades
        else if (id == R.id.student_grades) {
           Grades g = new Grades();
            fm.beginTransaction().replace(R.id.here, g).commit();
        }
        /////Student Report
        else if (id == R.id.student_report) {
            ReportFragment rf = new ReportFragment();
            fm.beginTransaction().replace(R.id.here, rf).commit();

        }
        ////Delete Student...
        else if (id == R.id.student_delete) {
            StudentDelete sd = new StudentDelete();
            fm.beginTransaction().replace(R.id.here, sd).commit();
        }
        ///////Photo....
        else if (id == R.id.student_photo){
            Intent intent = new Intent(this, PhotoScreen.class);
            startActivity(intent);

        }
        ////Lesson Group
        else if (id == R.id.lesson_new_group) {
            LessonNew ln = new LessonNew();
            fm.beginTransaction().replace(R.id.here, ln).commit();
        }
        ///Lesson Man to Man...
        else if (id == R.id.lesson_new_man) {
            LessonNewMan lnm = new LessonNewMan();
            fm.beginTransaction().replace(R.id.here, lnm).commit();
        }
        ////Add Student to lesson...
        else if (id == R.id.lesson_add_student) {
            LessonAddStudent las = new LessonAddStudent();
            fm.beginTransaction().replace(R.id.here, las).commit();
        }
        ///Lesson Information..
        else if (id == R.id.lesson_info) {
            ReportFragmentLess rfl = new ReportFragmentLess();
            fm.beginTransaction().replace(R.id.here, rfl).commit();
        }
        ///Mark Off Lessons
        else if (id == R.id.lesson_mark_off) {
            LessonMarkOff lmo = new LessonMarkOff();
            fm.beginTransaction().replace(R.id.here, lmo).commit();
        }
        ///Delete a Lesson...
        else if (id == R.id.lesson_delete) {
            LessonDelete ld = new LessonDelete();
            fm.beginTransaction().replace(R.id.here, ld).commit();
        }
        ////School Students Data
        else if (id == R.id.school_students) {
            ////Set String with student data....
            message = "students";
            ReportSchool rs = new ReportSchool();
            fm.beginTransaction().replace(R.id.here, rs).commit();
        }
        ////School Lesson Data
        else if (id == R.id.school_lessons) {
            ////Set String with lesson data....
            message = "lesson";
            ReportSchool rl = new ReportSchool();
            fm.beginTransaction().replace(R.id.here, rl).commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
