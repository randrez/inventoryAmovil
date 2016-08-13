package com.app.aplication.inventoryprojectamovil.fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.aplication.inventoryprojectamovil.R;
import com.app.aplication.inventoryprojectamovil.SlidingActivity;
import com.app.aplication.inventoryprojectamovil.adapters.InventoriesAdapter;
import com.app.aplication.inventoryprojectamovil.aplication.DaoApp;
import com.app.aplication.inventoryprojectamovil.models.Inventory;
import com.app.aplication.inventoryprojectamovil.models.Product;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by andres on 23/10/15.
 */
public class PageFragment extends Fragment {

    private static final String TAG = "PageFragment";
    private String fragmentName;
    private View root;
    private String content_code;
    private int sum;
    private int rest;
    private final Gson gson = new Gson();
    private RequestQueue queue;
    private EditText name_product;
    private String url;

    public static PageFragment newInstance(String fragmentName) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString("fragment_name", fragmentName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
        if (getArguments() != null) {
            fragmentName = getArguments().getString("fragment_name");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences settings = ((SlidingActivity)getActivity()).getSharedPreferences("myData", 0);
        content_code = settings.getString("content_code", "");

        if(fragmentName.equals(getString(R.string.register))){

            root = inflater.inflate(R.layout.register_page, container, false);
            initViewRegister(root);

        }else if(fragmentName.equals(getString(R.string.list_register))){

            root = inflater.inflate(R.layout.list_register_page, container, false);
            List<Inventory> inventoryList = DaoApp.getInventoryDao().loadAll();
            initViewList(inventoryList, root);

        }else if(fragmentName.equals(getString(R.string.update))){
            getActivity().finish();
            startActivity(getActivity().getIntent());
        }
        return  root;
    }

    private void initViewRegister(View register) {
        ImageView code_bar = (ImageView) register.findViewById(R.id.code_bar);
        final EditText code_bar_product = (EditText) register.findViewById(R.id.editText);
        final EditText code_product = (EditText) register.findViewById(R.id.editText2);
        name_product = (EditText) register.findViewById(R.id.name_product);
        Button save = (Button) register.findViewById(R.id.acept);
        Button cancel = (Button) register.findViewById(R.id.cancel);
        View number_picker = (View) register.findViewById(R.id.layout_number_picker);
        Button more = (Button) number_picker.findViewById(R.id.more);
        Button less = (Button) number_picker.findViewById(R.id.less);
        final EditText number = (EditText) number_picker.findViewById(R.id.number);

        name_product.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                RequestNameProduct(code_product.getText().toString());
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum = Integer.parseInt(number.getText().toString());
                sum = sum + 1;
                number.setText(String.valueOf(sum));
            }
        });

        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(number.getText().toString()) > 0){
                    rest = Integer.parseInt(number.getText().toString());
                    rest = rest - 1;
                    number.setText(String.valueOf(rest));
                }
            }
        });

        code_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntentIntegrator = new IntentIntegrator(((SlidingActivity) getActivity()));
                scanIntentIntegrator.initiateScan();
            }
        });

        code_bar_product.setText(content_code);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct(code_bar_product.getText().toString(), code_product.getText().toString(), name_product.getText().toString(), number.getText().toString());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm((ViewGroup) root.findViewById(R.id.register));
                ((SlidingActivity) getActivity()).toggle();
            }
        });

    }

    private void RequestNameProduct(final String code) {

        url = "http://www.json-generator.com/api/json/get/bUhvZFNYOa?indent=2";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Product product = gson.fromJson(response.toString(), Product.class);
                        if(product.getCode().equals(code)){
                            name_product.setText(product.getName());
                        }else{
                            name_product.setText("El codigo no existe");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Ah ocurrido un error", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

        queue.add(jsObjRequest);
    }

    private void initViewList(final List<Inventory> inventoryList, View list) {
        Log.e(TAG, inventoryList.toString());

        ListView listInventory = (ListView) list.findViewById(R.id.list_register);
        InventoriesAdapter inventoriesAdapter = new InventoriesAdapter(getActivity().getApplicationContext(),inventoryList);
        listInventory.setAdapter(inventoriesAdapter);

        listInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment fragment = new ProductsFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("idInventory", inventoryList.get(position).getId());
                fragment.setArguments(bundle);

                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });
    }

    private void saveProduct(String code_bar, String code_product, String name_product, String units) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formatteDate = df.format(c.getTime());

        List<Inventory> inventories = DaoApp.getInventoryDao().loadAll();

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_result);
        dialog.setTitle(getString(R.string.tittle_dialog));

        TextView name_product_dialog = (TextView) dialog.findViewById(R.id.name_product_dialog);
        TextView code_product_dialog = (TextView) dialog.findViewById(R.id.code_product_dialog);
        TextView units_product_dialog = (TextView) dialog.findViewById(R.id.units_dialog);

        if(name_product.isEmpty() || code_bar.isEmpty() || code_product.isEmpty()){
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Los valores no pueden ser vacios", Toast.LENGTH_LONG);
            toast.show();
        }else {
            if(inventories.size() > 0){
                clearForm((ViewGroup) root.findViewById(R.id.register));
                String date_compare = inventories.get(inventories.size()-1).getRegistration_date();
                if (date_compare.equals(formatteDate)){

                    Inventory inventory = DaoApp.getInventoryDao().load(inventories.get(inventories.size()-1).getId());
                    Product product = new Product();
                    product.setName(name_product);
                    product.setCode_bar(code_bar);
                    product.setCode(code_product);
                    product.setIdInventory(inventory.getId());
                    product.setUnits_product(Integer.parseInt(units));

                    DaoApp.getProductDao().insertInTx(product);
                    name_product_dialog.setText(name_product);
                    code_product_dialog.setText(code_product);
                    units_product_dialog.setText(units);
                    dialog.show();
                }else{
                    Inventory invent = new Inventory();
                    invent.setRegistration_date(formatteDate);
                    DaoApp.getInventoryDao().insertInTx(invent);

                    Product product = new Product();
                    product.setName(name_product);
                    product.setCode(code_product);
                    product.setCode_bar(code_bar);
                    product.setIdInventory(inventories.get(inventories.size() - 1).getId());
                    product.setUnits_product(Integer.parseInt(units));
                    DaoApp.getProductDao().insertInTx(product);

                    List<Inventory> inventories1 = DaoApp.getInventoryDao().loadAll();
                    Inventory inventory = DaoApp.getInventoryDao().load(inventories.get(inventories1.size()-1).getId());
                    List<Product> products = DaoApp.getProductDao()._queryInventory_Products(inventory.getId());
                    inventory.setName("Inventario " + inventory.getId());
                    inventory.setUnits(products.size());

                    DaoApp.getInventoryDao().update(inventory);
                    name_product_dialog.setText(name_product);
                    code_product_dialog.setText(code_product);
                    units_product_dialog.setText(units);
                    dialog.show();
                }
            }else{
                clearForm((ViewGroup) root.findViewById(R.id.register));
                Inventory invent = new Inventory();
                invent.setRegistration_date(formatteDate);
                DaoApp.getInventoryDao().insertInTx(invent);

                List<Inventory> inventoryList = DaoApp.getInventoryDao().loadAll();

                Product product = new Product();
                product.setName(name_product);
                product.setCode(code_product);
                product.setCode_bar(code_bar);
                product.setIdInventory(inventoryList.get(inventoryList.size() - 1).getId());
                product.setUnits_product(Integer.parseInt(units));
                DaoApp.getProductDao().insertInTx(product);

                Inventory inventory = DaoApp.getInventoryDao().load(inventoryList.get(inventoryList.size()-1).getId());
                List<Product> products = DaoApp.getProductDao()._queryInventory_Products(inventory.getId());
                inventory.setName("Inventario " + inventory.getId());
                inventory.setUnits(products.size());

                DaoApp.getInventoryDao().update(inventory);
                name_product_dialog.setText(name_product);
                code_product_dialog.setText(code_product);
                units_product_dialog.setText(units);
                dialog.show();
            }
        }
    }

    private void clearForm(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }

}


