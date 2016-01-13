package com.lzw.swiperefreshlayoutdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzw.swiperefreshlayoutdemo.view.WaveSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements WaveSwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @InjectView(R.id.WaveSwipeRefresh)
    WaveSwipeRefreshLayout mWaveSwipeRefresh;

    private Context context;
    private List<String> mData;
    private MyRecyclerViewAdapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.inject(this);

        mWaveSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mWaveSwipeRefresh.setOnRefreshListener(this);

        mData = new ArrayList<>();
        refresh();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new MyRecyclerViewAdapter();
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());

    }


    /*------------------------*/
    @Override
    public void onRefresh() {
        mWaveSwipeRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(context, "刷新", Toast.LENGTH_SHORT).show();
                mWaveSwipeRefresh.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onLoad() {
        mWaveSwipeRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadmore();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(context, "加载更多", Toast.LENGTH_SHORT).show();
                mWaveSwipeRefresh.setLoading(false);
            }
        }, 3000);
    }

    @Override
    public boolean canLoadMore() {
        return true;
    }

    @Override
    public boolean canRefresh() {
        return true;
    }

    /*------------------------*/


    private void refresh() {
        mData.clear();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            mData.add("refresh item "+random.nextInt(20));
        }
    }


    private void loadmore() {
        mData.clear();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            mData.add("loadmore item "+random.nextInt(20));
        }
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        private class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder)holder).textView.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }




















}
