package lahzouz.com.campusalert.view.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ExecutionException;

import lahzouz.com.campusalert.R;
import lahzouz.com.campusalert.databinding.FragmentAlertDetailsBinding;
import lahzouz.com.campusalert.service.model.Alert;
import lahzouz.com.campusalert.view.callback.AlertClickCallback;
import lahzouz.com.campusalert.viewmodel.AlertViewModel;

import static android.databinding.DataBindingUtil.inflate;

/**
 * Classe fragment alerte.
 */
public class AlertFragment extends Fragment implements LifecycleOwner{

    private static final String KEY_PROJECT_ID = "alert_id";
    private static long alertGlobalId;
    private static String className;
    String baseUrl ="https://www.google.com/maps/search/?api=1";
    private FragmentAlertDetailsBinding binding;
    private AlertViewModel viewModelDetails;
    private Snackbar snackbar;
    private Menu myMenu;
    /**
     * Classe AlertClickCallback, qui gère les clics.
     */
    private final AlertClickCallback alertClickCallback = new AlertClickCallback() {
        /**
         * Supprimer une alerte.
         * @param alert
         */
        @Override
        public void onDeleteClick(final Alert alert) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                if (alert != null) {

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Dialog);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    builder.setTitle("Supprimer alerte")
                            .setMessage("Êtes-vous sûr de vouloir supprimer l'alerte ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    myMenu.getItem(0).setVisible(false);
                                    viewModelDetails.deleteAlert(alert);
                                    snackbar = Snackbar.make(getView().findViewById(R.id.coordinator_details), R.string.alert_deleted, Snackbar.LENGTH_LONG).addCallback(new Snackbar.Callback() {
                                        /**
                                         * SnackBar onDismissed
                                         * @param snackbar
                                         * @param event
                                         */
                                        @Override
                                        public void onDismissed(Snackbar snackbar, int event) {
                                            //see Snackbar.Callback docs for event details
                                            if (event == 2) {
                                                assert (getActivity()) != null;
                                                ((MainActivity) getActivity()).removeCurrentFragment(className);
                                            }

                                        }

                                        /**
                                         * SnackBar onShown
                                         * @param snackbar
                                         */
                                        @Override
                                        public void onShown(Snackbar snackbar) {

                                        }
                                    });

                                    snackbar.setAction("Annuler", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            viewModelDetails.insertAlert(alert);
                                            myMenu.getItem(0).setVisible(true);
                                            Snackbar snackbar1 = Snackbar.make(getView().findViewById(R.id.coordinator_details), R.string.alert_restored, Snackbar.LENGTH_SHORT);
                                            snackbar1.show();

                                        }
                                    });
                                    snackbar.show();
                                }
                            })
                            /**
                             * Classe DialogInterface.
                             */
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                /**
                                 * DialogInterface onClick.
                                 * @param dialog
                                 * @param which
                                 */
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(R.drawable.ic_warning_white_48dp)
                            .show();
                }


            }
        }

        /**
         * Afficher le détail d'une alerte.
         * @param alert
         */
        @Override
        public void onClick(Alert alert) {
        }

        /**
         * Ouvrir le fragment d'ajout de nouvelle alerte.
         */
        @Override
        public void onAddClick() {
        }

        /**
         * Enregistrer une alerte.
         * @param alert
         */
        @Override
        public void onSaveClick(Alert alert) {
        }

    };


    /**
     * Crée un fragment avec l'id de l'alerte.
     * @param alertID
     * @return AlertFragment
     */
    public static AlertFragment forAlert(long alertID) {

        AlertFragment fragment = new AlertFragment();
        Bundle args = new Bundle();
        alertGlobalId = alertID;
        args.putLong(KEY_PROJECT_ID, alertID);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Fragment onCreateView.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = inflate(inflater, R.layout.fragment_alert_details, container, false);

        // Create and set the adapter for the RecyclerView.
        return binding.getRoot();
    }

    /**
     * Fragment onViewCreated.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        assert (getActivity()) != null;
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        className = this.getClass().getName();
    }

    /**
     * Fragment onActivityCreated.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert (getActivity()) != null;
        AlertViewModel.Factory factory = new AlertViewModel.Factory(
                getActivity().getApplication(), getArguments().getLong(KEY_PROJECT_ID));
        viewModelDetails = ViewModelProviders.of(this, factory)
                .get(AlertViewModel.class);
        try {
            binding.setAlert(viewModelDetails.getAlert(getArguments().getLong(KEY_PROJECT_ID)));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        binding.setCallback(alertClickCallback);
        binding.setIsLoading(true);

        observeViewModel(viewModelDetails);

    }

    /**
     * Observer LiveData de l'alerte.
     * @param viewModel
     */
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

    /**
     * Menu onViewCreated.
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        assert (getActivity()) != null;
        getActivity().getMenuInflater().inflate(R.menu.maps_menu, menu);
        myMenu = menu;
    }

    /**
     * Menu onOptionsItemSelected.
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                assert (getActivity()) != null;
                ((MainActivity)getActivity()).removeCurrentFragment(this.getClass().getName());
                return true;
            case R.id.action_maps:
                Alert currentAlert = null;
                try {
                    currentAlert = viewModelDetails.getAlert(alertGlobalId);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                assert currentAlert != null;
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(baseUrl+"&query="+currentAlert.getLatitude()+","+currentAlert.getLongitude()));
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
