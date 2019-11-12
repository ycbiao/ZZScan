package cn.ysbang.zxing;

import android.graphics.PointF;

import com.google.zxing.BarcodeFormat;

/**
 * 作者:王浩
 * 创建时间:2018/6/15
 * 描述:
 */
public class ScanResult {
    public String result;
    public PointF[] resultPoints;
    /**
     * EAN_13 ONLY_CODE_128为条形码
     * ONLY_QR_CODE为二维码
     */
    public BarcodeType barcodeType;

    public ScanResult(String result,BarcodeFormat format) {
        this.result = result;
        if(format == BarcodeFormat.CODE_128){
            barcodeType = BarcodeType.ONLY_CODE_128;
        }else if(format == BarcodeFormat.QR_CODE){
            barcodeType = BarcodeType.ONLY_QR_CODE;
        }
        else if(format == BarcodeFormat.EAN_13){
            barcodeType = BarcodeType.ONLY_EAN_13;
        }
    }

    public ScanResult(String result, PointF[] resultPoints) {
        this.result = result;
        this.resultPoints = resultPoints;
    }


}
