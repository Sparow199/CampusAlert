package lahzouz.com.campusalerte.service.localisation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Classe qui gère les informatiosn liées à la position.
 */
public class LocationProvider {

    /**
     * Vérifie si la localisation est activée sur l'appareil.
     * @param context
     * @return boolean
     */
    public boolean canGetLocation(Context context) {
        boolean gps_enabled = false;
        boolean network_enabled = false;

        LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {

        }

        return !(!gps_enabled && !network_enabled);
    }

    /**
     * Permets de retourner la position actuelle de l'appareil en utilisant différentes sources.
     * @param context
     * @param locationListener
     */
    public void locationUpdate(Context context, LocationListener locationListener){

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);

        LocationManager locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(criteria,true);
        if (provider == null) {
            Log.e("locationUpdate", "Provider is null !!!");
            return;
        }
        else {
            Log.e("locationUpdate", provider);
        }
        if(checkCoarseLocationPermission(context) && checkFineLocationPermission(context)){
            locationManager.requestSingleUpdate(provider,locationListener,null);
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,locationListener,null);
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,locationListener,null);
        }

    }

    /**
     * Vérifie si la permission de localisation fine est accordée.
     * @param context
     * @return boolean
     */
    private boolean checkFineLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Vérifie si la permission de localisation coarse est accordée.
     * @param context
     * @return boolean
     */
    private boolean checkCoarseLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}