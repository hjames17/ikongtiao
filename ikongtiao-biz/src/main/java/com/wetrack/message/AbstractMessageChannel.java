package com.wetrack.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhanghong on 16/3/1.
 */
public abstract class AbstractMessageChannel implements MessageChannel{
    private final static Logger logger = LoggerFactory.getLogger(AbstractMessageChannel.class);

    Map<Integer, MessageAdapter> adapterMap;
    BlockingQueue<MessageRaw> bufList;
//    List<MessageRaw> bufList;

    static class MessageRaw{
        int id;
        Map<String, Object> params;

        MessageRaw(int id , Map<String, Object> params){
            this.id = id;
            this.params = params;
        }
    }

    public AbstractMessageChannel(){
        adapterMap = new HashMap<Integer, MessageAdapter>();
//        bufList = new ArrayList<MessageRaw>();
        bufList = new LinkedBlockingQueue<MessageRaw>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                callSend();
            }
        });
        t.start();
    }

    public void registerAdapter(int messageId, MessageAdapter adapter){
        adapterMap.put(messageId, adapter);
    }

    public void sendMessage(int messageId, Map<String, Object> params){
        MessageRaw messageRaw = new MessageRaw(messageId, params);
        bufList.offer(messageRaw);
//        callSend();
    }

    private void callSend(){
        while(true) {
            try {
                MessageRaw messageRaw = bufList.take();
                if (adapterMap.get(messageRaw.id) != null) {
                    Message message = adapterMap.get(messageRaw.id).build(messageRaw.id, messageRaw.params);
                    if(message != null) {
                        try {
                            doSend(message);
                        }catch (Exception e){
                            logger.error("发送消息{}失败，messageChannel {}, 原因:",message.getId(),  this.getName(), e.getMessage());
                        }
                    }
                }
            } catch (InterruptedException e) {
                logger.error("message channel take message failed ! " + e.getMessage());
//            e.printStackTrace();
            } catch (Exception e){
                //抛弃任何异常
            }
        }
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        t.start();
//            Utils.get(ThreadExecutor.class).execute(new ThreadExecutor.Executor() {
//                @Override
//                public void execute() {
//                    doSend(adapterMap.get(messageRaw.id).build(messageRaw.id, messageRaw.params));
//                }
//            });
//        }
    }

    protected abstract void doSend(Message message);
}
