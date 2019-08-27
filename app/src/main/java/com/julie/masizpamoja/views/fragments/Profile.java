
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
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.julie.masizpamoja.R;
import com.julie.masizpamoja.models.UpdatePassword;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.ProfileViewModel;
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

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.julie.masizpamoja.api.ApiEndpoints.PROFILE_URL;


public class Profile extends Fragment {

    @BindView(R.id.profile_image)
    ImageView profileImage;


    @BindView(R.id.hidden_full_names)
    TextInputEditText hiddenFullNames;


    @BindView(R.id.hidden_email)
    TextInputEditText hiddenEmail;


    @BindView(R.id.edit_profile_picture)
    ImageButton editProfilePicture;



    private static final String IMAGE_DIRECTORY = "/masizpamoja";
    private int GALLERY = 1, CAMERA = 2;

    String path, nFullName, nImage, nEmail;

    @BindView(R.id.edit_password)
    TextView editPassword;

    @BindView(R.id.passwordLayout)
    TextInputLayout passwordLayout;
    @BindView(R.id.passwordEdit)
    TextInputEditText passwordEdit;


    @BindView(R.id.confirm_passwordLayout)
    TextInputLayout confrmPasswordLayout;
    @BindView(R.id.confirm_passwordEdit)
    TextInputEditText confirmPasswordEdit;

    @BindView(R.id.confirm_password_layout)
    LinearLayout confirmHolderLayout;

    @BindView(R.id.btn_done)
    CircularProgressButton done;

    
    ProfileViewModel profileViewModel;
    
    String accessToken;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        
        accessToken = SharedPreferencesManager.getInstance(getActivity()).getToken();

        profileViewModel.getUpdatePasswordResponse().observe(this, updatePasswordState -> {
            if(updatePasswordState.getUpdatePassword() != null){
                handleUpdatePassword(updatePasswordState.getUpdatePassword());
            }

            if(updatePasswordState.getErrorThrowable() != null){
                handleErrorThrowable(updatePasswordState.getErrorThrowable());
            }

            if(updatePasswordState.getMessage() != null){
                handleError(updatePasswordState.getMessage());
            }
        });


        nFullName = SharedPreferencesManager.getInstance(getActivity()).getNames();
        hiddenFullNames.setText(nFullName);

        nEmail = SharedPreferencesManager.getInstance(getActivity()).getEmail();
        hiddenEmail.setText(nEmail);

        nImage = SharedPreferencesManager.getInstance(getActivity()).getUserImage();
        Glide.with(getActivity()).load(PROFILE_URL + path).error(R.drawable.ic_person_black_24dp).into(profileImage);




        requestMultiplePermissions();

        editPassword.setOnClickListener(v->{
            confirmHolderLayout.setVisibility(View.VISIBLE);
        });


        done.setOnClickListener(v -> {

            String password = passwordEdit.getText().toString().trim();
            String confirmPassword = confirmPasswordEdit.getText().toString().trim();

            if(TextUtils.isEmpty(password)){
                Toast.makeText(getActivity(), "Enter a password", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(confirmPassword)){
                Toast.makeText(getActivity(), "Confirm Your Password", Toast.LENGTH_SHORT).show();
            }else{
                done.startMorphAnimation();
                profileViewModel.updatePassword(password,confirmPassword,"Bearer "+ accessToken);
            }

        });



        editProfilePicture.setOnClickListener(v -> {
            addPhotoDialog();
        });

        return view;
    }

    private void handleUpdatePassword(UpdatePassword updatePassword) {
        done.startMorphRevertAnimation();
        Toast.makeText(getActivity(), updatePassword.getStatus(), Toast.LENGTH_SHORT).show();
        confirmHolderLayout.setVisibility(View.GONE);

    }

    private void handleErrorThrowable(Throwable errorThrowable) {
        done.startMorphRevertAnimation();
        if (errorThrowable instanceof IOException) {
            Toast.makeText(getActivity(), "Network Failure", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(String message) {
        done.startMorphRevertAnimation();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                    SharedPreferencesManager.getInstance(getActivity()).saveUserImage(path);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            path = saveImage(thumbnail);
            SharedPreferencesManager.getInstance(getActivity()).saveUserImage(path);

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
