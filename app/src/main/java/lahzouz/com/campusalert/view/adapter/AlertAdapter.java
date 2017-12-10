package lahzouz.com.campusalert.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import lahzouz.com.campusalert.R;
import lahzouz.com.campusalert.databinding.AlertListItemBinding;
import lahzouz.com.campusalert.service.model.Alert;
import lahzouz.com.campusalert.view.callback.AlertClickCallback;

/**
 * Classe qui permet d'adapter les données pour les transmettre à la recycler view.
 */
public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertViewHolder> {

    @Nullable
    private final AlertClickCallback alertClickCallback;
    private List<? extends Alert> alertList;

    /**
     * Constructeur.
     * @param alertClickCallback
     */
    public AlertAdapter(@Nullable AlertClickCallback alertClickCallback) {
        this.alertClickCallback = alertClickCallback;
    }

    /**
     * Ajouter une liste d'objet à la recycler view.
     * @param alertList
     */
    public void setAlertList(final List<? extends Alert> alertList) {
        if (this.alertList == null) {
            this.alertList = alertList;
            notifyItemRangeInserted(0, alertList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                /**
                 * Retourne la taille de l'ancienne liste.
                 * @return int
                 */
                @Override
                public int getOldListSize() {
                    return AlertAdapter.this.alertList.size();
                }

                /**
                 * Retour la taille de la nouvelle liste.
                 * @return int
                 */
                @Override
                public int getNewListSize() {
                    return alertList.size();
                }

                /**
                 * Vérifie si les items sont les égaux.
                 * @param oldItemPosition
                 * @param newItemPosition
                 * @return boolean
                 */
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return AlertAdapter.this.alertList.get(oldItemPosition).id ==
                            alertList.get(newItemPosition).id;
                }

                /**
                 * Vérifie si le contenu des deux alertes est le même.
                 * @param oldItemPosition
                 * @param newItemPosition
                 * @return
                 */
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Alert alert = alertList.get(newItemPosition);
                    Alert old = AlertAdapter.this.alertList.get(oldItemPosition);
                    return alert.id == old.id && Objects.equals(alert.getAddress(), old.getAddress());
                }
            });

            this.alertList = alertList;
            result.dispatchUpdatesTo(this);
        }
    }

    /**
     * Fragment onCreateViewHolder.
     * @param parent
     * @param viewType
     * @return AlertViewHolder
     */
    @Override
    public AlertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AlertListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.alert_list_item,
                        parent, false);

        binding.setCallback(alertClickCallback);

        return new AlertViewHolder(binding);
    }

    /**
     * Fragment onBindViewHolder.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(AlertViewHolder holder, int position) {
        holder.binding.setAlert(alertList.get(position));
        holder.binding.executePendingBindings();
    }

    /**
     * Calcule le nombre d'objets Alert dans la liste.
     * @return int
     */
    @Override
    public int getItemCount() {
        return alertList == null ? 0 : alertList.size();
    }

    /**
     * Classe qui définie AlertViewHolder et bind les données à la vue.
     */
    static class AlertViewHolder extends RecyclerView.ViewHolder {

        final AlertListItemBinding binding;

        /**
         * Constructeur.
         * @param binding
         */
        public AlertViewHolder(AlertListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
