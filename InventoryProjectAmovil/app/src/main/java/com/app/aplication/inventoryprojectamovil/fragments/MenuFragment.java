package com.app.aplication.inventoryprojectamovil.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.app.aplication.inventoryprojectamovil.R;
import com.app.aplication.inventoryprojectamovil.SlidingActivity;
import com.app.aplication.inventoryprojectamovil.adapters.SlidingMenuAdapter;
import com.app.aplication.inventoryprojectamovil.models.SlidingMenuItem;

import java.util.ArrayList;

/**
 * Created by andres on 23/10/15.
 */
public class MenuFragment extends Fragment {

    private View rootView;
    private ListView listView;
    private ArrayList<SlidingMenuItem> listMenuItems;
    private final static String TAG = "MenuFragment";

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listMenuItems = new ArrayList<SlidingMenuItem>();
        listMenuItems.add(new SlidingMenuItem(R.drawable.code_bar, getString(R.string.register)));
        listMenuItems.add(new SlidingMenuItem(R.drawable.inventory, getString(R.string.list_register)));
        listMenuItems.add(new SlidingMenuItem(R.drawable.update, getString(R.string.update)));
        listMenuItems.add(new SlidingMenuItem(R.drawable.configuration, "Configuracion"));


        ((SlidingActivity)getActivity()).setSlidingActionBarEnabled(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.menu_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);
        View header = inflater.inflate(R.layout.header, listView, false);
        listView.addHeaderView(header, null, false);

        View footer = inflater.inflate(R.layout.footer, listView, false);
        listView.addFooterView(footer, null, false);

        Button close = (Button) footer.findViewById(R.id.exit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListViewAdapter();
    }

    private void setListViewAdapter() {
        SlidingMenuAdapter adapter = new SlidingMenuAdapter(getActivity(), R.layout.row_menu, listMenuItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener());
        Log.e(TAG, "create adapter " + listMenuItems.size());
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SlidingMenuItem item = (SlidingMenuItem) parent.getItemAtPosition(position);
                ((SlidingActivity) getActivity()).transactionFragments(PageFragment.newInstance(item.getMenuItemName()),
                        R.id.container);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        SlidingMenuItem slide = new SlidingMenuItem();
        slide = (SlidingMenuItem) listView.getAdapter().getItem(1);
        ((SlidingActivity) getActivity()).transactionFragments(PageFragment.newInstance(slide.getMenuItemName()),
                R.id.container);
    }
}
