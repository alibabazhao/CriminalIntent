package com.example.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CrimeListFragment extends ListFragment {

    private ArrayList<Crime> mCrimes;

    private static final String TAG="CrimeListFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);
        mCrimes= CrimeLab.get(getActivity()).getCrimes();

        CrimeAdapter adapter=new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //get the crime from the adapter
        Crime c=((CrimeAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, c.getTitle()+" was clicked");

        //start crimeActivity
        Intent i=new Intent(getActivity(), CrimeActivity.class);
        startActivity(i);
    }

    private class CrimeAdapter extends ArrayAdapter<Crime>{
        public CrimeAdapter(ArrayList<Crime> crimes){
            super(getActivity(), 0, crimes);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //if we weren't given a view, inflate one
            if(convertView==null){
                convertView=getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }

            //configure the view for this Crime
            Crime c=getItem(position);

            TextView titleTextView=(TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());

            TextView dateTextView=(TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(DateFormat.format("MMM dd, yyyy", c.getDate()));

            CheckBox solvedCheckBox=(CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }
}
