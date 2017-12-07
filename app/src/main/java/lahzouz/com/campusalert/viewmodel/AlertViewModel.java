package lahzouz.com.campusalert.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.ObservableField;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import lahzouz.com.campusalert.service.database.AppDatabase;
import lahzouz.com.campusalert.service.localisation.LocationProvider;
import lahzouz.com.campusalert.service.model.Alert;

import static lahzouz.com.campusalert.view.ui.AlertListFragment.TAG;


public class AlertViewModel extends AndroidViewModel {
    private final LiveData<Alert> alertObservable;
    private final long alertId;
    public ObservableField<Alert> alert = new ObservableField<>();
    private AppDatabase appDatabase;
    private MutableLiveData<Location> locationLiveData = new MutableLiveData<>();
    private double longitude;
    private double latitude;
    private String address;
    private Geocoder geocoder;

    public AlertViewModel(@NonNull Application application,
                          final long alertId) {
        super(application);
        this.alertId = alertId;
        appDatabase = AppDatabase.getAppDatabase(application.getApplicationContext());
        alertObservable = appDatabase.AlertModel().findOneAlert(alertId);
        this.geocoder = new Geocoder(application, Locale.getDefault());
    }

    public void getCurrentLocation(){
        LocationListener locationListener= new LocationListener(){
            @Override
            public void onLocationChanged(Location location) {
                try {

                    latitude=location.getLatitude();
                    longitude=location.getLongitude();

                    List<Address> results = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    StringBuilder strbu = new StringBuilder("");

                    strbu.append(latitude);
                    strbu.append(",");
                    strbu.append(longitude);
                    Log.e("location",strbu.toString() );

                    address=results.get(0).getAddressLine(0);
                    locationLiveData.postValue(location);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        LocationProvider locationProvider = new LocationProvider();
        locationProvider.locationUpdate(this.getApplication(),locationListener);
    }

    public void setAlert(Alert alert) {
        this.alert.set(alert);
    }

    public String getAddress() {
        return address;
    }

    public double getLongitude() {return longitude;}

    public double getLatitude() {
        return latitude;
    }


    public boolean checkLocationEnabled(Context context) {
        LocationProvider locationProvider = new LocationProvider();
        return locationProvider.canGetLocation(context);
    }

    public LiveData<Alert> getObservableProject() {
        return alertObservable;
    }

    public LiveData<Location> getLocationLiveData() {
        return locationLiveData;
    }



    public void deleteAlert(Alert alert) {
        new DeleteAlertAsync(alert).execute();
    }

    private class DeleteAlertAsync extends AsyncTask<Void, Void, Void> {

        private Alert alert;
        public DeleteAlertAsync(Alert alert) {
            super();
            // do stuff
            this.alert=alert;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            Log.d(TAG, "doInBackground: delete"+alert.toString());
            appDatabase.AlertModel().delete(alert);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //To after addition operation here.
        }
    }


    public void insertAlert(Alert alert) {
             new InsertAlertAsync(alert).execute();
    }

    private class InsertAlertAsync extends AsyncTask<Void, Void, Void> {

        private Alert alert;
        public InsertAlertAsync(Alert alert) {
            super();
            // do stuff
            this.alert=alert;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            Log.d(TAG, "doInBackground: insert"+alert.toString());
            appDatabase.AlertModel().insertOne(alert);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //To after addition operation here.
        }
    }

    public Alert getAlert(long alertId) throws ExecutionException, InterruptedException {
        return new GetAlertAsync(alertId).execute().get();
    }

    private class GetAlertAsync extends AsyncTask<Void, Void, Alert> {

        private long alertId;
        public GetAlertAsync(long alertId) {
            super();
            // do stuff
            this.alertId=alertId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        @Override
        protected Alert doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            Log.d(TAG, "doInBackground: get alert by id"+alertId);
            return appDatabase.AlertModel().getAlert(alertId);
        }

        @Override
        protected void onPostExecute(Alert alert) {
            super.onPostExecute(alert);
            //To after addition operation here.
        }
    }


    /**
     * A creator is used to inject the alert ID into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final long alertId;
        public Factory(@NonNull Application application, long alertId) {
            this.application = application;
            this.alertId = alertId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new AlertViewModel(application, alertId);
        }
    }



}
