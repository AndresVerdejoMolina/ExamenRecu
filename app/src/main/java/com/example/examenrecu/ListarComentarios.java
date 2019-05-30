package com.example.examenrecu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListarComentarios.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListarComentarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarComentarios extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListarComentarios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarComentarios.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarComentarios newInstance(String param1, String param2) {
        ListarComentarios fragment = new ListarComentarios();
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
        View v = inflater.inflate(R.layout.fragment_listar_comentario, container, false);

        RecyclerView rv = v.findViewById(R.id.talleresRecyclerView);
        ComentariosAdapter adapter = new ComentariosAdapter(mListener.getComentarios());
        rv.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getActivity());
        rv.setLayoutManager(linearLayoutManager);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        List<Comentario> getComentarios();
        void verComentario(Comentario comentario);
    }

    public class ComentariosAdapter extends RecyclerView.Adapter<ComentariosAdapter.ComentariosHolder> {

        List<Comentario> comentarioList;

        public ComentariosAdapter(List<Comentario> comentarioList) {
            this.comentarioList = comentarioList;
        }

        @NonNull
        @Override
        public ComentariosHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

            ComentariosHolder rh = new ComentariosHolder(v);

            v.setOnClickListener(rh);

            return rh;
        }

        @Override
        public void onBindViewHolder(@NonNull ComentariosHolder comentarioHolder, int i) {
            comentarioHolder.setComentario(comentarioList.get(i));
            comentarioHolder.valoracion.setText(comentarioList.get(i).getValoracion());
            comentarioHolder.comentario.setText(comentarioList.get(i).getComentarios());
        }

        @Override
        public int getItemCount() {
            return comentarioList.size();
        }

        public class ComentariosHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {

            TextView valoracion, comentario;
            Comentario comentario1;

            public ComentariosHolder(@NonNull View itemView) {
                super(itemView);
                valoracion = itemView.findViewById(R.id.valoracion);
                comentario = itemView.findViewById(R.id.comentario);
            }

            public void setComentario(Comentario r) {
                this.comentario1 = r;
            }

            @Override
            public void onClick(View v) {
                mListener.verComentario(this.comentario1);
            }
        }
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
}
