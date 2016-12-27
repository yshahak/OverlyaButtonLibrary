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

* Use the `OverlayButton.Builder` class. You can call it from any Service or BroadcastReciever or any other component in your application.


````java
new OverlayButton.Builder(this)
                .setLayoutId(R.layout.my_button) //optionally define your own layout for the Button
                .setGravity(Gravity.BOTTOM | Gravity.END) //optionally define your desired Gravitiy
                .setClickListener(new View.OnClickListener() {//define desired action when user click on Button
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "button clicked!", Toast.LENGTH_SHORT).show();
                    }
                }) 
                .build()
                .show();
````




ScreenShot:


![Screenshot](https://cloud.githubusercontent.com/assets/6691908/21393349/aac5efa8-c79c-11e6-9e08-d879f9b96d06.jpg)
