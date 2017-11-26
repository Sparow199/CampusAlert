package lahzouz.com.campusalert.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lahzouz.com.campusalert.R;
import lahzouz.com.campusalert.databinding.FragmentAlertDetailsBinding;
import lahzouz.com.campusalert.service.model.Alert;
import lahzouz.com.campusalert.view.callback.AlertClickCallback;
import lahzouz.com.campusalert.viewmodel.AlertViewModel;


public class AlertFragment extends Fragment implements LifecycleOwner{
    private static final String KEY_PROJECT_ID = "alert_id";
    private FragmentAlertDetailsBinding binding;
    private AlertViewModel viewModelDetails;
    private final AlertClickCallback alertClickCallback = new AlertClickCallback() {

        @Override
        public void onDeleteClick(Alert alert) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                //Log.d("DELETE", "onDeleteButtonClick: alertNull"+alert.toString());

                if (alert != null) {
                    Log.d("DELETE", "onDeleteButtonClick: test listner");
                    getFragmentManager().popBackStackImmediate();
                    viewModelDetails.deleteAlert(alert);
                }

            }
        }

        @Override
        public void onClick(Alert alert) {
        }

    };

    /**
     * Creates alert fragment for specific alert ID
     */
    public static AlertFragment forAlert(long alertID) {
        AlertFragment fragment = new AlertFragment();
        Bundle args = new Bundle();

        args.putLong(KEY_PROJECT_ID, alertID);
        fragment.setArguments(args);

        return fragment;
    }

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
                getActivity().getApplication(), getArguments().getLong(KEY_PROJECT_ID));

        viewModelDetails = ViewModelProviders.of(this, factory)
                .get(AlertViewModel.class);

        binding.setAlert(viewModelDetails.getAlert(getArguments().getLong(KEY_PROJECT_ID)));
        binding.setCallback(alertClickCallback);
        binding.setIsLoading(true);

        observeViewModel(viewModelDetails);


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

}
