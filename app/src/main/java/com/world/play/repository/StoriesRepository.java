package com.world.play.repository;

import android.content.Context;

import com.world.play.models.StoryDetail;
import com.world.play.network.ApiCallInterface;

import java.util.List;

import io.reactivex.Observable;

public class StoriesRepository extends BaseRepository {
    private ApiCallInterface apiCallInterface;

    /**
     * Instantiates a new Base repository.
     *
     * @param context the context
     */
    public StoriesRepository(Context context, ApiCallInterface apiCallInterface) {
        super(context);
        this.apiCallInterface = apiCallInterface;
    }

    public Observable<List<String>> getTopStories() {
        return apiCallInterface.getTopStories();
    }

    public Observable<StoryDetail> getStoryDetail(String storyID) {
        return apiCallInterface.getStoryDetails(storyID);
    }
}
