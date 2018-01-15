package evolution.module.message.dto.transfer;

import evolution.dto.TransferDTOLazy;
import evolution.module.message.dto.ChannelDTO;
import evolution.module.message.dto.ChannelDTOLazy;
import evolution.module.message.model.Channel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelDTOTransfer implements TransferDTOLazy<ChannelDTO, ChannelDTOLazy, Channel> {

    private final ModelMapper modelMapper;

    @Autowired
    public ChannelDTOTransfer(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ChannelDTO modelToDTO(Channel channel) {
        return modelMapper.map(channel, ChannelDTO.class);
    }

    @Override
    public ChannelDTOLazy modelToDTOLazy(Channel channel) {
        return modelMapper.map(channel, ChannelDTOLazy.class);
    }
}
