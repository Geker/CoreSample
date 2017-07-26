package org.corejava.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class IntroTest {

    private static final Logger logger = LoggerFactory.getLogger(IntroTest.class);
    protected static final String TAG = "TAG";
    @Test
    public void t1_ObserverSubscribe() {
        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {


            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(200);
                e.onComplete();

            }
        });

        Observer<Integer> integerSubscriber = new Observer<Integer>() {
            @Override
            public void onComplete() {
                logger.debug("Complete!");
            }

            @Override
            public void onError(Throwable e) {
                logger.error("error", e);
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("onNext: " + value);
            }

            @Override
            public void onSubscribe(Disposable d) {
                // d.dispose();
                logger.debug("on onSubscribe");
            }


        };
        integerObservable.subscribe(integerSubscriber);
    }

    @Test
    public void t2_RxJavaWithThread() throws Exception {

        // 简单的来说, subscribeOn() 指定的是上游发送事件的线程, observeOn() 指定的是下游接收事件的线程.

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(101);
                logger.debug("onNext :" + 101);

                e.onNext(992);
                logger.debug("onNext :" + 992);

                e.onComplete();

            }
        }).observeOn(Schedulers.newThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer t) throws Exception {
                logger.debug("acctpt :" + t);

            }
        });

        System.in.read();

    }

    @Test
    public void t3_flatMap() throws Exception {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }

                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                logger.debug(s);
            }
        });
        System.in.read();
    }

    @Test
    public void t4_flowable() throws Exception {
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR); // 增加了一个参数

        Subscriber<Integer> downstream = new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                s.request(Long.MAX_VALUE); // 注意这句代码
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        upstream.subscribe(downstream);

    }

    static class Log
    {
        public static void d(String t, String s) {
            logger.debug(t + "-" + s);
        }


        public static void w(String t, String s, Throwable e) {
            logger.warn(t + "-" + s, e);
        }
    }

}
