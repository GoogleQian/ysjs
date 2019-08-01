package com.he.water.service;

import com.he.water.entity.DSHOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.DelayQueue;

/**
 * 延时队列服务
 *
 * @author hzh
 */
@Service
public class DelayService {

    private static final Logger log = LoggerFactory.getLogger(DelayService.class);


    private boolean start;
    private OnDelayedListener listener;
    private DelayQueue<DSHOrder> delayQueue = new DelayQueue<DSHOrder>();

    public static interface OnDelayedListener {
        public void onDelayedArrived(DSHOrder order);
    }

    public void start(OnDelayedListener listener) {
        if (start) {
            return;
        }
        log.error("DelayService 启动");
        start = true;
        this.listener = listener;
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        DSHOrder order = delayQueue.take();
                        log.info("得到delayQueue.take()+++++++"+order.toString());
                        if (DelayService.this.listener != null) {
                            log.info("返回onDelayedArrived中的  order");
                            DelayService.this.listener.onDelayedArrived(order);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void add(DSHOrder order) {
        delayQueue.put(order);
    }

    public boolean remove(DSHOrder order) {
        return delayQueue.remove(order);
    }

    public void add(String orderNo) {
        delayQueue.put(new DSHOrder(orderNo, 60));
    }

    public void remove(String orderNo) {
        DSHOrder[] array = delayQueue.toArray(new DSHOrder[]{});
        if (array == null || array.length <= 0) {
            return;
        }
        DSHOrder target = null;
        for (DSHOrder order : array) {
            if (order.getOrderNo().equals(orderNo)) {
                target = order;
                break;
            }
        }
        if (target != null) {
            delayQueue.remove(target);
        }
    }
}