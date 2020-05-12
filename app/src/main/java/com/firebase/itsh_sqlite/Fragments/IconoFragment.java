package com.firebase.itsh_sqlite.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.itsh_sqlite.Actividades.AsignaActivity;
import com.firebase.itsh_sqlite.MainActivity;
import com.firebase.itsh_sqlite.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IconoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IconoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IconoFragment extends Fragment
implements Consultar_UsuariosFragment.OnFragmentInteractionListener,
AltaUsuariosFragment.OnFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public IconoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IconoFragment.
     */
    // TODO: Rename and change types and number of parameters

    View vista;
    Activity Actividad;
    RecyclerView recyclerFrutas;
    int valor=-2;
    Intent InicioActividad=null;
    Bundle PasarDatos = new Bundle();//pasar datos
    ImageView btnAgregar,btnConsultar,btnSalir;

    public static IconoFragment newInstance(String param1, String param2) {
        IconoFragment fragment = new IconoFragment();
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
    int retornar(){
        Bundle Obj =Actividad.getIntent().getExtras();
        return (Obj!=null)?Obj.getInt("info"):1;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_icono, container, false);
        // Inflate the layout for this fragment

        btnAgregar=vista.findViewById(R.id.Img_Agregar);
        btnConsultar=vista.findViewById(R.id.Img_Buscar);
        btnSalir=vista.findViewById(R.id.Img_Salir);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            valor=0;
            InicioActividad=new Intent(Actividad, MainActivity.class);
                ImplementarActividad(InicioActividad);
            }
        });
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            valor=1;
            InicioActividad=new Intent(Actividad, MainActivity.class);
            ImplementarActividad(InicioActividad);
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actividad.finish();
            }
        });
        return vista;
    }
void ImplementarActividad(Intent InicioActividad){
    if (InicioActividad!=null){
        PasarDatos.putInt("Indice",valor);
        InicioActividad.putExtras(PasarDatos);
        startActivity(InicioActividad);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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
