package com.jinax.hospital_management_backend.Controller;

import com.jinax.hospital_management_backend.Component.MyUserDetails;
import com.jinax.hospital_management_backend.Entity.Message;
import com.jinax.hospital_management_backend.Service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : chara
 */
@Api(tags = "MessageController")
@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ApiOperation("获取当前用户的消息")
    @ResponseBody
    @GetMapping("/")
    public List<Message> getMessageForCurrentUser(){
        MyUserDetails details = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return messageService.getMessageForUser(details.getId());
    }
}
