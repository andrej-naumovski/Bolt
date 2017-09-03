package mk.edu.ukim.feit.bolt.api.controllers;

import mk.edu.ukim.feit.bolt.api.models.ChatGroup;
import mk.edu.ukim.feit.bolt.api.models.GroupMessage;
import mk.edu.ukim.feit.bolt.api.models.Message;
import mk.edu.ukim.feit.bolt.api.models.User;
import mk.edu.ukim.feit.bolt.api.services.ChatGroupService;
import mk.edu.ukim.feit.bolt.api.services.MessageService;
import mk.edu.ukim.feit.bolt.api.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Created by andrejnaumovski on 8/12/17.
 */

@Controller
public class MessageController {
    private UserService userService;
    private MessageService messageService;
    private ChatGroupService chatGroupService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    public MessageController(
            UserService userService,
            MessageService messageService,
            ChatGroupService chatGroupService
    ) {
        if(userService == null) {
            throw new IllegalArgumentException(UserService.class.getName() + " cannot be null.");
        }
        if(messageService == null) {
            throw new IllegalArgumentException(MessageService.class.getName() + " cannot be null.");
        }
        if(chatGroupService == null) {
            throw new IllegalArgumentException(ChatGroupService.class.getName() + " cannot be null.");
        }
        this.userService = userService;
        this.messageService = messageService;
        this.chatGroupService = chatGroupService;
    }

    @MessageMapping(value = "/{sender}/{receiver}")
    @SendTo("/chat/private/{receiver}/{sender}")
    public Message message(
            @DestinationVariable String sender,
            @DestinationVariable String receiver,
            String messageText
    ) {
        logger.info(String.format("User %s sends %s to user %s.", sender, messageText, receiver));
        Message message = new Message();
        message.setMessage(messageText);
        User senderUser = userService.findByUsername(sender);
        User receiverUser = userService.findByUsername(receiver);
        message.setTimestamp(new Date());
        if(sender.equals(receiver)) {
            message.setSenderUser(senderUser);
            message.setReceiverUser(senderUser);
            senderUser.getSentMessages().add(message);
            senderUser.getReceivedMessages().add(message);
            userService.saveUser(senderUser);
            logger.debug(" " + messageService.findAll().size());
            return message;
        }
        message.setSenderUser(senderUser);
        message.setReceiverUser(receiverUser);
        senderUser.getSentMessages().add(message);
        receiverUser.getReceivedMessages().add(message);
        userService.saveUser(senderUser);
        //userService.saveUser(receiverUser);
        logger.debug(" " + messageService.findAll().size());
        return message;
    }

    @MessageMapping("/group/{groupName}/{sender}")
    @SendTo("/chat/group/{groupName}")
    public GroupMessage groupMessage(
            @DestinationVariable String groupName,
            @DestinationVariable String sender,
            String messageText
    ) {
        logger.info(String.format("User %s sends %s to group %s.", sender, messageText, groupName));
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setMessage(messageText);
        groupMessage.setTimestamp(new Date());
        User user = userService.findByUsername(sender);
        ChatGroup group = chatGroupService.findByName(groupName);
        groupMessage.setGroup(group);
        groupMessage.setSenderUser(user);
        userService.saveUser(user);
        try {
            chatGroupService.save(group);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupMessage;
    }
}
