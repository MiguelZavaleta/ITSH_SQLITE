package com.firebase.itsh_sqlite.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.itsh_sqlite.Adaptadores.AdaptadorAlumno;
import com.firebase.itsh_sqlite.R;
import com.firebase.itsh_sqlite.Recursos.Alumno;
import com.firebase.itsh_sqlite.Recursos.ComunicaFragments;
import com.firebase.itsh_sqlite.Recursos.utilidades;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Consultar_UsuariosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Consultar_UsuariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Consultar_UsuariosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View vista;
    Activity Actividad;
    RecyclerView RecyclerLista;
    public static int    Indice=-1;
    Intent InicioActividad=null;
    Bundle PasarDatos = new Bundle();//pasar datos
    EditText Buscar;
    ComunicaFragments Comunica;
    FloatingActionButton fabRegistro,fabEliminar;
    FloatingActionsMenu grupoBotones;

    AdaptadorAlumno miAdaptadorUsuario;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Consultar_UsuariosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Consultar_UsuariosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Consultar_UsuariosFragment newInstance(String param1, String param2) {
        Consultar_UsuariosFragment fragment = new Consultar_UsuariosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_consultar__usuarios, container, false);
        RecyclerLista=vista.findViewById(R.id.recyclerUsuarios);
        Buscar=vista.findViewById(R.id.IdBuscar);
        fabRegistro=vista.findViewById(R.id.idFabActualizar);
        fabEliminar=vista.findViewById(R.id.idFabEliminar);
        grupoBotones=vista.findViewById(R.id.grupoFab);

        Buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                utilidades.ConsultarAlumnos(Actividad,"SELECT * FROM "+utilidades.Tabla_Usuario +" where "+utilidades.CAMPO_ID+" like '"+s+"%';");
            }

            @Override
            public void afterTextChanged(Editable s) {
                miAdaptadorUsuario.notifyDataSetChanged();
                LLenarAdaptadorAlumnos();
            }
        });

        // Inflate the layout for this fragment
        RecyclerLista.setLayoutManager(new LinearLayoutManager(this.Actividad));
        RecyclerLista.setHasFixedSize(true);

        LLenarAdaptadorAlumnos();

        fabRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( AdaptadorAlumno.UltimaSeleccion!=-1 && utilidades.AlumnoSeleccionado!=null){
                    Comunica.EnviarAlumno(utilidades.AlumnoSeleccionado=utilidades.ListaAlumnos.get(AdaptadorAlumno.UltimaSeleccion));
                }else{
                    new SweetAlertDialog(Actividad,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("No tienes nada Seleccionado")
                            .show();
                }
            }
        });
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( AdaptadorAlumno.UltimaSeleccion!=-1  && utilidades.AlumnoSeleccionado!=null){

                    new SweetAlertDialog(Actividad, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Estas seguro?")
                            .setContentText("Vas a Eliminar a [ "+utilidades.AlumnoSeleccionado.getNumeroControl()+" ]\nY todos sus datos!")
                            .setConfirmText("Si,Elimiar!")
                            .setConfirmButtonBackgroundColor(R.color.md_red_900)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    String Eliminar = "Delete from " + utilidades.Tabla_Usuario + " where " + utilidades.CAMPO_ID + "='" + utilidades.AlumnoSeleccionado.getNumeroControl() + "';";
                                    if (utilidades.SQL(Actividad, Eliminar)) {
                                        sDialog
                                                .setTitleText("Eliminando!")
                                                .setContentText("Registro Eliminado con Exito!")
                                                .setConfirmText("OK")
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        RecyclerLista.scrollToPosition(0);
                                        utilidades.ConsultarAlumnos(Actividad,"");
                                        miAdaptadorUsuario.notifyDataSetChanged();
                                        LLenarAdaptadorAlumnos();

                                    } else {
                                        new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("No se pudo!")
                                                .show();
                                    }

                                }
                            })
                            .show();
                }else{
                    new SweetAlertDialog(Actividad,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("No tienes nada Seleccionado")
                            .show();
                }
            }
        });


        return vista;
    }

void LLenarAdaptadorAlumnos(){
     miAdaptadorUsuario=new AdaptadorAlumno(utilidades.ListaAlumnos,Actividad);
    RecyclerLista.setAdapter(miAdaptadorUsuario);
}
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            Actividad=(Activity)context;
            Comunica=(ComunicaFragments) this.Actividad;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
