package com.zhanggb.contacts.app.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;
import com.zhanggb.contacts.app.R;
import com.zhanggb.contacts.app.util.BitmapUtil;
import com.zhanggb.contacts.app.manager.ContactCacheManager;
import com.zhanggb.contacts.app.model.Contact;
import com.zhanggb.contacts.app.util.DeviceUtils;
import com.zhanggb.contacts.app.util.Log;
import com.zhanggb.contacts.app.util.StringUtils;
import com.zhanggb.contacts.app.view.AlphabetView;
import com.zhanggb.contacts.app.view.ContactListItemView;
import com.zhanggb.contacts.app.view.HeaderView;
import com.zhanggb.contacts.app.view.pullableview.PullToRefreshLayout;
import com.zhanggb.contacts.app.view.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaobo
 * @since 09/26/2016
 */
public class HomeContactFragment extends Fragment {
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Context context;
    private View view;
    private HeaderView headerView;
    private PullToRefreshLayout refreshLayout;
    private PullableListView listView;
    private EditText searchEdit;
    private ContactListAdapter listAdapter;
    private ContactCacheManager contactCacheManager;
    private Handler handler;
    private boolean isSearchLocked;
    private List<Contact> contacts = new ArrayList<Contact>();

    protected WindowManager windowManager;
    protected TextView popupTextView;
    protected LinearLayout popupLayout;
    protected AlphabetView alphabetView;
    private boolean isAlphabetViewMoving;

    private Log logger = Log.build(getClass());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity();
        view = inflater.inflate(R.layout.home_contact_fragment, null);
        handler = new Handler();
        contactCacheManager = ContactCacheManager.getInstance();
        initView();
        setupSearchView();
        createPopupLayout();
        new FindContactTask().execute();
        return view;
    }

    private void initView() {
        headerView = (HeaderView) view.findViewById(R.id.header_view);
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        listView = (PullableListView) view.findViewById(R.id.list_view);
        alphabetView = (AlphabetView) view.findViewById(R.id.contact_alphabet);
        headerView.getMiddleText().setText("联系人");
        headerView.getLeftImage().setVisibility(View.GONE);
        listAdapter = new ContactListAdapter();
        listView.setAdapter(listAdapter);
        refreshLayout.setCanPullDown(false);
        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                new FindContactTask().execute();
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
        final ImageView searchCloseImage = (ImageView) searchView.findViewById(R.id.search_close_image);
        searchEdit.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        searchEdit.setHint("搜索联系人");
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
                        new FindContactTask().execute();
                    }
                }, 500);
                if (StringUtils.isNotEmpty(StringUtils.trimToEmpty(searchEdit.getText().toString()))) {
                    searchCloseImage.setVisibility(View.VISIBLE);
                } else {
                    searchCloseImage.setVisibility(View.GONE);
                }
            }
        });
        searchCloseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEdit.setText("");
            }
        });
    }

    //快捷滚动条弹出框
    protected void createPopupLayout() {
        popupLayout = new LinearLayout(context);
        popupLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popupLayout.setBackgroundResource(R.mipmap.bg_contact_indexer_pop);
        popupLayout.setGravity(Gravity.CENTER);
        popupLayout.setPadding(2, 2, 2, 2);

        popupTextView = new TextView(context);
        popupTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38);
        popupTextView.setTypeface(Typeface.DEFAULT_BOLD);
        popupTextView.setTextColor(Color.WHITE);

        popupLayout.addView(popupTextView);
        popupLayout.setVisibility(View.INVISIBLE);

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int size = Math.round(DeviceUtils.getPx(context, 80));
        try {
            windowManager.addView(popupLayout,
                    new WindowManager.LayoutParams(
                            size, size,
                            WindowManager.LayoutParams.TYPE_APPLICATION,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            PixelFormat.TRANSLUCENT));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class FindContactTask extends AsyncTask<Void, Void, Void> {

        private boolean isRunning;
        private final Object lock = new Object();

        private void lockSearch() {
            while (isRunning) {
                synchronized (lock) {
                    try {
                        isSearchLocked = true;
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            isRunning = true;
        }

        private void resumeSearch() {
            isRunning = false;
            if (isSearchLocked) {
                isSearchLocked = false;
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            resumeSearch();
            refreshLayout.refreshFinish(0);
        }

        @Override
        protected Void doInBackground(Void... params) {
            lockSearch();
            if (StringUtils.isNotBlank(getSearchKeyword())) {
                contacts = contactCacheManager.searchContacts(getSearchKeyword());
            } else {
                contacts = contactCacheManager.findContacts(context);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listAdapter.setContacts(contacts);
                    listAdapter.notifyDataSetChanged();
                }
            });
            new AlphabetUpdater().execute(contacts);
            return null;
        }
    }

    class AlphabetUpdater extends AsyncTask<List<Contact>, Object, Object> {

        @Override
        protected Object doInBackground(final List<Contact>... objects) {
            List<Contact> caches = objects[0];
            final Map<String, Integer> maps = new HashMap<String, Integer>();
            for (int i = 0; i < caches.size(); i++) {
                String key = String.valueOf(caches.get(i).getSortKey().toUpperCase().charAt(0));
                if (StringUtils.isNotBlank(key) && ALPHABET.contains(key)) {
                    if (!maps.containsKey(key)) {
                        maps.put(key, i);
                    }
                } else {
                    if (!maps.containsKey("#")) {
                        maps.put("#", i);
                    }
                }
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    alphabetView.setVisibility(View.VISIBLE);
                    alphabetView.setCandidateAlphabets(new ArrayList<String>(maps.keySet()));
                    alphabetView.setAlphabetListener(new AlphabetView.AlphabetListener() {
                        @Override
                        public void onChange(String current) {
                            int index = maps.get(current) == null ? -1 : maps.get(current);
                            isAlphabetViewMoving = true;
                            popupTextView.setText(String.valueOf(current));
                            if (popupLayout.getVisibility() == View.INVISIBLE) {
                                popupLayout.setVisibility(View.VISIBLE);
                            }
                            if (index != -1) {
                                listView.setSelection(index);
                            }
                        }

                        @Override
                        public void onUp() {
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isAlphabetViewMoving = false;
                                }
                            }, 200);
                            if (popupLayout.getVisibility() == View.VISIBLE) {
                                popupLayout.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            });
            return null;
        }
    }

    private String getSearchKeyword() {
        return StringUtils.trimToEmpty(searchEdit.getText().toString());
    }

    class ContactListAdapter extends BaseAdapter {

        private List<Contact> contacts = new ArrayList<Contact>();

        public void setContacts(List<Contact> contacts) {
            this.contacts = contacts;
        }

        @Override
        public int getCount() {
            return contacts.size();
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
            ContactListItemView itemView;
            final Contact contact = contacts.get(position);
            if (convertView != null) {
                itemView = (ContactListItemView) convertView;
            } else {
                itemView = new ContactListItemView(context);
            }
//            itemView.getNameText().setText(contact.getName());
//            itemView.getPhoneText().setText(contact.getPhoneNumber());
            String searchMode = getSearchKeyword();
            boolean isSearchMode = StringUtils.isNotEmpty(searchMode);
            if (!isSearchMode) {
                contact.setMatchs(null);
                contact.setMatchNumber(null);
            }
            itemView.setDisplayName(contact.getName(), contact.getMatchs());
            itemView.setPhoneNum(contact.getPhoneNumber(), searchMode);
//            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contact.getId());
//            ImageLoader.getInstance().displayImage("content://" + uri.getPath(), itemView.getPhotoImage());
            if (contact.getPhotoId() > 0) {
                BitmapUtil.getInstance().loadBitmap(String.valueOf(contact.getContactId()), itemView.getPhotoImage(), context);
            } else {
                itemView.getPhotoImage().setImageResource(R.mipmap.default_avatar);
            }
            logger.debug("contact:  " + contact.toString());
            itemView.getContactLinear().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ContactDetailActivity.class);
                    intent.putExtra("contact", contact);
                    startActivity(intent);
                }
            });
            return itemView;
        }
    }
}
