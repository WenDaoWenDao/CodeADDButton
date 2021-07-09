package com.example.codeaddbutton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private LinkedList<Button> ListButton;//动态生成控件存储链表
    private ConstraintLayout CL_Scroll_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListButton = new LinkedList<>();
        CL_Scroll_View = findViewById(R.id.CL_SCROLLVIEW);
    }

    //动态添加控件
    public void BTN_ADD_Click(View view) {
        final Button BTN_ADD_One = new Button(this);

        RelativeLayout.LayoutParams btnAddParam = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,//自定义内容宽度，如果不设置宽度，默认为起始位置到屏幕最边缘
                ViewGroup.LayoutParams.WRAP_CONTENT);//适应内容高度

        // 设置属性
        BTN_ADD_One.setId(ListButton.size());
        String tx = getString(R.string.BTN_ADD_one) + ListButton.size() * 10;
        BTN_ADD_One.setText(tx);
        BTN_ADD_One.setAllCaps(false);
        BTN_ADD_One.bringToFront();
        BTN_ADD_One.setPadding(0, 0, 0, 0);//设置内容显示边距
        BTN_ADD_One.setOnClickListener(this::ListButtons_Click);//简写添加点击事件

        int Y = 500 + ListButton.size() * 200;

        BTN_ADD_One.setX(200);
        BTN_ADD_One.setY(Y);

        CL_Scroll_View.setMinHeight(CL_Scroll_View.getHeight() < Y ? Y : CL_Scroll_View.getHeight());//动态修改Layout的高度

        btnAddParam.setMargins(200, Y, 0, 0);//动态设置显示位置
//        btnAddParam.width = 260;//此处修改默认到屏幕最边缘的宽度，此处设置为260
//        btnAddParam.height = 150;坐标位置

        BTN_ADD_One.setLayoutParams(btnAddParam);//绑定layout

        ListButton.add(BTN_ADD_One);
        CL_Scroll_View.addView(ListButton.getLast(), btnAddParam);//添加到主界面

    }

    //动态按键点击事件
    private void ListButtons_Click(View view) {
        final int index = view.getId();

        ViewGroup vg = (ViewGroup) view.getParent();//获取控件所在组
        vg.removeView(view);//删除控件

        Id2FindBtn(index);
    }

    //删除动态添加的控件
    public void BTN_REM_Click(View view) {

        if (ListButton.size() == 0) {//如果链表里无控件则退出
            return;
        }

        int index = ListButton.getLast().getId();
        System.out.println("BTN_REM_Click index: " + index);
        View v = findViewById(index);//获取控件视图
        ViewGroup vg = (ViewGroup) v.getParent();//获取控件所在组
        vg.removeView(v);//删除控件

        ListButton.removeLast();
        CL_Scroll_View.setMinHeight(CL_Scroll_View.getHeight() - 200);//每次删除一个，高度减200pix
        System.out.println("BTN_REM_Click ListButton.size(): " + ListButton.size());
    }

    //通过ID查找BTN
    private void Id2FindBtn(int index) {
        boolean isFind = false;

        for (int i = 0; i < ListButton.size(); i++) {

            if (ListButton.get(index).getId() == i) {
                isFind = true;
                ListButton.remove(i);//删除当前数据，链表后面数据自动向前迁移
                System.out.println("Id2FindBtn Remove: " + i);

                if (ListButton.size() == 0 || i >= ListButton.size()) {//如果已经全部删除或删除的是最后一个
                    return;
                }
                ListButton.get(i).setId(i);//更新当前删除数据后面一个数据的ID
                continue;
            }

            if (isFind) {//更新后面所有的ID
                ListButton.get(i).setId((ListButton.get(i).getId()) - 1);
            }
        }
    }
}