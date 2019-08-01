package com.he.water.entity;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DSHOrder implements Delayed {

    private String orderNo;
    private long startTime;

    public DSHOrder() {

    }

    /**
     * orderNo:订单交易号
     * timeout：自动收货的超时时间，秒
     */
    public DSHOrder(String orderNo, int timeout) {
        this.orderNo = orderNo;
        this.startTime = System.currentTimeMillis() + timeout * 1000L;
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this) {
            return 0;
        }
        if (other instanceof DSHOrder) {
            DSHOrder otherRequest = (DSHOrder) other;
            long otherStartTime = otherRequest.getStartTime();
            return (int) (this.startTime - otherStartTime);
        }
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + orderNo.hashCode();
        result = 31 * result + (int) startTime;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DSHOrder other = (DSHOrder) obj;
        if (orderNo != other.orderNo)
            return false;
        if (startTime != other.startTime)
            return false;
        return true;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "DSHOrder [orderNo=" + orderNo + ", startTime=" + startTime + "]";
    }
}