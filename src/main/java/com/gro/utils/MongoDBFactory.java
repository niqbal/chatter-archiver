package com.gro.utils;

import com.mongodb.DB;
import com.mongodb.Mongo;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by IntelliJ IDEA.
 * User: niqbal
 * Date: 7/12/11
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class MongoDBFactory implements FactoryBean<DB>
{
    private Mongo mongo;
    private String dbName;
    private String user;
    private String password;

    @Override
    public DB getObject() throws Exception {
        return this.getDB();
    }

   @Override
   public Class<?> getObjectType() {
       return DB.class;
   }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Mongo getMongo() {
        return mongo;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DB getDB() {
        DB db = mongo.getDB(dbName);

        if(!"".equals(user) && !"".equals(password)) {
            db.authenticate(user, password.toCharArray());
        }

        return db;
    }

    public DB getDB(String myDB) {
        DB db = mongo.getDB(myDB);
        if(!"".equals(user) && !"".equals(password)) {
            db.authenticate(user, password.toCharArray());
        }

        return db;
    }
}
