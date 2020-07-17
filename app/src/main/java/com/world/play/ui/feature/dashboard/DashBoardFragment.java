package com.world.play.ui.feature.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.world.play.R;
import com.world.play.di.ManagerComponent;
import com.world.play.models.StoryDetail;
import com.world.play.ui.adapter.DashBoardAdapter;
import com.world.play.ui.feature.base.BaseViewModel;
import com.world.play.ui.feature.base.BaseViewModelFragment;
import com.world.play.ui.feature.news_detail.NewsDetailsActivity;
import com.world.play.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DashBoardFragment extends BaseViewModelFragment<DashBoardView> implements DashBoardView, DashBoardAdapter.Callback {

    public static final String TAG = "DashBoardFragment";

    @BindView(R.id.view_switcher)
    ViewSwitcher viewSwitcher;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_title)
    TextView textTitle;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    DashBoardViewModel viewModel;

    private Unbinder unbinder;
    private ArrayList<String> topStories;
    private ArrayList<String> fetchStoryDetails;
    private ArrayList<StoryDetail> storyDetails;
    private DashBoardAdapter dashBoardAdapter;

    private boolean isLoading = true;
    private LinearLayoutManager linearLayoutManager;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;

    private int currentIndex, maxStories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_layout, container, false);
    }

    @NonNull
    @Override
    public BaseViewModel<DashBoardView> initializeViewModel(@NonNull ManagerComponent managerComponent) {
        managerComponent.inject(this);
        ViewModel viewModel1 = ViewModelProviders.of(this, viewModelFactory).get(DashBoardViewModel.class);
        viewModel = (DashBoardViewModel) viewModel1;
        return viewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        unbinder = ButterKnife.bind(this, view);

        storyDetails = new ArrayList<>();
        topStories = new ArrayList<>();
        fetchStoryDetails = new ArrayList<>();

        textTitle.setText(R.string.text_dashboard);

        initAdapter();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getTopStories();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (isLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            isLoading = false;
                            fetchStoryDetailSubArray();
                            fetchNextStoryDetails();
                        }
                    }
                }
            }
        });

        viewModel.getTopStories();
    }

    @OnClick(R.id.img_back)
    public void onBackSelect() {
        getActivity().onBackPressed();
    }

    private void initAdapter() {
        dashBoardAdapter = new DashBoardAdapter(getContext(), storyDetails);
        dashBoardAdapter.setCallback(this);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(dashBoardAdapter);
    }

    @Override
    public void onFetchTopStoriesIds(List<String> topStories) {
        this.topStories.clear();
        this.topStories.addAll(topStories);
        this.storyDetails.clear();
        dashBoardAdapter.notifyDataSetChanged();
        maxStories = this.topStories.size();
        fetchStoryDetailSubArray();
        viewModel.getStoryDetail(fetchStoryDetails.get(currentIndex));
    }

    private void fetchStoryDetailSubArray() {
        int listEndIndex, startIndex = storyDetails.size() == 0 ? 0 : storyDetails.size() - 1;
        int diffChecker = maxStories - startIndex;
        if (diffChecker >= AppConstants.MAX_ITEM_SHOW_COUNT) {
            listEndIndex = startIndex + AppConstants.MAX_ITEM_SHOW_COUNT;
        } else {
            listEndIndex = maxStories - 1;
        }
        fetchStoryDetails.clear();
        fetchStoryDetails.addAll(topStories.subList(startIndex, listEndIndex));
    }

    @Override
    public void showStoryDetail(StoryDetail storyDetail) {
        storyDetails.add(storyDetail);
        dashBoardAdapter.notifyItemChanged(storyDetails.size() - 1);
    }

    @Override
    public void fetchNextStoryDetails() {
        if (AppConstants.MAX_ITEM_SHOW_COUNT - 1 > currentIndex && maxStories > storyDetails.size()) {
            viewModel.getStoryDetail(fetchStoryDetails.get(currentIndex));
            currentIndex++;
        } else {
            currentIndex = 0;
            isLoading = true;
        }
    }

    @Override
    public void onViewLoading(boolean state) {
        viewSwitcher.setDisplayedChild(state ? 0 : 1);
    }

    @Override
    public void showError(String error) {
        showMessage(error);
    }

    @Override
    public void showLoading(boolean state) {
        viewSwitcher.setDisplayedChild(state ? 0 : 1);
        if (!state) swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showFailure() {
        showMessage(R.string.error_something_went_wrong);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPositionSelect(int position) {
        startActivity(new Intent(getContext(), NewsDetailsActivity.class).putExtra(AppConstants.STORY_DETAILS, storyDetails.get(position)));
    }
}
