package pl.cutter72.sem5.notatnik

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.cutter72.sem5.notatnik.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}