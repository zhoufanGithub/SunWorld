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
import com.google.android.material.appbar.CollapsingToolbarLayout
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
 * AppBarLayout:加强版的ToolBar
 *       app:layout_scrollFlags="scroll|enterAlways|snap"
 *       scroll表示当RecyclerView向上滚动的时候，Toolbar会跟着一起向上滚动并隐藏
 *       enterAlways表示当RecyclerView向下滚动的时候，Toolbar会跟着一起向下滚动并显示
 *       snap表示当Toolbar还没有完全显示或者隐藏的时候，会根据当前滚动的距离，自动选择隐藏还是显示
 * SwipeRefreshLayout:实现下拉刷新
 * CollapsingToolbarLayout:可折叠式状态栏
 *       contentScrim:指定CollapsingToolbarLayout在趋于折叠状态以及折叠之后的背景色
 *       exitUntilCollapsed表示随着滚动完成折叠之后就保留在界面上，不再移出屏幕
 *       app:layout_collapseMode="parallax|pin"指定当前控件在CollapsingToolbarLayout
 *       折叠过程中的折叠模式，pin表示在折叠过程中位置始终保持不变，parallax表示在折叠的过程中会产生一定的错位偏移
 *
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
        // 初始化下拉刷新
        initSwipeRefreshLayout()
        // RecyclerView使用
        initRecyclerView()
    }

    private fun initSwipeRefreshLayout() {
        swipe_refresh_layout.setColorSchemeResources(R.color.design_default_color_primary)
        swipe_refresh_layout.setOnRefreshListener {
            Thread {
                Thread.sleep(2000)
                runOnUiThread {
                    swipe_refresh_layout.isRefreshing = false
                }
            }.start()
        }
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
        val layoutManager = GridLayoutManager(this, 2)
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
