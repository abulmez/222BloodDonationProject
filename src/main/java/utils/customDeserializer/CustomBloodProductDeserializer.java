package utils.customDeserializer;

import com.google.gson.*;
import model.BloodProduct;
import model.ProductType;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;

public class CustomBloodProductDeserializer implements JsonDeserializer<BloodProduct> {
    @Override
    public BloodProduct deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jobject = jsonElement.getAsJsonObject();

        String[] bday = jobject.get("validuntil").getAsString().split("T")[0].split("-");
        int[] bdayAsInt = Arrays.stream(bday).mapToInt(Integer::parseInt).toArray();

        return new BloodProduct(
                jobject.get("idbp").getAsInt(),
                jobject.get("idd").getAsInt(),
                ProductType.valueOf(jobject.get("producttype").getAsString()),
                LocalDate.of(bdayAsInt[0],bdayAsInt[1],bdayAsInt[2]),
                jobject.get("quantity").getAsDouble());
    }
}
