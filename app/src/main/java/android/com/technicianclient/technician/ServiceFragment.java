package android.com.technicianclient.technician;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import android.com.technicianclient.technician.contentprovider.SharedMethods;
import android.com.technicianclient.technician.controller.UiController;
import android.com.technicianclient.technician.serverconnetors.ServiceDataSender;


public class ServiceFragment extends Fragment {

    private Spinner spinnerServices;
    private String[] services;
    private String[] cities;
    private Spinner spinnerCities;

    private String city;
    private String item;
    public EditText name;
    public EditText phone;
    public EditText address;
    public MultiAutoCompleteTextView remarks;
    private Button submit;

    public ServiceFragment() {
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
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        spinnerCities = (Spinner)view.findViewById(R.id.sp_cities);
        cities = new String[] {"Karachi","Hyderabad","Sukkur"};


        ArrayAdapter<String> servicesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, cities);
        spinnerCities.setAdapter(servicesArrayAdapter);

        city = cities[0];

        //initialize fields
        name = (EditText) view.findViewById(R.id.et_name);
        phone = (EditText)view.findViewById(R.id.et_phone);
        address = (EditText)view.findViewById(R.id.et_address);
        submit = (Button) view.findViewById(R.id.btnSubmit);
        remarks = (MultiAutoCompleteTextView) view.findViewById(R.id.remarks);

        spinnerServices = (Spinner)view.findViewById(R.id.sp_services);
        services = new String[] {"Refrigrator ","AC","Deep Freezer","Microvawe","TV","DVD","Camera","Mobile Phone","Washing Machine"
        ,"Water Dispenser"};
        item =String.valueOf(1);
        final ArrayAdapter<String> servicesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, services);
        spinnerServices.setAdapter(servicesAdapter);


        spinnerServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = String.valueOf(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                work();
            }
        });
        return view;
    }

    public void work(){

        String name = this.name.getText().toString();
        String address = this.address.getText().toString();
        String phone = this.phone.getText().toString();
        String remarks = this.remarks.getText().toString();

        if (name.isEmpty()) {
            this.name.setError("Please enter name");
            return;
        }

//        if (address.isEmpty()) {
//            this.address.setError("Please enter address");
//            return;
//        }
        if (phone.isEmpty()) {
            this.phone.setError("Please enter phone number");
            return;
        }
        if (!SharedMethods.validatePhoneNumber(phone)){
            this.phone.setError("Invalid phone number");
            return;
        }
//        if (remarks.isEmpty()) {
//            UiController.showDialog("Please enter your message",getActivity());
//            return;
//        }

        if (!UiController.isNetworkAvailable(getContext()))
        {
            UiController.showDialog("Please connect to network",getActivity());
            return;
        }
        SharedMethods.hideKeyBoard(getActivity());
        ServiceDataSender sender = new ServiceDataSender(ServiceFragment.this);

        sender.execute(name,phone,address,item,remarks);

    }
}
