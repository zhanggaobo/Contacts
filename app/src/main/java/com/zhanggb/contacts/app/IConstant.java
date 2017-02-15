package com.zhanggb.contacts.app;

/**
 * @author zhanggaobo
 * @since 10/24/2016
 */
public interface IConstant {
    interface VersionControl {
        boolean IS_DEBUG = true;
    }

    interface Extra {
        String EXTRA = "extra_";
        String CALL_NETWORK = EXTRA + "call_network";
        String CALL_TIMES = EXTRA + "call_times";
        String RAWCONTACTID = EXTRA + "rawcontactid";
        String CONTACT_ID = EXTRA + "contactid";
        String CONTACT_NAME = EXTRA + "contact_name";
        String SEND_MESSAGE = EXTRA + "send_message";
        String SEND_GROUP_MESSAGE = EXTRA + "send_group_message";
        String SEND_EMAIL = EXTRA + "send_email";
        String SEND_VCARD = EXTRA + "send_vcard";
        String SEND_VCARD_MESSAGE = EXTRA + "send_vcard_message";
        String SEND_VCARD_CALLBACK = EXTRA + "send_vcard_callback";
        String DELETE_CONTACT = EXTRA + "delete_contact";
        String GROUP_ID = EXTRA + "add_groupid";
        String REMOVE_FAVORITE = EXTRA + "remove_favorite";
        String ADD_GROUP_MEMBERS = EXTRA + "add_groupmembers";
        String ADD_ICE_GROUP_MEMBERS = EXTRA + "add_ice_groupmembers";
        String REMOVE_GROUP_MEMBERS = EXTRA + "remove_groupmembers";
        String CHOOSE_FAVORITE = EXTRA + "choose_favorite";
        String CALL_LOG_NUMBER = EXTRA + "call_log_number";
        String CONTACT_CALL_LOG_NUMBERS = EXTRA + "contact_call_log_numbers";
        String CALL_LOG_TIMES = EXTRA + "call_log_times";
        String CALL_LOG_ID = EXTRA + "call_log_id";
        //        String CONTACT_PHONE_NUMBER = EXTRA + "contactphone_number";
        String CONTACT_PHONE_NUMBER = "phone";
        String SINGLE_CHOOSE_CONTACT = "single_choose";
        String MULTI_CHOOSE_CONTACT = "multi_choose";
        String CONTACT_PHONE_LIST = EXTRA + "contact_phone_list";
        String CONTACT_CALL_NAME = EXTRA + "contact_call_name";
        String CONTACT_DISPLAYNAME_LIST = EXTRA + "contact_displayname_list";
        String FEEDBACK_CONTENT_ID = EXTRA + "feedback_content_id";
        String FEEDBACK_LIST_KEY = EXTRA + "feedback_list_key";
        String MESSAGE_THREAD_ID = EXTRA + "message_thread_id";
        String MESSAGE_ITEM_ID = EXTRA + "message_item_id";
        String MESSAGE_ADDRESS = EXTRA + "message_address";
        String SEND_SIGN_MESSAGE = EXTRA + "send_vcard_sign";
        String SEND_SET_CALL = EXTRA + "send_set_call";
        String SEND_GET_CALL = EXTRA + "send_get_call";
        String CHOOSE_CARD_PHOTO = EXTRA + "choose_card_photo";
        String MESSAGE_DELETE_TYPE = EXTRA + "message_deletetype";
        String MESSAGE_CONTENT = EXTRA + "message_content";
        String MESSAGE_CHAT_NOTIFICATION = EXTRA + "message_chat_notification";
        String MESSAGE_UNREAD = EXTRA + "message_list_unread";
        String HOME_EXTRA_TAB = EXTRA + "home_extra_tab";
        String IS_MULTIPLE_CHAT = EXTRA + "is_multiple_chat";
        //sync
        String REPORT_ID = EXTRA + "report_id";
        String SYNCSKIP = EXTRA + "sync_skip";
        String SYNC_TAB = EXTRA + "sync_tabpage";

        String VIEW_PAGER_CURRENT = EXTRA + "view_pager_current";
        String SYS_MSG_ID = EXTRA + "sysmsg_id";
        String SYS_MSG_JOB_ID = EXTRA + "sysmsg_job_id";

        String PUBLIC_INFO_CATEGORY_ID = EXTRA + "pubilc_info_categoryid";
        String PUBLIC_INFO_CATEGORY_NAME = EXTRA + "pubilc_info_categoryname";
        String PUBLIC_INFO_COMPANY_ID = EXTRA + "pubilc_info_companyid";
    }

    public interface Message {
        String _ID = "_id";
        String THREAD_ID = "thread_id";
        String ADDRESS = "message_address";
        String COUNT_OF_UNREAD = "message_count_of_unread";
        String LAST_ITEM_CONTENT = "message_last_item_content";
        String LAST_ITEM_DATE = "message_last_item_date";
        String COUNT_OF_ITEM = "message_count_of_item";
        String RECIPINET_IDS = "message_recipinet_ids";
        String TYPE = "message_type";

        String COUNT_OF_ITEM_MMS = "message_count_of_item_mms";
        int CATEGORY_SMS = 1;
        int CATEGORY_MMS = 2;
        String TAB_ID_SMS = "111111";
        String TAB_ID_MMS = "222222";
    }

    public interface Attribution {
        String ATTRIBUTION_TABLE_NAME = "t_numberattribution";
        String NUMBER = "number";
        String AREA_CODE = "areaCode";
    }
}
