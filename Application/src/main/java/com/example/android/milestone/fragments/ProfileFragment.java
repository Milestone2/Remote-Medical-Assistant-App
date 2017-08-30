package com.example.android.milestone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.bumptech.glide.Glide;
import com.example.android.bluetoothlegatt.R;
import com.example.android.milestone.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.media.MediaRecorder.VideoSource.CAMERA;

/**
 * Created by Owner on 8/19/2017.
 */

public class ProfileFragment extends Fragment {

    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;

    Spinner spTemp;
    List<String> temp;//Liste d'element a mettre au spinner
    ArrayAdapter<String> adapterTemp;//Adpater liant la liste d'element au Spinner
    BackendlessUser userInfo;
    TextView tvProfilName;
    TextView tvProfilDate;
    TextView tv1;//user address
    TextView tv2;//user phone number
    TextView tv3;//user ID
    TextView tv5;//user main docteur
    TextView tv6;//user height
    TextView tv7;//user birthday
    TextView tvGS;
    TextView tvProfilePoids;//user weigth
    TextView tvTempreceiver;
    ImageView ivProfilImage;
    Button btnModify;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine_profil = inflater.inflate(R.layout.profil_ui, null);
        userInfo = Backendless.UserService.CurrentUser();
        spTemp = (Spinner) racine_profil.findViewById(R.id.spTemperature); //Spinner qui doit afficher C | F
        temp = new ArrayList<>();
        temp.add("˚C");
        temp.add("˚F");

        ivProfilImage = (ImageView) racine_profil.findViewById(R.id.ivProfilImage);
        tv1 = (TextView) racine_profil.findViewById(R.id.tv1);
        tv2 = (TextView) racine_profil.findViewById(R.id.tv2);
        tv3 = (TextView) racine_profil.findViewById(R.id.tv3);
        tv5 = (TextView) racine_profil.findViewById(R.id.tv5);
        tv6 = (TextView) racine_profil.findViewById(R.id.tv6);
        tv7 = (TextView) racine_profil.findViewById(R.id.tv7);
        tvProfilName = (TextView) racine_profil.findViewById(R.id.tvProfilName);
        tvProfilDate = (TextView) racine_profil.findViewById(R.id.tvProfilDate);
        tvGS = (TextView) racine_profil.findViewById(R.id.tvGS);
        tvProfilePoids = (TextView) racine_profil.findViewById(R.id.tvProfilPoids);
        tvTempreceiver = (TextView) racine_profil.findViewById(R.id.tvTempReceiver);
        btnModify = (Button) racine_profil.findViewById(R.id.btnModify);


        tvProfilName.setText(userInfo.getProperty("Nom").toString());
        tvProfilDate.setText(userInfo.getEmail());
        tv1.setText("Addresse:  "+ userInfo.getProperty("address").toString());
        tv2.setText("Telephone:  "+ userInfo.getProperty("Telephone").toString());
        tv3.setText("ID:  " + userInfo.getUserId());
        tv5.setText("Docteur (ref.)  : " + userInfo.getProperty("Doctor").toString());
        tvGS.setText("G.S: " + userInfo.getProperty("GS"));
        tv6.setText("Hauteur:  " + userInfo.getProperty("height").toString() + " m");
        tv7.setText("Date de naissance: " + userInfo.getProperty("birth").toString());
        tvProfilePoids.setText("Poids:  " + userInfo.getProperty("weight").toString() + " Kg");



        adapterTemp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, temp);
        adapterTemp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTemp.setAdapter(adapterTemp);
        final double temf = Integer.valueOf(userInfo.getProperty("temp_m").toString()) * 1.8 + 32;
        final int farei = (int) temf;

        spTemp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spTemp.getSelectedItemPosition() == 0){
                    tvTempreceiver.setText(userInfo.getProperty("temp_m").toString());
                }else{
                    tvTempreceiver.setText(farei + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ivProfilImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        return racine_profil;
    }

    public  static  ProfileFragment newInstance(BackendlessUser user){
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable("userInfo", user);
        profileFragment.setArguments(args);
        return profileFragment;
    }

    ////// for profil picture

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }



    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }



    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");

            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getContext(), new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != GALLERY && resultCode != CAMERA) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    ivProfilImage.setImageBitmap(bitmap);
                    Glide.with(getContext()).load(contentURI).into(ivProfilImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ivProfilImage.setImageBitmap(thumbnail);
            Glide.with(getContext()).load(thumbnail).into(ivProfilImage);
            //saveImage(thumbnail);
            Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }
}
