package com.example.mercimek.ybubilgisayar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by mercimek on 12.05.2017.
 */
public class TabFragment1 extends Fragment {
    TextView text,text1,text2,text3,text4;
    ArrayList<String> a= new ArrayList<>();
    Button btn;
    ProgressDialog pDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.tab_fragment_1, container, false);

        text = (TextView)view.findViewById(R.id.tarihyazisi);
        text1 = (TextView)view.findViewById(R.id.textView1);
        text2 = (TextView)view.findViewById(R.id.textView2);
        text3 = (TextView)view.findViewById(R.id.textView3);
        text4 = (TextView)view.findViewById(R.id.textView4);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Yükleniyor...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false); // ProgressDialog u iptal edilemez hale getirdik.
        pDialog.show();

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
            Element element;
            @Override
            public void run() {
                try {

                    doc = Jsoup.connect("http://ybu.edu.tr/sks/").get();
                    final Element table = doc.select("TABLE").first();
                    Elements rows = table.select("tr");
                    Elements columns=rows.select("td");
                     for (Element column : columns)
                        {

                            a.add(column.text());

                        }

                    if(getActivity() == null)
                        return;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pDialog.dismiss();


                                text.setText(a.get(2).toString());
                            text1.setText(a.get(3).toString());
                            text2.setText(a.get(4).toString());
                            text3.setText(a.get(5).toString());
                            text4.setText(a.get(6).toString());


                        }
                    });


                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }




        });
        t.start();


        return view;
}



}