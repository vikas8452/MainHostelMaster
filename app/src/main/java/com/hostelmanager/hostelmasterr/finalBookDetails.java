package com.hostelmanager.hostelmasterr;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class finalBookDetails extends AppCompatActivity {


    @BindView(R.id.submitBook)
    Button submitDetails;
    @BindView(R.id.bookImageInContent)
        ImageView bookImageInContent;
    @BindView(R.id.bookNameInContent)
        TextInputEditText bookNameInContent;
    @BindView(R.id.authorNameInContent)
        TextInputEditText authorNameInContent;
    @BindView(R.id.descriptionInContent)
        EditText descriptionInContent;
    @BindView(R.id.upload)
    Button upload;
    @BindView(R.id.remove)
    Button remove;
    String bookName;
    String authorName;
    String department;
    String semester;
    private final int PICK_IMAGE_REQUESI=70;
    Uri filepath;
    //BottomSheetBehavior sheetBehavior;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_final_book_details);
        ButterKnife.bind(this);
        Log.d("dffd","Entered in final book details");
        //setting to the Given Edittext
        Intent getData=getIntent();


        semester=getData.getStringExtra("Semester");

        Toast.makeText(this,"  fswfwrw      "+semester,Toast.LENGTH_SHORT).show();
        department=getData.getStringExtra("Department");
        Toast.makeText(this,"  fswfwrw      "+department,Toast.LENGTH_SHORT).show();

        bookName=getData.getStringExtra("bookNameSell");
        authorName=getData.getStringExtra("authorNameSell");
        bookNameInContent.setText(bookName);
        authorNameInContent.setText(authorName);

        Log.d("dsfijd","Entered in final book details");

        // sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

    /*     sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
           @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                      //  btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
*/
        //@OnClick(R.id.submitBook)

        submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle=new Bundle();
                if(filepath!=null)
                {
                    bundle.putString("imagePath",filepath.toString());
                }
                bundle.putString("Department", department);
                bundle.putString("Semester", semester);
                bundle.putString("bookName",bookNameInContent.getText().toString());
                bundle.putString("authorName",authorNameInContent.getText().toString());
                bundle.putString("description",descriptionInContent.getText().toString());
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }



        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(finalBookDetails.this, "Photo Removed", Toast.LENGTH_SHORT).show();
                bookImageInContent.setImageResource(R.drawable.book);

            }
        });
    }
    private void chooseImage()
    {
        Intent choose=new Intent();
        choose.setType("image/*");
        choose.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(choose,"Select Book Image"),PICK_IMAGE_REQUESI);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==70&&resultCode==RESULT_OK &&data!=null &&data.getData()!=null )
        {
            filepath=data.getData();
           // String path = filepath.uri.toString(); // "file:///mnt/sdcard/FileName.mp3"
           // String path=filepath.getPath();
            //File file = new File(new URI(path));
            Bitmap bitmap = null; //getBitmap(filepath.toString());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bookImageInContent.setImageBitmap(bitmap);
        }
    }


    // Image Rotation issue

    public Bitmap getBitmap(String path){
        Bitmap tmpBitmap = BitmapFactory.decodeFile(path);
        Bitmap bitmap = null;
        if(path != null){
            try {
                ExifInterface ei = new ExifInterface(path);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                Matrix mtx = new Matrix();
                int w = tmpBitmap.getWidth();
                int h = tmpBitmap.getHeight();
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        //rotate CCW
                        mtx.preRotate(-90);
                        bitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, w, h, mtx, true);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        //rotate CW
                        mtx.preRotate(90);
                        bitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, w, h, mtx, true);
                        break;

                    //CONSIDER OTHER CASES HERE....

                    default:
                        bitmap = tmpBitmap;
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return bitmap;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction=getSupportFragmentManager().executePendingTransactions();
        transaction.add(R.id.frame_container,fragment);
        //  transaction.addToBackStack(null);
        transaction.commit();
    }
// Get the real time path


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
