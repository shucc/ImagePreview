# ImagePreview

多图预览,共享元素的使用参考自[SETransitionDemo](https://github.com/mingdroid/SETransitionDemo),ImagePreviewLoad的配置以及ImagePreviewBuilder控制跳转参考自[ZoomPreviewPicture](https://github.com/yangchaojiang/ZoomPreviewPicture)

## 第三方包
[PhotoView](https://github.com/chrisbanes/PhotoView)

## 配置

在项目的build.gradle中,添加:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
在使用库的module中添加,为避免重复引用appcompat-v7包,推荐使用exclude:
```groovy
dependencies {
    implementation "com.android.support:appcompat-v7:latest.release.version"
    implementation "com.github.chrisbanes:PhotoView:latest.release.version"
    implementation ('com.github.shucc:ImagePreview:v1.3') {
        exclude group: 'com.android.support', module: 'appcompat-v7'
        exclude group: 'com.github.chrisbanes', module: 'PhotoView'
    }
}
```

## 使用
初始化图片加载配置,这里Demo使用的是Glide
```java
ImagePreviewLoad.setImagePreviewLoadListener(new ImagePreviewLoadListener() {
    @Override
    public void load(Fragment fragment, String imageUrl, final ImagePreviewLoadTarget imagePreviewLoadTarget) {
        Glide.with(fragment)
                .load(imageUrl)
                .into(new SimpleTarget<GlideDrawable>() {

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        imagePreviewLoadTarget.onLoadFailure();
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imagePreviewLoadTarget.getImageView().setImageDrawable(resource.getCurrent());
                        imagePreviewLoadTarget.onLoadSuccess();
                    }
                });
    }
});
```
打开预览界面:
```java
ImagePreviewBuilder.from(TestOneActivity.this)
        .setInitPosition(position)
        .setImageUrlArray(data)
        .setPairView(view)
        .setImagePreviewExitListener(new ImagePreviewExitListener() {
            @Override
            public View exitView(int exitPosition) {
                //根据关闭预览界面时的position计算缩小的view，一般使用tag标示计算
                return rvImage.findViewWithTag(exitPosition);
            }
        })
        .startActivity();
```

## Demo
![](https://raw.githubusercontent.com/shucc/ImagePreview/master/demo/demo.gif)