package com.steven.materialdesignlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_material_designer.*

/**
 * 本模块是演练android 5.0推出的Material Designer
 * ToolBar: 在清单文件里面使用 android:label="Fruits"即修改Toolbar中显示的文字内容
 * Menu: 添加菜单步骤：
 *       创建menu文件夹并在里面添加菜单文件
 *       app:showAsAction="always/ifRoom/never"
 *       always表示永远显示在Toolbar中
 *       ifRoom表示屏幕空间足够的情况下显示在Toolbar中，不够的话就显示在菜单中
 *       never表示永远显示在菜单中
 * DrawerLayout:侧滑菜单
 * NavigationView:配合侧滑菜单使用
 * FloatingActionButton:悬停按钮
 * Snackbar:类似于Toast的弹出提示
 * CoordinatorLayout:监听其所有子控件的各种事件
 * CardView:卡片式布局
 * RecyclerView:填充列表布局
 */
class MaterialDesignerActivity : AppCompatActivity() {

    private var mFruitList: MutableList<Fruit> = mutableListOf()
    private var mFruitAdapter: FruitAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_designer)
        setSupportActionBar(toolbar)
        // 侧滑菜单默认选中
        nav_view.setCheckedItem(R.id.nav_call)
        nav_view.setNavigationItemSelectedListener {
            drawer_layout.closeDrawers()
            true
        }
        // 悬停按钮点击事件
        fab.setOnClickListener {
            Snackbar.make(it, "Data deleted", Snackbar.LENGTH_SHORT).setAction(
                "Undo"
            ) {
                Toast.makeText(this@MaterialDesignerActivity, "Data restored", Toast.LENGTH_SHORT)
                    .show()
            }.show()
        }
        // RecyclerView使用
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val fruitOne = Fruit("拼盘1", R.drawable.fruit_one)
        val fruitTwo = Fruit("拼盘2", R.drawable.fruit_two)
        val fruitThree = Fruit("拼盘3", R.drawable.fruit_three)
        val fruitFour = Fruit("拼盘4", R.drawable.fruit_four)
        val fruitFive = Fruit("拼盘5", R.drawable.fruit_five)
        val fruitSix = Fruit("拼盘6", R.drawable.fruit_six)
        val fruitSeven = Fruit("拼盘7", R.drawable.fruit_seven)
        val fruitEight = Fruit("拼盘8", R.drawable.fruit_eight)
        mFruitList.add(fruitOne)
        mFruitList.add(fruitTwo)
        mFruitList.add(fruitThree)
        mFruitList.add(fruitFour)
        mFruitList.add(fruitFive)
        mFruitList.add(fruitSix)
        mFruitList.add(fruitSeven)
        mFruitList.add(fruitEight)
        mFruitAdapter = FruitAdapter(mFruitList)
        val layoutManager = GridLayoutManager(this,2)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = mFruitAdapter
    }

    /**
     * 创建菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.designer, menu)
        return true
    }

    /**
     * 响应菜单的点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.icon_one -> drawer_layout.openDrawer(GravityCompat.START)
            R.id.icon_two -> Toast.makeText(this, "你点击了第二个菜单", Toast.LENGTH_SHORT).show()
            R.id.icon_three -> Toast.makeText(this, "你点击了第三个菜单", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
