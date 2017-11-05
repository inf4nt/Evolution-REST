package evolution.serialization.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import evolution.model.User;
import evolution.model.UserAdditionalData;

import java.io.IOException;

/**
 * Created by Infant on 29.10.2017.
 */
public class CustomUserSerializerUserAdditionalData extends StdSerializer<UserAdditionalData> {

    public CustomUserSerializerUserAdditionalData() {
        this(null);
    }

    public CustomUserSerializerUserAdditionalData(Class<UserAdditionalData> t) {
        super(t);
    }

    @Override
    public void serialize(UserAdditionalData userAdditionalData, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (userAdditionalData != null && userAdditionalData.getUser() != null) {
            User u = new User(userAdditionalData.getUser().getId());
            userAdditionalData.setUser(u);
            generator.writeObject(userAdditionalData);
        }
    }
}
