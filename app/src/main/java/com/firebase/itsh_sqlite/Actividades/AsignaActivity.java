package com.firebase.itsh_sqlite.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;

import com.firebase.itsh_sqlite.Fragments.AltaUsuariosFragment;
import com.firebase.itsh_sqlite.Fragments.Consultar_UsuariosFragment;
import com.firebase.itsh_sqlite.R;

public class AsignaActivity extends AppCompatActivity implements
        AltaUsuariosFragment.OnFragmentInteractionListener,
        Consultar_UsuariosFragment.OnFragmentInteractionListener {
    Fragment MiFragment;

    int retornar(){
        Bundle Obj =this.getIntent().getExtras();
        return (Obj!=null)?Obj.getInt("Indice"):0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asigna);
        switch (retornar()){
            case 0:
                MiFragment=new AltaUsuariosFragment();
                break;
            case 1:
                MiFragment=new Consultar_UsuariosFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.Visualizar,MiFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
