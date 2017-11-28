package lahzouz.com.campusalert.view.callback;

import lahzouz.com.campusalert.service.model.Alert;


public interface AlertClickCallback {
    void onClick(Alert alert);
    void onDeleteClick(Alert alert);
    void onSaveClick(Alert alert);
    void onAddClick();

}
