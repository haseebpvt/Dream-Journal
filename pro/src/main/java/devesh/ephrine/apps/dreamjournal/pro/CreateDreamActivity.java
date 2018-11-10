package devesh.ephrine.apps.dreamjournal.pro;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;

import devesh.ephrine.apps.dreamjournal.pro.Data.Dream;
import devesh.ephrine.apps.dreamjournal.pro.Database.AppDatabase;

public class CreateDreamActivity extends AppCompatActivity {

    //Views
    private TextInputEditText TitleTx;
    private TextInputEditText DreamTx;
    private TextInputEditText DateTxt;
    private TextView Date;

    private AppDatabase appDatabase;
    private Bundle bundle;

    //Variables
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dream);
        getSupportActionBar().setTitle("Add Dream");

        //Initializing views
        TitleTx = findViewById(R.id.TxTitle);
        DreamTx = findViewById(R.id.DreamTxt);
        DateTxt = findViewById(R.id.InputDate);
        Date = findViewById(R.id.TextViewDateTx);
        DateTxt = findViewById(R.id.InputDate);

        //Database
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "dream_database")
                .allowMainThreadQueries()
                .build();

        //Getting extras
        bundle = getIntent().getExtras();

        if (bundle != null && bundle.getString("type").equals("type_edit")){
            id = bundle.getInt("dream_id");

            Dream dream = appDatabase.dreamDao().getDream(id);

            TitleTx.setText(dream.getTitle());
            DreamTx.setText(dream.getDream());
            DateTxt.setText(dream.getDate());
            Date.setText("Dream date: " + dream.getDate());

            DreamTx.setSelected(true);
        } else {

            Calendar c = Calendar.getInstance();

            int Tdt=c.get(Calendar.DATE);
            int Tmonth=c.get(Calendar.MONTH);
            Tmonth=Tmonth+1;
            int Tyear=c.get(Calendar.YEAR);
            String Datatx=String.valueOf(Tdt)+"/"+String.valueOf(Tmonth)+"/"+String.valueOf(Tyear);

            DateTxt.setText(Datatx);
            Date.setText("Today's Date: "+Datatx);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Save();
            CreateDreamActivity.this.finish();

            return true;
        }

      //  return true;
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dream_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {

            Save();
            CreateDreamActivity.this.finish();

            return true;
        }
        if (id == R.id.cancel) {
            CreateDreamActivity.this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void Save(){

        if(DreamTx.getText().toString()=="" || DreamTx==null || DreamTx.getText().toString().equals("") || DreamTx.getText().toString().equals(" ")){

        } else{
            String Datetx=DateTxt.getText().toString();

            if (bundle != null && bundle.getString("type").equals("type_edit")) {
                Dream d = appDatabase.dreamDao().getDream(bundle.getInt("dream_id"));
                d.setTitle(TitleTx.getText().toString());
                d.setDream(DreamTx.getText().toString());
                d.setDate(DateTxt.getText().toString());
                appDatabase.dreamDao().updateDream(d);
            } else {
                Dream dream = new Dream(Datetx, TitleTx.getText().toString(), DreamTx.getText().toString());
                appDatabase.dreamDao().insertDream(dream);
            }
        }

    }
}
