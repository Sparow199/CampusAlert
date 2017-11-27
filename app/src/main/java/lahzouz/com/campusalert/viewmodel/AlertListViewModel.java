package lahzouz.com.campusalert.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.OnLifecycleEvent;
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
        // If any transformation is needed, this can be simply done by Transformations class ...
        appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        // appDatabase.AlertModel().insertAll(alerts);
        // appDatabase.AlertModel().deleteAll();
        // Log.d("TEST", "AlertListViewModel: " + appDatabase.AlertModel().deleteAll());
        // appDatabase.AlertModel().insertAll(initAlertList());
        // uncomment to switch to local database mode
        alertListObservable = appDatabase.AlertModel().getAll();
        // uncomment to switch to Remote repository mode
//        alertListObservable = AlertRepository.getInstance().getProjectList("Apolline-Lille");
    }


    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<List<Alert>> getProjectListObservable() {
        return alertListObservable;
    }

    public void insertAllAlerts(List<Alert> list) {
        appDatabase.AlertModel().insertAll(list);
    }

    public void deleteAlert(Alert alert) {
        appDatabase.AlertModel().delete(alert);
    }

    public void deleteAllAlerts() {
        appDatabase.AlertModel().deleteAll();
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void create() {
        Log.d(TAG, "create: ");
        deleteAllAlerts();
        insertAllAlerts(initAlertList());
    }

}
