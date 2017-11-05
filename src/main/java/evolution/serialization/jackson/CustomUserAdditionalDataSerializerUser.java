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
public class CustomUserAdditionalDataSerializerUser extends StdSerializer<User> {

    public CustomUserAdditionalDataSerializerUser() {
        this(null);
    }

    public CustomUserAdditionalDataSerializerUser(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (user != null) {
            user = new User(user.getId());
            generator.writeObject(user);
        }
    }
}
