package com.zxsoure.contactdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
public class ContactNormal extends RecyclerView.ViewHolder {

    private TextView name;

    private ImageView img;

    public ContactNormal(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.contact_name);
        img = (ImageView)itemView.findViewById(R.id.contact_avatar);
    }

    public ImageView getImg() {
        return img;
    }

    public TextView getName() {
        return name;
    }

}
