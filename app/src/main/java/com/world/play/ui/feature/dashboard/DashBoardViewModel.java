package com.world.play.ui.feature.dashboard;

import com.world.play.models.StoryDetail;
import com.world.play.repository.StoriesRepository;
import com.world.play.ui.feature.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DashBoardViewModel extends BaseViewModel<DashBoardView> {

    private StoriesRepository storiesRepository;

    @Inject
    public DashBoardViewModel(StoriesRepository storiesRepository) {
        this.storiesRepository = storiesRepository;
    }

    public void getTopStories() {
        getView().showLoading(true);
        getCompositeDisposable().add(storiesRepository.getTopStories().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> topStories) {
                        if (getView() != null) {
                            getView().showLoading(false);
                            getView().onFetchTopStoriesIds(topStories);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().showLoading(false);
                            getView().showError(e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void getStoryDetail(String storyID) {
        getCompositeDisposable().add(storiesRepository.getStoryDetail(storyID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<StoryDetail>() {
                    @Override
                    public void onNext(StoryDetail storyDetail) {
                        if (getView() != null) {
                            getView().showStoryDetail(storyDetail);
                            getView().fetchNextStoryDetails();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
