package com.malpvaplicaciones.app3_mdc

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.malpvaplicaciones.app3_mdc.databinding.ActivityScrollingBinding

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_scrolling)

        // FIXME: 22-03-2021 binding
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sin Binding
/*        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            if (findViewById<BottomAppBar>(R.id.bottomAppBar).fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                findViewById<BottomAppBar>(R.id.bottomAppBar).fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            }else{
                findViewById<BottomAppBar>(R.id.bottomAppBar).fabAlignmentMode =BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }*/

        // FIXME: 22-03-2021 View with Binding
        binding.fab.setOnClickListener {
            if (binding.bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            }else{
                binding.bottomAppBar.fabAlignmentMode =BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }

        binding.bottomAppBar.setNavigationOnClickListener {
            // FIXME: 22-03-2021 Snackbar simple
            Snackbar.make(binding.root, R.string.message_action_success, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .show()
        }

        binding.content.btnSkip.setOnClickListener { binding.content.cvAd.visibility = View.GONE }

        binding.content.btnBuy.setOnClickListener {
            // FIXME: 22-03-2021 Snackbar whit action
            Snackbar.make(it, R.string.card_buying, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .setAction(R.string.to_go) {
                    Toast.makeText(this, R.string.card_historial, Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        loadImage()

        // FIXME: 23-03-2021 Evento CheckBox
        binding.content.cbEnableInputPass.setOnClickListener {
            binding.content.tilPass.isEnabled = !binding.content.tilPass.isEnabled
        }

        // FIXME: 23-03-2021 Focus
        binding.content.etUri.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            val url = binding.content.etUri.text.toString()
            var errorStr:String? = null

            if (!hasFocus){
                when {
                    url.isEmpty() -> {
                        errorStr = getString(R.string.card_required)
                    }
                    URLUtil.isValidUrl(url) -> {
                        loadImage(url)
                    }
                    else -> {
                        errorStr = getString(R.string.card_invalid_url)
                    }
                }
            }
            binding.content.etUri.error = errorStr
        }

        // FIXME: 24-03-2021 Manejo de Toggle Button
        binding.content.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            binding.content.root.setBackgroundColor(
                    when(checkedId){
                        R.id.btnBlue -> Color.BLUE
                        R.id.btnRed -> Color.RED
                        else -> Color.GREEN
                    }
            )
        }
    }

    private fun loadImage(url:String = "https://horasyminutos.com/wp-content/uploads/Rolex-Submariner-41-mm-2020-6-Horas-y-Minutos.jpg"){
        // FIXME: 22-03-2021 Use of Glide basic
        Glide.with(this)
            // Permisos de internet en manifest
            .load(url)
            // Administrar cache -> All guardará la imagen directa de la url y la procesada
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.content.imgCover)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}