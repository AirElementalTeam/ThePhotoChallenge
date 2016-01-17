package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.models.Photo;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters.ApprovePhotoPresenter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import Common.MessageDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApprovePhotoFragment extends android.app.Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{
    private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;

    private AlertDialog locationDialog;

    ImageView photoView;
    ImageButton takeLocationButton;
    ImageButton approvePhotoButton;
    ImageButton declinePhotoButton;
    private TextView locationText;
    private String locationString;
    private ApprovePhotoPresenter presenter;
    private Photo photo;
    private Bitmap bmp;

    public ApprovePhotoFragment() {
        // Required empty public constructor
    }



    private void displayLocation() {
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            List<Address> addresses = new LinkedList<>();
            System.out.println("LOCATION   --------->   " + latitude + ", " + longitude);
            Geocoder gcd = new Geocoder(getActivity().getApplicationContext(), Locale.ENGLISH);
            try {
                addresses = gcd.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.getCause();
            }
            if(addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                System.out.println(addresses.get(0).getCountryName());
                locationDialog.setTitle("Your location");
                locationDialog.setMessage(addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName());
                this.locationString = addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName();
                locationDialog.show();
            }


        } else {

        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    private boolean checkGooglePlayServices() {
        int checkGooglePlayServices = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getActivity());
        if (checkGooglePlayServices != ConnectionResult.SUCCESS) {

            GooglePlayServicesUtil.getErrorDialog(checkGooglePlayServices,
                    this.getActivity(), REQUEST_CODE_RECOVER_PLAY_SERVICES).show();

            return false;
        }

        return true;
    }

    protected void startLocationUpdates() {
       // LocationServices.FusedLocationApi.requestLocationUpdates( mGoogleApiClient, mLocationRequest, (LocationListener)this.getActivity());
    }

    private void stopLocationUpdates() {
       // if (mGoogleApiClient != null) { LocationServices.FusedLocationApi.removeLocationUpdatesmGoogleApiClient, this); }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approve_photo, container, false);
        presenter = new ApprovePhotoPresenter(getActivity(), this);

        locationDialog = new AlertDialog.Builder(getActivity()).create();
        locationDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                photo.setLocation(locationString);
                locationText.setText(locationString);
                locationDialog.cancel();
            }
        });
        locationDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                locationDialog.cancel();
            }
        });
        takeLocationButton = (ImageButton) view.findViewById(R.id.take_location_button);
        approvePhotoButton = (ImageButton) view.findViewById(R.id.approve_photo_button);
        declinePhotoButton = (ImageButton) view.findViewById(R.id.decline_photo_button);
        locationText = (TextView) view.findViewById(R.id.location_text);
        photoView = (ImageView) view.findViewById(R.id.new_photo_viewer);
        photoView.setImageBitmap(this.bmp);

        if (checkGooglePlayServices()) {
            buildGoogleApiClient();
            createLocationRequest();
        }

        takeLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation();
                //presenter.takeLocation(photo);
            }
        });

        approvePhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                presenter.onApprovedPhoto(photo);

            }
        });
//
        declinePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDeclinedPhoto(photo);
            }
        });

        return view;
    }

    //GoogleApiClient
    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        startLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    //location listener
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    //fragment
    @Override
    public void onStart() {
        super.onStart();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
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

    public TextView getLocationText() {
        return locationText;
    }

    public void setLocationText(TextView locationText) {
        this.locationText = locationText;
    }
}
