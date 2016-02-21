package com.zhenmei.testrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhenmei.testrecyclerview.R;
import com.zhenmei.testrecyclerview.bean.News;

import java.util.List;

/**
 * RecyclerView的适配器
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private List<News> list;
    private LayoutInflater mInflater;

    /**
     * 传入数据源，初始化适配器
     * @param mContext
     * @param list
     */
    public MyAdapter(Context mContext, List<News> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 创建ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * 加载item布局，生成ViewHolder并返回
         */
        View v = mInflater.inflate(R.layout.item_shu, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    /**
     * item的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 绑定ViewHolder
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        /**
         * ViewHolder中控件设置数据
         */
        News n = list.get(position);
        holder.ivTouxiang.setImageResource(n.getTouxiang());
        holder.tvBiaoti.setText(n.getBiaoti());
        holder.tvNeirong.setText(n.getNeirong());

        /**
         * item单击事件监听
         */
        if (onItemClickLinster != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLinster.onItemClick(
                            holder.itemView, holder.getLayoutPosition());
                }
            });
        }

        /**
         * item长按事件监听
         */
        if (onItemLongClickLinster != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickLinster.onItemLongClick(
                            holder.itemView, holder.getLayoutPosition());
                    return true;
                }
            });
        }

    }

    /**
     * 添加item
     * @param news
     * @param position
     */
    public void addItem(News news, int position) {
        list.add(position, news);
        notifyItemInserted(position);
    }

    /**
     * 删除item
     * @param position
     */
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 单击事件监听的回调接口
     */
    public interface OnItemClickLinster {
        void onItemClick(View view, int position);
    }

    private OnItemClickLinster onItemClickLinster;

    public void setOnItemClickLinster(OnItemClickLinster onItemClickLinster) {
        this.onItemClickLinster = onItemClickLinster;
    }

    /**
     * 长按事件监听的回调接口
     */
    public interface OnItemLongClickLinster {
        void onItemLongClick(View view, int position);
    }

    private OnItemLongClickLinster onItemLongClickLinster;

    public void setOnItemLongClickLinster(OnItemLongClickLinster onItemLongClickLinster) {
        this.onItemLongClickLinster = onItemLongClickLinster;
    }


    /**
     * 自定义ViewHolder
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivTouxiang;
        public TextView tvBiaoti, tvNeirong;

        public MyViewHolder(View itemView) {
            super(itemView);
            /**
             * 初始化ViewHolder中的控件
             */
            ivTouxiang = (ImageView) itemView.findViewById(R.id.iv_touxiang);
            tvBiaoti = (TextView) itemView.findViewById(R.id.tv_biaoti);
            tvNeirong = (TextView) itemView.findViewById(R.id.tv_neirong);
        }
    }

}