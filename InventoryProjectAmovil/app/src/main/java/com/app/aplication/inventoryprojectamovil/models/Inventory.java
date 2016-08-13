package com.app.aplication.inventoryprojectamovil.models;

import java.util.List;
import com.app.aplication.inventoryprojectamovil.models.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "INVENTORY".
 */
public class Inventory {

    private Long id;
    private String name;
    private String registration_date;
    private Integer units;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient InventoryDao myDao;

    private List<Product> products;

    public Inventory() {
    }

    public Inventory(Long id) {
        this.id = id;
    }

    public Inventory(Long id, String name, String registration_date, Integer units) {
        this.id = id;
        this.name = name;
        this.registration_date = registration_date;
        this.units = units;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInventoryDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Product> getProducts() {
        if (products == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProductDao targetDao = daoSession.getProductDao();
            List<Product> productsNew = targetDao._queryInventory_Products(id);
            synchronized (this) {
                if(products == null) {
                    products = productsNew;
                }
            }
        }
        return products;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetProducts() {
        products = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "units=" + units +
                ", registration_date='" + registration_date + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
