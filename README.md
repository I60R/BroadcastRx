
# Subscribe to Android broadcasts

[![](https://jitpack.io/v/I60R/BroadcastRx.svg)](https://jitpack.io/#I60R/BroadcastRx)

--------
--------

```java
        BroadcastRx.init(anyContext);

        BroadcastRx
                .local("my.app.ACTION")
                .mergeWith(BroadcastRx.global(Intent.ACTION_CONFIGURATION_CHANGED))
                    
                    ...
                    
                .subscribe(broadcast -> {
                    Log.d("received", broadcast.action);
                    Log.d("hasExtra", String.valueOf(broadcast.extras != null));
                        
                        ...
                        
                }); 
```

--------
--------

```xml
        <receiver android:name="i60r.broadcastrx.BroadcastRx">
            <intent-filter>
                <action android:name="android.intent.action.ACTION_POWER_CONNECTED" />
            </intent-filter>
        </receiver>
```

```java
        BroadcastRx
                .local(Intent.ACTION_POWER_CONNECTED)
                .subscribe(broadcast -> {
                    
                    ...
                    
                });
```

--------
--------

```java
        BroadcastRx
                .hook(upstream -> upstream
                        .doOnNext(broadcast -> Log.d("( ( ( (", broadcast.action)));
```