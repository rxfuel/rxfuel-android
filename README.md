# RxFuel - Android
<img src="https://raw.githubusercontent.com/rxfuel/rxfuel.github.io/master/assets/images/logo_colored.png" width="100" height="100" />

A lightweight framework for Android to create fully reactive apps. 
For documentation and additional information see the https://rxfuel.github.io

## Installation

Add the following dependency in your module build.gradle :

```gradle
implementation 'com.rxfuel.rxfuel:rxfuel-android:0.0.8'
```

Add `jcenter` repository in your project build.gradle : 

```gradle
allprojects {
    repositories {
        jcenter()
    }
}
```

## Documentation

Link : [https://rxfuel.github.io/docs](https://rxfuel.github.io/docs)

## Architecture

> Data flow in an activity is uni-direction using a single Observable stream. Making it clean, easy to test and more reliable. UI elements are highly decoupled with business logic.

![alt text](https://raw.githubusercontent.com/rxfuel/rxfuel.github.io/master/assets/images/architechure.png)

## Project Status

RxFuel is still in alpha stage and not recommended for production.
