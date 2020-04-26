package org.r.server.websocket.camera.handle;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.r.server.websocket.camera.handle.status.StatusEventContext;
import org.r.server.websocket.utils.SpringUtil;

/**
 * date 20-4-9 下午4:26
 *
 * @author casper
 **/
@Slf4j
@ToString
public class StatusEventHandler implements Runnable {


    private StatusEventContext context;
    private EventChain<StatusEventContext> chain;


    public StatusEventHandler(long IUser, long nStateCode, String pResponse) {
        context = new StatusEventContext(IUser, nStateCode, pResponse);
//        if (SpringUtil.getApplicationContext() != null) {
//            chain = (EventChain<StatusEventContext>) SpringUtil.getApplicationContext().getBean("statusEventChain");
//        }
        chain = SpringUtil.getBean("statusEventChain");

    }

    @Override
    public void run() {
//        System.out.println("*****************event call back[************************");
//        System.out.println(context.toString());
//        System.out.println("*****************]event call back************************");
        if (chain == null) {
            return;
        }
        chain.doChain(context);
    }

}
