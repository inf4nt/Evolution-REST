package evolution.service.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import evolution.model.message.Message;
import java.io.IOException;
import java.util.List;

/**
 * Created by Infant on 27.09.2017.
 */

public class CustomDialogSerializerMessageList extends StdSerializer<List<Message>> {

    protected CustomDialogSerializerMessageList() {
        this(null);
    }

    protected CustomDialogSerializerMessageList(Class<List<Message>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Message> value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        if (value != null && !value.isEmpty()) {
            value.forEach(message -> {
                if (message.getDialog() != null) {
                    message.getDialog().setFirst(null);
                    message.getDialog().setSecond(null);
                    message.getDialog().setMessageList(null);
                }
            });
            jsonGenerator.writeObject(value);
        }
    }
}
