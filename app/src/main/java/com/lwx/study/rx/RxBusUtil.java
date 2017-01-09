package com.lwx.study.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * description ：
 * project name：Network
 * author : Vincent
 * creation date: 2017/1/9 11:13
 *
 * @version 1.0
 */

public class RxBusUtil {

    public static volatile RxBusUtil instance;
    public final Subject bus;

    public RxBusUtil() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBusUtil getInstance() {
        if (instance == null) {
            synchronized (RxBusUtil.class) {
                if (instance == null) {
                    instance = new RxBusUtil();
                }
            }
        }
        return instance;
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    //对于本需求来说，不通过这个来toObservable方法来拿Observable对象也可以，可以直接就使用bus对象使用。
    public <T> Observable<T> toObservable (Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
