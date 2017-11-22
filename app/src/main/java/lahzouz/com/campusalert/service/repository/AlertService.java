package lahzouz.com.campusalert.service.repository;

import lahzouz.com.campusalert.service.model.Alert;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface AlertService {
    String HTTPS_API_GITHUB_URL = "https://api.github.com/";

    @GET("users/{user}/repos")
    Call<List<Alert>> getProjectList(@Path("user") String user);

    @GET("/repos/{user}/{reponame}")
    Call<Alert> getProjectDetails(@Path("user") String user, @Path("reponame") String alertName);

}
