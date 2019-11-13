package cn.ysbang.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import cn.bingoogolapple.qrcode.zbar.ZBarDecode;
import cn.ysbang.zxing.QRCodeDecoder;

public class ZXingView extends QRCodeView {


    public ZXingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZXingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setupReader() {

    }

    @Override
    protected ScanResult processBitmapData(Bitmap bitmap) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        // 1.将bitmap的RGB数据转化成YUV420sp数据
        byte[] bmpYUVBytes = Bmp2YUV.getBitmapYUVBytes(bitmap);
        Map map = QRCodeDecoder.syncDecodeQRCode(bmpYUVBytes,bitmapWidth,bitmapHeight,0,0,bitmapWidth,bitmapHeight,false);

        if(map == null){
            map = ZBarDecode.syncDecodeQRCode(bitmap);
        }
        bitmap.recycle();
        if(map != null){
            return new ScanResult(map.get("text").toString(),map.get("BarcodeFormat").toString());
        }else {
            return null;
        }
    }

    @Override
    protected ScanResult processData(byte[] data, int width, int height, boolean isRetry) {
        Rect scanBoxAreaRect = null;
        ScanResult scanResult;
        Map<String ,Object> result = null;

        try {
            scanBoxAreaRect = mScanBoxView.getScanBoxAreaRect(height);
            if (scanBoxAreaRect != null) {
                result = QRCodeDecoder.syncDecodeQRCode(data, width, height, scanBoxAreaRect.left, scanBoxAreaRect.top, scanBoxAreaRect.width(),
                        scanBoxAreaRect.height(), false);
            } else {
                result = QRCodeDecoder.syncDecodeQRCode(data, width, height, 0, 0, width, height, false);
            }

            String text = result.get("text").toString();
            String BarcodeFormat = result.get("BarcodeFormat").toString();
            ArrayList<PointF> points = (ArrayList<PointF>) result.get("resultPoints");
            scanResult = new ScanResult(text , BarcodeFormat);
            QRCodeUtil.d("格式为：" + BarcodeFormat);

            // 处理自动缩放和定位点
            boolean isNeedAutoZoom = isNeedAutoZoom(BarcodeFormat);
            if (isShowLocationPoint() || isNeedAutoZoom) {
                final PointF[] pointArr = new PointF[points.size()];
                int pointIndex = 0;
                for (PointF resultPoint : points) {
                    pointArr[pointIndex] = new PointF(resultPoint.x, resultPoint.y);
                    pointIndex++;
                }

                if (transformToViewCoordinates(pointArr, scanBoxAreaRect, isNeedAutoZoom,scanResult)) {
                    return null;
                }
            }
            return scanResult;
        } catch (Exception e) {
            QRCodeUtil.d(e.toString());
            return null;
        }
    }

    private boolean isNeedAutoZoom(String barcodeFormat) {
        return isAutoZoom() && barcodeFormat.equals( BarcodeType.ONLY_QR_CODE.name());
    }


    /**
     * 使用zbar解码,备用
     * @param bmpYUVBytes
     * @param bmpWidth
     * @param bmpHeight
     * @return
     */
    private static String decodeYUVByZbar(byte[] bmpYUVBytes, int bmpWidth, int bmpHeight) {
        String zbarResult = "";
        // Both dimensions must be greater than 0
        if (null != bmpYUVBytes && bmpWidth > 0 && bmpHeight > 0) {
//            ZBarDecoder decoder = new ZBarDecoder();
//            zbarResult = decoder.decodeRaw(bmpYUVBytes, bmpWidth, bmpHeight);
        }
        Log.e("HtscCodeScanningUtil", "decode by zbar, result = " + zbarResult);
        return zbarResult;
    }
}