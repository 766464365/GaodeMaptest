package com.xuwei.order.gaodemaptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.xuwei.order.gaodemaptest.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TextWatcher, AdapterView.OnItemClickListener, Inputtips.InputtipsListener {
    AutoCompleteTextView begin,end;
    ListView search_list;
    SearchAdapter searchAdapter;
    List<HashMap<String,String>> searchList;
    LinearLayout parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();

    }

    private void initview() {
        begin=(AutoCompleteTextView)findViewById(R.id.at_edit_begin);
        end=(AutoCompleteTextView)findViewById(R.id.at_edit_end);
        search_list= (ListView) findViewById(R.id.search_list);
        parent=(LinearLayout)findViewById(R.id.parent);
        begin.addTextChangedListener(this);
        end.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()){
                    search_list.setVisibility(View.GONE);
                }
                else {
                    search_list.setVisibility(View.VISIBLE);
                    InputtipsQuery inputtipsQuery = new InputtipsQuery(charSequence.toString().trim(), "");
                    inputtipsQuery.setCityLimit(false);
                    Inputtips inputtips = new Inputtips(MainActivity.this, inputtipsQuery);
                    inputtips.setInputtipsListener(new Inputtips.InputtipsListener() {
                        @Override
                        public void onGetInputtips(List<Tip> list, int returnCode) {
                            if (returnCode == AMapException.CODE_AMAP_SUCCESS) {//如果输入提示搜索成功
                                searchList = new ArrayList<HashMap<String, String>>();
                                for (int i = 0; i < list.size(); i++) {
                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap.put("name", list.get(i).getName());
                                    hashMap.put("district", list.get(i).getDistrict()+ list.get(i).getAddress());//将地址信息取出放入HashMap中
                                    searchList.add(hashMap);//将HashMap放入表中
                                }
                                searchAdapter = new SearchAdapter(MainActivity.this);//新建一个适配器
                                search_list.setAdapter(searchAdapter);//为listview适配
                                SimpleAdapter aAdapter = new SimpleAdapter(getApplicationContext(), searchList, R.layout.search_list_item,
                                        new String[]{"name", "district"}, new int[]{R.id.search_item_title, R.id.search_item_text});
                                search_list.setAdapter(aAdapter);
                                search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        end.setText(searchList.get(i).get("district")  + searchList.get(i).get("name"));
                                        search_list.setVisibility(View.GONE);
                                    }
                                });
                                aAdapter.notifyDataSetChanged();//动态更新listview


                            } else {
                                Toast.makeText(MainActivity.this, returnCode, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    inputtips.requestInputtipsAsyn();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        search_list.setOnItemClickListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().isEmpty()){
            search_list.setVisibility(View.GONE);
        }
        else {
            search_list.setVisibility(View.VISIBLE);
            InputtipsQuery inputtipsQuery = new InputtipsQuery(charSequence.toString().trim(), "");
            inputtipsQuery.setCityLimit(false);
            Inputtips inputtips = new Inputtips(this, inputtipsQuery);
            inputtips.setInputtipsListener(this);
            inputtips.requestInputtipsAsyn();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        begin.setText(searchList.get(i).get("district")+searchList.get(i).get("name"));
        search_list.setVisibility(View.GONE);
    }

    @Override
    public void onGetInputtips(List<Tip> list, int returnCode) {
        if(returnCode== AMapException.CODE_AMAP_SUCCESS){//如果输入提示搜索成功
            searchList=new ArrayList<HashMap<String, String>>() ;
            for (int i=0;i<list.size();i++){
                HashMap<String,String> hashMap=new HashMap<String, String>();
                hashMap.put("name",list.get(i).getName());
                hashMap.put("district",list.get(i).getDistrict()+" "+list.get(i).getAddress());//将地址信息取出放入HashMap中
                searchList.add(hashMap);//将HashMap放入表中
            }
            searchAdapter=new SearchAdapter(this);//新建一个适配器
            search_list.setAdapter(searchAdapter);//为listview适配
            SimpleAdapter aAdapter = new SimpleAdapter(getApplicationContext(), searchList, R.layout.search_list_item,
                    new String[] {"name","district"}, new int[] {R.id.search_item_title, R.id.search_item_text});

            search_list.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();//动态更新listview
            Log.i("地区为",list.get(0).getDistrict());
        }else{
            Toast.makeText(this,returnCode,Toast.LENGTH_SHORT).show();
        }
    }
}

