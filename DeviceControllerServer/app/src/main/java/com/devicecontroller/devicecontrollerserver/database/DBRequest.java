package com.devicecontroller.devicecontrollerserver.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.devicecontroller.devicecontrollerserver.R;
import com.devicecontroller.devicecontrollerserver.connection.AppProtocol;
import com.devicecontroller.devicecontrollerserver.database.exception.DBExceptionDelete;
import com.devicecontroller.devicecontrollerserver.database.exception.DBExceptionInsert;
import com.devicecontroller.devicecontrollerserver.database.exception.DBExceptionNullValues;
import com.devicecontroller.devicecontrollerserver.database.exception.DBExceptionUpdate;

import static com.devicecontroller.devicecontrollerserver.database.DBConstants.*;

public class DBRequest {

    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    private Context context;

    // The database creator and updater helper
    public DBRequest(Context context) {
        this.context = context;
        // Create or retrieve the database
        dbOpenHelper = new DBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        // open the database
        openDB();
    }

    public void stateOnResume() {
        openDB();
    }

    public void stateOnPause() {
        closeDB();
    }

    /**
     * * Open the database* *
     *
     * @throws SQLiteException
     */
    public void openDB() throws SQLiteException {
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
        Log.e("DBSTATE ", db.isDatabaseIntegrityOk() + "");
    }

    /**
     * Close Database
     */
    public void closeDB() {
        db.close();
    }

    /**
     * @param tableName
     * @param contentValues containing values (ex : contentValues.put(TAG_KEY_COL_TAG, 123321);
     *                      contentValues.put(TAG_KEY_COL_NAME, "pseudo");
     *                      contentValues.put(TAG_KEY_COL_STATUS, 0);)
     * @throws DBExceptionInsert
     * @throws DBExceptionNullValues
     */
    public void insert(String tableName, ContentValues contentValues) throws DBExceptionInsert, DBExceptionNullValues {
        // Verif if values are null
        if (contentValues == null)
            throw new DBExceptionNullValues();

        // Insert the line in the database and Test to see if the insertion was ok
        if (db.insert(tableName, null, contentValues) == -1)
            throw new DBExceptionInsert();
    }

    /**
     * @param tableName     la table concernant la ligne a update
     * @param contentValues containing values (ex : contentValues.put(TAG_KEY_COL_TAG, 123321);
     *                      contentValues.put(TAG_KEY_COL_NAME, "pseudo");
     *                      contentValues.put(TAG_KEY_COL_STATUS, 0);)
     * @param tag_id        le tag concernant la ligne a update
     * @throws DBExceptionUpdate
     * @throws DBExceptionNullValues
     */
    public void update(String tableName, ContentValues contentValues, long tag_id) throws DBExceptionUpdate, DBExceptionNullValues {
        // Verif if values are null
        if (contentValues == null)
            throw new DBExceptionNullValues();

        // update the database and test to see if the insertion was ok
        if (db.update(tableName, contentValues, KEY_COL_TAG + "=" + tag_id, null) == -1)
            throw new DBExceptionUpdate();
    }

    /**
     * @param tableName la table concernant la ligne a delete
     * @param tag_id    le tag concernant la ligne a delete
     * @throws DBExceptionDelete
     */
    public void delete(String tableName, long tag_id) throws DBExceptionDelete {
        if (db.delete(tableName, KEY_COL_TAG + "=" + tag_id, null) == -1)
            throw new DBExceptionDelete();
    }


    /**
     * Query a the database
     *
     * @param tableName   Nom de la table utiliser une constante. (exemple : TAG_TABLE)
     * @param columnNames Nom des ou de la colonne que l'onsouhaite recuperer ou null si on les veux toutes. (exemple : new String{KEY_COL_TAG, KEY_COL_NAME})
     * @param where       Condition en ce qui concerne la selection des donnees, null si pas de condition. (exemple : KEY_COL_NAME + "=Ossama")
     * @return Un Cursor contenant les lignes selectionner depuis la table et les parametres fourni.
     */
    public Cursor select(String tableName, String[] columnNames, String where) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName);
        if (where != null)
            qb.appendWhere(where);

        // qb.setDistinct(true);
        return qb.query(db, columnNames, null, null, null, null, null);
    }

    /**
     * Query a the database
     *
     * @param tableName       Nom de la table utiliser une constante. (exemple : TAG_TABLE)
     * @param columnNames     Nom des ou de la colonne que l'onsouhaite recuperer ou null si on les veux toutes. (exemple : new String{KEY_COL_TAG, KEY_COL_NAME})
     * @param conditionColumn Nom de la colonne sur laquelle on souhaite fixer une condition, null si pas de condition. (exemple : KEY_COL_TAG=?)
     * @param conditionValue  Valeur de la colonne sur laquelle la condition est fixer, null si pas de condition. (exemple : 1547854)
     * @param groupBy         (The groupBy clause) Nom de la colonne par laquelle on souhaite regrouper les donnees, null si pas de regroupement. (exemple : KEY_COL_NAME)
     * @param orderBy         (The order by clause (column name followed by Asc or DESC)) Nom de la colonne sur laquelle on souhaite trier les donnees, null si pas de trie. (exemple : KEY_COL_TAG + "  ASC") ASC pour croissant et DESC pour decroissant
     * @param limit           (Maximal size of the results list) Nombre limite de lignes a selectionner, null si pas de limite
     * @return Un Cursor contenant les lignes selectionner depuis la table et les parametres fourni.
     */
    public Cursor select(String tableName, String[] columnNames, String conditionColumn, String conditionValue[], String groupBy, String orderBy, String limit) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName);
        // qb.appendWhere(Constants.KEY_COL_HAIR_COLOR+ "=blond");
        //qb.setDistinct(true);
        return qb.query(db, columnNames, conditionColumn, conditionValue, groupBy, null, orderBy);
    }

    /**
     * @param cursor
     * @return
     * @throws DBExceptionNullValues
     */
    public String getResults(Cursor cursor) throws DBExceptionNullValues {
        StringBuilder sb = new StringBuilder();

        if (cursor.moveToFirst()) {
            int nbColumn = cursor.getColumnCount();
            do {
                for (int i = 0; i < nbColumn; i++) {
                    sb.append(cursor.getString(i));
                    if (i != (nbColumn - 1))
                        sb.append(AppProtocol.DATA_SEPARATOR);
                }
                sb.append(System.lineSeparator());
            } while (cursor.moveToNext());
        } else {
            throw new DBExceptionNullValues();
        }
        return sb.substring(0, sb.length() - System.lineSeparator().length());
    }
}
