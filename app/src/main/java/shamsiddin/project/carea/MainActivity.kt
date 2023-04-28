package shamsiddin.project.carea

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import shamsiddin.project.carea.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, MainSplashFragment())
            .commit()
    }
}