package lahzouz.com.campusalerte.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import lahzouz.com.campusalerte.service.database.AppDatabase;
import lahzouz.com.campusalerte.service.model.Alert;

import static lahzouz.com.campusalerte.service.database.InitDatabase.initAlertList;
import static lahzouz.com.campusalerte.view.ui.AlertListFragment.TAG;

/**
 * Classe Liste viewModel qui gère la connexion entre la partie vue et modèle.
 */
public class AlertListViewModel extends AndroidViewModel implements LifecycleObserver {

    private LiveData<List<Alert>> alertListObservable;
    private AppDatabase appDatabase;

    /**
     * Constructeur.
     * @param application
     */
    public AlertListViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        alertListObservable = appDatabase.AlertModel().getAll();
    }

    /**
     * Insérer une liste d'alertes.
     * @param list
     */
    private void insertAllAlerts(List<Alert> list) {
        new InsertAllAlertsAsync(list).execute();
    }

    /**
     * Supprimer toutes les alertes.
     */
    private void deleteAllAlerts() {
        new DeleteAllAlertsAsync().execute();
    }

    /**
     * Getter
     * @return LiveData<List<Alert>>
     */
    public LiveData<List<Alert>> getProjectListObservable() {
        return alertListObservable;
    }

    /**
     * Supprimer et initialiser la base de données.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void create() {
        deleteAllAlerts();
        insertAllAlerts(initAlertList());
    }

    /**
     * Async task pour insérer une liste d'alerte.
     */
    @SuppressLint("StaticFieldLeak")
    private class InsertAllAlertsAsync extends AsyncTask<Void, Void, Void> {

        private List<Alert> alertList;

        /**
         * Constructeur.
         * @param alertList
         */
        InsertAllAlertsAsync(List<Alert> alertList) {
            super();
            // do stuff
            this.alertList=alertList;
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
            Log.d(TAG, "doInBackground: Insert alert list");
            appDatabase.AlertModel().insertAll(alertList);
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
     * AsyncTask supprimer toutes les alertes.
     */
    @SuppressLint("StaticFieldLeak")
    private class DeleteAllAlertsAsync extends AsyncTask<Void, Void, Void> {
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
            Log.d(TAG, "doInBackground: Delete alert list");
            appDatabase.AlertModel().deleteAll();
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


}
