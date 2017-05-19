package com.example.mercimek.ybubilgisayar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mercimek on 12.05.2017.
 */
public class TabFragment3 extends Fragment {
    ListView list;
    ArrayList<String> arraylist=new ArrayList<>();
    ArrayList<String> arrayhref=new ArrayList<String>();
    ArrayList<Integer> imageId=new ArrayList<>();
    Button btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.tab_fragment_3, container, false);
        list=(ListView)view.findViewById(R.id.listView2);
        btn=(Button)view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                System.exit(0);
            }
        });



        Thread t = new Thread(new Runnable() {
            Document doc;
            Elements element;
            Elements a;
            Element href;
            @Override
            public void run() {
                try {
                    doc = Jsoup.connect("http://ybu.edu.tr/muhendislik/bilgisayar/").get();
                    element = doc.select("div.cnContent");
                    a=element.select("div.cncItem");

                    if(getActivity() == null)
                        return;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(Element i: a){
                                href=i.select("a").first();

                                arraylist.add(href.attr("title").toString());

                                String a="http://www.ybu.edu.tr/muhendislik/bilgisayar/"+href.attr("href");
                                arrayhref.add(a.toString());



                            }
                            for(int i=0;i<arraylist.size();i++){
                                imageId.add(R.drawable.news_icon);
                            }
                            CustomList adapter = new
                                    CustomList(getActivity(), arraylist, imageId);

                            list.setAdapter(adapter);

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                                    int a=position;
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(arrayhref.get(a).toString()));
                                    startActivity(browserIntent);



                                }
                            });






                        }
                    });



                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });
        t.start();


        return  view;
    }
}