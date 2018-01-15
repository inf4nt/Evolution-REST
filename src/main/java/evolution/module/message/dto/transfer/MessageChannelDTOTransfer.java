package evolution.message.dto.transfer;


import evolution.dto.TransferDTO;
import evolution.message.dto.MessageChannelDTO;
import evolution.message.model.MessageChannel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageChannelDTOTransfer implements TransferDTO<MessageChannelDTO, MessageChannel> {

    private final ModelMapper modelMapper;

    @Autowired
    public MessageChannelDTOTransfer(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageChannelDTO modelToDTO(MessageChannel messageChannel) {
        return modelMapper.map(messageChannel, MessageChannelDTO.class);
    }
}
