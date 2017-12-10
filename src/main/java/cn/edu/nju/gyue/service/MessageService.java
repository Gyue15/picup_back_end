package cn.edu.nju.gyue.service;

import cn.edu.nju.gyue.entities.Message;
import cn.edu.nju.gyue.entities.User;
import cn.edu.nju.gyue.models.MessageModel;
import cn.edu.nju.gyue.util.ResultMessage;

import java.util.List;

public interface MessageService {

    List<MessageModel> getMessageList(Integer uid);

    ResultMessage saveMessage(Integer owner, String text, User user);

    ResultMessage saveMessageList(List<Integer> ownerList, String text, User user);

    ResultMessage readMessageList(Integer uid);

    ResultMessage readMessageList(List<Message> originalMessage);
}
