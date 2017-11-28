package lahzouz.com.campusalert.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.List;

import lahzouz.com.campusalert.service.database.AppDatabase;
import lahzouz.com.campusalert.service.model.Alert;

import static lahzouz.com.campusalert.service.database.InitDatabase.initAlertList;


public class AlertListViewModel extends AndroidViewModel implements LifecycleObserver {

    private LiveData<List<Alert>> alertListObservable;
    private AppDatabase appDatabase;

    public AlertListViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        alertListObservable = appDatabase.AlertModel().getAll();
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
        deleteAllAlerts();
        insertAllAlerts(initAlertList());
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<List<Alert>> getProjectListObservable() {
        return alertListObservable;
    }

}
