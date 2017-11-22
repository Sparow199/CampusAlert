package lahzouz.com.campusalert.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.CountDownTimer;
import android.util.Log;

import lahzouz.com.campusalert.service.database.AppDatabase;
import lahzouz.com.campusalert.service.model.Alert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlertListViewModel extends AndroidViewModel {

    private LiveData<List<Alert>> alertListObservable;
    private AppDatabase appDatabase;
    private Alert pj1 = new Alert("test0","test0", new Date(), new Date(),"url", "Java", 1, 1,"test");
    private Alert pj2 = new Alert("test1","test1", new Date(), new Date(),"url", "Kotlin", 2, 2,"test");
    private Alert pj3 = new Alert("test2","test2", new Date(), new Date(),"url", "Java", 3, 3,"test");
    private Alert pj4 = new Alert("test3","test3", new Date(), new Date(),"url", "Java", 1, 1,"test");
    private Alert pj5 = new Alert("test4","test4", new Date(), new Date(),"url", "Kotlin", 2, 2,"test");
    private Alert pj6 = new Alert("test5","test5", new Date(), new Date(),"url", "Java", 3, 3,"test");
    private List<Alert> alerts = new ArrayList<>();

    public AlertListViewModel(Application application) {
        super(application);
        alerts.add(pj1);
        alerts.add(pj2);
        alerts.add(pj3);

        alerts.add(pj4);
        alerts.add(pj5);
        alerts.add(pj6);

        // If any transformation is needed, this can be simply done by Transformations class ...
        appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        appDatabase.ProjectModel().insertAll(alerts);
        appDatabase.ProjectModel().deleteAll();
        Log.d("TEST", "AlertListViewModel: "+appDatabase.ProjectModel().deleteAll());
        appDatabase.ProjectModel().insertAll(alerts);

    /***********************************************************************************************
     * Etape == 03 -->> prochaine étape -->> AlertViewModel
     **********************************************************************************************/
        // uncomment to switch to local database mode
        alertListObservable = appDatabase.ProjectModel().getAll();

        // uncomment to switch to Remote repository mode
//        alertListObservable = AlertRepository.getInstance().getProjectList("Apolline-Lille");
    /***********************************************************************************************
     **********************************************************************************************/
    }


    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<List<Alert>> getProjectListObservable() {
        return alertListObservable;}


    public void deleteAlert(Alert alert) {
        appDatabase.ProjectModel().delete(alert);
    }
}
