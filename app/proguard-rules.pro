# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#this method reaches the user who connected the app
-keep class * com.google.firebase.auth.FirebaseAuth{
public FirebaseUser getCurrentUser();
}


#the user fields so importent so we want to save them beacuse it will
#keep our app quicker to reload and get the products which are available
-keepclassmembers class com.example.shoppinglists.Products {
    private <fields>;
}
