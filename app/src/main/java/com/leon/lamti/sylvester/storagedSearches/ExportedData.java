package com.leon.lamti.sylvester.storagedSearches;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.leon.lamti.sylvester.R;
import com.leon.lamti.sylvester.searchRecyclerViewPackage.Searches;
import com.leon.lamti.sylvester.storagedSearches.RealmBackupRestore;
import com.scand.realmbrowser.RealmBrowser;

import io.realm.Realm;

public class ExportedData extends AppCompatActivity {

    RealmBackupRestore rbr;
    Button bb, rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported_data);



        Realm realm = Realm.getDefaultInstance();
        new RealmBrowser.Builder(this)
                .add(realm, Searches.class)
                .show();



        /*bb = (Button) findViewById(R.id.bb);
        rb = (Button) findViewById(R.id.rb);

        rbr = new RealmBackupRestore(this);

        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rbr.backup();
            }
        });

        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rbr.restore();
            }
        });*/
    }
}
