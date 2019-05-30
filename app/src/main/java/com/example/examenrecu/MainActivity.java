package com.example.examenrecu;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements AgregarComentario.OnFragmentInteractionListener, ListarComentarios.OnFragmentInteractionListener {

    Toolbar mainToolbar;
    List<Taller> tallerList;
    List<Comentario> comentarioList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);

        tallerList = new ArrayList<Taller>();
        comentarioList = new ArrayList<Comentario>();
        Taller taller = new Taller("Seat", "El trabajador");
        tallerList.add(taller);
        new Mihilo().execute("https://jdarestaurant.firebaseio.com/talleres.json");
        FirebaseDatabase.getInstance().getReference().child("comentario").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comentario comentario = dataSnapshot.getValue(Comentario.class);
                comentarioList.add(comentario);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Bienvenida bienvenida = new Bienvenida();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, bienvenida).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.añadirComentario:
                AgregarComentario AgregarComentario = new AgregarComentario();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, AgregarComentario).commit();
                break;
            case R.id.talleres:
                ListarComentarios listarComentarios = new ListarComentarios();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, listarComentarios).commit();
                break;
            case R.id.verComentarios:
                VerComentarios verComentarios = new VerComentarios();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, verComentarios).commit();

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void crearComentario(Comentario comentario) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("cometario").push();
        ref.setValue(comentario);
    }

    @Override
    public List<Comentario> getComentarios() {
        return comentarioList;
    }

    @Override
    public void verComentario(Comentario comentario) {
        VerComentarios verComentarios = VerComentarios.newInstance(comentario);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, verComentarios).commit();

    }

    public class Mihilo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            HttpsURLConnection connection = null;
            URL url = null;
            BufferedReader br = null;
            String data = "";

            try {
                url = new URL(strings[0]);
                connection = (HttpsURLConnection) url.openConnection();

                br =  new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ( (linea = br.readLine() ) != null ) {
                    data += linea;
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            try {
                JSONArray jsonArr = new JSONArray(data);
                Log.i("JSON", String.valueOf(jsonArr));
                for (int i = 0 ; i < jsonArr.length() ; i++) {
                    JSONObject taller = jsonArr.getJSONObject(i);
                    Log.i("JSONArray", String.valueOf(taller));
                    Log.i("marca", String.valueOf((taller.getJSONArray("marcas").getString(i))));
                    Log.i("nombre", taller.getString("nombre"));
                    tallerList.add(new Taller( "pruebas",
                            taller.getString("nombre")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
