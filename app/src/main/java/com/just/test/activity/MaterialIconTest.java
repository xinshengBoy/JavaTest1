package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.MaterialIcon;
import com.just.test.widget.MyActionBar;

/**
 * 将图标做成字体的形式，直接在项目中使用Material图标，不用自己去加载资源文件，也不用管图标的大小适配。
 *https://github.com/vin89423/material-design-icons-for-android
 * http://www.itlanbao.com/code/20150806/10033/100144.html
 * Created by admin on 2017/6/13.
 */

public class MaterialIconTest extends Activity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.layout_material_icon);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "图标变字体");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        /**一定要设置图片具体的大小，不然无法显示**/
        ImageView iv_material_1 = (ImageView) findViewById(R.id.iv_material_1);
        ImageView iv_material_2 = (ImageView) findViewById(R.id.iv_material_2);
        ImageView iv_material_3 = (ImageView) findViewById(R.id.iv_material_3);
        ImageView iv_material_4 = (ImageView) findViewById(R.id.iv_material_4);
        ImageView iv_material_5 = (ImageView) findViewById(R.id.iv_material_5);
        ImageView iv_material_6 = (ImageView) findViewById(R.id.iv_material_6);
        ImageView iv_material_7 = (ImageView) findViewById(R.id.iv_material_7);
        ImageView iv_material_8 = (ImageView) findViewById(R.id.iv_material_8);
        ImageView iv_material_9 = (ImageView) findViewById(R.id.iv_material_9);
        ImageView iv_material_10 = (ImageView) findViewById(R.id.iv_material_10);
        ImageView iv_material_11 = (ImageView) findViewById(R.id.iv_material_11);
        ImageView iv_material_12 = (ImageView) findViewById(R.id.iv_material_12);
        ImageView iv_material_13 = (ImageView) findViewById(R.id.iv_material_13);
        ImageView iv_material_14 = (ImageView) findViewById(R.id.iv_material_14);
        ImageView iv_material_15 = (ImageView) findViewById(R.id.iv_material_15);
        ImageView iv_material_16 = (ImageView) findViewById(R.id.iv_material_16);
        ImageView iv_material_17 = (ImageView) findViewById(R.id.iv_material_17);
        ImageView iv_material_18 = (ImageView) findViewById(R.id.iv_material_18);
        ImageView iv_material_19 = (ImageView) findViewById(R.id.iv_material_19);
        ImageView iv_material_20 = (ImageView) findViewById(R.id.iv_material_20);
        ImageView iv_material_21 = (ImageView) findViewById(R.id.iv_material_21);
        ImageView iv_material_22 = (ImageView) findViewById(R.id.iv_material_22);
        ImageView iv_material_23 = (ImageView) findViewById(R.id.iv_material_23);
        ImageView iv_material_24 = (ImageView) findViewById(R.id.iv_material_24);
        ImageView iv_material_25 = (ImageView) findViewById(R.id.iv_material_25);
        ImageView iv_material_26 = (ImageView) findViewById(R.id.iv_material_26);
        ImageView iv_material_27 = (ImageView) findViewById(R.id.iv_material_27);
        ImageView iv_material_28 = (ImageView) findViewById(R.id.iv_material_28);
        ImageView iv_material_29 = (ImageView) findViewById(R.id.iv_material_29);
        ImageView iv_material_30 = (ImageView) findViewById(R.id.iv_material_30);
        ImageView iv_material_31 = (ImageView) findViewById(R.id.iv_material_31);
        ImageView iv_material_32 = (ImageView) findViewById(R.id.iv_material_32);
        ImageView iv_material_33 = (ImageView) findViewById(R.id.iv_material_33);
        ImageView iv_material_34 = (ImageView) findViewById(R.id.iv_material_34);
        ImageView iv_material_35 = (ImageView) findViewById(R.id.iv_material_35);
        ImageView iv_material_36 = (ImageView) findViewById(R.id.iv_material_36);
        ImageView iv_material_37 = (ImageView) findViewById(R.id.iv_material_37);
        ImageView iv_material_38 = (ImageView) findViewById(R.id.iv_material_38);
        ImageView iv_material_39 = (ImageView) findViewById(R.id.iv_material_39);
        ImageView iv_material_40 = (ImageView) findViewById(R.id.iv_material_40);
        ImageView iv_material_41 = (ImageView) findViewById(R.id.iv_material_41);
        ImageView iv_material_42 = (ImageView) findViewById(R.id.iv_material_42);
        ImageView iv_material_43 = (ImageView) findViewById(R.id.iv_material_43);
        ImageView iv_material_44 = (ImageView) findViewById(R.id.iv_material_44);
        ImageView iv_material_45 = (ImageView) findViewById(R.id.iv_material_45);
        ImageView iv_material_46 = (ImageView) findViewById(R.id.iv_material_46);
        ImageView iv_material_47 = (ImageView) findViewById(R.id.iv_material_47);
        ImageView iv_material_48 = (ImageView) findViewById(R.id.iv_material_48);
        ImageView iv_material_49 = (ImageView) findViewById(R.id.iv_material_49);
        ImageView iv_material_50 = (ImageView) findViewById(R.id.iv_material_50);
        ImageView iv_material_51 = (ImageView) findViewById(R.id.iv_material_51);
        ImageView iv_material_52 = (ImageView) findViewById(R.id.iv_material_52);
        ImageView iv_material_53 = (ImageView) findViewById(R.id.iv_material_53);
        ImageView iv_material_54 = (ImageView) findViewById(R.id.iv_material_54);
        ImageView iv_material_55 = (ImageView) findViewById(R.id.iv_material_55);
        ImageView iv_material_56 = (ImageView) findViewById(R.id.iv_material_56);
        ImageView iv_material_57 = (ImageView) findViewById(R.id.iv_material_57);
        ImageView iv_material_58 = (ImageView) findViewById(R.id.iv_material_58);
        ImageView iv_material_59 = (ImageView) findViewById(R.id.iv_material_59);
        ImageView iv_material_60 = (ImageView) findViewById(R.id.iv_material_60);
        ImageView iv_material_61 = (ImageView) findViewById(R.id.iv_material_61);
        ImageView iv_material_62 = (ImageView) findViewById(R.id.iv_material_62);
        ImageView iv_material_63 = (ImageView) findViewById(R.id.iv_material_63);
        ImageView iv_material_64 = (ImageView) findViewById(R.id.iv_material_64);
        ImageView iv_material_65 = (ImageView) findViewById(R.id.iv_material_65);
        ImageView iv_material_66 = (ImageView) findViewById(R.id.iv_material_66);
        ImageView iv_material_67 = (ImageView) findViewById(R.id.iv_material_67);
        ImageView iv_material_68 = (ImageView) findViewById(R.id.iv_material_68);
        ImageView iv_material_69 = (ImageView) findViewById(R.id.iv_material_69);
        ImageView iv_material_70 = (ImageView) findViewById(R.id.iv_material_70);
        ImageView iv_material_71 = (ImageView) findViewById(R.id.iv_material_71);
        ImageView iv_material_72 = (ImageView) findViewById(R.id.iv_material_72);
        ImageView iv_material_73 = (ImageView) findViewById(R.id.iv_material_73);
        ImageView iv_material_74 = (ImageView) findViewById(R.id.iv_material_74);
        ImageView iv_material_75 = (ImageView) findViewById(R.id.iv_material_75);
        ImageView iv_material_76 = (ImageView) findViewById(R.id.iv_material_76);
        ImageView iv_material_77 = (ImageView) findViewById(R.id.iv_material_77);
        ImageView iv_material_78 = (ImageView) findViewById(R.id.iv_material_78);
        ImageView iv_material_79 = (ImageView) findViewById(R.id.iv_material_79);
        ImageView iv_material_80 = (ImageView) findViewById(R.id.iv_material_80);
        ImageView iv_material_81 = (ImageView) findViewById(R.id.iv_material_81);
        ImageView iv_material_82 = (ImageView) findViewById(R.id.iv_material_82);
        ImageView iv_material_83 = (ImageView) findViewById(R.id.iv_material_83);
        ImageView iv_material_84 = (ImageView) findViewById(R.id.iv_material_84);
        ImageView iv_material_85 = (ImageView) findViewById(R.id.iv_material_85);
        ImageView iv_material_86 = (ImageView) findViewById(R.id.iv_material_86);
        ImageView iv_material_87 = (ImageView) findViewById(R.id.iv_material_87);
        ImageView iv_material_88 = (ImageView) findViewById(R.id.iv_material_88);
        ImageView iv_material_89 = (ImageView) findViewById(R.id.iv_material_89);
        ImageView iv_material_90 = (ImageView) findViewById(R.id.iv_material_90);
        ImageView iv_material_91 = (ImageView) findViewById(R.id.iv_material_91);
        ImageView iv_material_92 = (ImageView) findViewById(R.id.iv_material_92);
        ImageView iv_material_93 = (ImageView) findViewById(R.id.iv_material_93);
        ImageView iv_material_94 = (ImageView) findViewById(R.id.iv_material_94);
        ImageView iv_material_95 = (ImageView) findViewById(R.id.iv_material_95);
        ImageView iv_material_96 = (ImageView) findViewById(R.id.iv_material_96);
        ImageView iv_material_97 = (ImageView) findViewById(R.id.iv_material_97);
        ImageView iv_material_98 = (ImageView) findViewById(R.id.iv_material_98);
        ImageView iv_material_99 = (ImageView) findViewById(R.id.iv_material_99);
        ImageView iv_material_100 = (ImageView) findViewById(R.id.iv_material_100);

        //// TODO: 2017/6/13 可设置颜色
        MaterialIcon md = new MaterialIcon(context);
        iv_material_1.setImageDrawable(md.getDrawable("ic_3d_rotation"));
        iv_material_2.setImageDrawable(md.getDrawable("ic_accessibility"));
        iv_material_3.setImageDrawable(md.getDrawable("ic_account_balance"));
        iv_material_4.setImageDrawable(md.getDrawable("ic_account_balance_wallet"));
        iv_material_5.setImageDrawable(md.getDrawable("ic_account_box"));
        iv_material_6.setImageDrawable(md.getDrawable("ic_account_child"));
        iv_material_7.setImageDrawable(md.getDrawable("ic_account_circle"));
        iv_material_8.setImageDrawable(md.getDrawable("ic_add_shopping_cart"));
        iv_material_9.setImageDrawable(md.getDrawable("ic_alarm"));
        iv_material_10.setImageDrawable(md.getDrawable("ic_alarm_add"));
        iv_material_11.setImageDrawable(md.getDrawable("ic_alarm_off"));
        iv_material_12.setImageDrawable(md.getDrawable("ic_alarm_on"));
        iv_material_13.setImageDrawable(md.getDrawable("ic_android"));
        iv_material_14.setImageDrawable(md.getDrawable("ic_announcement"));
        iv_material_15.setImageDrawable(md.getDrawable("ic_aspect_ratio"));
        iv_material_16.setImageDrawable(md.getDrawable("ic_assessment"));
        iv_material_17.setImageDrawable(md.getDrawable("ic_assignment"));
        iv_material_18.setImageDrawable(md.getDrawable("ic_assignment_ind"));
        iv_material_19.setImageDrawable(md.getDrawable("ic_assignment_late"));
        iv_material_20.setImageDrawable(md.getDrawable("ic_assignment_return"));
        iv_material_21.setImageDrawable(md.getDrawable("ic_assignment_returned"));
        iv_material_22.setImageDrawable(md.getDrawable("ic_assignment_turned_in"));
        iv_material_23.setImageDrawable(md.getDrawable("ic_autorenew"));
        iv_material_24.setImageDrawable(md.getDrawable("ic_backup"));
        iv_material_25.setImageDrawable(md.getDrawable("ic_book"));
        iv_material_26.setImageDrawable(md.getDrawable("ic_bookmark"));
        iv_material_27.setImageDrawable(md.getDrawable("ic_bookmark_outline"));
        iv_material_28.setImageDrawable(md.getDrawable("ic_bug_report"));
        iv_material_29.setImageDrawable(md.getDrawable("ic_cached"));
        iv_material_30.setImageDrawable(md.getDrawable("ic_class"));
        iv_material_31.setImageDrawable(md.getDrawable("ic_credit_card"));
        iv_material_32.setImageDrawable(md.getDrawable("ic_dashboard"));
        iv_material_33.setImageDrawable(md.getDrawable("ic_delete"));
        iv_material_34.setImageDrawable(md.getDrawable("ic_description"));
        iv_material_35.setImageDrawable(md.getDrawable("ic_dns"));
        iv_material_36.setImageDrawable(md.getDrawable("ic_done"));
        iv_material_37.setImageDrawable(md.getDrawable("ic_done_all"));
        iv_material_38.setImageDrawable(md.getDrawable("ic_event"));
        iv_material_39.setImageDrawable(md.getDrawable("ic_exit_to_app"));
        iv_material_40.setImageDrawable(md.getDrawable("ic_explore"));
        iv_material_41.setImageDrawable(md.getDrawable("ic_extension"));
        iv_material_42.setImageDrawable(md.getDrawable("ic_face_unlock"));
        iv_material_43.setImageDrawable(md.getDrawable("ic_favorite"));
        iv_material_44.setImageDrawable(md.getDrawable("ic_favorite_outline"));
        iv_material_45.setImageDrawable(md.getDrawable("ic_find_in_page"));
        iv_material_46.setImageDrawable(md.getDrawable("ic_find_replace"));
        iv_material_47.setImageDrawable(md.getDrawable("ic_flip_to_back"));
        iv_material_48.setImageDrawable(md.getDrawable("ic_flip_to_front"));
        iv_material_49.setImageDrawable(md.getDrawable("ic_get_app"));
        iv_material_50.setImageDrawable(md.getDrawable("ic_grade"));
        iv_material_51.setImageDrawable(md.getDrawable("ic_group_work"));
        iv_material_52.setImageDrawable(md.getDrawable("ic_help"));
        iv_material_53.setImageDrawable(md.getDrawable("ic_highlight_remove"));
        iv_material_54.setImageDrawable(md.getDrawable("ic_history"));
        iv_material_55.setImageDrawable(md.getDrawable("ic_home"));
        iv_material_56.setImageDrawable(md.getDrawable("ic_https"));
        iv_material_57.setImageDrawable(md.getDrawable("ic_info"));
        iv_material_58.setImageDrawable(md.getDrawable("ic_info_outline"));
        iv_material_59.setImageDrawable(md.getDrawable("ic_input"));
        iv_material_60.setImageDrawable(md.getDrawable("ic_invert_colors"));
        iv_material_61.setImageDrawable(md.getDrawable("ic_label"));
        iv_material_62.setImageDrawable(md.getDrawable("ic_label_outline"));
        iv_material_63.setImageDrawable(md.getDrawable("ic_language"));
        iv_material_64.setImageDrawable(md.getDrawable("ic_launch"));
        iv_material_65.setImageDrawable(md.getDrawable("ic_list"));
        iv_material_66.setImageDrawable(md.getDrawable("ic_lock"));
        iv_material_67.setImageDrawable(md.getDrawable("ic_lock_open"));
        iv_material_68.setImageDrawable(md.getDrawable("ic_lock_outline"));
        iv_material_69.setImageDrawable(md.getDrawable("ic_loyalty"));
        iv_material_70.setImageDrawable(md.getDrawable("ic_markunread_mailbox"));
        iv_material_71.setImageDrawable(md.getDrawable("ic_note_add"));
        iv_material_72.setImageDrawable(md.getDrawable("ic_open_in_browser"));
        iv_material_73.setImageDrawable(md.getDrawable("ic_open_in_new"));
        iv_material_74.setImageDrawable(md.getDrawable("ic_open_with"));
        iv_material_75.setImageDrawable(md.getDrawable("ic_pageview"));
        iv_material_76.setImageDrawable(md.getDrawable("ic_payment"));
        iv_material_77.setImageDrawable(md.getDrawable("ic_perm_camera_mic"));
        iv_material_78.setImageDrawable(md.getDrawable("ic_perm_contact_cal"));
        iv_material_79.setImageDrawable(md.getDrawable("ic_perm_data_setting"));
        iv_material_80.setImageDrawable(md.getDrawable("ic_perm_device_info"));
        iv_material_81.setImageDrawable(md.getDrawable("ic_perm_identity"));
        iv_material_82.setImageDrawable(md.getDrawable("ic_perm_media"));
        iv_material_83.setImageDrawable(md.getDrawable("ic_perm_phone_msg"));
        iv_material_84.setImageDrawable(md.getDrawable("ic_perm_scan_wifi"));
        iv_material_85.setImageDrawable(md.getDrawable("ic_polymer"));
        iv_material_86.setImageDrawable(md.getDrawable("ic_print"));
        iv_material_87.setImageDrawable(md.getDrawable("ic_query_builder"));
        iv_material_88.setImageDrawable(md.getDrawable("ic_question_answer"));
        iv_material_89.setImageDrawable(md.getDrawable("ic_receipt"));
        iv_material_90.setImageDrawable(md.getDrawable("ic_redeem"));
        iv_material_91.setImageDrawable(md.getDrawable("ic_report_problem"));
        iv_material_92.setImageDrawable(md.getDrawable("ic_restore"));
        iv_material_93.setImageDrawable(md.getDrawable("ic_room"));
        iv_material_94.setImageDrawable(md.getDrawable("ic_schedule"));
        iv_material_95.setImageDrawable(md.getDrawable("ic_search"));
        iv_material_96.setImageDrawable(md.getDrawable("ic_settings"));
        iv_material_97.setImageDrawable(md.getDrawable("ic_settings_applications"));
        iv_material_98.setImageDrawable(md.getDrawable("ic_settings_backup_restore"));
        iv_material_99.setImageDrawable(md.getDrawable("ic_settings_bluetooth"));
        iv_material_100.setImageDrawable(md.getDrawable("ic_settings_cell"));
    }
}
