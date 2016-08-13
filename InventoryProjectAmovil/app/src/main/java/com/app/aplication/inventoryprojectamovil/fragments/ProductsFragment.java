package com.app.aplication.inventoryprojectamovil.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.aplication.inventoryprojectamovil.R;
import com.app.aplication.inventoryprojectamovil.adapters.ProductAdapter;
import com.app.aplication.inventoryprojectamovil.aplication.DaoApp;
import com.app.aplication.inventoryprojectamovil.models.Product;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {

    private static final String TAG = "Product fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_product);
        long idInventory=getArguments().getLong("idInventory");
        Log.e(TAG, " id inventory = "+idInventory);

        List<Product> productList = DaoApp.getProductDao()._queryInventory_Products(idInventory);

        Log.e(TAG, productList.toString());
        ProductAdapter productAdapter = new ProductAdapter(getActivity(), productList);
        listView.setAdapter(productAdapter);
        return view;
    }

}
