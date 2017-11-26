package lahzouz.com.campusalert.service.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lahzouz.com.campusalert.service.model.Alert;

/**
 * Created by sparow on 11/26/17.
 */

public class InitDatabase {

    private static Alert alt1 = new Alert("type1", "desc1", "adress1", -10.0, 10.0, new Date());
    private static Alert alt2 = new Alert("type2", "desc2", "adress2", -10.0, 10.0, new Date());
    private static Alert alt3 = new Alert("type3", "desc3", "adress3", -10.0, 10.0, new Date());
    private static Alert alt4 = new Alert("type4", "desc4", "adress4", -10.0, 10.0, new Date());
    private static Alert alt5 = new Alert("type5", "desc5", "adress5", -10.0, 10.0, new Date());
    private static Alert alt6 = new Alert("type6", "desc6", "adress6", -10.0, 10.0, new Date());
    private static Alert alt7 = new Alert("type7", "desc7", "adress7", -10.0, 10.0, new Date());
    private static Alert alt8 = new Alert("type8", "desc8", "adress8", -10.0, 10.0, new Date());
    private static Alert alt9 = new Alert("type9", "desc9", "adress9", -10.0, 10.0, new Date());
    private static Alert alt10 = new Alert("type10", "desc10", "adress10", -10.0, 10.0, new Date());
    private static Alert alt11 = new Alert("type11", "desc11", "adress11", -10.0, 10.0, new Date());
    private static Alert alt12 = new Alert("type12", "desc12", "adress12", -10.0, 10.0, new Date());
    private static Alert alt13 = new Alert("type13", "desc13", "adress13", -10.0, 10.0, new Date());
    private static Alert alt14 = new Alert("type14", "desc14", "adress14", -10.0, 10.0, new Date());
    private static Alert alt15 = new Alert("type15", "desc15", "adress15", -10.0, 10.0, new Date());
    private static List<Alert> alerts = new ArrayList<>();

    public static List<Alert> initAlertList() {
        alerts.add(alt1);
        alerts.add(alt2);
        alerts.add(alt3);
        alerts.add(alt4);
        alerts.add(alt5);
        alerts.add(alt6);
        alerts.add(alt7);
        alerts.add(alt8);
        alerts.add(alt9);
        alerts.add(alt10);
        alerts.add(alt11);
        alerts.add(alt12);
        alerts.add(alt13);
        alerts.add(alt14);
        alerts.add(alt15);
        return alerts;
    }

}
