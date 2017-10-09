//package evolution.data;
//
//import evolution.model.Dialog;
//import evolution.model.Message;
//import evolution.security.model.CustomSecurityUser;
//import evolution.service.SecuritySupportService;
//import evolution.service.TechnicalDataService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
///**
// * Created by Infant on 04.09.2017.
// */
//@Service
//public class DialogDataService {
//
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    private final DialogRepository dialogRepository;
//
//    private final TechnicalDataService technicalDataService;
//
//    private final MessageRepository messageRepository;
//
//    private final SecuritySupportService securitySupportService;
//
//    @Autowired
//    public DialogDataService(DialogRepository dialogRepository, TechnicalDataService technicalDataService, MessageRepository messageRepository, SecuritySupportService securitySupportService) {
//        this.dialogRepository = dialogRepository;
//        this.technicalDataService = technicalDataService;
//        this.messageRepository = messageRepository;
//        this.securitySupportService = securitySupportService;
//    }
//
//    @Transactional(readOnly = true)
//    public List<Dialog> findDialogByUser(Long userId) {
//        return dialogRepository.findDialogByUser(userId);
//    }
//
//    @Transactional(readOnly = true)
//    public List<Dialog> findAll() {
//        return dialogRepository.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    public List<Dialog> findAllAndRepair() {
//        List<Dialog> list = dialogRepository.findAll();
//        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
//        if (!list.isEmpty() && principal.isPresent())
//            return technicalDataService.repairDialogForDialogList(list, principal.get().getUser());
//        else
//            return list;
//    }
//
//    @Transactional
//    public void delete(Dialog dialog) {
//        dialogRepository.delete(dialog);
//    }
//
//    @Transactional
//    public void deleteMessageInDialog(Message message) {
//        Optional<Dialog> optional = findOne(message.getDialog().getId());
//
//        //todo: replace delete to deactivated
//        if (optional.isPresent()) {
//            Dialog dialog = optional.get();
//            if (dialog.getMessageList().isEmpty() || dialog.getMessageList().size() == 1) {
//                LOGGER.info("delete dialog " + dialog);
//                dialogRepository.delete(dialog);
//            } else {
//                dialog.getMessageList().remove(message);
//                dialogRepository.save(dialog);
//                LOGGER.info("delete message " + message);
//            }
//        }
//
//    }
//
//    @Transactional
//    public void delete(Long id) {
//        Optional<Dialog> optional = findOne(id);
//        optional.ifPresent(dialog -> {
//            dialog.getMessageList().size();
//            dialogRepository.delete(dialog);
//        });
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<Dialog> findOne(Long id) {
//        return Optional.ofNullable(dialogRepository.findOne(id));
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<Dialog> findOneAndRepair(Long id) {
//        Dialog dialog = dialogRepository.findOne(id);
//        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
//        if (dialog != null && principal.isPresent()) {
//            return Optional.of(technicalDataService.repairDialog(dialog, principal.get().getUser()));
//        } else
//            return Optional.empty();
//    }
//
//    // todo: fix
////    @Transactional
////    public Dialog save(String text, Long senderUserId, Long secondUserId) {
////
////        UserLight authUser = new UserLight(senderUserId);
////
////        Optional<Dialog> optional = this.findDialogByUsers(secondUserId, authUser.getId());
////        Message message = new Message();
////        Dialog dialog;
////
////        // dialog exist
////        // dialog not exist
////        dialog = optional.orElseGet(() -> new Dialog(new UserLight(senderUserId), new UserLight(secondUserId)));
////
////        message.setMessage(text);
////        message.setDialog(dialog);
////        message.setSender(authUser);
////
////        dialog.getMessageList().add(message);
////
////        return this.dialogRepository.save(dialog);
////    }
//
//    @Transactional(readOnly = true)
//    public Optional<Dialog> findDialogByUsersAndRepair(Long userid1, Long userid2) {
//        Dialog dialog = dialogRepository.findDialogByUsers(userid1, userid2);
//        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
//        if (dialog != null && principal.isPresent()) {
//            return Optional.of(technicalDataService.repairDialog(dialog, principal.get().getUser()));
//        } else
//            return Optional.empty();
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<Dialog> findDialogByUsers(Long userid1, Long userid2) {
//        Dialog dialog = dialogRepository.findDialogByUsers(userid1, userid2);
//        return Optional.ofNullable(dialog);
//    }
//}
