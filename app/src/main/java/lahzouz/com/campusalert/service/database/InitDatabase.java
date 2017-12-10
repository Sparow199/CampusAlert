package lahzouz.com.campusalert.service.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lahzouz.com.campusalert.service.model.Alert;

/**
 * Classe qui permet créé les objets nécessaires au remplissage de la base de données.
 */
public class InitDatabase {

    private static Alert alt1 = new Alert("Arbre à tailler", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 1", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt2 = new Alert("Arbre à abattre", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 2", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt3 = new Alert("Arbre à tailler", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 3", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt4 = new Alert("Arbre à abattre", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 4", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt5 = new Alert("Arbre à tailler", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 5", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt6 = new Alert("Arbre à abattre", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 6", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt7 = new Alert("Détritus", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 7", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt8 = new Alert("Détritus", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 8", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt9 = new Alert("Haie à tailler", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 9", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt10 = new Alert("Haie à tailler", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 10", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt11 = new Alert("Mauvaise herbe", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 11", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt12 = new Alert("Mauvaise herbe", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 12", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt13 = new Alert("Mauvaise herbe", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 13", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt14 = new Alert("Autre", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 14", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static Alert alt15 = new Alert("Autre", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. 15", "Cité Scientifique, 59650 Villeneuve-d'Ascq", 50.6091409, 3.1365931, new Date());
    private static List<Alert> alerts = new ArrayList<>();

    /**
     * Initialise la liste des alertes.
     * @return List<Alert>
     */
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
