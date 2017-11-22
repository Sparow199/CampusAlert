package lahzouz.com.campusalert.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import lahzouz.com.campusalert.service.model.Alert;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlertRepository {
    private AlertService alertService;
    private static AlertRepository alertRepository;

    private AlertRepository() {
        //TODO this alertService instance will be injected using Dagger in part #2 ...
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AlertService.HTTPS_API_GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        alertService = retrofit.create(AlertService.class);
    }

    public synchronized static AlertRepository getInstance() {
        //TODO No need to implement this singleton in Part #2 since Dagger will handle it ...
        if (alertRepository == null) {
            if (alertRepository == null) {
                alertRepository = new AlertRepository();
            }
        }
        return alertRepository;
    }


    public LiveData<List<Alert>> getAlertList(String userId) {
        final MutableLiveData<List<Alert>> data = new MutableLiveData<>();

        alertService.getProjectList(userId).enqueue(new Callback<List<Alert>>() {
            @Override
            public void onResponse(Call<List<Alert>> call, Response<List<Alert>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Alert>> call, Throwable t) {
                // TODO better error handling in part #2 ...
                data.setValue(null);
            }
        });

        return data;
    }


    public LiveData<Alert> getAlertDetails(String userID, String alertName) {
        final MutableLiveData<Alert> data = new MutableLiveData<>();

        alertService.getProjectDetails(userID, alertName).enqueue(new Callback<Alert>() {
            @Override
            public void onResponse(Call<Alert> call, Response<Alert> response) {
                simulateDelay();
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Alert> call, Throwable t) {
                // TODO better error handling in part #2 ...
                data.setValue(null);
            }
        });

        return data;
    }


    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
