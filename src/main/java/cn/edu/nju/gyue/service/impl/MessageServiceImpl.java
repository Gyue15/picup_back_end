package cn.edu.nju.gyue.service.impl;

import cn.edu.nju.gyue.entities.Message;
import cn.edu.nju.gyue.entities.User;
import cn.edu.nju.gyue.models.MessageModel;
import cn.edu.nju.gyue.repositories.MessageRepository;
import cn.edu.nju.gyue.service.MessageService;
import cn.edu.nju.gyue.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<MessageModel> getMessageList(Integer uid) {
        List<Message> messageList = messageRepository.findByOwner(uid);
        List<MessageModel> messageModelList = new ArrayList<>();

        for (Message message : messageList) {
            MessageModel messageModel = new MessageModel();
            messageModel.avatar = message.avatar;
            messageModel.date = message.date;
            messageModel.formatDate = dateFormat.format(message.date);
            messageModel.isRead = message.isRead;
            messageModel.uid = message.uid;
            messageModel.text = message.text;
            messageModel.owner = message.owner;
            messageModel.mid = message.mid;
            messageModelList.add(messageModel);
        }
        return messageModelList;
    }

    @Override
    public ResultMessage saveMessage(Integer owner, String text, User user) {
        if (user == null || user.uid == null) {
            return ResultMessage.FAILURE;
        }
        messageRepository.save(toMessage(owner, text, user));
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage saveMessageList(List<Integer> ownerList, String text, User user) {
        if (user == null || user.uid == null) {
            return ResultMessage.FAILURE;
        }
        List<Message> messageList = new ArrayList<>();
        for (Integer owner : ownerList) {
            messageList.add(toMessage(owner, text, user));
        }
        messageRepository.save(messageList);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage readMessageList(Integer uid) {
        List<Message> messageList = messageRepository.findByOwner(uid);
        return readMessageList(messageList);
    }

    @Override
    public ResultMessage readMessageList(List<Message> originalMessage) {
        List<Message> toSave = new ArrayList<>();
        for (Message message : originalMessage) {
            toSave.add(message);
        }
        messageRepository.save(toSave);
        return ResultMessage.SUCCESS;
    }

    private Message toMessage(Integer owner, String text, User user) {
        Message message = new Message();
        message.avatar = user.avatar;
        message.date = new Date();
        message.isRead = false;
        message.owner = owner;
        message.text = text;
        message.uid = user.uid;
        message.username = user.username;
        return message;
    }
}
