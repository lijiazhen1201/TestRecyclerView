package com.zhenmei.testrecyclerview.activity;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zhenmei.testrecyclerview.R;
import com.zhenmei.testrecyclerview.adapter.MyAdapter;
import com.zhenmei.testrecyclerview.bean.News;
import com.zhenmei.testrecyclerview.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
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
    private List<News> list;

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
        list = new ArrayList<News>();
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

        leftDelete();
    }

    private ItemTouchHelper.Callback mCallback;

    /**
     * RecyclerView的拖动和滑动
     * http://www.apkbus.com/android-246159-1-1.html
     */
    private void leftDelete() {

        /**
         * SimpleCallback构造方法需要我们传入两个参数：
         1、dragDirs- 表示拖动的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
         2、swipeDirs- 表示滑动的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
         【注】：如果为0，则表示不触发该操作（滑动or拖拽）
         */
        mCallback = new ItemTouchHelper.SimpleCallback(
                // 上下拖动
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                // 左右滑动
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //拖动时的回调
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到拖动ViewHolder的position
                int fromPosition = viewHolder.getAdapterPosition();
                //得到目标ViewHolder的position
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    //分别把中间所有的item的位置重新交换
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(list, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(list, i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                //返回true表示执行拖动
                return true;
            }

            //滑动时的回调
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //得到滑动的ViewHolder的position
                int position = viewHolder.getAdapterPosition();
                //删除item
                adapter.removeItem(position);
            }

            /**
             * 实现 onChildDraw() 方法，该方法用于Item的绘制，
             * actionState有三种状态SWIPE（滑动）、IDLE（静止）、DRAG（拖动）
             * 我们可以根据相应的状态来绘制Item的一些效果
             */
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                //当选中Item时候会调用该方法，重写此方法可以实现选中时候的一些动画逻辑
                Log.i("info", "onSelectedChanged");
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                //当动画已经结束的时候调用该方法，重写此方法可以实现恢复Item的初始状态
                Log.i("info", "clearView");
            }
        };

        //初始化一个item触摸的帮助类，需要一个ItemTouchHelper.Callback参数
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        //将RecyclerView和帮助类绑定在一起
        itemTouchHelper.attachToRecyclerView(recyclerView);

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
