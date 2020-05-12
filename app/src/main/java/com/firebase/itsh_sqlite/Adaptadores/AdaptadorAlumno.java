package com.firebase.itsh_sqlite.Adaptadores;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.itsh_sqlite.Fragments.Consultar_UsuariosFragment;
import com.firebase.itsh_sqlite.R;
import com.firebase.itsh_sqlite.Recursos.Alumno;
import com.firebase.itsh_sqlite.Recursos.utilidades;

import java.util.List;

public class AdaptadorAlumno extends RecyclerView.Adapter<AdaptadorAlumno.ViewHolderJugador> implements View.OnClickListener {

    private View.OnClickListener listener;
    List<Alumno> ListaAlumnos;
    View vista;
    Activity actividad;
   public static int UltimaSeleccion = -1;
    int posicionMarcada = 0;

    public AdaptadorAlumno(List<Alumno> listaJugador, Activity context) {
        this.ListaAlumnos = listaJugador;
        this.actividad=context;
    }

    @NonNull
    @Override
    public ViewHolderJugador onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_usuario, viewGroup, false);
        vista.setOnClickListener(this);
        return new ViewHolderJugador(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderJugador viewHolderJugador, int i) {

        //se resta uno ya que buscamos la lista de elementos que inicia en la pos 0
        viewHolderJugador.imgAvatar.setImageResource((ListaAlumnos.get(i).getGenero().equalsIgnoreCase("masculino")) ? R.drawable.varon1 : R.drawable.usf);
        viewHolderJugador.txtNombre.setText(ListaAlumnos.get(i).getNumeroControl());//correo del usuario
        viewHolderJugador.txtGenero.setText(ListaAlumnos.get(i).getNombre() + "\n" + ListaAlumnos.get(i).getCarrera()
        +"\n" + ListaAlumnos.get(i).getCorreo());
        final int pos = i;
        viewHolderJugador.cardAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicionMarcada=pos;
                Toast.makeText(actividad, "Seleccionaste : "+ListaAlumnos.get(pos).getNumeroControl(), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }
        });
            if(posicionMarcada==pos){
                viewHolderJugador.barraSeleccion.setBackgroundColor(vista.getResources().getColor(R.color.md_amber_A700));
                utilidades.AlumnoSeleccionado=utilidades.ListaAlumnos.get(posicionMarcada);
                UltimaSeleccion=posicionMarcada;

            }else {
                viewHolderJugador.barraSeleccion.setBackgroundColor(vista.getResources().getColor(R.color.md_white_1000));
            }





    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return ListaAlumnos.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class ViewHolderJugador extends RecyclerView.ViewHolder {
        CardView cardAvatar;
        ImageView imgAvatar;
        TextView txtNombre;
        TextView txtGenero;
        TextView   barraSeleccion;
        public ViewHolderJugador(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.idAvatar);
            txtNombre = itemView.findViewById(R.id.idNombre);
            txtGenero = itemView.findViewById(R.id.idDes);
            cardAvatar = itemView.findViewById(R.id.cardAvatar);
            barraSeleccion=itemView.findViewById(R.id.barraSeleccionId);


        }

    }
}
