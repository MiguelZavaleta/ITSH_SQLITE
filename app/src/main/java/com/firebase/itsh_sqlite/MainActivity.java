package com.firebase.itsh_sqlite;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import com.firebase.itsh_sqlite.Fragments.AltaUsuariosFragment;
import com.firebase.itsh_sqlite.Fragments.Consultar_UsuariosFragment;
import com.firebase.itsh_sqlite.Fragments.IconoFragment;
import com.firebase.itsh_sqlite.Fragments.InfoFragment;
import com.firebase.itsh_sqlite.Fragments.ModificarUsuarioFragment;
import com.firebase.itsh_sqlite.Recursos.Alumno;
import com.firebase.itsh_sqlite.Recursos.ComunicaFragments;
import com.firebase.itsh_sqlite.Recursos.utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IconoFragment.OnFragmentInteractionListener,
        AltaUsuariosFragment.OnFragmentInteractionListener,
        Consultar_UsuariosFragment.OnFragmentInteractionListener ,
        ModificarUsuarioFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener,
        ComunicaFragments {
    Fragment fragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        utilidades.ConsultarAlumnos(this,"");
        switch (retornar()){
            case 0:
                fragment=new AltaUsuariosFragment();
                break;
            case 1:
                fragment=new Consultar_UsuariosFragment();
                break;
                default:
                    fragment=new IconoFragment();
                    break;
        }
        if(findViewById(R.id.idContentMain)!=null){
            if(savedInstanceState!=null){
                return;
            }
        }
        DestruirFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.idContentMain ,fragment).commit();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);//tinta para iconos
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    int retornar(){
        Bundle Obj =this.getIntent().getExtras();
        return (Obj!=null)?Obj.getInt("Indice"):-1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;*
        }/*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment miFragment=null;
        boolean ActivarFragment=false;
        if (id == R.id.nav_home) {
            // Handle the camera action
            miFragment=new IconoFragment();
            ActivarFragment=true;
        } else if (id == R.id.nav_Alta) {

            miFragment=new AltaUsuariosFragment();
            ActivarFragment=true;

        } else if (id == R.id.nav_Buscar) {
            miFragment=new Consultar_UsuariosFragment();
            ActivarFragment=true;

        } else if (id == R.id.nav_share) {

            miFragment=new InfoFragment();
            ActivarFragment=true;
        }
        if(ActivarFragment==true){
            getSupportFragmentManager().beginTransaction().replace(R.id.idContentMain,miFragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void DestruirFragment(){
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void EnviarAlumno(Alumno alumno) {
        Fragment detalleFragment=new ModificarUsuarioFragment();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putSerializable("objeto",alumno);
        detalleFragment.setArguments(bundleEnvio);
        getSupportFragmentManager().beginTransaction().add(R.id.idContentMain ,detalleFragment).addToBackStack(null).commit();

    }
}
