package br.com.fiap.paymentapi.annotations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CardNumberMaskSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null && value.length() >= 4) {
            // Aplica a máscara, mostrando apenas os últimos 4 dígitos
            String maskedNumber = "**** **** **** " + value.substring(value.length() - 4);
            gen.writeString(maskedNumber);
        } else {
            gen.writeString(value);
        }
    }
}

