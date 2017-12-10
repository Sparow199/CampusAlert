package lahzouz.com.campusalert.viewmodel;

import android.annotation.SuppressLint;
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

/**
 *  * Classe Alert viewModel qui gère la connexion entre la partie vue et modèle.
 */
public class AlertViewModel extends AndroidViewModel {
    private final LiveData<Alert> alertObservable;
    public ObservableField<Alert> alert = new ObservableField<>();
    private AppDatabase appDatabase;
    private MutableLiveData<Location> locationLiveData = new MutableLiveData<>();
    private double longitude;
    private double latitude;
    private String address;
    private Geocoder geocoder;

    /**
     * Constructeur.
     * @param application
     * @param alertId
     */
    AlertViewModel(@NonNull Application application,
                   final long alertId) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(application.getApplicationContext());
        alertObservable = appDatabase.AlertModel().findOneAlert(alertId);
        this.geocoder = new Geocoder(application, Locale.getDefault());
    }

    /**
     * Avoir les coordonnée GPS.
     */
    public void getCurrentLocation(){
        LocationListener locationListener= new LocationListener(){
            /**
             * LocationListener onLocationChanged.
             * @param location
             */
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

            /**
             * LocationListener onStatusChanged.
             * @param s
             * @param i
             * @param bundle
             */
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            /**
             * LocationListener onProviderEnabled.
             * @param s
             */
            @Override
            public void onProviderEnabled(String s) {

            }

            /**
             * LocationListener onProviderDisabled.
             * @param s
             */
            @Override
            public void onProviderDisabled(String s) {

            }
        };
        LocationProvider locationProvider = new LocationProvider();
        locationProvider.locationUpdate(this.getApplication(),locationListener);
    }

    /**
     * Setter
     * @param alert
     */
    public void setAlert(Alert alert) {
        this.alert.set(alert);
    }

    /**
     * Getter
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter
     * @return double
     */
    public double getLongitude() {return longitude;}

    /**
     * Getter
     * @return double
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Vérifie si la localisation est activée sur l'appareil.
     * @param context
     * @return boolean
     */
    public boolean checkLocationEnabled(Context context) {
        LocationProvider locationProvider = new LocationProvider();
        return locationProvider.canGetLocation(context);
    }

    /**
     * Getter
     * @return LiveData<Alert>
     */
    public LiveData<Alert> getObservableProject() {
        return alertObservable;
    }

    /**
     * Getter
     * @return LiveData<Location>
     */
    public LiveData<Location> getLocationLiveData() {
        return locationLiveData;
    }


    /**
     * Supprimer l'alerte
     * @param alert
     */
    public void deleteAlert(Alert alert) {
        new DeleteAlertAsync(alert).execute();
    }

    /**
     * Insérer l'alerte
     * @param alert
     */
    public void insertAlert(Alert alert) {
        new InsertAlertAsync(alert).execute();
    }

    /**
     * AsyncTask, permets de retourner une alerte.
     * @param alertId
     * @return Alert
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Alert getAlert(long alertId) throws ExecutionException, InterruptedException {
        return new GetAlertAsync(alertId).execute().get();
    }

    /**
     * Classe usine, pour la création des viewModels.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final long alertId;

        /**
         * Constructeur.
         * @param application
         * @param alertId
         */
        public Factory(@NonNull Application application, long alertId) {
            this.application = application;
            this.alertId = alertId;
        }

        /**
         * Retourner un vieModel.
         * @param modelClass
         * @param <T>
         * @return AlertViewModel
         */
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new AlertViewModel(application, alertId);
        }
    }

    /**
     * AsyncTask pour supprimer une alerte.
     */
    @SuppressLint("StaticFieldLeak")
    private class DeleteAlertAsync extends AsyncTask<Void, Void, Void> {

        private Alert alert;

        /**
         * Constructeur.
         * @param alert
         */
        DeleteAlertAsync(Alert alert) {
            super();
            // do stuff
            this.alert=alert;
        }

        /**
         * AsyncTask onPreExecute.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        /**
         * AsyncTask doInBackground.
         * @param voids
         * @return null
         */
        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            Log.d(TAG, "doInBackground: delete"+alert.toString());
            appDatabase.AlertModel().delete(alert);
            return null;
        }

        /**
         * AsyncTask onPostExecute.
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //To after addition operation here.
        }
    }

    /**
     * AsyncTask pour insérer une alerte.
     */
    @SuppressLint("StaticFieldLeak")
    private class InsertAlertAsync extends AsyncTask<Void, Void, Void> {

        private Alert alert;

        /**
         * Constructeur.
         * @param alert
         */
        InsertAlertAsync(Alert alert) {
            super();
            // do stuff
            this.alert=alert;
        }

        /**
         * AsyncTask onPreExecute.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        /**
         * AsyncTask doInBackground.
         * @param voids
         * @return null
         */
        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            Log.d(TAG, "doInBackground: insert"+alert.toString());
            appDatabase.AlertModel().insertOne(alert);
            return null;
        }

        /**
         * AsyncTask  onPostExecute.
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //To after addition operation here.
        }
    }

    /**
     * AsyncTask pour avoir une alerte.
     */
    @SuppressLint("StaticFieldLeak")
    private class GetAlertAsync extends AsyncTask<Void, Void, Alert> {

        private long alertId;

        /**
         * Constructeur.
         * @param alertId
         */
        GetAlertAsync(long alertId) {
            super();
            // do stuff
            this.alertId=alertId;
        }

        /**
         * AsyncTask onPreExecute.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        /**
         * AsyncTask doInBackground.
         * @param voids
         * @return Alert
         */
        @Override
        protected Alert doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            Log.d(TAG, "doInBackground: get alert by id"+alertId);
            return appDatabase.AlertModel().getAlert(alertId);
        }

        /**
         * AsyncTask  onPostExecute.
         * @param alert
         */
        @Override
        protected void onPostExecute(Alert alert) {
            super.onPostExecute(alert);
            //To after addition operation here.
        }
    }



}
