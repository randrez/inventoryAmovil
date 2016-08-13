package com.app.aplication.inventoryprojectamovil.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.aplication.inventoryprojectamovil.R;
import com.app.aplication.inventoryprojectamovil.aplication.DaoApp;
import com.app.aplication.inventoryprojectamovil.models.Inventory;
import com.app.aplication.inventoryprojectamovil.models.Product;

import java.util.List;

/**
 * Created by andres on 26/10/15.
 */
public class InventoriesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Inventory> inventories;

    public InventoriesAdapter(Context context, List<Inventory> inventories){
        this.context = context;
        this.inventories = inventories;
        inflater = LayoutInflater.from(this.context);
    }


    @Override
    public int getCount() {
        return inventories.size();
    }

    @Override
    public Object getItem(int position) {
        return inventories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InvViewHolder invViewHolder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.inventory_layout, parent, false);
            invViewHolder = new InvViewHolder(convertView);
            convertView.setTag(invViewHolder);
        }else{
            invViewHolder = (InvViewHolder) convertView.getTag();
        }

        Inventory inventory = (Inventory)getItem(position);
        List<Product> products = DaoApp.getProductDao()._queryInventory_Products(inventory.getId());
        inventory.setUnits(products.size());
        DaoApp.getInventoryDao().update(inventory);
        String unit = String.valueOf(products.size());
        Log.e("Adapter", unit);
        invViewHolder.name_inventory.setText(inventory.getName());
        invViewHolder.date_inventory.setText(inventory.getRegistration_date());
        invViewHolder.units_inventory.setText(unit);

        return convertView;
    }

    public class InvViewHolder{

        private TextView name_inventory;
        private TextView date_inventory;
        private TextView units_inventory;

        public InvViewHolder(View item){
            name_inventory = (TextView) item.findViewById(R.id.name_inventory);
            date_inventory = (TextView) item.findViewById(R.id.date_inventory);
            units_inventory = (TextView) item.findViewById(R.id.units_inventory);
        }
    }
}
