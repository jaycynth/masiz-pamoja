
package com.julie.masizpamoja.views.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.julie.masizpamoja.R;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Profile extends Fragment {

    @BindView(R.id.profile_image)
    ImageView profileImage;

    @BindView(R.id.full_names)
    TextView fullNames;
    @BindView(R.id.hidden_full_names)
    EditText hiddenFullNames;

    @BindView(R.id.occupation)
    TextView occupation;
    @BindView(R.id.hidden_occupation)
    EditText hiddenOccupation;

    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.hidden_email)
    EditText hiddenEmail;

    @BindView(R.id.edit_profle_buttton)
    ImageButton editProfileButton;

    @BindView(R.id.btn_done)
    Button done;

    private static final String IMAGE_DIRECTORY = "/masizpamoja";
    private int GALLERY = 1, CAMERA = 2;

    String path, nFullName, nOccupation, nEmail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        path = SharedPreferencesManager.getInstance(getActivity()).getUserImage();
        Glide.with(getActivity()).load(path).error(R.drawable.ic_add_a_photo_black_24dp).into(profileImage);

        nFullName = SharedPreferencesManager.getInstance(getActivity()).getNames();
        fullNames.setText(nFullName);

        nOccupation = SharedPreferencesManager.getInstance(getActivity()).getOccuption();
        occupation.setText(nOccupation);

        nEmail = SharedPreferencesManager.getInstance(getActivity()).getEmail();
        email.setText(nEmail);


        requestMultiplePermissions();

        editProfileButton.setOnClickListener(v -> {
            hiddenEmail.setVisibility(View.VISIBLE);
            hiddenFullNames.setVisibility(View.VISIBLE);
            hiddenOccupation.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);

            nFullName = hiddenFullNames.getText().toString().trim();
            SharedPreferencesManager.getInstance(getActivity()).saveNames(nFullName);
            nOccupation = hiddenOccupation.getText().toString().trim();
            SharedPreferencesManager.getInstance(getActivity()).saveOccupation(nOccupation);
            nEmail = hiddenEmail.getText().toString().trim();
            SharedPreferencesManager.getInstance(getActivity()).saveEmail(nEmail);


            fullNames.setText(nFullName);
            occupation.setText(nOccupation);
            email.setText(nEmail);
        });

        done.setOnClickListener(v -> {
            hiddenEmail.setVisibility(View.GONE);
            hiddenFullNames.setVisibility(View.GONE);
            hiddenOccupation.setVisibility(View.GONE);
            done.setVisibility(View.GONE);

            fullNames.setText(nFullName);
            occupation.setText(nOccupation);
            email.setText(nEmail);
        });

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_photo) {
            //add photo logic
            addPhotoDialog();


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu, menu);

    }

    private void addPhotoDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Choose Action");
        String[] pictureDialogItems = {"Gallery", "Camera"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            choosePhotoFromGallery();
                            break;
                        case 1:
                            takePhotoFromCamera();
                            break;
                    }
                });
        pictureDialog.show();
    }

    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    path = saveImage(bitmap);

                    Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    profileImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            path = saveImage(thumbnail);
            profileImage.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }

        if (path != null) {
            SharedPreferencesManager.getInstance(getActivity()).saveUserImage(path);

        }

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
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }


                }).
                withErrorListener(error -> Toast.makeText(getActivity(), "Some Error! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(getActivity()).load(path).into(profileImage);
    }
}
