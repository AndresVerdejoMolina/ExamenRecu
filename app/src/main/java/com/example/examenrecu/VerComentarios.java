package com.example.examenrecu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class VerComentarios extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String VALORACION = "valoracion";
    private static final String COMENTARIO = "COMENTARIO";

    // TODO: Rename and change types of parameters
    private String valoracion, comentario;

    public VerComentarios() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static VerComentarios newInstance(Comentario comentario) {
        VerComentarios fragment = new VerComentarios();
        Bundle args = new Bundle();
        args.putString(COMENTARIO, comentario.getComentarios());
        args.putString(VALORACION, comentario.getValoracion());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comentario = getArguments().getString(COMENTARIO);
            valoracion = getArguments().getString(VALORACION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ver_comentarios, container, false);
        TextView comentarioView, valoracionView;
        comentarioView = v.findViewById(R.id.comentario);
        valoracionView = v.findViewById(R.id.valoracion);

        comentarioView.setText(comentario);
        valoracionView.setText(valoracion);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
