package cn.edu.nju.gyue.controller;

import cn.edu.nju.gyue.models.MessageModel;
import cn.edu.nju.gyue.service.MessageService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;


    @PostMapping("/messageList")
    @ResponseBody
    public String getMessageList(Integer uid) {
        List<MessageModel> messageModelList = messageService.getMessageList(uid);
        String res = JSON.toJSONString(messageModelList);
        messageService.readMessageList(uid);
        return res;
    }
}
