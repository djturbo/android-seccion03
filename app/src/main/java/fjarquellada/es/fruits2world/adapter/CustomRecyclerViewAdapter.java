package fjarquellada.es.fruits2world.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fjarquellada.es.fruits2world.R;
import fjarquellada.es.fruits2world.model.Fruit;

/**
 * Created by francisco on 17/3/18.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private List<Fruit> fruits;
    private int layout;
    private Activity activity;
    private OnItemClickListener listener;

    public CustomRecyclerViewAdapter(List<Fruit> fruits, int layout, Activity activity, OnItemClickListener listener) {
        this.fruits = fruits;
        this.layout = layout;
        this.activity = activity;
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.activity).inflate(this.layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = this.fruits.get(position);
        holder.bind(fruit, position, this.listener);
    }

    @Override
    public int getItemCount() {
        return this.fruits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        @BindView(R.id.textViewName)
        public TextView textViewName;
        @BindView(R.id.textViewDescription)
        public TextView textViewDescription;
        @BindView(R.id.textViewQuantity)
        public TextView textViewQuantity;
        @BindView(R.id.imageViewBackground)
        public ImageView imageViewBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            // Añadimos al view el listener para el context menu, en vez de hacerlo en
            // el activity mediante el método registerForContextMenu
            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(final Fruit fruit, final int position,  final OnItemClickListener listener) {

            this.textViewName.setText(fruit.getName());
            this.textViewDescription.setText(fruit.getDescription());
            this.textViewQuantity.setText(fruit.getQuantity() + "");
            // Lógica aplicada para la limitación de la cantidad en cada elemento fruta
            if (fruit.getQuantity() == Fruit.LIMIT_QUANTITY) {
                textViewQuantity.setTextColor(ContextCompat.getColor(activity, R.color.colorAlert));
                textViewQuantity.setTypeface(null, Typeface.BOLD);
            } else {
                textViewQuantity.setTextColor(ContextCompat.getColor(activity, R.color.defaultTextColor));
                textViewQuantity.setTypeface(null, Typeface.NORMAL);
            }
            // Cargamos la imagen con Picasso
            Picasso
                    .get()
                    .load(fruit.getImgBackground())
                    .fit()
                    .into(this.imageViewBackground);
            // Añadimos el listener click para cada elemento fruta
            this.imageViewBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(fruit, getPosition());
                }
            });
        }

        // Sobreescribimos onCreateContextMenu, dentro del ViewHolder,
        // en vez de hacerlo en el activity
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            // Recogemos la posición con el método getAdapterPosition
            Fruit fruitSelected = fruits.get(getPosition());
            // Establecemos título e icono para cada elemento, mirando en sus propiedades
            contextMenu.setHeaderTitle(fruitSelected.getName());
            contextMenu.setHeaderIcon(fruitSelected.getImgIcon());
            // Inflamos el menú
            // MenuInflater inflater = activity.getMenuInflater();
            // inflater.inflate(R.menu.context_menu_layout, contextMenu);


            contextMenu.add(0, R.id.item_delete_fruit, getPosition(), "delete");
            contextMenu.add(0, R.id.item_reset_fruit_quantity, getPosition(), "reset Quantity");

        }

    }

}
