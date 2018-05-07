//package evolution.controller;
//
//import evolution.crud.api.UserCrudManagerService;
//import evolution.model.MessagePrivate;
//import evolution.model.User;
//import evolution.repository.MessagePrivateRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping(value = "/message_ev")
//@CrossOrigin
//public class MessagePrivateController {
//
//    @Autowired
//    private MessagePrivateRepository messagePrivateRepository;
//
//    @Autowired
//    private UserCrudManagerService userCrudManagerService;
//
//    @GetMapping(value = "/populate")
//    public ResponseEntity<List<MessagePrivate>> list() {
//        messagePrivateRepository.deleteAll();
//        List<MessagePrivate> list = new ArrayList<>();
//
//        for (int i = 1; i <= 10; i++) {
//            MessagePrivate m = new MessagePrivate();
//            m.setSender(userCrudManagerService.findOne(5L).get());
//            m.setRecipient(userCrudManagerService.findOne(6L).get());
//            m.setText("text_" + i);
//            m.setActive(true);
//            m.setRead(false);
//            m.setDatePost(new Date());
//            list.add(m);
//        }
//        messagePrivateRepository.save(list);
//        return ResponseEntity.ok(messagePrivateRepository.findAll());
//    }
//}
