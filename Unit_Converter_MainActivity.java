package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText inputEditText;
    private TextView outputTextView;
    private Spinner fromSpinner;
    private Spinner toSpinner;

    private String[] unitOptions = {"Centimeters", "Inches", "Meters", "Feet", "Kilometers", "Miles"};

    private double[][] conversionFactors = {
            {1, 0.393701, 0.01, 0.0328084, 0.00001, 0.00000621371},
            {2.54, 1, 0.0254, 0.0833333, 0.0000254, 0.0000157828},
            {100, 39.3701, 1, 3.28084, 0.001, 0.000621371},
            {30.48, 12, 0.3048, 1, 0.0003048, 0.000189394},
            {100000, 39370.1, 1000, 3280.84, 1, 0.621371},
            {160934, 63360, 1609.34, 5280, 1, 0.621371}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = findViewById(R.id.input_edit_text);
        outputTextView = findViewById(R.id.output_text_view);
        fromSpinner = findViewById(R.id.from_spinner);
        toSpinner = findViewById(R.id.to_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, unitOptions);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        fromSpinner.setSelection(0);
        toSpinner.setSelection(1);

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    convert();
                }
            }
        });
    }

    private void convert() {
        String inputString = inputEditText.getText().toString();
        if (inputString.isEmpty()) {
            outputTextView.setText("");
            return;
        }

        double inputValue = Double.parseDouble(inputString);
        int fromUnitIndex = fromSpinner.getSelectedItemPosition();
        int toUnitIndex = toSpinner.getSelectedItemPosition();

        double conversionFactor = conversionFactors[fromUnitIndex][toUnitIndex];
        double result = inputValue * conversionFactor;

        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        String resultString = decimalFormat.format(result);
        outputTextView.setText(resultString);
    }
}
