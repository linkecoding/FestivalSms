package com.codekong.festival_sms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.codekong.festival_sms.R;
import com.codekong.festival_sms.activity.ChooseMsgActivity;
import com.codekong.festival_sms.bean.Festival;
import com.codekong.festival_sms.bean.FestivalLab;
import com.codekong.festival_sms.config.Config;

/**
 * Created by szh on 2016/12/22.
 * 节日类别Fragment
 */

public class FestivalCategoryFragment extends Fragment {
    private GridView mGridView;
    private ArrayAdapter<Festival> mAdapter;
    private LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        return inflater.inflate(R.layout.fragment_festival_category, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        mGridView = (GridView) view.findViewById(R.id.id_gv_festival_category);
        mGridView.setAdapter(mAdapter = new ArrayAdapter<Festival>(getActivity(), -1,
                FestivalLab.getInstance().getFestivals()){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if(convertView == null){
                    viewHolder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.festival_item, parent, false);
                    viewHolder.textView = (TextView) convertView.findViewById(R.id.id_tv_festival_name);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.textView.setText(getItem(position).getName());

                return convertView;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChooseMsgActivity.class);
                intent.putExtra(Config.FESTIVAL_ID, mAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
    }

    class ViewHolder{
        TextView textView;
    }
}
