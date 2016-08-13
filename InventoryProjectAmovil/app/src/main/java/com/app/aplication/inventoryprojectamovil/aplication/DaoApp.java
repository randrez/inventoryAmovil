package com.app.aplication.inventoryprojectamovil.aplication;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.app.aplication.inventoryprojectamovil.models.DaoMaster;
import com.app.aplication.inventoryprojectamovil.models.DaoSession;
import com.app.aplication.inventoryprojectamovil.models.InventoryDao;
import com.app.aplication.inventoryprojectamovil.models.ProductDao;

/**
 * Created by andres on 25/10/15.
 */
public class DaoApp extends Application {

    static InventoryDao inventoryDao;
    static ProductDao productDao;

    public static InventoryDao getInventoryDao() {
        return inventoryDao;
    }

    public static ProductDao getProductDao() {
        return productDao;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "inventory_db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        inventoryDao = daoSession.getInventoryDao();
        productDao = daoSession.getProductDao();
    }
}
