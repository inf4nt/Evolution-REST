//package evolution.controller;
//
//import evolution.data.MessageDataService;
//import evolution.dto.model.MessageDTO;
//import evolution.model.Message;
//import evolution.dto.transfer.TransferDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * Created by Infant on 07.11.2017.
// */
//@RestController
//@RequestMapping(value = "/test")
//@CrossOrigin
//public class TestMessageController {
//
//    private final MessageDataService messageDataService;
//
//    private final TransferDTO transferDTO;
//
//    @Autowired
//    public TestMessageController(MessageDataService messageDataService, TransferDTO transferDTO) {
//        this.messageDataService = messageDataService;
//        this.transferDTO = transferDTO;
//    }
//
//    @GetMapping
//    public List<MessageDTO> findAll() {
//        List<Message> list = messageDataService.findAll();
//
//        List<MessageDTO> messageDTOS = transferDTO.modelToDTOListMessage(list);
//
//        return messageDTOS;
//
//    }
//
//
//}
