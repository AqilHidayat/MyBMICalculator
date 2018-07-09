package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button bCal;
    Button bRes;
    TextView tvDate;
    TextView tvBMI;
    TextView tvOutcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        bCal = findViewById(R.id.buttonCal);
        bRes = findViewById(R.id.buttonRes);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvOutcome = findViewById(R.id.textViewOutcome);

        ////////////////////////////////////////////////////

        // Button Calculate
        bCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());

                float BMI = (weight / (height * height));

                tvBMI.setText("Last Calculated BMI: " + String.valueOf(BMI));

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                tvDate.setText("Last Calculated Date: " + datetime);

                if(BMI < 18.5){
                    tvOutcome.setText("You are underweight");

                }
                else if(BMI < 24.9){
                    tvOutcome.setText("Your BMI is normal");

                }
                else if(BMI < 29.9){
                    tvOutcome.setText("You are overweight");
                }
                else{
                    tvOutcome.setText("Your BMI and the chao pubor Marcus is the same");
                }
            }
        });

        // Button for reset
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        bRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            etHeight.setText("");
            etWeight.setText("");
            tvDate.setText("Last Calculated Date : ");
            tvBMI.setText("Last Calculated BMI : ");

            SharedPreferences.Editor prefEdit = prefs.edit();

            prefEdit.clear().commit();



            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!etHeight.getText().toString().isEmpty() && !etWeight.getText().toString().isEmpty()) {
            Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
            String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                    (now.get(Calendar.MONTH) + 1) + "/" +
                    now.get(Calendar.YEAR) + " " +
                    now.get(Calendar.HOUR_OF_DAY) + ":" +
                    now.get(Calendar.MINUTE);

            float weight = Float.parseFloat(etWeight.getText().toString());
            float height = Float.parseFloat(etHeight.getText().toString());

            float BMI = (weight / (height * height));

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            SharedPreferences.Editor prefEdit = prefs.edit();


            prefEdit.putFloat("BMI", BMI);

            prefEdit.putString("Date", datetime);


            prefEdit.commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        float BMI = prefs.getFloat("BMI", 0.0f);
        String Date = prefs.getString("Date", "");

        tvBMI.setText("Last Calculated BMI: " + String.valueOf(BMI));
        tvDate.setText("Last Calculated Date: " + Date);
    }
}
