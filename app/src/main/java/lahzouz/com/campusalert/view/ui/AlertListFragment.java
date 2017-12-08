package lahzouz.com.campusalert.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lahzouz.com.campusalert.R;
import lahzouz.com.campusalert.databinding.FragmentAlertListBinding;
import lahzouz.com.campusalert.service.model.Alert;
import lahzouz.com.campusalert.view.adapter.AlertAdapter;
import lahzouz.com.campusalert.view.callback.AlertClickCallback;
import lahzouz.com.campusalert.viewmodel.AlertListViewModel;

import static android.databinding.DataBindingUtil.inflate;


public class AlertListFragment extends Fragment implements LifecycleOwner {

    public static final String TAG = "AlertListFragment";
    private final AlertClickCallback alertClickCallback = new AlertClickCallback() {
        @Override
        public void onClick(Alert alert) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                Fragment f = AlertFragment.forAlert(alert.getId());
                assert (getActivity()) != null;
                ((MainActivity) getActivity()).show(f);
            }
        }

        @Override
        public void onDeleteClick(Alert alert) {
        }

        public void onAddClick() {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                assert (getActivity()) != null;
                ((MainActivity) getActivity()).show(new AlertNewFragment());
            }
        }

        @Override
        public void onSaveClick(Alert alert) {
        }

    };
    private AlertAdapter alertAdapter;
    private FragmentAlertListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = inflate(inflater, R.layout.fragment_alert_list, container, false);
        alertAdapter = new AlertAdapter(alertClickCallback);
        binding.alertList.setAdapter(alertAdapter);
        binding.setCallback(alertClickCallback);
        binding.setIsLoading(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        assert (getActivity()) != null;
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AlertListViewModel viewModelList = ViewModelProviders.of(this).get(AlertListViewModel.class);
        observeViewModel(viewModelList);
        assert (getActivity()) != null;
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void observeViewModel(AlertListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getProjectListObservable().observe(this, new Observer<List<Alert>>() {
            @Override
            public void onChanged(@Nullable List<Alert> alerts) {
                if (alerts != null) {
                    binding.setIsLoading(false);
                    alertAdapter.setAlertList(alerts);
                }
            }
        });
    }

}