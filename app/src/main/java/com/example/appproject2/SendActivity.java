package com.example.appproject2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.view.View;

public class SendActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    private FileUriCallback fileUriCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)) {
            // Disable NFC features here
        } else {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            fileUriCallback = new FileUriCallback();
            nfcAdapter.setBeamPushUrisCallback(fileUriCallback, this);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFile(null);
            }
        });
    }

    // Request code for selecting a PDF document.
    private static final int PICK_PDF_FILE = 2;

    private void openFile(Uri pickerInitialUri) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, PICK_PDF_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                // Perform operations on the document using its URI.

                Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                //int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();

                Snackbar.make(findViewById(R.id.fab), "Added " + returnCursor.getString(nameIndex), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                fileUris[0] = uri;
            }
        }
    }

    private Uri[] fileUris = new Uri[10];
    private class FileUriCallback implements
            NfcAdapter.CreateBeamUrisCallback {
        public FileUriCallback() {

        }
        @Override
        public Uri[] createBeamUris(NfcEvent event) {
            return fileUris;
        }
    }


}