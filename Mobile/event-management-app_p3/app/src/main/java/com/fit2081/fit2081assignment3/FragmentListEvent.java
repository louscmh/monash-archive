package com.fit2081.fit2081assignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit2081.fit2081assignment3.provider.EntityViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListEvent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListEvent newInstance(String param1, String param2) {
        FragmentListEvent fragment = new FragmentListEvent();
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

    Gson gson = new Gson();
    ArrayList<Event> listEvent;
    EventRecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private EntityViewModel entityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_list_event, container, false);

        entityViewModel = new ViewModelProvider(this).get(EntityViewModel.class);

        // get reference to the recycler view
        recyclerView = fragmentView.findViewById(R.id.eventRecyclerView);

        // A Linear RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        LiveData<List<Event>> liveDataList =  entityViewModel.getAllEvents();
        liveDataList.observe(getViewLifecycleOwner(), listData -> {
            listEvent = new ArrayList<Event>(listData);
            recyclerView.setLayoutManager(layoutManager);
            recyclerAdapter = new EventRecyclerAdapter();
            recyclerAdapter.setData(listEvent);
            recyclerView.setAdapter(recyclerAdapter);
        });

//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UNIQUE_FILE_NAME", Context.MODE_PRIVATE);
//        String eventKey = sharedPreferences.getString("event_array", "");
//        if (!eventKey.equals("")){
//            Type type = new TypeToken<ArrayList<Event>>() {}.getType();
//            listEvent = gson.fromJson(eventKey,type);
//        } else {
//            listEvent = new ArrayList<>();
//        }
        // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager

        return fragmentView;
    }
}