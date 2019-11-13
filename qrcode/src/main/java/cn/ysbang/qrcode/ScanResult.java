package cn.ysbang.qrcode;

import android.graphics.PointF;


/**
 */
public class ScanResult {
    public String result;
    public PointF[] resultPoints;
    /**
     * EAN_13 ONLY_CODE_128为条形码
     * ONLY_QR_CODE为二维码
     */
    public BarcodeType barcodeType;

    public ScanResult(String result,String format) {
        if(format == null)
            return;
        this.result = result;
        if(format.equals("CODE_128")){
            barcodeType = BarcodeType.ONLY_CODE_128;
        }else if(format.equals("QR_CODE")){
            barcodeType = BarcodeType.ONLY_QR_CODE;
        }
        else if(format.equals("EAN_13")){
            barcodeType = BarcodeType.ONLY_EAN_13;
        }
    }

    public ScanResult(String result, PointF[] resultPoints) {
        this.result = result;
        this.resultPoints = resultPoints;
    }


}
