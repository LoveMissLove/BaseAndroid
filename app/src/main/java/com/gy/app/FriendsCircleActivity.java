package com.gy.app;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltbase.base_activity.BaseActivity;
import com.example.ltbase.base_utils.TransfereeImageUtils;
import com.gy.app.bean.FriendsCircleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿朋友圈
 * <p>
 * Created by Vans Z on 2020/4/16.
 */
public class FriendsCircleActivity extends BaseActivity {
    private List<FriendsCircleBean>mList=new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        List<String> largeList = new ArrayList<>();
        largeList.add("https://ww4.sinaimg.cn/bmiddle/a716fd45ly1gf5nskmynvj20ku2q37wh.jpg");
        largeList.add("https://ww3.sinaimg.cn/bmiddle/a716fd45ly1gf5nskwbduj20ku2ao1kx.jpg");
        largeList.add("https://ww3.sinaimg.cn/bmiddle/a716fd45ly1gf5nsl2fvkj20ku3g3x6p.jpg");
        largeList.add("https://ww2.sinaimg.cn/bmiddle/a716fd45ly1gf5nsl75taj20ku2pyb29.jpg");
        largeList.add("https://ww3.sinaimg.cn/bmiddle/a716fd45ly1gf5nskqvnuj20ku2gn4qp.jpg");
        largeList.add("https://ww2.sinaimg.cn/bmiddle/a716fd45ly1gf5nsl3d0nj20ku2yt4qp.jpg");
        largeList.add("https://ww2.sinaimg.cn/bmiddle/a716fd45ly1gf5nslgftdj20ku2ay1kx.jpg");
        largeList.add("https://ww1.sinaimg.cn/bmiddle/a716fd45ly1gf5nslorclj20ku2igx6p.jpg");
        largeList.add("https://ww2.sinaimg.cn/bmiddle/a716fd45ly1gf5nslgqtsj20ku16eat4.jpg");
        for (int i = 0; i <10 ; i++) {
            FriendsCircleBean friendsCircleBean=new FriendsCircleBean();
            friendsCircleBean.setList(largeList);
            mList.add(friendsCircleBean);
        }
        TransfereeImageUtils transfereeImageUtils=new TransfereeImageUtils(this,transferee,config);
        RecyclerView rvImages = findViewById(R.id.rv_images);
        rvImages.setLayoutManager(new LinearLayoutManager(this));
        rvImages.setAdapter(new FriendsCircleAdapter(R.layout.item_friends_circle,mList,this,transfereeImageUtils));
    }

    @Override
    protected void initData() {

    }

}
