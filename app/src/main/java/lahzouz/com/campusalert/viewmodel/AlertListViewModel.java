package lahzouz.com.campusalert.viewmodel;

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

import lahzouz.com.campusalert.service.database.AppDatabase;
import lahzouz.com.campusalert.service.model.Alert;

import static lahzouz.com.campusalert.service.database.InitDatabase.initAlertList;
import static lahzouz.com.campusalert.view.ui.AlertListFragment.TAG;


public class AlertListViewModel extends AndroidViewModel implements LifecycleObserver {

    private LiveData<List<Alert>> alertListObservable;
    private AppDatabase appDatabase;

    public AlertListViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        alertListObservable = appDatabase.AlertModel().getAll();
    }

    private void insertAllAlerts(List<Alert> list) {
        new InsertAllAlertsAsync(list).execute();
    }

    private void deleteAllAlerts() {
        new DeleteAllAlertsAsync().execute();
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<List<Alert>> getProjectListObservable() {
        return alertListObservable;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void create() {
        deleteAllAlerts();
        insertAllAlerts(initAlertList());
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertAllAlertsAsync extends AsyncTask<Void, Void, Void> {

        private List<Alert> alertList;

        InsertAllAlertsAsync(List<Alert> alertList) {
            super();
            // do stuff
            this.alertList=alertList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            Log.d(TAG, "doInBackground: Insert alert list");
            appDatabase.AlertModel().insertAll(alertList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //To after addition operation here.
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteAllAlertsAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            Log.d(TAG, "doInBackground: Delete alert list");
            appDatabase.AlertModel().deleteAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //To after addition operation here.
        }
    }


}
