package com.hostelmanager.hostelmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class feedback extends AppCompatActivity {

    EditText email_type,comment;
    TextView ask;
    Button snd_Button;
    String cmnt=null,value=null;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);





    //email_type=findViewById(R.id.email_type);
    ask=findViewById(R.id.ask);
    comment=findViewById(R.id.comment);

    snd_Button=findViewById(R.id.snd_Button);

    snd_Button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            cmnt=comment.getText().toString();
            RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
             value =((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

                 Intent sndIntent=new Intent();
                 sndIntent.setAction(Intent.ACTION_SEND);
                 sndIntent.putExtra(Intent.EXTRA_TEXT,"Would you like to use this app again : "+value+"\n"+"\n"+cmnt);
                 sndIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"svksvksvksvk123@gmail.com"});
                 sndIntent.setType("message/rfc822");
                 startActivity(sndIntent);

                 Toast.makeText(getBaseContext(),"We would love to hear from you",Toast.LENGTH_SHORT).show();
             }
            });




    }
}
