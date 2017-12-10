package lahzouz.com.campusalert.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import lahzouz.com.campusalert.R;
import lahzouz.com.campusalert.viewmodel.AlertListViewModel;

/**
 * Classe de l'activitée principale.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Activity onCreate.
     * @param savedInstanceState
     */
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

        AlertListViewModel viewModelList = ViewModelProviders.of(this).get(AlertListViewModel.class);
        this.getLifecycle().addObserver(viewModelList);
    }


    /**
     * Ajouter un fragment au sommet de la pile.
     * @param fragment
     */
    public void show(Fragment fragment) {

        String fragmentName = fragment.getClass().getName();
        FragmentManager fragManager = getSupportFragmentManager();
        boolean fragmentPopped = fragManager.popBackStackImmediate(fragmentName, 0);

        if (fragManager.findFragmentByTag(fragmentName) == null && !fragmentPopped) {
            FragmentTransaction fragTrans = fragManager.beginTransaction();
            fragTrans.replace(R.id.fragment_container, fragment, fragmentName);
            fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragTrans.addToBackStack(fragmentName);
            fragTrans.commit();
        }

    }

    /**
     * Supprimer un fragment de la pile.
     * @param className
     */
    public void removeCurrentFragment(String className){
        getSupportFragmentManager().popBackStack(className, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
