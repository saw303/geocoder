package ch.silviowangler.addresses.geocoder.importer;

import ch.silviowangler.addresses.geocoder.domain.Location;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class StreetRecordStreamingParser {

    private final Consumer<Location> recordConsumer;
    private final InputStream inputStream;

    public StreetRecordStreamingParser(InputStream inputStream, Consumer<Location> recordConsumer) {

        this.inputStream = inputStream;
        this.recordConsumer = recordConsumer;
    }


    public void run() {

        try (JsonParser parser = new JsonFactory().createParser(this.inputStream)) {

            JsonToken jsonToken = parser.nextToken();

            Location streetRecord = null;

            while (jsonToken != null) {

                String currentName = parser.getCurrentName();

                if (jsonToken == JsonToken.START_OBJECT) {
                    streetRecord = new Location();
                }

                if (streetRecord != null && jsonToken == JsonToken.VALUE_STRING) {

                    final String value = parser.getValueAsString();
                    if ("street".equals(currentName)) {
                        streetRecord.setStreet(value);
                    } else if ("houseNumber".equals(currentName)) {
                        streetRecord.setHousenumber(value);
                    } else if ("postalCode".equals(currentName)) {
                        streetRecord.setZip(value);
                    } else if ("city".equals(currentName)) {
                        streetRecord.setCity(value);
                    } else if ("country".equals(currentName)) {
                        streetRecord.setCountry(value);
                    } else {
                        // skip
                    }
                }

                if (jsonToken == JsonToken.END_OBJECT && streetRecord != null) {
                    recordConsumer.accept(streetRecord);
                }
                jsonToken = parser.nextToken();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
