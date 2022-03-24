## Wallet Core 

The main module for implementing a de-fi wallet. Uses Trust HDWallet as a multi-currency wallet provider. The main task of the module for hiding the storage of the wallet, and asynchronous abstraction. 

### Features
- Generating mnemonic phrase
- Import HDWallet from mnemonic
- Store one wallet mnemonic in proto file
- Obtaining private keys
- Obtaining addresses

### How to use

1. Because HDWallet is JNI lib, first init HDWallet lib in your application class, like this
```kotlin
class App : Application() {
    init {
        System.loadLibrary("TrustWalletCore")
    }
}
```
2. This lib uses Koin as Dependency Injection, for koin projects just import koin module `walletCoreModule` 
3. For using `HDWalletRepository`, you need implement `MemoryStorage` interface in your project, and paste loaded mnemonic phrase into. After provide MemoryStorage object via Koin module
```kotlin
object LocalMemoryStorage: MemoryStorage {
    override var mnemonic: String = ""
}
```

### Module Dependency 
- Koin, for Dependency Injection
- Tink, for Crypto
- ProtoDataStore, for saving wallets
- WalletCore, for interaction with HDWallet

### See more 
1. [Official Trust Wallet Documentation for HDWallet](https://developer.trustwallet.com/wallet-core/)