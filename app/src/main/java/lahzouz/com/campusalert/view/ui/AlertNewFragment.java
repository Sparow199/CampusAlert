package lahzouz.com.campusalert.view.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import lahzouz.com.campusalert.R;
import lahzouz.com.campusalert.databinding.FragmentAlertNewBinding;
import lahzouz.com.campusalert.service.model.Alert;
import lahzouz.com.campusalert.view.callback.AlertClickCallback;
import lahzouz.com.campusalert.viewmodel.AlertViewModel;

import static lahzouz.com.campusalert.view.ui.AlertListFragment.TAG;


public class AlertNewFragment extends Fragment implements LifecycleOwner {

    private FragmentAlertNewBinding binding;
    private Spinner spinner_type;
    private AlertViewModel viewModelDetails;
    private Alert alertGlobal = new Alert();
    private EditText edit_address;
//    private WebView myWebView;

    private final AlertClickCallback alertClickCallback = new AlertClickCallback() {

        @Override
        public void onSaveClick(Alert alert) {
                alert.setType(alertGlobal.getType());
                alert.setCreated_at(new Date());
                alert.setLongitude(alertGlobal.getLongitude());
                alert.setLatitude(alertGlobal.getLatitude());

                if (alert.getType() != null) {
                    if (alert.getAddress() != null) {
                        if (alert.getLatitude() != -2 && alert.getLongitude() != -2) {
                            viewModelDetails.insertAlert(alert);
                            removeCurrentFragment();
                        } else {
                            Toast.makeText(getActivity(), "Erreur, données de localisation introuvables, latitude : " + alert.getLatitude() + " longitude : " + alert.getLongitude(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Erreur, veuillez indiquer une addresse", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Erreur,veuillez indiquer un type", Toast.LENGTH_LONG).show();
                }
            }


        @Override
        public void onDeleteClick(Alert alert) {
        }

        @Override
        public void onClick(Alert alert) {
        }

        @Override
        public void onAddClick() {
        }

    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alert_new, container, false);

        // Create and set the adapter for the RecyclerView.
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner_type = view.findViewById(R.id.alert_type_spinner);
        edit_address = view.findViewById(R.id.address);
        setHasOptionsMenu(true);

//        myWebView = view.findViewById(R.id.new_web_view);
//        String mapPath = "https://maps.google.com/maps";
//        myWebView.loadUrl(mapPath+"?q=43.0054446,-87.9678884&t=m&z=7");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.geolocation_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_location:
                viewModelDetails.getCurrentLocation();
                Toast.makeText(getActivity(), "Localisation actualisée", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AlertViewModel.Factory factory = new AlertViewModel.Factory(
                getActivity().getApplication(), 0);

        viewModelDetails = ViewModelProviders.of(this, factory).get(AlertViewModel.class);
        viewModelDetails.getCurrentLocation();
        
        binding.setAlert(new Alert());
        binding.setCallback(alertClickCallback);


        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                alertGlobal.setType((String) parent.getItemAtPosition(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        LiveData<Location> locationLiveData = viewModelDetails.getLocationLiveData();
        locationLiveData.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                edit_address.setText(viewModelDetails.getAddress());
                alertGlobal.setLatitude(viewModelDetails.getLatitude());
                alertGlobal.setLongitude(viewModelDetails.getLongitude());
            }
        });



    }

    private void removeCurrentFragment(){
        getActivity().getSupportFragmentManager().popBackStack(this.getClass().getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


}
