package lahzouz.com.campusalert.view.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import lahzouz.com.campusalert.R;
import lahzouz.com.campusalert.service.model.Alert;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add alert list fragment if this is first creation
        if (savedInstanceState == null) {
            AlertListFragment fragment = new AlertListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, AlertListFragment.TAG).commit();
        }
    }

    /** Shows the alert detail fragment */
    public void show(Alert alert) {
        AlertFragment alertFragment = AlertFragment.forAlert(alert.name);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("alert")
                .replace(R.id.fragment_container,
                        alertFragment, null).commit();
    }
}
