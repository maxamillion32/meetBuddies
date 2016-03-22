package com.mk.meetbuddies.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mk.meetbuddies.MainActivity;
import com.mk.meetbuddies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuddiesFragment extends Fragment {

    ListView mListView;
    String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };
    public BuddiesFragment() {
        // Required empty public constructor
        int cas;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     //   this.getParentFragment().
        View view = inflater.inflate(R.layout.fragment_buddies, container, false);

        mListView = (ListView) view.findViewById(R.id.buddiesListView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1, prenoms);
        //mListView.setAdapter(adapter);
        afficherListeBuddies();
        return view;
    }

    private void afficherListeNoms(){
        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, prenoms);
        mListView.setAdapter(adapter);
    }

    private List<Buddies> genererBuddies(){
        List<Buddies> buddies = new ArrayList<Buddies>();
        //boucle for et récupération de la base de données:
        buddies.add(new Buddies(Color.BLACK, "Mourad Mamlouk", "mourad_mamlouk@gmail.com"));
        return buddies;
    }

    private void afficherListeBuddies(){
        List<Buddies> buddies = genererBuddies();

        BuddiesAdapter adapter = new BuddiesAdapter(getActivity().getApplicationContext(), buddies);
        mListView.setAdapter(adapter);
    }

}
