package jp.blogspot.jusoncode.adverbialtwo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import jp.blogspot.jusoncode.adverbialtwo.students.SpinnerAdapterStudent;


public class PhotoScreen extends AppCompatActivity {

    private final int REQUEST_IMAGE_CAPTURE = 1;
    private String nameCap;
    private TextView  tv;
    private String savedName = "savedName";
    private Button rotate, takePhoto, deletePhoto;
    private ImageView iv;
    private Bitmap  imageBitmap, createdNew;
    private boolean imageReceived;
    private String imageReceivedSaved = "savedImageReceived";
    private HashMapBitMap bit;
    private int degrees = 90;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;





    private HashMap<String,HashMapBitMap> images = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Do I need this? Yes, it provides back navigation.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv = (TextView) findViewById(R.id.textViewNewPhotoMessage);
        spinner = (Spinner)findViewById(R.id.spinnerPhoto);

        rotate = (Button) findViewById(R.id.buttonPhotoRotate);
        rotate.setVisibility(View.INVISIBLE);

        takePhoto = (Button) findViewById(R.id.buttonPhoto);
        deletePhoto = (Button) findViewById(R.id.buttonPhotoDelete);
        iv = (ImageView) findViewById(R.id.photoScreenImageView);

        load();



        if(savedInstanceState != null) {
            nameCap = savedInstanceState.getString(savedName,"no name");
            imageReceived = savedInstanceState.getBoolean(imageReceivedSaved);
            if(images.containsKey(nameCap) & imageReceived) {
                HashMapBitMap hash = new HashMapBitMap(images.get(nameCap).getBitmap());
                createdNew = hash.getBitmap();

                iv.setImageBitmap(hash.getBitmap());
                deletePhoto.setVisibility(View.INVISIBLE);
                takePhoto.setVisibility(View.INVISIBLE);
                rotate.setVisibility(View.VISIBLE);
                }

        }

        final SpinnerAdapterStudent spinnerAdapterStudent = new SpinnerAdapterStudent(this,HomeScreen.data.studentInMap);
        spinner.setAdapter(spinnerAdapterStudent);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameCap = spinnerAdapterStudent.getItem(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rotate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                if(imageReceived) {

                    Matrix matrix = new Matrix();
                    matrix.setRotate(degrees);

                    Bitmap rotated = Bitmap.createBitmap(createdNew, 0, 0, createdNew.getWidth(), createdNew.getHeight(), matrix, true);

                    iv.setImageBitmap(rotated);

                    bit = new HashMapBitMap(rotated);

                    degrees += 90;

                    images.put(nameCap, bit);

                    save();


                }else{
                    tv.setText(getResources().getString(R.string.noImage));
                }

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo_screen, menu);
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
            //Toast.makeText(this,"hi",Toast.LENGTH_SHORT).show();
            MyDialogCon myDialogCon  = new MyDialogCon();
            myDialogCon.show(getFragmentManager(), "MyDialog");
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("unchecked resources")
    public void load(){
        try {
            FileInputStream fis = openFileInput("adverbial_two_data.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            images = (HashMap<String, HashMapBitMap>) ois.readObject();
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

    public void save(){

       try {
            String file = "adverbial_two_data.txt";
            FileOutputStream fos;
            fos = openFileOutput(file, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(images);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            Toast toast = Toast.makeText(this, "Unable to save data, Report to developer.", Toast.LENGTH_SHORT);
            toast.show();

        }

    }


    public void dispatchPictureIntent(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

            createdNew = Bitmap.createBitmap(imageBitmap,0,0,imageBitmap.getWidth(),imageBitmap.getHeight());

            iv.setImageBitmap(createdNew);


            bit = new HashMapBitMap(createdNew);

            imageReceived = true;


            deletePhoto.setVisibility(View.INVISIBLE);
            takePhoto.setVisibility(View.INVISIBLE);
            rotate.setVisibility(View.VISIBLE);

            ////////Will need to pass in a string for image names......
            if (!images.containsKey(nameCap)) {
                images.put(nameCap, bit);

                save();
                tv.setText(getString(R.string.imagedSaved));

            }else{
                tv.setText("Unable To Set Image\nImage Name Already Exists");
            }
        }else{
            Toast.makeText(this,"Didn't get image..",Toast.LENGTH_SHORT).show();
        }
    }



    public void getName(View view){


        if(nameCap != null) {
            if (!images.containsKey(nameCap)) {
                dispatchPictureIntent(view);
            } else {
                tv.setText(getString(R.string.imageExists));
            }

        }else{
            tv.setText(getResources().getString(R.string.noFile));
        }
    }


    public  void deletePhoto(View view){

        if(nameCap != null) {
            if (images.containsKey(nameCap)) {
                images.remove(nameCap);
                tv.setText(getString(R.string.deleted));
                save();
            } else {
                tv.setText(getString(R.string.noImage));
            }
        }else{
            tv.setText(getResources().getString(R.string.noFile));
        }
    }


    public void onPause(){
        super.onPause();

    }

    public void onSaveInstanceState(Bundle out){
     super.onSaveInstanceState(out);
        out.putString(savedName, nameCap);
        out.putBoolean(imageReceivedSaved,imageReceived);

    }


    public static class MyDialogCon extends DialogFragment {

        protected  String information = "Images can be captured or deleted. Depending on which camera you use" +
                " the image might be returned upside down. Please use the rotate button to correct this. Thanks for using this application. We hope" +
                " you have a trouble free experience.";

        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(information);
            return builder.create();
        }

    }


}
