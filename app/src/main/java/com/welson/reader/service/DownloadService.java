package com.welson.reader.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.welson.reader.entity.BookMixAToc;
import com.welson.reader.entity.ChapterRead;
import com.welson.reader.eventbus.BookDownloadMessage;
import com.welson.reader.eventbus.DownloadEvent;
import com.welson.reader.manager.CacheManager;
import com.welson.reader.retrofit.RetrofitHelper;
import com.welson.reader.util.BookContentUtil;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DownloadService extends Service {

    private CompositeDisposable compositeDisposable;
    private EventBus eventBus;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private BookMixAToc bookMixAToc;

    public DownloadService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        eventBus = EventBus.getDefault();
        eventBus.register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeDisposable();
        if(eventBus.isRegistered(this))
            eventBus.unregister(this);
    }


    private void download(String url, final String bookId, final int chapter){
        RetrofitHelper.getInstance().getChapterRead(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChapterRead>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ChapterRead chapterRead) {
                        CacheManager.getInstance().saveChapterFile(bookId,chapter, BookContentUtil
                                .getSaveString(chapterRead.getChapter().getTitle()
                                        ,chapterRead.getChapter().getBody()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        BookDownloadMessage message = new BookDownloadMessage();
                        message.setBookId(bookId);
                        message.setMessage("fail");
                        message.setComplete(false);
                        eventBus.post(message);
                    }

                    @Override
                    public void onComplete() {
                        BookDownloadMessage message = new BookDownloadMessage();
                        message.setBookId(bookId);
                        message.setMessage("success");
                        message.setComplete(true);
                        eventBus.post(message);
                    }
                });
    }

    private void getChapter(String bookId, final List<Integer> mChapters){
        RetrofitHelper.getInstance().getBookMixAToc(bookId,"chapters")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookMixAToc>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(final BookMixAToc mBookMixAToc) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                for (Integer chapter : mChapters){
                                    if (mBookMixAToc.isOk()){
                                        Log.d("dingyl","mBookMixAToc : " + mBookMixAToc.getMixToc().getBook());
                                        download(mBookMixAToc.getMixToc().getChapters().get(chapter).getLink(),mBookMixAToc
                                                .getMixToc().getBook(),chapter);
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addDisposable(Disposable disposable){
        if(compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    private void removeDisposable(){
        if (compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadEvent(DownloadEvent message){
        getChapter(message.getBookId(), message.getChapters());
    }

}
