# Wallet Assets

This module contains crypto assets and small info about assets included name, type, decimal and more.

### Features
- Storing static assets in [coins.json](https://gitlab.com/quybit/techculture/euphoria/euphoria-android-app/-/tree/blob/develop/wallet/assets/src/main/assets/coins.json)
- Caching assets in database 
- Versioning assets 

### TODO
- Write config installer (Read more in Lucid)
- Load assets from external storage-network (proposal)
- Remove HDWallet dependency

### How to use

1. Because HDWallet is JNI lib, first init HDWallet lib in your application class, like this
```kotlin
class App : Application() {
    init {
        System.loadLibrary("TrustWalletCore")
    }
}
```
2. This lib uses Koin as Dependency Injection, for koin projects just import koin module `assetsModule` 
3. You ready to use `AssetsRepository`, just inject it from di


### How to add new coins
Currently assets stored in [coins.json](https://gitlab.com/quybit/techculture/euphoria/euphoria-android-app/-/tree/blob/develop/wallet/assets/src/main/assets/coins.json). For add new cryptocurrency find info about coin(check Trust Wallet Assets) and insert in json. <b>Don't  forgot about bump `version` field!</b>

### Dependency
- Koin
- HDWallet (for coin type value)
- Room

### See more
1. [TrustWallet assets (GitHub)](https://github.com/trustwallet/assets)
2. [Diagram and schemes of module (Lucid)](https://lucid.app/lucidchart/bf2a83df-86ca-49c6-936a-12b23b0199b3/edit?invitationId=inv_5d8cbb55-a95c-42e1-91a0-386f89c0ab65&page=fhqMVXAS0xwC#)