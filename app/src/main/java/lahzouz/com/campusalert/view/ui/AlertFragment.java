package lahzouz.com.campusalert.view.ui;


import android.arch.lifecycle.LifecycleOwner;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lahzouz.com.campusalert.R;
import lahzouz.com.campusalert.databinding.FragmentAlertDetailsBinding;
import lahzouz.com.campusalert.service.model.Alert;

import lahzouz.com.campusalert.viewmodel.AlertViewModel;

public class AlertFragment extends Fragment implements LifecycleOwner{
    private static final String KEY_PROJECT_ID = "alert_id";
    private FragmentAlertDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alert_details, container, false);

        // Create and set the adapter for the RecyclerView.
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AlertViewModel.Factory factory = new AlertViewModel.Factory(
                getActivity().getApplication(), getArguments().getString(KEY_PROJECT_ID));

        final AlertViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(AlertViewModel.class);

        binding.setAlertViewModel(viewModel);
        binding.setIsLoading(true);

        observeViewModel(viewModel);


    }


    private void observeViewModel(final AlertViewModel viewModel) {
        // Observe alert data
        viewModel.getObservableProject().observe(this, new Observer<Alert>() {
            @Override
            public void onChanged(@Nullable Alert alert) {
                if (alert != null) {
                    binding.setIsLoading(false);
                    viewModel.setAlert(alert);
                }
            }
        });
    }



    /** Creates alert fragment for specific alert ID */
    public static AlertFragment forAlert(String alertID) {
        AlertFragment fragment = new AlertFragment();
        Bundle args = new Bundle();

        args.putString(KEY_PROJECT_ID, alertID);
        fragment.setArguments(args);

        return fragment;
    }

}
