package com.example.myapplication

import DatabaseHelper
import SheetEntry
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var reloadBtn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // init about intent
        val aboutIntent = Intent(this, AboutActivity::class.java)

        // init the reloadbtn
        reloadBtn = findViewById(R.id.refreshBtn)
        reloadBtn.setOnClickListener {
            showToast(this, "lolz reloaded")
        }

        // Apply insets to AppBarLayout
        val appBarLayout: View = findViewById(R.id.app_bar)
        ViewCompat.setOnApplyWindowInsetsListener(appBarLayout) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = systemBars.top)
            insets
        }

        // Set up the Toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set up DrawerLayout and ActionBarDrawerToggle
        drawerLayout = findViewById(R.id.main)
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        // set the hamburger btn to be white
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Handle Navigation Drawer item clicks
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_camera -> startActivity(aboutIntent)
                R.id.menu_gallery -> showToast(this, "Gallery selected")
                R.id.menu_tools -> showToast(this, "Tools selected")
                R.id.menu_share -> showToast(this, "Share selected")
                R.id.menu_send -> showToast(this, "Send selected")
            }
            drawerLayout.closeDrawers()
            true
        }

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get data passed from SplashActivity
        val dataJson = intent.getStringExtra("data")
        if (dataJson != null) {
            val data: List<SheetEntry> = Gson().fromJson(dataJson, object : TypeToken<List<SheetEntry>>() {}.type)
            recyclerView.adapter = DataAdapter(data)
        } else {
            showAlertDialog(this, "something went wrong.. data could not be loaded.")
        }
    }
}
