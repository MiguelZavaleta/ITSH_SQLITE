package com.firebase.itsh_sqlite.Recursos;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class utilidades {
    public static final String Nombre_BD="ITSH",
            Tabla_Usuario="Alumnos";
    public static final String CAMPO_ID="NumeroControl";
    public static final String CAMPO_NOMBRE="Nombre";
    public static final String CAMPO_Apellidos="Apellidos";
    public static final String CAMPO_Carrera="Carrera";
    public static final String CAMPO_Correo="Correo";
    public static final String CAMPO_Direccion="Dir";
    public static final String CAMPO_Telefono="Tel";

    public static final String CAMPO_GENERO="Genero";
    public static final String CREAR_TABLA_Usuario="CREATE TABLE "+Tabla_Usuario+"(" +CAMPO_ID+ " string primary key,"+
            CAMPO_NOMBRE+" text,"+
            CAMPO_Apellidos+" text,"+
            CAMPO_GENERO+" text,"+
            CAMPO_Carrera+" text,"+
            CAMPO_Correo+" text,"+
            CAMPO_Direccion+" text,"+
            CAMPO_Telefono+" text);";
    /*Variables a Utilizad:*/
    public static ArrayList<Alumno> ListaAlumnos=null;
    public static Alumno AlumnoSeleccionado=null;

    public static boolean SQL(Activity Actividad , String query){
        boolean band=false;
        try {
            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(Actividad,Nombre_BD,null,1);
            SQLiteDatabase db=conn.getWritableDatabase();

            db.execSQL(query);
            db.close();
            band= true;
        }catch (Exception e){
            new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText(e.toString())
                    .show();
            band=false;
        }
        return band;
    }
    public static void ConsultarAlumnos(Activity actividad,String Query) {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(actividad,Nombre_BD,null,1);
        SQLiteDatabase db=conn.getReadableDatabase();

        Alumno Alumnos=null;
        ListaAlumnos=new ArrayList<Alumno>();
        //select * from usuarios
        Cursor cursor=db.rawQuery(((Query.trim().isEmpty())?"SELECT * FROM ":Query)+Tabla_Usuario,null);

        while (cursor.moveToNext()){
            Alumnos=new Alumno();
            Alumnos.setNumeroControl(cursor.getString(0));
            Alumnos.setNombre(cursor.getString(1));
            Alumnos.setApellidos(cursor.getString(2));
            Alumnos.setGenero(cursor.getString(3));
            Alumnos.setCarrera(cursor.getString(4));
            Alumnos.setCorreo(cursor.getString(5));
            Alumnos.setDireccion(cursor.getString(6));
            Alumnos.setTelefono(cursor.getString(7));

            ListaAlumnos.add(Alumnos);
        }

        db.close();
    }
    public static boolean ValidarCorreo(String val) {
        String Co = "[a-zA-Z0-9]+[-_.]*[a-zA-Z0-9]+\\@[a-zA-Z]+\\.[a-zA-Z]+";
        return (val.matches(Co) ? true : false);
    }

    public static boolean ValidaTelefono(String num) {
        return (num.matches("(\\+?[0-9]{2,3}\\-)?([0-9]{10})") ? true : false);

    }

}
