package com.zhanggb.contacts.app.activity;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.zhanggb.contacts.app.R;
import com.zhanggb.contacts.app.adapter.NoteListAdapter;
import com.zhanggb.contacts.app.manager.CallBack;
import com.zhanggb.contacts.app.manager.NoteDatabaseManager;
import com.zhanggb.contacts.app.manager.impl.NoteDatabaseManagerImpl;
import com.zhanggb.contacts.app.model.Note;
import com.zhanggb.contacts.app.util.Log;
import com.zhanggb.contacts.app.view.HeaderView;
import com.zhanggb.contacts.app.view.pullableview.PullToRefreshLayout;
import com.zhanggb.contacts.app.view.pullableview.PullableListView;

import java.util.List;

//import com.feiwo.view.FwBannerManager;
//import com.feiwo.view.FwRecevieAdListener;

/**
 * @author zhanggaobo
 * @since 09/26/2016
 */
public class HomeMessageFragment extends Fragment {

    private Log logger = Log.build(getClass());

    //    public static String APPKEY_FEIWO = "7f7884a41d8cd08c91314fce7f13dd1c";
    public static String APPKEY_FEIWO = "99632f8a814e38a9f2e8d2f37dab6d9e";
    private Context context;
    private View view;
    private HeaderView headerView;
    private PullToRefreshLayout refreshLayout;
    private PullableListView listView;
    private LinearLayout layoutFeiw;
    private FrameLayout bannerFrame;
    private ImageView bannerCloseImage;
    private NoteListAdapter listAdapter;
    private Handler handler;

    private NoteDatabaseManager noteDatabaseManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity();
        view = inflater.inflate(R.layout.home_message_fragment, null);
        noteDatabaseManager = new NoteDatabaseManagerImpl(context);
        handler = new Handler();
        initView();
//        initBanner();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new FindNoteTask(false).execute();
    }

    private void initView() {
        headerView = (HeaderView) view.findViewById(R.id.header_view);
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        listView = (PullableListView) view.findViewById(R.id.list_view);
        layoutFeiw = (LinearLayout) view.findViewById(R.id.feiwo_banner_ll);
        bannerFrame = (FrameLayout) view.findViewById(R.id.banner_frame);
        bannerCloseImage = (ImageView) view.findViewById(R.id.banner_close_image);
        headerView.getMiddleText().setText("记事本");
        headerView.getLeftImage().setVisibility(View.GONE);
        headerView.getRightImage().setVisibility(View.VISIBLE);
        headerView.getRightImage().setImageResource(R.mipmap.icon_header_add_plus);
        headerView.getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteEditActivity.class);
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                new FindNoteTask(true).execute();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                refreshLayout.refreshFinish(0);
            }
        });
        bannerCloseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bannerFrame.setVisibility(View.GONE);
            }
        });
    }

    private void refreshList(List<Note> notes) {
        listAdapter = new NoteListAdapter(context, notes, R.layout.note_list_item_view);
        listAdapter.setCallBack(new CallBack() {
            @Override
            public void callBack(final String id) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        new FindNoteTask(false).execute();
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        noteDatabaseManager.deleteNote(id);
                        return null;
                    }
                }.execute();
            }
        });
        listView.setAdapter(listAdapter);
    }

    class FindNoteTask extends AsyncTask<Void, Void, List<Note>> {
        boolean isRefresh = false;

        public FindNoteTask(boolean isRefresh) {
            this.isRefresh = isRefresh;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);
            refreshList(notes);
            if (isRefresh) {
                refreshLayout.refreshFinish(0);
            }
        }

        @Override
        protected List<Note> doInBackground(Void... params) {
            List<Note> notes = noteDatabaseManager.findNotes();
            return notes;
        }
    }

//    private void initBanner() {
//        AwesomeBManager.getInstance().init(context, APPKEY_FEIWO);
//        AwesomeBManager.getInstance().loadBannerAD(context, layoutFeiw);
//        AwesomeBManager.getInstance().loadBannerAD(context, layoutFeiw, new AwesomeBListener() {
//            @Override
//            public void onSuccess() {
//                logger.debug("AwesomeBManager onSuccess");
//                bannerFrame.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onFailure() {
//                logger.debug("AwesomeBManager onFailure");
//                bannerFrame.setVisibility(View.GONE);
//            }
//        });

//    }

//    class NoteListAdapter extends BaseAdapter {
//
//        private List<Note> notes = new ArrayList<Note>();
//
//        public void setNotes(List<Note> notes) {
//            this.notes = notes;
//        }
//
//        @Override
//        public int getCount() {
//            return notes.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return notes.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            final Note item = notes.get(position);
//            NoteSwipeItemView itemView;
//            if (convertView == null) {
//                itemView = new NoteSwipeItemView(context);
//            } else {
//                itemView = (NoteSwipeItemView) convertView;
//            }
//            String time = DateUtil.getTime("yyyy-MM-dd", Long.parseLong(item.getUploadDate()));
//            if (StringUtils.isNotEmpty(item.getTheme())) {
//                itemView.getTextTheme().setText(item.getTheme());
//            } else {
//                itemView.getTextTheme().setText(item.getContent());
//            }
//            itemView.getTextTime().setText(time);
//            itemView.getRelativeNote().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, NoteEditActivity.class);
//                    intent.putExtra("note", item);
//                    context.startActivity(intent);
//                }
//            });
//
//            return itemView;
//        }
//    }
}
