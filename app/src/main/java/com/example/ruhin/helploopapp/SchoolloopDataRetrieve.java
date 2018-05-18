package com.example.ruhin.helploopapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rubin
 * version 2
 * this class retrieves that data from the schoolloop website and parses the data then stores it in firebase
 */

public class SchoolloopDataRetrieve extends AsyncTask<String,Void,Document> {
    private String userName;
    private String password;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ArrayList<String> classes;
    private ArrayList<String> dueDates;
    private ArrayList<String> info;
    public SchoolloopDataRetrieve(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        classes = new ArrayList<>();
        dueDates = new ArrayList<>();
        info = new ArrayList<>();
    }

    @Override
    protected Document doInBackground(String... strings) {
        Connection.Response loginForm;
        String loginUrl;
        String returnUrl = "";
        Map<String, String> formData = new HashMap<>();
        formData.put("login_name", userName);
        formData.put("password", password);
        formData.put("event_override", "login");
        formData.put("reverse", "");
        formData.put("sort", "");
        formData.put("login_form_reverse", "");
        formData.put("login_form_page_index", "");
        formData.put("login_form_page_item_count", "");
        formData.put("login_form_sort", "");
        formData.put("forward", "");
        formData.put("redirect", "");
        formData.put("login_form_letter", "");
        formData.put("login_form_filter", "");
        try {
            loginForm = Jsoup.connect(strings[0])
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .method(Connection.Method.GET)
                    .execute();
            Document doc = loginForm.parse();
            Element loginUrlElement = doc.getElementsByClass("btn_cms_public01").first();
            loginUrl = loginUrlElement.attr("href");
            loginForm = Jsoup.connect(loginUrl)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("https://homestead.schoolloop.com/")
                    .method(Connection.Method.GET)
                    .execute();
            returnUrl = loginUrl.substring(loginUrl.indexOf("return_url=") + 11, loginUrl.length());
            formData.put("return_url", returnUrl);
            Document data = loginForm.parse();
            Element formElement = data.getElementById("form");
            Elements inputElements = formElement.getElementsByTag("input");
            for (Element elements : inputElements) {
                if (elements.attr("id").equals("form_data_id")) {
                    formData.put("form_data_id", elements.attr("value"));
                }
            }

            Map<String, String> loginCookies = loginForm.cookies();
            final Connection.Response mainPage = Jsoup.connect("https://homestead.schoolloop.com/portal/login?etarget=login_form")
                    .data(formData)
                    .cookies(loginCookies)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("https://homestead.schoolloop.com/")
                    .method(Connection.Method.POST)
                    .execute();
            Document mainPageData = mainPage.parse();
            Elements assignmentDatas = mainPageData.getElementsByClass("ajax_accordion_row jsTrackerRefresh");
            for (Element elements : assignmentDatas) {
                Element tr = elements.select("table").select("tr").first();
                Elements tds = tr.select("td");
                info.add(tds.get(3).text());
                classes.add(tds.get(4).text());
                dueDates.add(tds.get(5).text().substring(5, tds.get(5).text().length()));
            }
            for(int i = 0; i < info.size();i++) {
                final String className = classes.get(i);
                final String infos = info.get(i);
                final String date = dueDates.get(i);
                databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("CompletedAssignments").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean exists = false;
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if(child.getKey().equals(infos.hashCode()+"")) {
                                exists = true;
                                break;
                            }
                        }

                        if(!exists) {
                            databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("Assignments")
                                    .child(infos.hashCode()+"").setValue(new Assignment(infos,className,date));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            return mainPageData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);

    }
}
