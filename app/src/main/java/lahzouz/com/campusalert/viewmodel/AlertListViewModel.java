package lahzouz.com.campusalert.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lahzouz.com.campusalert.service.database.AppDatabase;
import lahzouz.com.campusalert.service.model.Alert;

public class AlertListViewModel extends AndroidViewModel {

    private LiveData<List<Alert>> alertListObservable;
    private AppDatabase appDatabase;

    private Alert alt1 = new Alert("type1", "desc1", "adress1", -10.0, 10.0, new Date());
    private Alert alt2 = new Alert("type2", "desc2", "adress2", -10.0, 10.0, new Date());
    private Alert alt3 = new Alert("type3", "desc3", "adress3", -10.0, 10.0, new Date());
    private Alert alt4 = new Alert("type4", "desc4", "adress4", -10.0, 10.0, new Date());
    private Alert alt5 = new Alert("type5", "desc5", "adress5", -10.0, 10.0, new Date());
    private Alert alt6 = new Alert("type6", "desc6", "adress6", -10.0, 10.0, new Date());
    private Alert alt7 = new Alert("type7", "desc7", "adress7", -10.0, 10.0, new Date());
    private Alert alt8 = new Alert("type8", "desc8", "adress8", -10.0, 10.0, new Date());
    private Alert alt9 = new Alert("type9", "desc9", "adress9", -10.0, 10.0, new Date());
    private Alert alt10 = new Alert("type10", "desc10", "adress10", -10.0, 10.0, new Date());
    private List<Alert> alerts = new ArrayList<>();

    public AlertListViewModel(Application application) {
        super(application);

        alerts.add(alt1);
        alerts.add(alt2);
        alerts.add(alt3);
        alerts.add(alt4);
        alerts.add(alt5);
        alerts.add(alt6);
        alerts.add(alt7);
        alerts.add(alt8);
        alerts.add(alt9);
        alerts.add(alt10);

        // If any transformation is needed, this can be simply done by Transformations class ...
        appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        appDatabase.AlertModel().insertAll(alerts);
        appDatabase.AlertModel().deleteAll();
        Log.d("TEST", "AlertListViewModel: " + appDatabase.AlertModel().deleteAll());
        appDatabase.AlertModel().insertAll(alerts);

    /***********************************************************************************************
     * Etape == 03 -->> prochaine étape -->> AlertViewModel
     **********************************************************************************************/
        // uncomment to switch to local database mode
        alertListObservable = appDatabase.AlertModel().getAll();

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
        appDatabase.AlertModel().delete(alert);
    }
}
