package lahzouz.com.campusalert.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import lahzouz.com.campusalert.service.database.AppDatabase;
import lahzouz.com.campusalert.service.model.Alert;


public class AlertViewModel extends AndroidViewModel {
    private final LiveData<Alert> alertObservable;
    private final long alertId;
    public ObservableField<Alert> alert = new ObservableField<>();
    private AppDatabase appDatabase;

    public AlertViewModel(@NonNull Application application,
                          final long alertId) {
        super(application);
        this.alertId = alertId;
        appDatabase = AppDatabase.getAppDatabase(application.getApplicationContext());

    /***********************************************************************************************
     * Etape == 04 -->> prochaine étape -->> AlertListFragment
     **********************************************************************************************/
        // uncomment to switch to Database mode
        alertObservable = appDatabase.AlertModel().findOneAlert(alertId);
        // uncomment to switch to Remote repository mode
//        alertObservable = AlertRepository.getInstance().getProjectDetails("Apolline-Lille", alertId);
    /***********************************************************************************************
     **********************************************************************************************/
    }

    public LiveData<Alert> getObservableProject() {
        return alertObservable;
    }


    public void setAlert(Alert alert) {
        this.alert.set(alert);
    }


    public void deleteAlert(Alert alert) {
        appDatabase.AlertModel().delete(alert);
    }

    public Alert getAlert(long alertId) {
        return appDatabase.AlertModel().getAlert(alertId);
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
