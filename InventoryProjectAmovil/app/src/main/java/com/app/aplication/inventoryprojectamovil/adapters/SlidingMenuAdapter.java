package com.app.aplication.inventoryprojectamovil.adapters;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aplication.inventoryprojectamovil.R;
import com.app.aplication.inventoryprojectamovil.models.SlidingMenuItem;

import java.util.ArrayList;

/**
 * Created by andres on 23/10/15.
 */
public class SlidingMenuAdapter extends ArrayAdapter<SlidingMenuItem>{

    private FragmentActivity activity;
    private ArrayList<SlidingMenuItem> items;

    public SlidingMenuAdapter(FragmentActivity activity, int resource, ArrayList<SlidingMenuItem> objects){
        super(activity, resource, objects);
        this.activity = activity;
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_menu, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemName.setText(getItem(position).getMenuItemName());
        holder.itemImage.setImageResource(getItem(position).getImageResource());

        return convertView;
    }

    private class ViewHolder {
        private TextView itemName;
        private ImageView itemImage;

        public ViewHolder(View v) {
            itemName = (TextView) v.findViewById(R.id.name);
            itemImage = (ImageView) v.findViewById(R.id.image);
        }
    }

}
