 [ ![Download](https://api.bintray.com/packages/yshahak/OverlyaButtonLibrary/OverlyaButtonLibrary/images/download.svg) ](https://bintray.com/yshahak/OverlyaButtonLibrary/OverlyaButtonLibrary/_latestVersion)
# OverlyaButtonLibrary
Esaily show a Button or other View on top of any other app, even when your app is in the background.

#Usage
* __In order to use this library you muse target your project to API 22 or below:__

```gradle
defaultConfig {
        ...
        ...
        targetSdkVersion 22
        versionCode 1
        versionName "1.0"
    }
```

* Add the dependency from jCenter to your app's (not project) build.gradle file:

```gradle
repositories {
    jcenter()
}
```


```gradle
dependencies {
    compile 'com.thedroidboy.www.overlaybuttonlibrary:overlaybuttonlibrary:1.0.0'
 }
 ```


* Extends `OverlayButtonService` and make sure you override `onClick` method:


```java
public class MyService extends OverlayButtonService {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onClick(View view) {
        //here you can choose what to do when user click on the button, for example:
        Toast.makeText(this, "button clicked!", Toast.LENGTH_SHORT).show();
    }
}
```

* Start your service whenever you want. You can supply your own gravity and layout file for the button by adding extras to the intent:

```java

        Intent intent = new Intent(this, MyService.class);
        intent.putExtra(OverlayButtonService.EXTRA_GRAVITY, Gravity.BOTTOM | Gravity.LEFT);
        intent.putExtra(OverlayButtonService.EXTRA_LAYOUT_ID, R.layout.my_button);
        startService(intent);
```

Or you can simply start the `Service` like that:

```java
    startService(new Intent(this, MyService.class));
```


ScreenShot:


![Screenshot](https://cloud.githubusercontent.com/assets/6691908/21393349/aac5efa8-c79c-11e6-9e08-d879f9b96d06.jpg)
