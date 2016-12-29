package com.userticketingsystem;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.checkin)
    TextView checkin;
    @BindView(R.id.checkout)
    TextView checkout;
    @BindView(R.id.fare)
    TextView fare;
    @BindView(R.id.wallet)
    TextView wallet;


    private LinearLayout mContainer;

    private ProgressDialog progressDialog;

    private static String[] mPermissions = {Manifest.permission.ACCESS_FINE_LOCATION};
    private MyApp.OnEnterExitListener onEnterExitListener;
    private boolean isEntered;

    @Override
    protected void onResume() {
        super.onResume();
        MyApp.getInstance().onEnterExitListener = onEnterExitListener;
        MyApp.getInstance().context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (!havePermissions()) {
            Log.i(TAG, "Requesting permissions needed for this app.");
            requestPermissions();
        }

        if (!isBlueEnable()) {
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(bluetoothIntent);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        mContainer = (LinearLayout) findViewById(R.id.activity_main);

        onEnterExitListener = new MyApp.OnEnterExitListener() {
            @Override
            public void onEnter() {
                enterRegion();
            }

            @Override
            public void onExit() {
                exitRegion();
            }
        };

        List<String> items = new ArrayList<>(MyApp.getInstance().regionNameList);
    }

    private boolean isBlueEnable() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        return bluetoothAdapter.isEnabled();

    }


    private boolean havePermissions() {
        for (String permission : mPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                mPermissions, PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != PERMISSIONS_REQUEST_CODE) {
            return;
        }
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    Log.i(TAG, "Permission denied without 'NEVER ASK AGAIN': " + permission);
                } else {
                    Log.i(TAG, "Permission denied with 'NEVER ASK AGAIN': " + permission);
                }
            } else {
                Log.i(TAG, "Permission granted, building GoogleApiClient");
            }
        }
    }

    private void enterRegion() {
        isEntered = true;
        checkin.setText("Checkin: Loc 1");
        NetworkDataManager<ApiResponse> manager = new NetworkDataManager<>();
        NetworkDataManager.NetworkResponseListener listener = manager.new NetworkResponseListener() {
            @Override
            public void onSuccessResponse(ApiResponse response) {
                Log.i("TAG", "Enter Update Success for beacon: ");

            }

            @Override
            public void onFailure(int code, String message) {
                Log.i("TAG", "Enter Update Fail for beacon: ");
            }
        };
        Call<ApiResponse> call = ApiClient.getClient().create(BusService.class).checkIn(UserUtil.getId(), 50f, true);
        manager.execute(call, listener);
    }

    private void exitRegion() {
        if(!isEntered){
            return;
        }
        checkout.setText("Checkout: Loc 2");
        fare.setText("Fare: 20");
        wallet.setText("Wallet: 30");
        isEntered = false;
        NetworkDataManager<ApiResponse> manager = new NetworkDataManager<>();
        NetworkDataManager.NetworkResponseListener listener = manager.new NetworkResponseListener() {
            @Override
            public void onSuccessResponse(ApiResponse response) {
                Log.i("TAG", "Exit Update Success for beacon: ");
            }

            @Override
            public void onFailure(int code, String message) {
                Log.i("TAG", "Exit Update Fail for beacon: ");
            }
        };
        Call<ApiResponse> call = ApiClient.getClient().create(BusService.class).checkIn(UserUtil.getId(), 30f, false);
        manager.execute(call, listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApp.getInstance().onEnterExitListener = null;
        MyApp.getInstance().context = null;
    }
}
