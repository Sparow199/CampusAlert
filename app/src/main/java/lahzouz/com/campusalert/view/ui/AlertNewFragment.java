package lahzouz.com.campusalert.view.ui;

import android.Manifest;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import lahzouz.com.campusalert.R;
import lahzouz.com.campusalert.databinding.FragmentAlertNewBinding;
import lahzouz.com.campusalert.service.model.Alert;
import lahzouz.com.campusalert.view.callback.AlertClickCallback;
import lahzouz.com.campusalert.viewmodel.AlertViewModel;
import pub.devrel.easypermissions.EasyPermissions;

import static android.databinding.DataBindingUtil.inflate;

/**
 * Classe fragment ajouter nouvelle alerte.
 */
public class AlertNewFragment extends Fragment implements LifecycleOwner,EasyPermissions.PermissionCallbacks {

    private static final int FINE_LOCATION_PERMISSION = 0;
    private static String className;
    private FragmentAlertNewBinding binding;
    private EditText edit_address;
    private Spinner spinner_type;
    private AlertViewModel viewModelDetails;
    private Alert alertGlobal = new Alert();
    /**
     * Classe AlertClickCallback, qui gère les clics.
     */
    private final AlertClickCallback alertClickCallback = new AlertClickCallback() {
        /**
         * Enregistrer une alerte.
         * @param alert
         */
        @Override
        public void onSaveClick(Alert alert) {
            alert.setType(alertGlobal.getType());
            alert.setCreated_at(new Date());
            alert.setLongitude(alertGlobal.getLongitude());
            alert.setLatitude(alertGlobal.getLatitude());

            if (alert.getType() != null && !alert.getType().equals("")) {
                if (alert.getLatitude() != -2 && alert.getLongitude() != -2) {
                    if (alert.getAddress() != null && !alert.getAddress().equals("")) {
                        viewModelDetails.insertAlert(alert);
                        assert (getActivity()) != null;
                        ((MainActivity) getActivity()).removeCurrentFragment(className);
                    } else {
                        Toast.makeText(getActivity(), "Erreur, veuillez indiquer une addresse", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(checkLocationPermission(getContext())){
                        Toast.makeText(getActivity(), "Erreur, données de localisation introuvables, veuillez activer votre GPS s'ils vous plait", Toast.LENGTH_SHORT).show();
                    }

                }
            } else {
                Toast.makeText(getActivity(), "Erreur,veuillez indiquer un type", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * Supprimer une alerte.
         * @param alert
         */
        @Override
        public void onDeleteClick(Alert alert) {
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

    };



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
        binding = inflate(inflater, R.layout.fragment_alert_new, container, false);

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

        spinner_type = view.findViewById(R.id.alert_type_spinner);
        edit_address = view.findViewById(R.id.address);

        setHasOptionsMenu(true);
        assert (getActivity()) != null;
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        className = this.getClass().getName();
        checkLocationPermission(getContext());
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
        getActivity().getMenuInflater().inflate(R.menu.geolocation_menu, menu);
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
            case R.id.home:
                assert (getActivity()) != null;
                ((MainActivity)getActivity()).removeCurrentFragment(className);
                return true;
            case R.id.action_location:
                if(viewModelDetails.checkLocationEnabled(this.getContext())){
                    viewModelDetails.getCurrentLocation();
                    Toast.makeText(getActivity(), "Localisation actualisée avec succèe", Toast.LENGTH_SHORT).show();
                }else{
                    if(checkLocationPermission(getContext())) {
                        Toast.makeText(getActivity(), "Veuillez activer votre GPS s'ils vous plait", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                getActivity().getApplication(), 0);
        viewModelDetails = ViewModelProviders.of(this, factory).get(AlertViewModel.class);
        viewModelDetails.getCurrentLocation();
        binding.setAlert(new Alert());
        binding.setCallback(alertClickCallback);
        /**
         * Classe qui gère le spinner.
         */
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Spinner onItemSelected.
             * @param parent
             * @param view
             * @param pos
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                alertGlobal.setType((String) parent.getItemAtPosition(pos));
            }

            /**
             * Spinner onNothingSelected.
             * @param adapterView
             */
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

    /**
     * Vérifie si les permissions de localisation sont accordées, sinon demande les permissions.
     * @param context
     * @return boolean
     */
    private boolean checkLocationPermission(Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            EasyPermissions.requestPermissions(this, "L'application à besoin d'acceder à la permission", FINE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            return false;
        }
        return true;
    }

    /**
     * Vérifie les résultats de la demande de permissions actuelle.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * S'execute si les permission sont accordées suite à la demande actuelle.
     * @param requestCode
     * @param list
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        Toast.makeText(getActivity(), "Permission accordée", Toast.LENGTH_SHORT).show();
    }

    /**
     * S'execute si les permission ne sont accordées suite à la demande actuelle.
     * @param requestCode
     * @param list
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        Toast.makeText(getActivity(), "Permission refusée, impossible d'ajouter une alerte", Toast.LENGTH_SHORT).show();
    }


}
