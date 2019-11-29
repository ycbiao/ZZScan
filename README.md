### 相机使用zxing识别二维码,本地相册图片使用zxing结合zbar识别二维码

[参考项目BGAQRCode](https://github.com/bingoogolapple/BGAQRCode-Android)

扫码框识别使用zxing

本地相册选取图片识别二维码首先使用zxing，失败则使用zbar识别，提高对不同二维码的识别率

## 1.0.2版本

仅支持一维码en13、code128、二维码qr码识别

zbar仅支持'armeabi-v7a', 'arm64-v8a'手机系统，不支持ipad

### gradle使用方法
```java
implementation 'com.ysb:zzscan:1.0.2'
```

### 使用方法demo
```java
<cn.ysbang.zxing.ZXingView
        android:id="@+id/scan_bga_zxing"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:qrcv_animTime="1000"
        app:qrcv_borderColor="@color/_ffffff"
        app:qrcv_borderSize="0dp"
        app:qrcv_cornerColor="@color/_ffffff"
        app:qrcv_cornerLength="20dp"
        app:qrcv_cornerSize="1dp"
        app:qrcv_customScanLineDrawable="@drawable/img_scan_line"
        app:qrcv_rectWidth="250dp"
        app:qrcv_scanLineColor="@color/_fd5c02"
        app:qrcv_scanLineSize="1px"
        app:qrcv_topOffset="190dp"
        app:qrcv_isScanLineReverse="true"
        app:qrcv_isOnlyDecodeScanBoxArea="false"
        app:qrcv_isAutoZoom="true"
        app:qrcv_isShowLocationPoint ="true"
        app:qrcv_maskColor ="@color/_00ffffff"/>
```

### 使用api
```java
#    qrcv_topOffset	扫描框距离 toolbar 底部的距离	90dp
#    qrcv_cornerSize	扫描框边角线的宽度	3dp
#    qrcv_cornerLength	扫描框边角线的长度	20dp
#    qrcv_cornerColor	扫描框边角线的颜色	@android:color/white
#    qrcv_cornerDisplayType	扫描框边角线显示位置(相对于边框)，默认值为中间	center
#    qrcv_rectWidth	扫描框的宽度	200dp
#    qrcv_barcodeRectHeight	条码扫样式描框的高度	140dp
#    qrcv_maskColor	除去扫描框，其余部分阴影颜色	#33FFFFFF
#    qrcv_scanLineSize	扫描线的宽度	1dp
#    qrcv_scanLineColor	扫描线的颜色「扫描线和默认的扫描线图片的颜色」	@android:color/white
#    qrcv_scanLineMargin	扫描线距离上下或者左右边框的间距	0dp
#    qrcv_isShowDefaultScanLineDrawable	是否显示默认的图片扫描线「设置该属性后 qrcv_scanLineSize 将失效，可以通过 qrcv_scanLineColor 设置扫描线的颜色，避免让你公司的UI单独给你出特定颜色的扫描线图片」	false
#    qrcv_customScanLineDrawable	扫描线的图片资源「默认的扫描线图片样式不能满足你的需求时使用，设置该属性后 qrcv_isShowDefaultScanLineDrawable、qrcv_scanLineSize、qrcv_scanLineColor 将失效」	null
#    qrcv_borderSize	扫描边框的宽度	1dp
#    qrcv_borderColor	扫描边框的颜色	@android:color/white
#    qrcv_animTime	扫描线从顶部移动到底部的动画时间「单位为毫秒」	1000
#    qrcv_isCenterVertical（已废弃，如果要垂直居中用 qrcv_verticalBias="0.5"来代替）	扫描框是否垂直居中，该属性为true时会忽略 qrcv_topOffset 属性	false
#    qrcv_verticalBias	扫描框中心点在屏幕垂直方向的比例，当设置此值时，会忽略 qrcv_topOffset 属性	-1
#    qrcv_toolbarHeight	Toolbar 的高度，通过该属性来修正由 Toolbar 导致扫描框在垂直方向上的偏差	0dp
#    qrcv_isBarcode	扫描框的样式是否为扫条形码样式	false
#    qrcv_tipText	提示文案	null
#    qrcv_tipTextSize	提示文案字体大小	14sp
#    qrcv_tipTextColor	提示文案颜色	@android:color/white
#    qrcv_isTipTextBelowRect	提示文案是否在扫描框的底部	false
#    qrcv_tipTextMargin	提示文案与扫描框之间的间距	20dp
#    qrcv_isShowTipTextAsSingleLine	是否把提示文案作为单行显示	false
#    qrcv_isShowTipBackground	是否显示提示文案的背景	false
#    qrcv_tipBackgroundColor	提示文案的背景色	#22000000
#    qrcv_isScanLineReverse	扫描线是否来回移动	true
#    qrcv_isShowDefaultGridScanLineDrawable	是否显示默认的网格图片扫描线	false
#    qrcv_customGridScanLineDrawable	扫描线的网格图片资源	nulll
#    qrcv_isOnlyDecodeScanBoxArea	是否只识别扫描框中的码	false
#    qrcv_isShowLocationPoint	是否显示定位点	false
#    qrcv_isAutoZoom	码太小时是否自动缩放	false
```
