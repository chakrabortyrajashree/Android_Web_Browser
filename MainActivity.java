package com.example.myfirstwebbrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    WebView myWebView;
    EditText editText;
    Button button;
    private List<Message> browseHistoryList;

    String mapbookurl,mapbookid,mapoptionsmenu;
    ArrayList<String> mapurllist = new ArrayList<>();
    ArrayList<String> mapidlist = new ArrayList<>();
    ArrayList<String> mapoptionslist = new ArrayList<>();
    ArrayList<HashMap<String,String>> userlist = new ArrayList<>();
    DatabaseHelper mydb;

    ListView booklist;
    ListAdapter lviewadapter;
    LinearLayout empty;
    BookMarks bkMrks;
    DatabaseReference ref;

//    DatabaseReference retrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"Firebase connection success",Toast.LENGTH_LONG).show();

        editText = (EditText) findViewById(R.id.editText);

        myWebView = (WebView) findViewById(R.id.webview);

        WebViewClient myWebViewClient = new WebViewClient();
        myWebView.setWebViewClient(myWebViewClient);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        myWebView.setVerticalScrollBarEnabled(false);
        //This statement hides the Vertical scroll bar and does not remove it.

        myWebView.setHorizontalScrollBarEnabled(false);
        //This statement hides the Horizontal scroll bar and does not remove it.
        /**
         * Enabling zoom-in controls
         * */
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(true);


        myWebView.loadUrl("https://www.google.com");

        mydb = new DatabaseHelper(this);
        bkMrks = new BookMarks();
        ref = FirebaseDatabase.getInstance().getReference().child("BookMarks");
    }


    //Retain an object during a configuration change
    //Handle the configuration change

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }







    //This method works when clicked Go icon
    //Example--amazon.com has been entered in the address bar and clicked on the Go Button.
    //Amazon.com web site is open
    public void showWeb(View view) {
        //InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        //myWebView.loadUrl("https://wwww." + editText.getText().toString());
        //editText.setText("");

        String prefix="https://www.";
        //back=prefix+editText.getText().toString();
        myWebView.loadUrl(prefix+editText.getText().toString().trim());
        Log.d("Url",myWebView.getUrl());

    }

    //This method to take the user to the previous page while click the back icon
    public void backButton(View view) {
        if (myWebView.canGoBack()) {
            myWebView.goBack();

            myWebView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    editText.setText(myWebView.getUrl());
                }
            });
        }
        Log.d("Message","Back to previous page working:");
    }
    //This method to take the user to the Next page while click the forward icon
    public void forwardButton(View view) {
        if (myWebView.canGoForward()) {
            myWebView.goForward();
        }
        Log.d("Message","Next page working:");
    }
    //This method to take the user to the Home page while click the Home icon
    public void homeButton(View view) {
        myWebView.loadUrl("https://www.google.com");
        editText.setText(myWebView.getUrl());

    }

    //This method to reload the page while click the replay icon
    public void replayButton(View view){
        myWebView.reload();
    }


    //This method close the  history page while click the close icon
    public void closeButton(View view){

        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());

        myWebView.getSettings().setJavaScriptEnabled(true);
        //This statement is used to enable the execution of JavaScript

        myWebView.setVerticalScrollBarEnabled(false);
        //This statement hides the Vertical scroll bar and does not remove it.

        myWebView.setHorizontalScrollBarEnabled(false);
        myWebView.loadUrl("https://www.google.com");



    }

    // This method delete history while user click the delete button
    public void deleteHistoryButton(View view){
        //myWebView.clearHistory();
        myWebView =new WebView(MainActivity.this);
        historyButton(view);
    }


   // public void historyButton(View view) {
//        ListView list =(ListView)findViewById(R.id.listView);
//        List<Message> fullList=new ArrayList<Message>();
//
//        ListViewCustomerAdapter listAdapter=new ListViewCustomerAdapter(this,fullList);
//        list.setAdapter(listAdapter);
//
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("Clicked","Item clicked");
//            }
//        });


    //This method shows user history while user click the HISTORY button in a different layout
        public void historyButton(View view) {
            setContentView(R.layout.second_main);

            browseHistoryList= new ArrayList<Message>();
            ListView list= findViewById(R.id.listView);


            WebBackForwardList currentList= myWebView.copyBackForwardList();

            int currentSize= currentList.getSize();
            for(int i=0; i<currentSize; i++){
                WebHistoryItem item= currentList.getItemAtIndex(i);
                String url= item.getUrl();

                browseHistoryList.add(new Message(item.getTitle(), item.getUrl()));

                Log.d("Message","URL at index:"+ Integer.toString(i)+ " is-" + url );
            }

            ListViewCustomerAdapter lvca= new ListViewCustomerAdapter(this,browseHistoryList);
            list.setAdapter(lvca);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("Item","clicked..");
                }
            });
        }

    // this method for bookmark
    public void bookmarkButtonSql(View view)
    {
        String title = myWebView.getTitle();
        String url = myWebView.getUrl();
        int id = 1;
        boolean isInserted = mydb.insertData(title,url);
        //id++;
        if(isInserted)
        {
        Toast.makeText(this, "Bookmarked", Toast.LENGTH_SHORT).show();
        Log.d("ItemBook","Bookmark..");
        }
        else
        {
        Toast.makeText(this, "Error adding Bookmark", Toast.LENGTH_SHORT).show();
        }

    }

    // this method for bookmark
    public void bookmarkButtonFirebase(View view)
    {
        String title = myWebView.getTitle();
        String url = myWebView.getUrl();

        Toast.makeText(this, "adding Bookmark", Toast.LENGTH_SHORT).show();

        bkMrks.setTitle(title);
        bkMrks.setUrl(url);

        ref.push().setValue(bkMrks);

        Toast.makeText(this, "inserted data", Toast.LENGTH_LONG).show();
    }

    //
    public  void showBookMarksSql( View view){

        Log.d("ItemBooK","Show Bookmark..");
        setContentView(R.layout.booklist);
        booklist = findViewById(R.id.booklistview);
        empty = findViewById(R.id.emptyview);
        empty.setVisibility(View.GONE);

        userlist = mydb.Showdata();

        Log.d("ItemBooK", String.valueOf(userlist));


        if(userlist.isEmpty())
        {
            empty.setVisibility(View.VISIBLE);
            Log.d("ItemBooK","Show Bookmark.. VISIBLE");
            return;
        }
        lviewadapter = new SimpleAdapter(MainActivity.this,userlist,R.layout.book_custom_list,
                new String[]{"id","title","url"},
                new int[]{R.id.custombookid,R.id.custombooktitle,R.id.custombookurl});
        booklist.setAdapter(lviewadapter);

        Log.d("ItemBooK","Show Bookmark.. end");

        booklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("ItemBooK","Show Bookmark.. onItemClick");

                Object o = booklist.getAdapter().getItem(i);
                if(o instanceof Map)
                {
                    Map map = (Map)o;
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    intent.putExtra("urlkey",String.valueOf(map.get("url")));
                    startActivity(intent);

                    Log.d("ItemBooK","Show Bookmark.. instanceof");

                }
            }

        });

        Log.d("ItemBooK","Show Bookmark.. the end");
    }

    // This function invokes on BookMarks button click
    // to fetch data from Firebase Db
    public  void showBookMarksFirebase( View view){

        Toast.makeText(this, "Show BookMark Data", Toast.LENGTH_LONG).show();

        setContentView(R.layout.booklist);

        booklist = findViewById(R.id.booklistview);

        empty = findViewById(R.id.emptyview);

        empty.setVisibility(View.GONE);

        ref = FirebaseDatabase.getInstance().getReference().child("BookMarks");

        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                int i =0;

                for(DataSnapshot ds: dataSnapshot.getChildren() )
                {

                    HashMap<String,String> user = new HashMap<>();
                    user.put("title",ds.child("title").getValue().toString());
                    user.put("url",ds.child("url").getValue().toString());
                    userlist.add(user);

                    Log.d("How Many Data inserted ", String.valueOf(i));
                    i++;

                }

                lviewadapter = new SimpleAdapter(MainActivity.this,userlist,R.layout.book_custom_list,
                        new String[]{"id","title","url"},
                        new int[]{R.id.custombookid,R.id.custombooktitle,R.id.custombookurl});
                booklist.setAdapter(lviewadapter);

                Log.d("ItemBooK","Show Bookmark.. end");

            }

            @Override
            public  void onCancelled(@NonNull DatabaseError databaseError){
                Log.d("ItemBookMark","showBookMarksFirebase - onCancell");
            }


        });

    }




}