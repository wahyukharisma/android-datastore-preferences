package id.dayplug.sampleprefdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import id.dayplug.sampleprefdatastore.databinding.ActivityMainBinding
import id.dayplug.sampleprefdatastore.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        with(binding){
            btnSave.setOnClickListener {
                val name = etName.text.toString()
                if(name == ""){
                    etName.error = "Masukkan nama"
                    etName.requestFocus()
                }else{
                    viewModel.saveToDataStore(name)
                }
            }

            viewModel.readFromDataStore.observe(this@MainActivity,{
                preferences ->
                    tvName.text = preferences
            })
        }
    }
}