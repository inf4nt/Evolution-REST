package evolution.service.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import evolution.model.dialog.Dialog;
import evolution.model.message.Message;

import java.io.IOException;

/**
 * Created by Infant on 28.09.2017.
 */
public class CustomMessageSerializerDialog extends StdSerializer<Dialog> {

    public CustomMessageSerializerDialog() {
        this(null);
    }

    public CustomMessageSerializerDialog(Class<Dialog> t) {
        super(t);
    }

    @Override
    public void serialize(Dialog dialog, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (dialog != null) {
            dialog.setMessageList(null);
            generator.writeObject(dialog);
        }
    }


}
