package steph.rs.controlesbasicos.data.preferencia;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionPreferences {

    //constantes
    private static final String PREF_NAME = "DATA_SESSION";
    private static final String PREF_PRODUCTO   = "PREF_PRODUCTO";

    private final SharedPreferences mPref;
    private  static SessionPreferences INSTANCE;

    public static SessionPreferences get(Context context){
        if (INSTANCE == null){
            INSTANCE = new SessionPreferences(context);
        }
        return INSTANCE;
    }

    public SessionPreferences(Context context) {
        this.mPref = context.getApplicationContext().getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    public void setProducto(int codigo){
        SharedPreferences.Editor objEdit = mPref.edit();
        objEdit.putInt(PREF_PRODUCTO, codigo + 1);
        objEdit.apply();
    }

    public int getProducto(){
        return mPref.getInt(PREF_PRODUCTO, 1);
    }

}
