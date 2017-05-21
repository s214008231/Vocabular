package nmmu.wrap302.Task01;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import java.io.File;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        db = new TermDB(this);
        final ImageButton btnBrowse = (ImageButton)findViewById(R.id.btnBrowse),
                btnAdd = (ImageButton)findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked(v);
            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBrowseClicked(v);
            }
        });
    }

    public void onBrowseClicked(View view){
        Intent intent = new Intent(this, Browser.class);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    public static TermDB db;

    public void onAddClicked(final View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //alertDialog.setTitle("New Term");
        final EditText term = new EditText(this),
                definition = new EditText(this),
                synonym = new EditText(this),
                antonym = new EditText(this);
        definition.setScroller(new Scroller(this));
        definition.setVerticalScrollBarEnabled(true);
        definition.setMaxLines(2);
        definition.setMinLines(2);
        definition.setGravity(Gravity.TOP);
        LinearLayout vlContent = new LinearLayout(this);
        vlContent.setOrientation(1);

        term.setHint("Term");
        definition.setHint("Definition");
        synonym.setHint("Synonyms e.g: Term1, Term2");
        antonym.setHint("Antonyms e.g: Term1, Term2");

        vlContent.addView(term);
        vlContent.addView(definition);
        vlContent.addView(synonym);
        vlContent.addView(antonym);

        alertDialog.setView(vlContent);
        alertDialog.setCancelable(true);
        alertDialog.setNeutralButton("Pull Definition", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setPositiveButton("Add Term", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(term != null){
                    Term newTerm = new Term(term.getText().toString(), definition.getText().toString(),
                            synonym.getText().toString(), antonym.getText().toString());
                    Browser.termAdapter.add(newTerm);
                    if(db.addTerm(newTerm))
                        Toast.makeText(MyActivity.this, newTerm.term + " Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MyActivity.this, newTerm.term + " not inserted", Toast.LENGTH_LONG).show();
                }
                onBrowseClicked(view);
            }
        });
        alertDialog.show();
    }
}