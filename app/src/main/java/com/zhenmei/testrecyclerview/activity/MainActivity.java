package com.zhenmei.testrecyclerview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zhenmei.testrecyclerview.R;
import com.zhenmei.testrecyclerview.adapter.MyAdapter;
import com.zhenmei.testrecyclerview.bean.News;
import com.zhenmei.testrecyclerview.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 练习使用RecyclerView
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    private void initView() {
        /**
         * 初始化RecyclerView
         */
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        /**
         * 初始化LinearLayoutManager，设置竖直样式，仿ListView
         */
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager1);
        /**
         * 初始化分割线，设置竖直
         */
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(divider);
        /**
         * 设置默认动画效果
         */
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        /**
         * 创建模拟数据
         */
        List<News> list = new ArrayList<News>();
        for (int i = 0; i < 30; i++) {
            News n = new News(R.mipmap.ic_launcher, "biaoti" + i, "neirong" + i);
            list.add(n);
        }
        /**
         * 设置适配器
         */
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);

        /**
         * item的事件监听
         */
        adapter.setOnItemClickLinster(new MyAdapter.OnItemClickLinster() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemLongClickLinster(new MyAdapter.OnItemLongClickLinster() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "长按:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                adapter.addItem(new News(R.mipmap.ic_launcher, "biaoti", "neirong"), 0);
                break;
            case R.id.action_remove:
                adapter.removeItem(0);
                break;
            case R.id.action_shu_listview:
                LinearLayoutManager manager1 = new LinearLayoutManager(this);
                manager1.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager1);
                break;
            case R.id.action_heng_listview:
                LinearLayoutManager manager2 = new LinearLayoutManager(this);
                manager2.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager2);
                break;
            case R.id.action_shu_gridview:
                /**
                 * 初始化GridLayoutManager，仿GridView
                 * 第二个参数是一行几个item
                 */
                GridLayoutManager manager3 = new GridLayoutManager(this, 3);
                recyclerView.setLayoutManager(manager3);
                break;
            case R.id.action_heng_girdview:
                /**
                 * 第一个参数是一行几个item
                 * 第二个参数是方向
                 */
                StaggeredGridLayoutManager manager4 = new
                        StaggeredGridLayoutManager(5,
                        StaggeredGridLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager4);
                break;
            case R.id.action_pubuliu:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
