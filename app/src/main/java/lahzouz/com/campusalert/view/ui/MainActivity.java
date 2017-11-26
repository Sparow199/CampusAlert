package lahzouz.com.campusalert.view.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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
        AlertFragment alertFragment = AlertFragment.forAlert(alert.getId());
        String fragmentName = alertFragment.getClass().getName();
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(fragmentName)
                .replace(R.id.fragment_container,
                        alertFragment, fragmentName).commit();
    }

    /**
     * Shows the new alert fragment
     */
    public void addNew() {
        AlertNewFragment alertNewFragment = AlertNewFragment.forNewAlert();
        String fragmentName = alertNewFragment.getClass().getName();
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(fragmentName)
                .replace(R.id.fragment_container,
                        alertNewFragment, fragmentName).commit();
    }

}
