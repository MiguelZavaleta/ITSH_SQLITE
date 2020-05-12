package com.firebase.itsh_sqlite.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.firebase.itsh_sqlite.Adaptadores.AdaptadorAlumno;
import com.firebase.itsh_sqlite.MainActivity;
import com.firebase.itsh_sqlite.R;
import com.firebase.itsh_sqlite.Recursos.Alumno;
import com.firebase.itsh_sqlite.Recursos.utilidades;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModificarUsuarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModificarUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarUsuarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ModificarUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarUsuarioFragment.
     */

    View vista;
    Activity Actividad;
    FloatingActionsMenu grupoBotones;
    FloatingActionButton fabRegistro, faEliminar, faModificar;
    RadioButton rM, rF;
    ImageView Img;
    EditText[] Txt = new EditText[7];
   static Bundle ObjetoAlumno = null;//variable que Capturara Datos del Activiti
    Alumno AlumnoCapturado = null;
    Alumno AlumnoModificado = null;
    String genero = "";

    // TODO: Rename and change types and number of parameters
    public static ModificarUsuarioFragment newInstance(String param1, String param2) {
        ModificarUsuarioFragment fragment = new ModificarUsuarioFragment();
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
        vista = inflater.inflate(R.layout.fragment_modificar_usuario, container, false);
        // Inflate the layout for this fragment
        Img = vista.findViewById(R.id.IdImgAvatar);
        Txt[0] = vista.findViewById(R.id.txtControl);
        Txt[1] = vista.findViewById(R.id.txtNomb);
        Txt[2] = vista.findViewById(R.id.txtAp);
        Txt[3] = vista.findViewById(R.id.txtCarrera);
        Txt[4] = vista.findViewById(R.id.txtTel);
        Txt[5] = vista.findViewById(R.id.txtCorreo);
        Txt[6] = vista.findViewById(R.id.txtDireccion);

        rF = vista.findViewById(R.id.radioF);
        rM = vista.findViewById(R.id.radioM);

        grupoBotones = vista.findViewById(R.id.grupoFab);
        faEliminar = vista.findViewById(R.id.idFabEliminar);
        faModificar = vista.findViewById(R.id.idFabActualizar);
        //Recibimos los Parametros de Nuestra Consulta de datos Anterior
        ObjetoAlumno = getArguments();
        if (ObjetoAlumno != null) {
           AsignarDetalles(AlumnoCapturado);
            AsignarImagen();
            CargarDatos();
        }
        EventoBotonesFlotantes();

        return vista;
    }
public static void AsignarDetalles(Alumno al){
    al = (Alumno) ObjetoAlumno.getSerializable("objeto");
}
    public void EventoBotonesFlotantes() {
        faModificar.setOnClickListener(new View.OnClickListener() {//Boton para modificar
            @Override
            public void onClick(View v) {

                if (ValidarCampos()) {
                    CargarDatosModificados();

                    if (VerificarNumeroControl()) {
                        ActualizarDatosBD();
                    } else {
                        /*Si el numero de control lo Modificamos Primero modificamos este y
                        luego Todos los datos*/
                        ActualizarNumeroControl();
                    }

                }
            }
        });
        faEliminar.setOnClickListener(new View.OnClickListener() {//Eliminar Boton
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(Actividad, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Estas seguro?")
                        .setContentText("Vas a Eliminar a [ "+utilidades.AlumnoSeleccionado.getNumeroControl()+" ]\nY todos sus datos!")
                        .setConfirmText("Si,Elimiar!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                String Eliminar = "Delete from " + utilidades.Tabla_Usuario + " where " + utilidades.CAMPO_ID + "='" + AlumnoCapturado.getNumeroControl() + "';";
                                if (utilidades.SQL(Actividad, Eliminar)) {

                                    sDialog
                                            .setTitleText("Eliminando!")
                                            .setContentText("Registro Eliminado con Exito!")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                  ActivityBusqueda();

                                } else {
                                    new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("No se pudo!")
                                            .show();
                                }

                            }
                        })
                        .show();

            }
        });
    }
public void ActivityBusqueda(){
    ObjetoAlumno.putInt("Indice",0);
    getFragmentManager().beginTransaction().remove(this).commit();
    startActivity(new Intent(Actividad, MainActivity.class).putExtras(ObjetoAlumno));

}
    private boolean VerificarNumeroControl() {
        /*Validacion Para el Numero de control en caso de que se modifique, verificamos si los datos extraidos
         * desde la Actividad Consultar son iguales,A los que modificamos, si coinciden, se procede a la Modificacion de datos
         * mediante el Numero de control, PERO si son Diferentes, nos encargaremos se insertarlo primero*/
        return (AlumnoCapturado.getNumeroControl().equals(AlumnoModificado.getNumeroControl()));
    }

    public void ActualizarDatosBD() {
        String Modificar = "Update " + utilidades.Tabla_Usuario + " set " +
                utilidades.CAMPO_ID +  "='" + AlumnoModificado.getNumeroControl() + "', "
                + utilidades.CAMPO_NOMBRE + " ='" + AlumnoModificado.getNombre() + "', " +
                utilidades.CAMPO_Apellidos + " ='" + AlumnoModificado.getApellidos() + "'," +
                utilidades.CAMPO_GENERO + " ='" + AlumnoModificado.getGenero() + "'," +
                utilidades.CAMPO_Carrera + " ='" + AlumnoModificado.getCarrera() + "'," +
                utilidades.CAMPO_Correo + " ='" + AlumnoModificado.getCorreo() + "'," +
                utilidades.CAMPO_Direccion + " ='" + AlumnoModificado.getDireccion() + "'," +
                utilidades.CAMPO_Telefono + " ='" + AlumnoModificado.getTelefono() + "'" + " where " + utilidades.CAMPO_ID + "='" + AlumnoModificado.getNumeroControl() + "' ";

        if (utilidades.SQL(Actividad, Modificar)) {
            new SweetAlertDialog(Actividad)
                    .setTitleText("Cambios Guardados!")
                    .setConfirmButtonBackgroundColor(R.color.md_blue_400)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            ActivityBusqueda();
                        }
                    })
                    .show();

        } else {
            new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Tuvimos un error al Actualizar :(..")
                    .show();
        }
    }
    void ActualizarNumeroControl() {
        if (utilidades.SQL(Actividad, "update " + utilidades.Tabla_Usuario + " set " + utilidades.CAMPO_ID + " ='" + AlumnoModificado.getNumeroControl() + "' where " +
                utilidades.CAMPO_ID + "='" + AlumnoCapturado.getNumeroControl() + "';")) {
            new SweetAlertDialog(Actividad)
                    .setTitleText("Se Cambio el Numero de control con Exito")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            ActualizarDatosBD();
                        }
                    })
                    .show();
        } else {
            new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Ocurrio un Error al Actualizar el Numero de Control!")
                    .show();
        }
    }
    private void AsignarImagen() {
        if (AlumnoCapturado.getGenero().equalsIgnoreCase("Masculino")) {
            rM.setChecked(true);
            Img.setImageResource(R.drawable.varon1);
        } else if (AlumnoCapturado.getGenero().equalsIgnoreCase("Femenino")) {
            rF.setChecked(true);
            Img.setImageResource(R.drawable.usf);
        }
    }

    private void CargarDatosModificados() {
        AlumnoModificado = new Alumno();
        AlumnoModificado.setNumeroControl(Txt[0].getText().toString());
        AlumnoModificado.setNombre(Txt[1].getText().toString());
        AlumnoModificado.setApellidos(Txt[2].getText().toString());
        AlumnoModificado.setCarrera(Txt[3].getText().toString());
        AlumnoModificado.setTelefono(Txt[4].getText().toString());
        AlumnoModificado.setCorreo(Txt[5].getText().toString());
        AlumnoModificado.setDireccion(Txt[6].getText().toString());
        AlumnoModificado.setGenero(genero);
    }

    private void CargarDatos() {
        Txt[0].setText(AlumnoCapturado.getNumeroControl());
        Txt[1].setText(AlumnoCapturado.getNombre());
        Txt[2].setText(AlumnoCapturado.getApellidos());
        Txt[3].setText(AlumnoCapturado.getCarrera());
        Txt[4].setText(AlumnoCapturado.getTelefono());
        Txt[5].setText(AlumnoCapturado.getCorreo());
        Txt[6].setText(AlumnoCapturado.getDireccion());
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

    boolean ValidarCampos() {
        boolean bandera;
        if (rF.isChecked() == true) {
            genero = "Femenino";
        } else if (rM.isChecked() == true) {
            genero = "Masculino";
        } else {
            genero = "No Seleccionado";
        }
        if (!genero.equals("No Seleccionado") && VerificarCamposVacios()
                && utilidades.ValidarCorreo(Txt[5].getText().toString()) &&
                utilidades.ValidaTelefono("" + Txt[4].getText().toString())) {
            CargarDatosModificados();//CargamoslosDatos una vez que pasa la validacion

            bandera = true;
        } else {
            AsignarAlertaEditText();
            bandera = false;
        }
        return bandera;
    }

    void AsignarAlertaEditText() {
        if (utilidades.ValidarCorreo(Txt[5].getText().toString())) {
            Txt[5].setError("Correo Incorrecto");
        }
        if (utilidades.ValidaTelefono("" + Txt[4].getText().toString())) {
            Txt[4].setError("Telefono Incorrecto");
        }
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
            Actividad = (Activity) context;
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
