package uz.gita.contact_di.presentation.screen

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.contact_di.R
import uz.gita.contact_di.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(this, "vuiguiguigigghgyghyghygghyg", Toast.LENGTH_SHORT).show()

        Snackbar.make(binding.root,
            "Check if the fields are not empty!!!",
            Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this,
                R.color.teal_700))
            .show()

    }
}

