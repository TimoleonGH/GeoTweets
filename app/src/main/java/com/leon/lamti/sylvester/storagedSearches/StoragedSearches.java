package com.leon.lamti.sylvester.storagedSearches;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leon.lamti.sylvester.R;
import com.leon.lamti.sylvester.SearchActivity;
import com.leon.lamti.sylvester.searchRecyclerViewPackage.Searches;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class StoragedSearches extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnTouchListener, View.OnClickListener {

    // R e a l m
    private Realm realm;
    private RealmResults<Searches> realmResults;
    private RealmResults<Searches> realmResults2;

    // A d a p t e r    S t u f f
    private ArrayList<StoragedSearchObject> ssoList = new ArrayList();
    private StoragedSearchObject sso;
    private GridAdapter gridAdapter;

    // V i e w s
    private GridView mGridView;
    private PopupWindow popupWindow;
    private RelativeLayout parentLay;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }*/

        setContentView(R.layout.activity_storaged_searches);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exportData();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        if(toolbar != null) {

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Storaged Searches");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        realm = Realm.getDefaultInstance();
        realmResults = realm.where(Searches.class).findAllSorted("nameId");

        mGridView = (GridView) findViewById(R.id.gridView);

        parentLay = (RelativeLayout) findViewById(R.id.relLay);

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //Toast.makeText(StoragedSearches.this, "Item pressed!", Toast.LENGTH_SHORT).show();

                /*LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup_delete, null);
                popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                Button btnDismiss = (Button)popupView.findViewById(R.id.deleteB);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        deleteObject(ssoList.get(position).getName(), position);
                        //parentLay.setOnTouchListener(StoragedSearches.this);
                        //parentLay.setOnClickListener(StoragedSearches.this);
                        popupWindow.dismiss();
                    }});

                //popupWindow.setOutsideTouchable(true);
                //popupWindow.setFocusable(true);

                popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                //.showAsDropDown( gridAdapter.getView(position, view, parent), 50, -30);*/


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(StoragedSearches.this);
                alertDialog.setTitle("Search options");
                alertDialog.setMessage("Delete or search again.");

                alertDialog.setPositiveButton("Search",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                realm = Realm.getDefaultInstance();
                                realmResults2 = realm.where(Searches.class).contains("nameId", ssoList.get(position).getName())
                                        .contains("date", dateMethod()).findAll();

                                if (realmResults2.size() >= 1) {

                                    Toast.makeText(StoragedSearches.this, "Search already exists!", Toast.LENGTH_SHORT).show();

                                } else {

                                    Intent intent = new Intent(StoragedSearches.this, SearchActivity.class);
                                    intent.putExtra("userText", ssoList.get(position).getName());
                                    startActivity(intent);
                                }
                            }
                        });

                alertDialog.setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                deleteObject(ssoList.get(position).getName(), position);
                                //dialog.cancel();
                            }
                        });

                alertDialog.show();

                return true;
            }
        });

        String tempName = "Mpirdimpikoulas";
        String tempDate = "00/00/00";
        boolean flag = false;
        for (int i=0; i<realmResults.size(); i++) {

            tempName = realmResults.get(i).getNameId().replace(".ss", "");
            tempDate = realmResults.get(i).getDate();

            for ( int j=0; j<ssoList.size(); j++ ) {
                if (ssoList.get(j).getName().equalsIgnoreCase(tempName)) {

                    flag = true;
                }
            }

            if ( !flag ) {

                sso = new StoragedSearchObject();
                sso.setName(tempName.replace(".ss", ""));
                sso.setDate(tempDate);
                ssoList.add(sso);
            }

            flag = false;
        }

        gridAdapter = new GridAdapter( this, ssoList);
        mGridView.setAdapter(gridAdapter);
        mGridView.setOnItemClickListener(this);
       // mGridView.setOnTouchListener(this);
    }

    public boolean checkIfExists (String tempName) {

        RealmQuery<Searches> query = realm.where(Searches.class)
                .equalTo("nameId", tempName);

        return query.count() == 0 ? false : true;
    }

    public String dateMethod () {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
        String yesterdayAsString = dateFormat.format(calendar.getTime());

        Log.e("SearchFrag", "date" + yesterdayAsString);
        return yesterdayAsString;
    }

    private void deleteObject( String name, int position ) {

        ssoList.remove(position);
        RealmResults f = realm.where(Searches.class).contains("nameId", name).findAll();

        realm.beginTransaction();
        f.deleteAllFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void onStart() {
        super.onStart();

        realmResults.addChangeListener(realmChangeListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        realmResults.removeChangeListener(realmChangeListener);
    }

    private void exportData() {

        /*Intent intent = new Intent(StoragedSearches.this, ExportedData.class);
        startActivity(intent);*/

        RealmBackupRestore rbr = new RealmBackupRestore(this);
        rbr.backup();
        rbr.restore();
    }

    private RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {

            gridAdapter.notifyDataSetChanged();
            //dialog.dismiss();
            Log.d("ChangeListener", " was called!");

        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        GridHolder holder = (GridHolder) view.getTag();
        StoragedSearchObject name = (StoragedSearchObject) holder.mNameTV.getTag();
        Intent intent = new Intent(StoragedSearches.this, SearchActivity.class);
        intent.putExtra("userText", name.getName());
        intent.putExtra("search", false);
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        /*if ( popupWindow.isShowing() ) {

            popupWindow.dismiss();
            Toast.makeText(StoragedSearches.this, "in onTouch", Toast.LENGTH_SHORT).show();
        }*/

        Toast.makeText(StoragedSearches.this, "out onTouch" + popupWindow.isShowing(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onClick(View v) {

        /*if ( popupWindow.isShowing() ) {

            popupWindow.dismiss();
            Toast.makeText(StoragedSearches.this, "in onClick", Toast.LENGTH_SHORT).show();
        }*/

        Toast.makeText(StoragedSearches.this, "out onClick" + popupWindow.isShowing(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
