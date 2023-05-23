package uz.gita.contact_di.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.contact_di.R
import uz.gita.contact_di.data.request.LoginRequest
import uz.gita.contact_di.data.request.RegisterRequest
import uz.gita.contact_di.data.source.local.sharedprevreference.SharedPrefRefrence
import uz.gita.contact_di.databinding.RegisterUserBinding
import uz.gita.contact_di.domain.repositories.AuthRepositories
import javax.inject.Inject

@AndroidEntryPoint
class Register : Fragment(R.layout.register_user) {

    private val binding by viewBinding(RegisterUserBinding::bind)

    @Inject
    lateinit var retrof: AuthRepositories

    @Inject
    lateinit var shared: SharedPrefRefrence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val str = shared.getString("token")
        if (str.isNotEmpty()) {
            findNavController().navigate(RegisterDirections.actionRegisterToHomeFragment(str))
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            sign.setOnClickListener {
                layout.visibility = View.GONE
                progressCircular.visibility = View.VISIBLE
                val name = "      "
                val surname = "         "
                val phone = number.text.toString().trim()
                val password = password.text.toString().trim()
                val request = RegisterRequest(name, surname, phone, password)

                retrof.register(request).onEach { result ->
                    Log.d("TTT", "retro")
                    result.onSuccess {
                        layout.visibility = View.VISIBLE
                        progressCircular.visibility = View.GONE
                        if (it.message == "test")
                            return@onEach
                        lay1.animate()
                            .setDuration(2000)
                            .translationX(lay1.translationX + 1500f)
                            .start()
                        lay2.animate()
                            .setDuration(2000)
                            .translationX(lay1.translationX - 1500f)
                            .withEndAction {
                                findNavController().navigate(RegisterDirections.actionRegisterToVerifyFragment(
                                    phone))
                            }
                            .start()
                    }
                    result.onFailure { it1 ->
                        layout.visibility = View.VISIBLE
                        progressCircular.visibility = View.GONE
                        Snackbar.make(binding.root,
                            "${it1.message}",
                            Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(),
                                R.color.teal_700))
                            .show()
                    }

                }.launchIn(lifecycleScope)

            }

            login.setOnClickListener {

                layout.visibility = View.GONE
                progressCircular.visibility = View.VISIBLE
                val phone = number.text.toString().trim()
                val password = password.text.toString().trim()

                val request = LoginRequest(phone, password)

                retrof.login(request).onEach { result ->

                    result.onSuccess {
                        layout.visibility = View.VISIBLE
                        progressCircular.visibility = View.GONE
                        if (it.token == "null") {
                            Snackbar.make(binding.root,
                                "kiritishda xatolik boliwi mumkin",
                                Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(ContextCompat.getColor(requireContext(),
                                    R.color.teal_700))
                                .show()
                            return@onEach
                        }
                        lay1.animate()
                            .setDuration(2000)
                            .translationX(lay1.translationX + 1500f)
                            .start()
                        lay2.animate()
                            .setDuration(2000)
                            .translationX(lay1.translationX - 1500f)
                            .withEndAction {
                                shared.putString("token", it.token)
                                findNavController().navigate(RegisterDirections.actionRegisterToHomeFragment(
                                    it.token))
                            }
                            .start()
                    }

                    result.onFailure {
                        layout.visibility = View.VISIBLE
                        progressCircular.visibility = View.GONE
                        Snackbar.make(binding.root,
                            "kiritishda xatolik boliwi mumkin",
                            Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(),
                                R.color.teal_700))
                            .show()
                    }

                }.launchIn(lifecycleScope)
            }

            replace.setOnClickListener {
                if (sign.visibility == View.VISIBLE) {
                    sign.visibility = View.GONE
                    login.visibility = View.VISIBLE
                } else {
                    sign.visibility = View.VISIBLE
                    login.visibility = View.GONE
                }
            }

        }
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

}