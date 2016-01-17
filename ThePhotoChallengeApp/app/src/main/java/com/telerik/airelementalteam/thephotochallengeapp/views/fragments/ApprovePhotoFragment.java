package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.models.Photo;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters.ApprovePhotoPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApprovePhotoFragment extends android.app.Fragment
{

    ImageView photoView;
    ImageView takeLocationButton;
    ImageView approvePhotoButton;
    ImageView declinePhotoButton;

    private ApprovePhotoPresenter presenter;
    private Photo photo;
    private Bitmap bmp;

    public ApprovePhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approve_photo, container, false);
        presenter = new ApprovePhotoPresenter(getActivity(), this);
        takeLocationButton = (ImageView) view.findViewById(R.id.take_location_button);
        approvePhotoButton = (ImageView) view.findViewById(R.id.approve_photo_button);
        declinePhotoButton = (ImageView) view.findViewById(R.id.decline_friend_button);
        photoView = (ImageView) view.findViewById(R.id.new_photo_viewer);
        photoView.setImageBitmap(this.bmp);

        takeLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.takeLocation(photo);
            }
        });

        approvePhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                presenter.onApprovedPhoto(photo);

            }
        });

        declinePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDeclinedPhoto(photo);

            }
        });

        return view;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Bitmap getBmp() {
        return bmp;
    }
    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

}
