package evolution.service.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import evolution.model.User;

import java.io.IOException;

/**
 * Created by Infant on 29.10.2017.
 */
public class CustomUserSerializerUserAdditionalData extends StdSerializer<User> {

    public CustomUserSerializerUserAdditionalData() {
        this(null);
    }

    public CustomUserSerializerUserAdditionalData(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (user != null && user.getUserAdditionalData() != null) {
            User u = new User(user.getUserAdditionalData().getUser().getId());
            user.getUserAdditionalData().setUser(u);
        }
    }
}
