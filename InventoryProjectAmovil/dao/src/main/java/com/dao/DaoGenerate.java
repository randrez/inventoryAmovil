package com.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;;

public class DaoGenerate {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.app.aplication.inventoryprojectamovil.models");

        Entity inventory = schema.addEntity("Inventory");
        inventory.addIdProperty();
        inventory.addStringProperty("name");
        inventory.addStringProperty("registration_date");
        inventory.addIntProperty("units");

        Entity product = schema.addEntity("Product");
        product.addIdProperty();
        product.addStringProperty("code");
        product.addStringProperty("code_bar");
        product.addStringProperty("name");
        product.addIntProperty("units_product");

        Property idInventory = product.addLongProperty("idInventory").getProperty();
        ToMany inventoryProduct = inventory.addToMany(product,idInventory);
        inventoryProduct.setName("products");
        product.addToOne(inventory, idInventory);

        new DaoGenerator().generateAll(schema, "../app/src/main/java");

    }



}
