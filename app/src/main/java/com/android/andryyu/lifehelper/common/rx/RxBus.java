package com.android.andryyu.lifehelper.common.rx;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by yufei on 2017/3/13.
 */

public class RxBus {

    private static volatile RxBus instance;

    private Subject<Object> bus;

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public RxBus() {
        bus = PublishSubject.create().toSerialized();
    }

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    /**
     * 根据传递的eventtype类型返回特定类型（eventype）的被观察者
     */
    public <T> Observable<T> tObservable(Class<T> EventType) {
        return bus.ofType(EventType);
    }


    /**
     * 判断是否有订阅者
     */
    public boolean hasSubscribers() {
        return bus.hasObservers();
    }

    /**
     * 注销
     */
    public void unRegisterAll() {
        bus.onComplete();
    }
}
