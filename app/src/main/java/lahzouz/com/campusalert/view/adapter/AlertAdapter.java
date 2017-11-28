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


public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertViewHolder> {

    @Nullable
    private final AlertClickCallback alertClickCallback;
    private List<? extends Alert> alertList;

    public AlertAdapter(@Nullable AlertClickCallback alertClickCallback) {
        this.alertClickCallback = alertClickCallback;
    }

    public void setAlertList(final List<? extends Alert> alertList) {
        if (this.alertList == null) {
            this.alertList = alertList;
            notifyItemRangeInserted(0, alertList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return AlertAdapter.this.alertList.size();
                }

                @Override
                public int getNewListSize() {
                    return alertList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return AlertAdapter.this.alertList.get(oldItemPosition).id ==
                            alertList.get(newItemPosition).id;
                }

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

    @Override
    public AlertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AlertListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.alert_list_item,
                        parent, false);

        binding.setCallback(alertClickCallback);

        return new AlertViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AlertViewHolder holder, int position) {
        holder.binding.setAlert(alertList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return alertList == null ? 0 : alertList.size();
    }

    static class AlertViewHolder extends RecyclerView.ViewHolder {

        final AlertListItemBinding binding;

        public AlertViewHolder(AlertListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
