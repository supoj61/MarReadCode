package masterung.androidthai.in.th.ungreadcode.fragment;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import masterung.androidthai.in.th.ungreadcode.NotificationActivity;
import masterung.androidthai.in.th.ungreadcode.R;
import masterung.androidthai.in.th.ungreadcode.utility.ChangeStringToArray;
import masterung.androidthai.in.th.ungreadcode.utility.EditStatusWhereIdUserAnStatus;
import masterung.androidthai.in.th.ungreadcode.utility.GetChildWhereIdUser;
import masterung.androidthai.in.th.ungreadcode.utility.MyConstant;

/**
 * Created by masterung on 23/3/2018 AD.
 */

public class ShowNotiFragment extends Fragment{

    private String[] messageStrings, loginStrings;
    private boolean statusABoolean = true;
    public static ShowNotiFragment showNotiInstance(String[] messageStrings) {

        ShowNotiFragment showNotiFragment = new ShowNotiFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("Message", messageStrings);
        showNotiFragment.setArguments(bundle);
        return showNotiFragment;
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Get Value From Argument
        messageStrings = getArguments().getStringArray("Message");

//        Get Value From SherePreferance
        getValueFromSharePreference();

        changeStatus();


//        Create Toolbar
        createToolbar();

//        Create ListView
        createListView();
        myLoop();
    }   // Main Class

    private void changeStatus() {

        try {

            MyConstant myConstant = new MyConstant();
            EditStatusWhereIdUserAnStatus editStatusWhereIdUserAnStatus = new EditStatusWhereIdUserAnStatus(getActivity());
            editStatusWhereIdUserAnStatus.execute(loginStrings[0], myConstant.getUrlEditStatusWhereIDuser());

            Log.d("23MarchV1", "Result From Change Status ==> " + editStatusWhereIdUserAnStatus.get());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getValueFromSharePreference() {

        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences("LoginFile", Context.MODE_PRIVATE);

        String resultString = sharedPreferences.getString("Login", null);
        Log.d("23MarchV1", "result form Prefer ==> " + resultString);

        ChangeStringToArray changeStringToArray = new ChangeStringToArray(getActivity());
        loginStrings = changeStringToArray.myChangeStringToArray(resultString);


    }

    //------------------------------------------

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void myLoop() {

//        To Do
        try {

            MyConstant myConstant = new MyConstant();
            GetChildWhereIdUser getChildWhereIdUser = new GetChildWhereIdUser(getActivity());
            getChildWhereIdUser.execute(loginStrings[0], myConstant.getUrlGetChildWhereIdUser());

            String jsonString = getChildWhereIdUser.get();
            Log.d("23MarchV1", "json form loop ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i=0; i<jsonArray.length(); i+=1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("Status").equals("1")) {
                    statusABoolean = false;

                    String[] columnStrings = myConstant.getColumnMessageStrings();
                    String[] messageStrings = new String[columnStrings.length];

                    for (int i1=0; i1<columnStrings.length; i1+=1) {
                        messageStrings[i1] = jsonObject.getString(columnStrings[i1]);
                    }
                    myNoticication(messageStrings);
                    Log.d("23MarchV1", "Loop Stop");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (statusABoolean) {
                    myLoop();
                }
            }
        }, 1000);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void myNoticication(String[] messageStrings) {

        Log.d("23MarchV1", "Notification Work");

        Intent intent = new Intent(getActivity(), NotificationActivity.class);
        intent.putExtra("Message", messageStrings);

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                (int) System.currentTimeMillis(), intent, 0);

        Uri uri = RingtoneManager.getDefaultUri(Notification.DEFAULT_SOUND);

        Notification.Builder builder = new Notification.Builder(getActivity());
        builder.setTicker(getString(R.string.app_name));
        builder.setContentTitle(messageStrings[3] + " Have Message");
        builder.setContentText("Please Click Here");
        builder.setSmallIcon(R.drawable.ms);
        builder.setSound(uri);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.DEFAULT_SOUND | Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONLY_ALERT_ONCE;
        notificationManager.notify(0, notification);


    }

    //----------------------------------------------
    private void createListView() {

        ListView listView = getView().findViewById(R.id.listViewMessage);
        ChangeStringToArray changeStringToArray = new ChangeStringToArray(getActivity());
        String[] dateStrings = changeStringToArray.myChangeStringToArray(messageStrings[6]);
        String[] newsStrings = changeStringToArray.myChangeStringToArray(messageStrings[7]);

        String[] contentStrings = new String[dateStrings.length];
        for (int i=0; i<dateStrings.length; i+=1) {
            contentStrings[i] = dateStrings[i] + "\n" + newsStrings[i];
        }


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, contentStrings);
        listView.setAdapter(stringArrayAdapter);

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarShowNoti);
        ((NotificationActivity) getActivity()).setSupportActionBar(toolbar);

        ((NotificationActivity) getActivity())
                .getSupportActionBar()
                .setTitle(messageStrings[3]);

        ((NotificationActivity) getActivity()).getSupportActionBar().setSubtitle(loginStrings[1] );

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_notification,
                container, false);
        return view;
    }
}   // Main Class
