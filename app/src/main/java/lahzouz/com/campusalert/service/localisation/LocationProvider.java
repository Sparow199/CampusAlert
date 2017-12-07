package lahzouz.com.campusalert.service.localisation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import static android.content.Context.LOCATION_SERVICE;


public class LocationProvider {

    public boolean canGetLocation(Context context) {
        boolean result = true;
        LocationManager lm = null ;
        boolean gps_enabled = false;
        boolean network_enabled = false;

        if (lm == null){
            lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        }

        // exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if (gps_enabled == false || network_enabled == false) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }




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

    private boolean checkFineLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkCoarseLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}