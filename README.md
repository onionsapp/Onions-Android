# Onions for Android

#### Setting Up

You will need:

* Android Studio
* [Android Gears](https://github.com/androidgears/plugin)
* A Parse Account, as well as a sample app

To build, you will need to create a `ParseConstants.java` file that looks like this:

```java
package com.bennyguitar.onions_android;

public class ParseConstants {
    public static String parseAppId() {
        return "YOUR_SAMPLE_APP_ID_HERE";
    }

    public static String parseClientKey() {
        return "YOUR_PARSE_CLIENT_KEY_HERE";
    }
}
```

Make sure that it is in the same directory as every other class in that package.

#### More Coming Soon...