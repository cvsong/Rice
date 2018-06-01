package com.cvsong.study.rice.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.cvsong.study.library.R;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * ListView的BaseAdapter
 */
public abstract class AppBaseAdapter<T, VH extends AppBaseAdapter.AppHolder> extends BaseAdapter {

    final static Collection nuCon = new Vector();

    static {
        nuCon.add(null);
    }

    protected Context context;
    protected List<T> data;

    public AppBaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
        if (data != null) {

            data.removeAll(nuCon);
        }
    }

    @Override
    public int getCount() {
        if (data != null) {
            data.removeAll(nuCon);
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public T getItem(int position) {
        return (data != null && position >= 0 && position < data.size()) ? data.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected VH getHelper(View convertView, ViewGroup parent, int viewType) {
        if (convertView != null) {
//            Object obj = convertView.getTag(R.id.adapter_holder_id + viewType * 16);
            Object obj = convertView.getTag( viewType * 16);

            if (obj != null) {
                VH holder = (VH) obj;
                holder.convertView = convertView;
                return holder;
            }
        }

        VH holder = onCreateViewHolder(parent, viewType);
//        holder.convertView.setTag(R.id.adapter_holder_id + viewType * 16, holder);
        holder.convertView.setTag(viewType * 16, holder);

        return holder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH helper = getHelper(convertView, parent, getItemViewType(position));
        onBindViewHolder(helper, position);
        return helper.convertView;
    }

    /**
     * 创建ViewHolder
     *
     * @param parent
     * @return
     */
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 设置View
     *
     * @param holder
     * @param position
     * @return
     */
    public abstract void onBindViewHolder(VH holder, int position);

    public static class AppHolder {
        protected View convertView;

        public AppHolder(View view) {
            convertView = view;
//            ButterKnife.bind(this, view);
            convertView = view;
        }

        public View getConvertView() {
            return convertView;
        }
    }

}
