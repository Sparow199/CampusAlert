package lahzouz.com.campusalert.service.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lahzouz.com.campusalert.service.model.Alert;


public class InitDatabase {

    private static Alert alt1 = new Alert("type1", "desc1", "address1", -10.0, 10.0, new Date());
    private static Alert alt2 = new Alert("type2", "desc2", "address2", -10.0, 10.0, new Date());
    private static Alert alt3 = new Alert("type3", "desc3", "address3", -10.0, 10.0, new Date());
    private static Alert alt4 = new Alert("type4", "desc4", "address4", -10.0, 10.0, new Date());
    private static Alert alt5 = new Alert("type5", "desc5", "address5", -10.0, 10.0, new Date());
    private static Alert alt6 = new Alert("type6", "desc6", "address6", -10.0, 10.0, new Date());
    private static Alert alt7 = new Alert("type7", "desc7", "address7", -10.0, 10.0, new Date());
    private static Alert alt8 = new Alert("type8", "desc8", "address8", -10.0, 10.0, new Date());
    private static Alert alt9 = new Alert("type9", "desc9", "address9", -10.0, 10.0, new Date());
    private static Alert alt10 = new Alert("type10", "desc10", "address10", -10.0, 10.0, new Date());
    private static Alert alt11 = new Alert("type11", "desc11", "address11", -10.0, 10.0, new Date());
    private static Alert alt12 = new Alert("type12", "desc12", "address12", -10.0, 10.0, new Date());
    private static Alert alt13 = new Alert("type13", "desc13", "address13", -10.0, 10.0, new Date());
    private static Alert alt14 = new Alert("type14", "desc14", "address14", -10.0, 10.0, new Date());
    private static Alert alt15 = new Alert("type15", "desc15", "address15", -10.0, 10.0, new Date());
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
