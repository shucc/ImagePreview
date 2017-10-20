# ImagePreview

多图预览,共享元素的使用参考自[SETransitionDemo](https://github.com/mingdroid/SETransitionDemo)

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
    compile "com.android.support:appcompat-v7:latest.release.version"
    compile "com.github.chrisbanes:PhotoView:latest.release.version"
    compile ('com.github.shucc:ImagePreview:v1.2') {
        exclude group: 'com.android.support', module: 'appcompat-v7'
        exclude group: 'com.github.chrisbanes', module: 'PhotoView'
    }
}
```

## 使用

初始化图片加载,推荐放在Application中:
```java
ImageLoader.init(new ImageLoaderListener() {
    @Override
    public void load(Context context, PhotoView photoView, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(photoView);
    }
});
```
打开预览界面:
```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    Pair<View, String> pair = Pair.create(view, view.getTransitionName());
    ImagePreViewActivity.launch(MainActivity.this, position, (ArrayList) data, pair);
    enterPosition = position;
    setSharedElementCallback();
} else {
    ImagePreViewActivity.launch(MainActivity.this, position, (ArrayList) data);
}
```