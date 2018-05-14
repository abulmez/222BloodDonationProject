package utils;

import com.google.gson.*;
import model.Medic;
import model.TCP;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;

public class CustomTCPDeserializer implements JsonDeserializer<TCP> {
    @Override
    public TCP deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jobject = jsonElement.getAsJsonObject();

        String[] bday = jobject.get("birthday").getAsString().split("-");
        int[] bdayAsInt = Arrays.stream(bday).mapToInt(Integer::parseInt).toArray();

        return new TCP(
                jobject.get("idu").getAsInt(),
                jobject.get("cnp").getAsString(),
                jobject.get("name").getAsString(),
                LocalDate.of( bdayAsInt[0],bdayAsInt[1],bdayAsInt[2]),
                jobject.get("mail").getAsString(),
                jobject.get("phone").getAsString(),
                jobject.get("iddc").getAsInt()
        );
    }
}