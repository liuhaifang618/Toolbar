# Toolbar

#### 自定义toolbar，解决toolbar在PAD上适配的问题.遵循Toolbar的api来实现的自定义toolbar，减少跟换toolbar造成的工作量。


<img src="/snapshot/shapshot.gif" alt="alt text" style="width:200;height:200">

#Usage

1. 添加xml布局.

    ```xml
        <com.safewaychinay.toolbar.widget.Toolbar
            android:id="@+id/toolbar"
            android:background="@android:color/black"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />
    ```

2. 初始化布局.

    ```java
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout.setScrimColor(Color.parseColor("#66000000"));
        toolbar.setTitle("营养");
        toolbar.addMenu(R.menu.menu_main);
        toolbar.setOnMenuClickLisnter(new Toolbar.OnMenuClickLisnter() {
            @Override
            public void onMenuClick(int id) {
                Toast.makeText(getApplicationContext(), "adsadad", Toast.LENGTH_SHORT).show();
            }
        });
        ToolbarDrawerToggle toggle = new ToolbarDrawerToggle(drawerLayout, toolbar);
        toggle.syncState();
    ```

#联系我
欢迎共同学习交流：liuhaifang618@163.com


#最低支持系统版本
  
  * Android  2.3+
  
# 历史记录


### Version: 1.0

  * 初始化编译


## License

    Copyright 2015, liuhaifang

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
