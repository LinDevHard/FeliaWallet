package com.gexabyte.android.wallet.core.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.gexabyte.android.wallet.core.Wallet
import com.google.crypto.tink.Aead
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

class WalletSerializer(private val aead: Aead) : Serializer<Wallet> {
    override val defaultValue: Wallet = Wallet.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Wallet {
        return try {
            val encryptedInput = input.readBytes()

            val decryptedInput = if (encryptedInput.isNotEmpty()) {

                aead.decrypt(encryptedInput, null)
            } else {
                encryptedInput
            }

            Wallet.parseFrom(decryptedInput)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Error deserializing proto", exception)
        }
    }

    override suspend fun writeTo(
        t: Wallet,
        output: OutputStream
    ) = run {
        val encryptedBytes = aead.encrypt(t.toByteArray(), null)
        output.write(encryptedBytes)
    }
}
