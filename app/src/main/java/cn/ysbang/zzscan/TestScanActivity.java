package cn.ysbang.zzscan;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import cn.ysbang.qrcode.QRCodeView;
import cn.ysbang.qrcode.ScanResult;
import cn.ysbang.qrcode.ZXingView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author biao
 * 扫码基类页面
 */
public class TestScanActivity extends AppCompatActivity implements QRCodeView.Delegate, EasyPermissions.PermissionCallbacks {

    public static final String TAG = TestScanActivity.class.getSimpleName();

    public static final int SELECT_IMAGE_REQUEST_CODE = 0x0101;


    public ConstraintLayout cl_scan;

    public ZXingView mZXView;

    public ImageView iv_scan_choose_photo;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        set();
    }


    @Override
    public void onStart() {
        mZXView.startCamera();
        mZXView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mZXView.startSpotAndShowRect();
            }
        },500);
        super.onStart();
    }

    @Override
    public void onStop() {
        mZXView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mZXView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void set() {
        iv_scan_choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkWriteStoragePermission();
            }
        });
    }

    private void checkWriteStoragePermission() {
        //检查相机权限  android 6.0
        requestCodeQRCodePermissions();
    }

    /**
     * 打开相册
     */
    private void openSysAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE);
    }

    public void init() {
        setContentView(R.layout.scan_activity);
        cl_scan = findViewById(R.id.cl_scan);
        mZXView = findViewById(R.id.scan_bga_zxing);
        iv_scan_choose_photo = findViewById(R.id.iv_scan_choose_photo);
        mZXView.setDelegate(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        switch (requestCode) {
            case SELECT_IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    mZXView.post(new Runnable() {
                        @Override
                        public void run() {
                            mZXView.decodeQRCode(PathUtils.getFilePathByUri(TestScanActivity.this,data.getData()));
                        }
                    });
                    break;
                }
        }
    }

    public void  onScanQRCodeResult(ScanResult result){
        if(result != null){
            Toast.makeText(this,result.result,Toast.LENGTH_SHORT).show();
            Log.e(getClass().getSimpleName(),result.result + "  " + result.barcodeType.name());
        }
    };

    @Override
    public void onScanQRCodeSuccess(ScanResult result) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        onScanQRCodeResult(result);
    }
    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        if(isDark){
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        openSysAlbum();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .setRationale("此功能需要外置存储空间读写权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("是")
                    .setNegativeButton("否")
                    .setTitle("")
                    .build()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "读取图片需要打开外置存储空间读写权限", 1, perms);
        }else {
            openSysAlbum();
        }
    }
}
