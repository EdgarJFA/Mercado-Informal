package com.rademotions.mercadoinformal.visao;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rademotions.mercadoinformal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragVazio extends Fragment {


    public FragVazio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_vazio, container, false);

        return view;
    }

}
