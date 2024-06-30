package com.rodrigolmti.lunch.money.companion.composition.data.model.serializers

import com.rodrigolmti.lunch.money.companion.composition.data.model.response.TransactionMetadataResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

internal object TransactionMetadataResponseSerializer : KSerializer<TransactionMetadataResponse> {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("TransactionMetadataResponse", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: TransactionMetadataResponse) {
        val json = json.encodeToString(TransactionMetadataResponse.serializer(), value)
        encoder.encodeString(json)
    }

    override fun deserialize(decoder: Decoder): TransactionMetadataResponse {
        val jsonString = decoder.decodeString()
        return json.decodeFromString(TransactionMetadataResponse.serializer(), jsonString)
    }
}