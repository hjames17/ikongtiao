package com.wetrack.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghong on 16/3/1.
 */
public abstract class AbstractMessageChannel implements MessageChannel{
    Map<Integer, MessageAdapter> adapterMap;
    List<MessageRaw> bufList;

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
        bufList = new ArrayList<MessageRaw>();
    }

    public void registerAdapter(int messageId, MessageAdapter adapter){
        adapterMap.put(messageId, adapter);
    }

    public void sendMessage(int messageId, Map<String, Object> params){
        MessageRaw messageRaw = new MessageRaw(messageId, params);
        bufList.add(messageRaw);
        callSend();
    }

    private void callSend(){
        while(bufList.size() > 0){
            MessageRaw messageRaw = bufList.remove(0);
            if(adapterMap.get(messageRaw.id) == null)
                continue;

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    doSend(adapterMap.get(messageRaw.id).build(messageRaw.id, messageRaw.params));
                }
            });
            t.start();
//            Utils.get(ThreadExecutor.class).execute(new ThreadExecutor.Executor() {
//                @Override
//                public void execute() {
//                    doSend(adapterMap.get(messageRaw.id).build(messageRaw.id, messageRaw.params));
//                }
//            });
        }
    }

    protected abstract void doSend(Message message);
}
