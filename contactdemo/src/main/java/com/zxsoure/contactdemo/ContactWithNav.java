package com.zxsoure.contactdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created
 *
 * @author 张枭
 * @version V1.0
 * @Title: ${enclosing_method}
 * @Description: ${todo}(这里用一句话描述这个方法的作用)
 * @date 2016/1/11
 * @throws ${tags}
 */
public class ContactWithNav extends ContactNormal {

    private TextView nav;

    public ContactWithNav(View itemView) {
        super(itemView);

        nav = (TextView) itemView.findViewById(R.id.contact_nav);

    }

    public TextView getNav() {
        return nav;
    }
}
