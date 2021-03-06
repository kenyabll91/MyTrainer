package com.bignerdranch.android.mytrainer;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.util.UUID;

public class NewCustomerFragment extends Fragment {

    private static final String ARG_CUSTOMER_ID = "customer_id";

    private static final int REQUEST_PHOTO= 1;

    private Customer mCustomer;
    private EditText mFullName;
    private EditText mEmail;
    private EditText mAddress;
    private EditText mPhoneNumber;
    private Button mButton;
    private ImageView mImageView;
    private File mPhotoFile;


    public static NewCustomerFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        NewCustomerFragment fragment = new NewCustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID customerId = (UUID) getArguments().getSerializable(ARG_CUSTOMER_ID);
        mCustomer = CustomerLab.get(getActivity()).getCustomer(customerId);
        mPhotoFile = CustomerLab.get(getActivity()).getPhotoFile(mCustomer);
    }

    @Override
    public void onPause() {
        super.onPause();

        CustomerLab.get(getActivity())
                .updateCustomer(mCustomer);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_new_customer, container, false);

        mFullName = (EditText)view.findViewById(R.id.FullName);
        mFullName.setText(mCustomer.getFullNmae());
        mFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCustomer.setFullNmae(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmail = (EditText)view.findViewById(R.id.Email);
        mEmail.setText(mCustomer.getEmail());
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCustomer.setEmail(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAddress = (EditText)view.findViewById(R.id.Address);
        mAddress.setText(mCustomer.getAddress());
        mAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCustomer.setAddress(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPhoneNumber = (EditText)view.findViewById(R.id.PhoneNumber);
        mPhoneNumber.setText(mCustomer.getPhoneNumber());
        mPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCustomer.setPhoneNumber(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        PackageManager packageManager = getActivity().getPackageManager();

        mButton = (Button) view.findViewById(R.id.imageB);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mButton.setEnabled(canTakePhoto);

        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }

        });
        mImageView = (ImageView) view.findViewById(R.id.customerimage);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    //    inflater.inflate(R.menu.main_menu_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
                case R.id.logout_settings:
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mImageView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mImageView.setImageBitmap(bitmap);
        }
    }
}
