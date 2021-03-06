package com.jude.emotionshow.presentation.seed;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.CategoryDetail;
import com.jude.emotionshow.domain.entities.Seed;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/22.
 * “暗恋”，“热恋”····和“查看更多”什么的点进去的Activity。
 */
@RequiresPresenter(CategoryPresenter.class)
public class CategoryActivity extends BeamDataActivity<CategoryPresenter, CategoryDetail> {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.background)
    ImageView background;
    @Bind(R.id.seed_count)
    TextView seedCount;
    @Bind(R.id.container_seed)
    LinearLayout containerSeed;
    @Bind(R.id.visit_count)
    TextView visitCount;
    @Bind(R.id.container_visit)
    LinearLayout containerVisit;
    @Bind(R.id.praise_count)
    TextView praiseCount;
    @Bind(R.id.container_praise)
    LinearLayout containerPraise;
    private SeedAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        /**
         * 这里加载头部Header
         * @see <a href="https://github.com/blipinsk/RecyclerViewHeader"/>
         */
        //上下拉刷新控件
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        //设置刷新时动画的颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.black);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            getPresenter().refresh();
        });
        RecyclerViewHeader header = RecyclerViewHeader.fromXml(this, R.layout.head_category);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        header.attachTo(recycler, false);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        recycler.setAdapter(adapter = new SeedAdapter(this));
        adapter.setMore(new View(this), () -> {
            getPresenter().loadMore();
        });
    }

    //填充Header
    @Override
    public void setData(CategoryDetail data) {
        title.setText(data.getName());
        Picasso.with(this).load(ImageModel.getLargeImage(data.getBackground())).into(background);
        seedCount.setText(data.getSeedCount() + "");
        visitCount.setText(data.getVisitCount() + "");
        praiseCount.setText(data.getPraiseCount() + "");
    }

    public void clearSeed(){
        mSwipeRefreshLayout.setRefreshing(false);
        adapter.clear();
    }

    public void addSeed(List<Seed> data) {
        adapter.addAll(data);
    }
}
