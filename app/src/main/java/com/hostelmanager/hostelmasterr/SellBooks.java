package com.hostelmanager.hostelmasterr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SellBooks extends Fragment implements AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    String item1;
    String item2;

    private Button btnLogin1;
TextInputEditText bookNameSell;
TextInputEditText authorNameSell;



    public SellBooks() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_sell_books, container, false);
       // ButterKnife.bind(this,view);

        bookNameSell=view.findViewById(R.id.bookNameSell);
        authorNameSell=view.findViewById(R.id.bookAuthorSell);
        btnLogin1 = view. findViewById(R.id.btnLogin1);
        // Spinner element
        Spinner spinner1 = view. findViewById(R.id.spinner3);
        Spinner spinner2 = view. findViewById(R.id.spinner4);

        // Spinner click listener
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> department = new ArrayList<String>();
        department.add("Information Science");
        department.add("Computer Science");
        department.add("Mechanical");
        department.add("Electronics");
        department.add("Electrical");
        department.add("Civil");
        department.add("Biotechnology");
        department.add("Telecommunication");

        List<String> semester = new ArrayList<String>();
        semester.add("First");
        semester.add("Second");
        semester.add("Third");
        semester.add("Fourth");
        semester.add("Fifth");
        semester.add("Sixth");
        semester.add("Seventh");
        semester.add("Eighth");


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, department);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, semester);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter1);
        spinner2.setAdapter(dataAdapter2);
        btnLogin1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if ((bookNameSell != null && bookNameSell.length() > 0) && (authorNameSell != null && authorNameSell.length() > 0)) {
                    {      //THEN SEND TO THE NEXT ACTIVITY
                        Intent i = new Intent(getActivity().getBaseContext(), finalBookDetails.class);
                        i.putExtra("Department", item1);
                        i.putExtra("Semester", item2);
                        i.putExtra("bookNameSell",bookNameSell.getText().toString());
                        i.putExtra("authorNameSell",authorNameSell.getText().toString());

                        getActivity().startActivity(i);
                    }

                }
                else if (!(bookNameSell != null && bookNameSell.length() > 0))
                {
                    Toast.makeText(getActivity(),"Book Name can't be empty ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(),"Author Name can't be empty ",Toast.LENGTH_SHORT).show();
                }


                   // btnLogin1.setError("Please Enter The Book Name");
                    //Toast.makeText(, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch(adapterView.getId())
        {
            case R.id.spinner3:
                // On selecting a spinner item
                item1 = adapterView.getItemAtPosition(i).toString();
                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + item1, Toast.LENGTH_LONG).show();
                break;
            case R.id.spinner4:
                // On selecting a spinner item
                item2 = adapterView.getItemAtPosition(i).toString();
                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + item2, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // TODO: Rename method, update argument and hook method into UI event



}
