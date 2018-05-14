package utils;

import com.google.gson.*;
import model.Donor;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;

public class CustomDonorDeserializer implements JsonDeserializer<Donor> {
    @Override
    public Donor deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jobject = jsonElement.getAsJsonObject();

        String[] bday = jobject.get("birthday").getAsString().split("-");
        int[] bdayAsInt = Arrays.stream(bday).mapToInt(Integer::parseInt).toArray();

        return new Donor(
                jobject.get("idu").getAsInt(),
                jobject.get("cnp").getAsString(),
                jobject.get("name").getAsString(),
                LocalDate.of( bdayAsInt[0],bdayAsInt[1],bdayAsInt[2]),
                jobject.get("mail").getAsString(),
                jobject.get("phone").getAsString(),
                jobject.get("bloodgroup").getAsString(),
                jobject.get("weight").getAsFloat());
    }
}
