package my.e.imageloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etInputLink;
    private ImageView ivDownloadedImage;
    private Button btnDownload, btnShow;

    private DownloadManager mDownloadManager;
    private List<Long> mDownloadList = new ArrayList<>();
    private String mDownloadFileName;
    private String mAppName;
    private final int WRITE_STORAGE = 101;
    private final int READ_STORAGE = 102;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppName = getApplicationInfo().loadLabel(getPackageManager()).toString();
        Log.d(TAG, mAppName);
        initViews();
        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private void initViews() {
        etInputLink = findViewById(R.id.et_link);
        ivDownloadedImage = findViewById(R.id.iv_load_image);
        btnDownload = findViewById(R.id.btn_download);
        btnShow = findViewById(R.id.btn_show);
        btnShow.setEnabled(false);
        btnDownload.setOnClickListener(this);
        btnShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                String url = etInputLink.getText().toString().trim();
                if (TextUtils.isEmpty(url)) {
                    etInputLink.setError("Please fill this field");
                    etInputLink.requestFocus();
                } else {
                    beginDownload(url);
                }
                break;
            case R.id.btn_show:
                readImageFile();
        }
    }

    private void beginDownload(String url) {

        // check permission
        if (!isPermissionFileWriteGranted()) {
            requestPermissionWriteFile(this);
            return;
        }

        // check url valid
        if (!isUrlValid(url)) {
            Toast.makeText(this, "This url is not valid", Toast.LENGTH_SHORT).show();
            return;
        }

        // check url extension
        String imageExtension = getFileExtensionFromUrl(url);
        if (!imageExtension.equalsIgnoreCase(".jpeg") &&
                !imageExtension.equalsIgnoreCase(".jpg") &&
                !imageExtension.equalsIgnoreCase(".png") &&
                !imageExtension.equalsIgnoreCase(".bmp")) {
            etInputLink.requestFocus();
            etInputLink.setError("Please provide right extension");
            Toast.makeText(this, "Please, provide url with jpeg, ipg, png or bmp extension",
                    Toast.LENGTH_SHORT).show();
        } else {
            // create download request
            String imageName = getFilenameFromUrl(url);
            Log.d(TAG, imageName);
            Uri imageUri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(imageUri);

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                    | DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);
            request.setTitle(getString(R.string.msg_download_title));
            request.setDescription(getString(R.string.msg_download_description));
            request.setVisibleInDownloadsUi(true);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            mDownloadFileName = mAppName + "/" + imageName + imageExtension;
            Log.d(TAG, mDownloadFileName);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                    "/" + mDownloadFileName);

            //get download service and enqueue
            long refId = mDownloadManager.enqueue(request);
            mDownloadList.add(refId);
        }
    }

    private boolean isUrlValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getFilenameFromUrl(String url) {
        String lastParcel = url.substring(url.lastIndexOf("/"));
        return lastParcel.substring(0, lastParcel.length() - lastParcel.lastIndexOf("."));
    }

    private String getFileExtensionFromUrl(String url) {
        return url.substring(url.lastIndexOf("."));
    }

    private void readImageFile() {
        if (!isPermissionFileReadGranted()) {
            requestPermissionReadFile(this);
            return;
        }

        if (mDownloadFileName == null) {
            Toast.makeText(this, R.string.msg_read_null, Toast.LENGTH_SHORT).show();
            return;
        }

        Picasso.get()
                .load(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        , mDownloadFileName))
                .into(ivDownloadedImage);
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long refId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            mDownloadList.remove(refId);
            if (mDownloadList.isEmpty()) {
                btnShow.setEnabled(true);
                Toast.makeText(context,
                        mDownloadFileName + " is downloaded", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }

    // ********* Permissions
    private boolean isPermissionFileReadGranted() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isPermissionFileWriteGranted() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissionReadFile(final AppCompatActivity activity) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setMessage(R.string.msg_file_read)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    READ_STORAGE);
                        }
                    }).create();
            dialog.show();
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE);
        }
    }

    public void requestPermissionWriteFile(final AppCompatActivity activity) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setMessage(R.string.msg_file_write)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    WRITE_STORAGE);
                        }
                    })
                    .create();
            dialog.show();
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length != 1) return;
        if (requestCode == READ_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readImageFile();
            } else
                new AlertDialog.Builder(this)
                        .setMessage(R.string.msg_deny)
                        .setPositiveButton("Ok", null)
                        .show();


        } else if (requestCode == WRITE_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                beginDownload(etInputLink.getText().toString());
            } else
                new AlertDialog.Builder(this)
                        .setMessage(R.string.msg_deny)
                        .setPositiveButton("Ok", null)
                        .show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}