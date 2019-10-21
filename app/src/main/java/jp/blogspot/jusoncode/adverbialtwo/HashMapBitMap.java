package jp.blogspot.jusoncode.adverbialtwo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class HashMapBitMap implements Serializable {


    private byte[]compressed_bitmap;


    //takes a bit map compresses it, you can now save it as it's serializable.
    public HashMapBitMap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        compressed_bitmap = stream.toByteArray();
    }

    //Read the bit map..
    public Bitmap getBitmap() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(compressed_bitmap,0,compressed_bitmap.length);
        return bitmap;
    }
}
