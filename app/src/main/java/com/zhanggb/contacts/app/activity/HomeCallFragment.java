package com.zhanggb.contacts.app.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.zhanggb.contacts.app.R;
import com.zhanggb.contacts.app.manager.*;
import com.zhanggb.contacts.app.manager.impl.AssertDatabasesManagerImpl;
import com.zhanggb.contacts.app.manager.impl.PreferenceKeyManagerImpl;
import com.zhanggb.contacts.app.model.Call;
import com.zhanggb.contacts.app.model.CallItem;
import com.zhanggb.contacts.app.util.*;
import com.zhanggb.contacts.app.view.CallListItemView;
import com.zhanggb.contacts.app.view.DialogFactory;
import com.zhanggb.contacts.app.view.HeaderView;
import com.zhanggb.contacts.app.view.pullableview.PullToRefreshLayout;
import com.zhanggb.contacts.app.view.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaobo
 * @since 09/26/2016
 */
public class HomeCallFragment extends Fragment {
    private Context context;
    private View view;
    private HeaderView headerView;
    private PullToRefreshLayout refreshLayout;
    private PullableListView listView;
    private EditText searchEdit;
    private CallListAdapter listAdapter;
    private ContactCacheManager contactCacheManager;
    private Handler handler;
    private List<Call> calls = new ArrayList<Call>();
    private Log logger = Log.build(getClass());
    private AssertDatabasesManager assertDatabasesManager;
    private PreferenceKeyManager preferenceKeyManager;
    private DialerConfirmViewBuilder dialerConfirmView;
    private boolean isDialogShow = false;
    private Dialog dialogCallItemClick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity();
        view = inflater.inflate(R.layout.home_call_fragment, null);
        contactCacheManager = ContactCacheManager.getInstance();
        assertDatabasesManager = new AssertDatabasesManagerImpl(context);
        preferenceKeyManager = new PreferenceKeyManagerImpl(context);
        handler = new Handler();
        initView();
        setupSearchView();
        new FindCallTask().execute();
        return view;
    }

    private void initView() {
        headerView = (HeaderView) view.findViewById(R.id.header_view);
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        listView = (PullableListView) view.findViewById(R.id.list_view);
        headerView.getMiddleText().setText("电话");
        headerView.getLeftImage().setVisibility(View.GONE);

        dialerConfirmView = new DialerConfirmViewBuilder();

        listAdapter = new CallListAdapter();
        listView.setAdapter(listAdapter);
        refreshLayout.setCanPullDown(false);
        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                new FindCallTask().execute();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                refreshLayout.refreshFinish(0);
            }
        });
    }

    private void setupSearchView() {
        View searchView = LayoutInflater.from(context).inflate(R.layout.search_view, null);
        searchEdit = (EditText) searchView.findViewById(R.id.search_edit);
        final TextView searchBtn = (TextView) searchView.findViewById(R.id.search_text);
        final ImageView searchCloseImage = (ImageView) searchView.findViewById(R.id.search_close_image);
        listView.addHeaderView(searchView);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new FindCallTask().execute();
                    }
                }, 500);
                if (StringUtils.isNotEmpty(StringUtils.trimToEmpty(searchEdit.getText().toString()))) {
                    searchBtn.setVisibility(View.VISIBLE);
                    searchCloseImage.setVisibility(View.VISIBLE);
                } else {
                    searchBtn.setVisibility(View.GONE);
                    searchCloseImage.setVisibility(View.GONE);
                }
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IntentFactory.createCallIntent(
                        StringUtils.trimToEmpty(searchEdit.getText().toString())))
                ;
            }
        });
        searchCloseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEdit.setText("");
            }
        });
    }

    class FindCallTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            refreshLayout.refreshFinish(0);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String keyword = StringUtils.trimToEmpty(searchEdit.getText().toString());
            if (StringUtils.isBlank(keyword)) {
                calls = contactCacheManager.findCall(context);
            } else {
                calls = contactCacheManager.searchCall(keyword);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    listAdapter.setCallList(calls);
                    listAdapter.notifyDataSetChanged();
                }
            });
            return null;
        }
    }


    class CallListAdapter extends BaseAdapter {

        private List<Call> callList = new ArrayList<Call>();

        public void setCallList(List<Call> callList) {
            this.callList = callList;
        }

        @Override
        public int getCount() {
            return callList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final CallListItemView itemView;
            final Call item = callList.get(position);
            String keyword = StringUtils.trimToEmpty(searchEdit.getText().toString());
            final CallItem lastItem = item.lastItem;
            if (convertView != null) {
                itemView = (CallListItemView) convertView;
            } else {
                itemView = new CallListItemView(context);
            }
            itemView.getNameText().setText(lastItem.displayName);
//            itemView.getPhoneText().setText(lastItem.number);
            if (StringUtils.isNotEmpty(keyword)) {
                int index = -1;
                int start = 0;
                SpannableStringBuilder spannable = new SpannableStringBuilder(lastItem.number);
                index = StringUtils.indexOf(lastItem.number, keyword, start);
                if (index > -1) {
                    ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.theme));
                    spannable.setSpan(span, index, index + keyword.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                itemView.getPhoneText().setText(spannable);
            } else {
                itemView.getPhoneText().setText(lastItem.number);
            }

            if (lastItem.type == CallItem.Type.INCOMING) {
                itemView.getTypeImage().setImageResource(R.mipmap.ic_record_incomming_normal);
            } else if (lastItem.type == CallItem.Type.MISSED) {
                itemView.getTypeImage().setImageResource(R.mipmap.ic_record_missed_normal);
            } else if (lastItem.type == CallItem.Type.OUTGOING) {
                itemView.getTypeImage().setImageResource(R.mipmap.ic_record_outgoing_nomal);
            }
            String time = DateUtil.getTime("yyyy-MM-dd hh:mm", lastItem.callTime);
            if (DateUtil.IsToday(lastItem.callTime)) {
                time = DateUtil.getTime("hh:mm", lastItem.callTime);
            } else if (DateUtil.IsYesterday(lastItem.callTime)) {
                time = "昨天" + DateUtil.getTime("hh:mm", lastItem.callTime);
            }
            if (lastItem.duration != 0) {
                itemView.getDurationText().setText(DateUtil.createTime(lastItem.duration));
            } else {
                itemView.getDurationText().setText("");
            }
            itemView.getTimeText().setText(time);
            if (item.photoId > 0) {
                BitmapUtil.getInstance().loadBitmap(String.valueOf(item.contactId), itemView.getPhotoImage(), context);
            } else {
                itemView.getPhotoImage().setImageResource(R.mipmap.default_avatar);
            }
            itemView.getCallLinear().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(IntentFactory.createCallPageIntent(lastItem.number));
                    itemViewClick(item, itemView);
                }
            });
            String city = assertDatabasesManager.getAttributionOfNumber(context, lastItem.number);
            city = StringUtils.isNotEmpty(city) ? city : "";
            itemView.getAttrText().setText(city);
            logger.debug("call: " + item.toString());
            return itemView;
        }
    }

    private void itemViewClick(final Call call, CallListItemView listItem) {
        if (call != null && (call.lastItem == null || isValidPhoneNumber(call.lastItem.number))) {
            Toast.makeText(context, "无效的号码", Toast.LENGTH_SHORT).show();
        } else {
            if (dialerConfirmView.enableShown()) {
                dialerConfirmView.populate(call);
                dialerConfirmView.setContactPhoto(listItem.getPhotoImage().getDrawable());
                Dialog dialog = DialogFactory.createConfirmDialog(context, "确认拨出电话?",
                        dialerConfirmView.getView(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                isDialogShow = false;
                                try {
                                    dialogInterface.dismiss();
                                    dialerConfirmView.remove();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                dialerConfirmView.setShown();
                                startActivity(IntentFactory.createCallIntent(call.lastItem.number));
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                isDialogShow = false;
                                try {
                                    dialogInterface.dismiss();
                                    dialerConfirmView.remove();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        if (i == KeyEvent.KEYCODE_BACK) {
                            isDialogShow = false;
                            try {
                                dialogInterface.dismiss();
                                dialerConfirmView.remove();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        } else if (i == KeyEvent.KEYCODE_SEARCH) {
                            return true;
                        }
                        return false;
                    }
                });
                if (!isDialogShow) {
                    dialogCallItemClick = dialog;
                    isDialogShow = true;
                }
                try {
                    if (!dialogCallItemClick.isShowing()) {
                        dialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (call != null) {
                startActivity(IntentFactory.createCallIntent(call.lastItem.number));
            }
        }
    }

    class DialerConfirmViewBuilder {
        public ImageView contactPhoto;
        public TextView displayName;
        public TextView phoneNumber;
        public CheckBox checkDial;
        private View view;
        private PreferenceKeyManager.Key<Boolean> diaConfirm;


        DialerConfirmViewBuilder() {
            view = LayoutInflater.from(context).inflate(R.layout.dial_out_layout, null, false);
            contactPhoto = (ImageView) view.findViewById(R.id.contact_photo);
            displayName = (TextView) view.findViewById(R.id.text_dial_name);
            phoneNumber = (TextView) view.findViewById(R.id.text_dial_number);
            checkDial = (CheckBox) view.findViewById(R.id.check_dial);
            diaConfirm = preferenceKeyManager.getFeatureSet().dialConfirm();
        }

        public void setContactPhoto(Drawable contactPhoto) {
            this.contactPhoto.setImageDrawable(contactPhoto);
        }

        public boolean enableShown() {
            return diaConfirm.get();
        }

        public void setShown() {
            diaConfirm.set(!checkDial.isChecked());
        }

        public View getView() {
            return view;
        }

        public void populate(Call call) {
            displayName.setText(filterName(call.displayName, call.lastItem.number));
            phoneNumber.setText((call == null || StringUtils.endsWith(call.displayName, call.lastItem.number)) ?
                    StringUtils.EMPTY : call.lastItem.number);
            checkDial.setChecked(false);
        }

        public void remove() {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private String filterName(String name, String number) {
        if (StringUtils.isEmpty(name)) {
            return "未知";
        }
        if (PhoneNumberUtils.isEmergencyNumber(number)) {
            return "紧急号码";
        } else {
            boolean bl = StringUtils.isEmpty(number) ? false : number.contains("-");
            return bl ? "未知" : name;
        }
    }


    private static Bitmap getContactPhoto(Context c, String personId, int defaultIco) {
        byte[] data = new byte[0];
        Uri u = Uri.parse("content://com.android.contacts/data");
        String where = "raw_contact_id = " + personId + " AND mimetype ='vnd.android.cursor.item/photo'";
        Cursor cursor = c.getContentResolver().query(u, null, where, null, null);
        if (cursor.moveToFirst()) {
            data = cursor.getBlob(cursor.getColumnIndex("data15"));
        }
        cursor.close();
        if (data == null || data.length == 0) {
            return BitmapFactory.decodeResource(c.getResources(), defaultIco);
        } else return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    private boolean isValidPhoneNumber(String value) {
        return StringUtils.isBlank(value) || value.contains("-");
    }
}
