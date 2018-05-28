package utils.customDeserializer;

import com.google.gson.*;
import model.BloodProduct;
import model.DTO.DonationCenterDTO;
import model.ProductType;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;

//public class CustomCentersDTODeserializer implements JsonDeserializer<DonationCenterDTO>{
//
////        @Override
////        public DonationCenterDTO deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
////            JsonObject jobject = jsonElement.getAsJsonObject();
////
////            String[] bday = jobject.get("validuntil").getAsString().split("T")[0].split("-");
////            int[] bdayAsInt = Arrays.stream(bday).mapToInt(Integer::parseInt).toArray();
////
////            return new DonationCenterDTO(
////                    jobject.get("centername").getAsString(),
////                    jobject.get("phonenumber").getAsString(),
////                    jobject.get("adress").getAsString(),
////                    ProductType.valueOf(jobject.get("producttype").getAsString()),
////                    LocalDate.of(bdayAsInt[0],bdayAsInt[1],bdayAsInt[2]),
////                    jobject.get("quantity").getAsDouble());
////        }
//
//}
