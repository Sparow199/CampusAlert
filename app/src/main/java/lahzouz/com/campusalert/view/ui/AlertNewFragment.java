package lahzouz.com.campusalert.view.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.Date;

import lahzouz.com.campusalert.R;
import lahzouz.com.campusalert.databinding.FragmentAlertNewBinding;
import lahzouz.com.campusalert.service.model.Alert;
import lahzouz.com.campusalert.view.callback.AlertClickCallback;
import lahzouz.com.campusalert.viewmodel.AlertViewModel;


public class AlertNewFragment extends Fragment implements LifecycleOwner {

    private FragmentAlertNewBinding binding;
    private Spinner spinner_type;
    private String alertType = "";
    private AlertViewModel viewModelDetails;
    private final AlertClickCallback alertClickCallback = new AlertClickCallback() {

        @Override
        public void onSaveClick(Alert alert) {
            if (alert != null) {
                alert.setType(alertType);
                alert.setCreated_at(new Date());
                getFragmentManager().popBackStackImmediate();
                viewModelDetails.insertAlert(alert);
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

    /**
     * Creates alert fragment for specific alert ID
     */
    public static AlertNewFragment forNewAlert() {
        AlertNewFragment fragment = new AlertNewFragment();
        return fragment;
    }

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AlertViewModel.Factory factory = new AlertViewModel.Factory(
                getActivity().getApplication(), 0);

        viewModelDetails = ViewModelProviders.of(this, factory).get(AlertViewModel.class);

        binding.setAlert(new Alert());
        binding.setCallback(alertClickCallback);


        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                alertType = (String) parent.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }



}
