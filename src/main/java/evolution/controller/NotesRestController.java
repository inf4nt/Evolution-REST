//package evolution.controller;
//
//import evolution.model.Notes;
//import evolution.model.User;
//import evolution.repository.NotesRepository;
//import evolution.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping(value = "/notes")
//@CrossOrigin
//public class NotesRestController {
//
//    private final NotesRepository notesRepository;
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public NotesRestController(NotesRepository notesRepository,
//                               UserRepository userRepository) {
//        this.notesRepository = notesRepository;
//        this.userRepository = userRepository;
//    }
//
//    @PostConstruct
//    public void postConstruct() {
//        notesRepository.deleteAll();
//        List<Notes> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Notes n = new Notes();
//            n.setActive(true);
//            n.setDatePost(new Date());
//            User sender = userRepository.findOne(6L);
//            n.setSender(sender);
//            n.setText("notes_number_" + i);
//            list.add(n);
//        }
//        notesRepository.save(list);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Notes>> findAll() {
//        return ResponseEntity.ok(notesRepository.findAll());
//    }
//}
