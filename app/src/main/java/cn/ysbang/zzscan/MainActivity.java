package cn.ysbang.zzscan;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_enter_scan = findViewById(R.id.tv_enter_scan);
        tv_enter_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] perms = {Manifest.permission.CAMERA};
                if(EasyPermissions.hasPermissions(view.getContext(),perms)){
                    Intent i = new Intent(view.getContext(),ScanActivity.class);
                    startActivity(i);
                }else {
                    EasyPermissions.requestPermissions(MainActivity.this, "扫描二维码需要打开相机的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }



    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Intent i = new Intent(this,ScanActivity.class);
        startActivity(i);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .setRationale("此功能需要相机的权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("是")
                    .setNegativeButton("否")
                    .setTitle("")
                    .build()
                    .show();
        }
    }

    private void requestCodeQRCodePermissions() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
