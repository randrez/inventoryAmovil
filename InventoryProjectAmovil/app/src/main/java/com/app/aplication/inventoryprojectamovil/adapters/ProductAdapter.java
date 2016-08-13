package com.app.aplication.inventoryprojectamovil.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.aplication.inventoryprojectamovil.R;
import com.app.aplication.inventoryprojectamovil.models.Product;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by andres on 26/10/15.
 */
public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;
    private LayoutInflater inflater;

    public ProductAdapter(Context context, List<Product> products){
        this.context = context;
        this.products = products;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductViewHolder productViewHolder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.product_layout, parent, false);
            productViewHolder = new ProductViewHolder(convertView);
            convertView.setTag(productViewHolder);
        }else{
            productViewHolder = (ProductViewHolder) convertView.getTag();
        }

        Product product = (Product) getItem(position);
        productViewHolder.name_product.setText(product.getName());
        productViewHolder.code_product.setText("Cod. "+product.getCode());
        productViewHolder.units_product.setText(product.getUnits_product().toString());

        return convertView;
    }

    public class ProductViewHolder{

        private TextView name_product;
        private TextView code_product;
        private TextView units_product;

        public ProductViewHolder(View item){
            name_product = (TextView) item.findViewById(R.id.name_product);
            code_product = (TextView) item.findViewById(R.id.code_product);
            units_product = (TextView) item.findViewById(R.id.units_product);
        }
    }
}
