package com.clique.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.clique.R;
import com.clique.adapter.InviteAdapter;
import com.clique.utils.CustomTextView;
import com.clique.utils.EasyPermissions;

public class InviteActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    private ImageView ivBack;
    private static final int REQUEST_EXTERNAL_STORAGE = 105;
    private String[] permission = {Manifest.permission_group.CONTACTS};
    private CustomTextView tvInvite;
    private LinearLayout tvError;
    private RecyclerView rvInvite;
    private InviteAdapter inviteAdapter;
    private ArrayList<String> alInvite = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FullScreencall();
        setContentView(R.layout.activity_invite);
        EasyPermissions.requestPermissions(InviteActivity.this, InviteActivity.this, "Phone no sync", REQUEST_EXTERNAL_STORAGE, permission);
        tvError = (LinearLayout) findViewById(R.id.ll_error);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        rvInvite = (RecyclerView) findViewById(R.id.rv_invitedfriend);
        tvError.setVisibility(View.GONE);
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        alInvite.add("");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InviteActivity.this);
        rvInvite.setLayoutManager(linearLayoutManager);
        inviteAdapter = new InviteAdapter(alInvite);
        rvInvite.setAdapter(inviteAdapter);

        tvInvite = (CustomTextView) findViewById(R.id.tv_invite);
        ivBack.setOnClickListener(this);
        tvInvite.setOnClickListener(this);

       // tvError.setVisibility(View.VISIBLE);
        //rvInvite.setVisibility(View.GONE);
       /* View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == View.VISIBLE) {
                    if (Build.VERSION.SDK_INT < 19) {
                        View v = InviteActivity.this.getWindow().getDecorView();
                        v.setSystemUiVisibility(View.GONE);
                    } else {
                        getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                        View.SYSTEM_UI_FLAG_IMMERSIVE
                        );

                    }
                } else {
                }
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        //FullScreencall();
    }

  /*  public void FullScreencall() {
        if (Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for higher api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions =
                    View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }*/


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View view) {
        if (view == ivBack) {
            finish();
        } else if (view == tvInvite) {
            PackageManager pm = getPackageManager();
            try {

                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                String text = "Download this fabulous girl’s hygiene app," + getString(R.string.app_name) + "! You will love it.\n http://play.google.com/store/apps/details?id=" + getPackageName();

                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                //Check if package exists or not. If not then code
                //in catch block will be called
                waIntent.setPackage("com.whatsapp");

                waIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(waIntent, "Share with"));

            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        new SaveImageTask().execute();
//        ArrayList<HashMap<String, String>> alconatct = getConatctNo();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsPermanentlyDeclined(int requestCode, List<String> perms) {

    }

    private ArrayList<HashMap<String, String>> getConatctNo() {
        ArrayList<HashMap<String, String>> alContacts = new ArrayList<HashMap<String, String>>();
        ContentResolver cr = getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        HashMap<String, String> map = new HashMap<>();
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String name = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        map.put("name", name);
                        map.put("phoneno", contactNumber);
                        alContacts.add(map);
                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext());
        }
        return alContacts;
    }

    public class SaveImageTask extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {
        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Void... voids) {
            return getConatctNo();
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> hashMaps) {
            super.onPostExecute(hashMaps);
        }
    }
}
