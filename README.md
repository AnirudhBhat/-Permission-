# ಅನುಮತಿ
ಅನುಮತಿ (Permission in Kannada language) is a simple lightweight library for managing runtime permissions in Android

# Setting up
Add Jitpack as one of the source to fetch dependency in your `build.gradle` file

```
maven { url 'https://jitpack.io' }
```

Add the dependency for Anumathi library in your app's `build.gradle` file
```
implementation 'com.github.AnirudhBhat:Anumathi:1.0'
```

# Usage
Add this line to register your Activity/Fragment for requesting permissions
```
private val registerPermission = registerPermissions()
```

## Request for permissions
Anumathi library is based on Kotlin coroutine. Below code is a `suspend` function and must be run inside a coroutine.
```
val result = anumathi(
                registerPermission,
                arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_MEDIA_LOCATION
                )
            )
```

## Handling the permission result
```
result.map { (permission, state) ->
                when (state) {
                    is PermissionResult.Denied -> {

                    }
                    PermissionResult.Granted -> {

                    }
                }
            }
```

## Here's combined code required to get started with the Anumathi library
```
private val registerPermission = registerPermissions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifeCycleScope.launch {
            val result = anumathi(
                registerPermission,
                arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_MEDIA_LOCATION
                )
            )
            result.map { (permission, state) ->
                when (state) {
                    is PermissionResult.Denied -> {

                    }
                    PermissionResult.Granted -> {

                    }
                }
            }
        }
    }
```
