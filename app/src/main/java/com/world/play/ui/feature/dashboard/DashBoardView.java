package com.world.play.ui.feature.dashboard;

import com.world.play.models.StoryDetail;
import com.world.play.ui.feature.base.Contract;

import java.util.List;

public interface DashBoardView extends Contract.View {

    void onFetchTopStoriesIds(List<String> topStories);

    void showStoryDetail(StoryDetail storyDetail);

    void fetchNextStoryDetails();

    void onViewLoading(boolean state);
}
