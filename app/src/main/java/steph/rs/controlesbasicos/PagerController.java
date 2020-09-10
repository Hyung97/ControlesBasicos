package steph.rs.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerController extends FragmentPagerAdapter {

    int numoftabs;

    public PagerController(@NonNull FragmentManager fm, int behavior)
    {
        super(fm, behavior);
        this.numoftabs = behavior;
    }




    @NonNull
    @Override
    public Fragment getItem(int position) {
        // aquí se determina cual fragment cargar
        switch (position){
            case 0:
                return new Contactos();
            case 1:
                return new Correos();
            case 2:
                return new Mensajes();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        // Este método retorna el número de tabs
        return numoftabs;
    }
}
