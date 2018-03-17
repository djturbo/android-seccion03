package fjarquellada.es.fruits2world.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import fjarquellada.es.fruits2world.R;
import fjarquellada.es.fruits2world.adapter.CustomRecyclerViewAdapter;
import fjarquellada.es.fruits2world.adapter.OnItemClickListener;
import fjarquellada.es.fruits2world.model.Fruit;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private static final String TAG = "MainActivity :: ";

    private List<Fruit>fruits;
    private CustomRecyclerViewAdapter fruitsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fruits        = this.getAllFruits();
        this.recyclerView  = findViewById(R.id.recyclerView);
        this.layoutManager = new LinearLayoutManager(this);
        this.fruitsAdapter = new CustomRecyclerViewAdapter(this.fruits, R.layout.cardview_fruit_layout, this, this);

        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.fruitsAdapter);

        registerForContextMenu(this.recyclerView);
    }

    private List<Fruit> getAllFruits() {
        return new ArrayList<Fruit>() {{
            add(new Fruit("Strawberry", "Strawberry description", R.drawable.strawberry_bg, R.mipmap.ic_strawberry, 0));
            add(new Fruit("Orange", "Orange description", R.drawable.orange_bg, R.mipmap.ic_orange, 0));
            add(new Fruit("Apple", "Apple description", R.drawable.apple_bg, R.mipmap.ic_apple, 0));
            add(new Fruit("Banana", "Banana description", R.drawable.banana_bg, R.mipmap.ic_banana, 0));
            add(new Fruit("Cherry", "Cherry description", R.drawable.cherry_bg, R.mipmap.ic_cherry, 0));
            add(new Fruit("Pear", "Pear description", R.drawable.pear_bg, R.mipmap.ic_pear, 0));
            add(new Fruit("Raspberry", "Raspberry description", R.drawable.raspberry_bg, R.mipmap.ic_raspberry, 0));
        }};
    }

    private void resetFruitQuantity(Fruit fruit){

    }
    private void addFruit(){
        int position = fruits.size();
        fruits.add(position, new Fruit("Plum " + (++counter), "Fruit added by the user", R.drawable.plum_bg, R.mipmap.ic_plum, 0));
        this.fruitsAdapter.notifyItemInserted(position);
        layoutManager.scrollToPosition(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean res = true;
        switch(item.getItemId()){
            case R.id.item_add_fruit:
                this.addFruit();
                break;
            default:
                res = super.onOptionsItemSelected(item);
        }
        return res;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "onContextItemSelected :: clicado item: ");
        boolean res = true;

        switch(item.getItemId()){
            case R.id.item_delete_fruit:
                fruits.remove(item.getOrder());
                this.fruitsAdapter.notifyItemRemoved(item.getOrder());
                break;
            case R.id.item_reset_fruit_quantity:
                fruits.get(item.getOrder()).resetQuantity();
                this.fruitsAdapter.notifyItemChanged(item.getOrder());
                break;
            default:
                res = super.onContextItemSelected(item);
        }
        return res;
    }


    @Override
    public void onItemClick(Fruit fruit, int position) {
        Log.d(TAG, "onItemClick :: position: "+position);
        fruit.addQuantity(1);
        this.fruitsAdapter.notifyItemChanged(position);
    }
}
