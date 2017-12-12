package lahzouz.com.campusalerte.view.adapter;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Classe qui définit un CustomBindingAdapter qui permet de communiquer avec l'XML.
 */
public class CustomBindingAdapter {
    /**
     * Afficher ou cacher l'écran de chargement.
     * @param view
     * @param show
     */
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}