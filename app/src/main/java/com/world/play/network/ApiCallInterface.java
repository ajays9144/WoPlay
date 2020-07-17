package com.world.play.network;

import com.world.play.models.StoryDetail;
import com.world.play.utils.AppConstants;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCallInterface {

    @GET(AppConstants.U_TOP_STORIES)
    Observable<List<String>> getTopStories();

    @GET(AppConstants.U_DETAILS_STORY + "{story_id}" + AppConstants.URL_ExTENSION)
    Observable<StoryDetail> getStoryDetails(@Path("story_id") String StoryId);
}
