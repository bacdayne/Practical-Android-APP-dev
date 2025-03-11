package com.example.droidcafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.droidcafe.R;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String spinnerLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.order_textview), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        String message = "Order: " +
                intent.getStringExtra(MainActivity.EXTRA_ORDER_MESSAGE);
        TextView textView = findViewById(R.id.order_textview);
        textView.setText(message);
        Spinner spinner = findViewById(R.id.label_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked.
        if (view.getId() == R.id.sameday) {
            if (checked) {
                // Same day service
                displayToast(getString(R.string.same_day_messenger_service));
            }
        } else if (view.getId() == R.id.nextday) {
            if (checked) {
                // Next day delivery
                displayToast(getString(R.string.next_day_ground_delivery));
            }
        } else if (view.getId() == R.id.pickup) {
            if (checked) {
                // Pick up
                displayToast(getString(R.string.pick_up));
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerLabel = adapterView.getItemAtPosition(i).toString();
        displayToast(spinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}