package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.CommonModel;
import com.jude.emotionshow.data.model.SeedModel;
import com.jude.emotionshow.domain.entities.Notify;
import com.jude.utils.JUtils;

import java.util.List;

import rx.Subscriber;

/**
 * Created by ske on 2016/1/27.
 */
public class NotifyCommentPresenter extends Presenter<NotifyCommentActivity> {
    @Override
    protected void onCreate(NotifyCommentActivity view, Bundle savedState) {
        onRefresh();
    }

    public void onRefresh() {
        CommonModel.getInstance().getNotifyList(0, "2").subscribe(new Subscriber<List<Notify>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Notify> notifies) {
                getView().setNotifyList(notifies);
            }
        });
    }

    public void delComment(int id){
        SeedModel.getInstance().delComment(id)
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        JUtils.Toast("删除成功");
                    }
                });
    }
}
