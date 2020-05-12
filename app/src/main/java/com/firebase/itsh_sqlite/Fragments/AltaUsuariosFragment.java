package com.firebase.itsh_sqlite.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.itsh_sqlite.R;
import com.firebase.itsh_sqlite.Recursos.Alumno;
import com.firebase.itsh_sqlite.Recursos.utilidades;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AltaUsuariosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AltaUsuariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AltaUsuariosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View vista;
    Activity Actividad;
    FloatingActionButton fabRegistro;
    RadioButton rM,rF;
    FloatingActionsMenu grupoBotones;
    EditText[] Txt = new EditText[6];
    EditText NumeroControl;
    TextView Mensaje;
    String genero="";
    Alumno Alumno=new Alumno();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AltaUsuariosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AltaUsuariosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AltaUsuariosFragment newInstance(String param1, String param2) {
        AltaUsuariosFragment fragment = new AltaUsuariosFragment();
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
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_alta_usuarios, container, false);
        Mensaje=vista.findViewById(R.id.IdIndicador);//Imprimiremos mensaje de Validos o no
        NumeroControl=vista.findViewById(R.id.txtControl);
        Txt[0] =vista.findViewById(R.id.txtNomb);
        Txt[1] =vista.findViewById(R.id.txtAp);
        Txt[2] =vista.findViewById(R.id.txtCarrera);
        Txt[3] =vista.findViewById(R.id.txtTel);
        Txt[4] =vista.findViewById(R.id.txtCorreo);
        Txt[5] =vista.findViewById(R.id.txtDireccion);
        rF=vista.findViewById(R.id.radioF);
        rM=vista.findViewById(R.id.radioM);
        fabRegistro=vista.findViewById(R.id.idFabConfirmar);

        grupoBotones=vista.findViewById(R.id.grupoFab);


        fabRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarUsuario();
                grupoBotones.collapse();
            }
        });
        return vista;
    }
public void CargarAlumno(){
    Alumno.setNumeroControl(NumeroControl.getText().toString());
    Alumno.setNombre(Txt[0].getText().toString());
    Alumno.setApellidos( Txt[1].getText().toString());
    Alumno.setCarrera(Txt[2].getText().toString());
    Alumno.setTelefono(Txt[3].getText().toString());
    Alumno.setCorreo(Txt[4].getText().toString());
    Alumno.setDireccion(Txt[5].getText().toString());
}
    private void RegistrarUsuario() {
        CargarAlumno();//Inicializamos el Objeto
    if(!ValidarCampos()){//mientras no se cumpla
        new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Algun Campo Incorrecto o Vacio Verifica!")
                .show();
    }else{
        String insertar="INSERT INTO "+ utilidades.Tabla_Usuario+" values('"+Alumno.getNumeroControl()+"','"+
                Alumno.getNombre()+"','"+Alumno.getApellidos()+"','"+
                Alumno.getGenero()+"','"+Alumno.getCarrera()+"','"+
                Alumno.getCorreo()+"','"+Alumno.getDireccion()+"','"+
                Alumno.getTelefono()+"');";
        if(utilidades.SQL(Actividad,insertar)){
            new SweetAlertDialog(Actividad)
                    .setTitleText("Guardado Exitosamente!")
                    .show();
        }else{
            new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Ocurrio un Error Dx!")
                    .show();
        }
    }
    }

    boolean ValidarCampos(){

        boolean bandera=false;
        if(rF.isChecked()==true){
            genero="Femenino";
        }else if(rM.isChecked()==true){
            genero="Masculino";
        }else{
            genero="No Seleccionado";
        }
        if(!genero.equals("No Seleccionado")&& VerificarCamposVacios()
                &&utilidades.ValidarCorreo(Alumno.getCorreo())&& utilidades.ValidaTelefono(""+Alumno.getTelefono())){
            Alumno.setGenero(genero);
            bandera=true;
        }else{
            bandera=false;
        }
        return bandera;
    }
    boolean VerificarCamposVacios() {
        boolean bandera = false;
        for (int i = 0; i < Txt.length; i++) {
            if (!Txt[i].getText().toString().trim().isEmpty()) {
                bandera = true;//si se valida correctamente los campos no estan vacios
            } else {
                Txt[i].requestFocus();
                bandera = false;//los campos estan vacios
                break;
            }
        }
        return bandera;
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
