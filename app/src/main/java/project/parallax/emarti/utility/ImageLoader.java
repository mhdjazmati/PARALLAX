package project.parallax.emarti.utility;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by informatic on 17/02/2018.
 */

public class ImageLoader {
    public static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap userImageBitmap;
    private AppCompatActivity context;

    public ImageLoader(AppCompatActivity context){
        this.context=context;
    }

    public void startImagePickActivity(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        context.startActivityForResult(Intent.createChooser(intent, context.getString(R.string.select_picture)), PICK_IMAGE_REQUEST);
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    public Bitmap getBitMapImage(int requestCode, int resultCode, Intent data){

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                userImageBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), filePath);
                ContentResolver cR = context.getApplicationContext().getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String path = mime.getExtensionFromMimeType(cR.getType(filePath));
                return userImageBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String bitMapToString(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
