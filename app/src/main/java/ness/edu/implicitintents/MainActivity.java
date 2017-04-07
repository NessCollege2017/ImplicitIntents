package ness.edu.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        //timePicker.setCurrentHour(0);
        //timePicker.setCurrentMinute(0);

        Intent intent = getIntent();
        if (intent!=null){
            int hour = intent.getIntExtra(AlarmClock.EXTRA_HOUR, 0);
            int minutes = intent.getIntExtra(AlarmClock.EXTRA_MINUTES, 0);
            timePicker.setCurrentMinute(minutes);
            timePicker.setCurrentHour(hour);
            //
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        }
    }

    public void dial(View view) {

        //DATA
        Uri tel = Uri.parse("tel:0544545455");

        Intent dialIntent =
                new Intent(Intent.ACTION_DIAL/*action*/, tel/*data*/);

        //intent resolution?!
        if (canOpen(dialIntent))
            startActivity(dialIntent);
    }

    public void website(View view) {
        //http://support.logitech.com/en_us/home
        Uri address = Uri.parse("https://www.google.co.il/search?q=david");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, address);

        //Intent resolution?!
        if (canOpen(webIntent)) {
            startActivity(webIntent);
        }else {
            Toast.makeText(this, "No Browser", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean canOpen(Intent intent) {
        return intent.resolveActivity(getPackageManager())!=null;
    }

    public void setAlarm(View view) {
        int hour = 0;
        int minutes = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minutes = timePicker.getMinute();
        }else {
            hour = timePicker.getCurrentHour();
            minutes = timePicker.getCurrentMinute();
        }

        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);

        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, hour);

        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, "Good Morning!");
        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        if (canOpen(alarmIntent)){
            startActivity(alarmIntent);
        }
    }

}
