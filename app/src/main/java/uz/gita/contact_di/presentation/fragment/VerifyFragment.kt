package uz.gita.contact_di.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.contact_di.R
import uz.gita.contact_di.data.request.VerifyRequest
import uz.gita.contact_di.data.source.local.sharedprevreference.SharedPrefRefrence
import uz.gita.contact_di.databinding.FragmentVerifyBinding
import uz.gita.contact_di.domain.repositories.AuthRepositories
import javax.inject.Inject

@AndroidEntryPoint
class VerifyFragment : Fragment(R.layout.fragment_verify) {

    private val args by navArgs<VerifyFragmentArgs>()
    private val binding by viewBinding(FragmentVerifyBinding::bind)

    @Inject
    lateinit var retrof: AuthRepositories

    @Inject
    lateinit var shared: SharedPrefRefrence

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ph = args.phone.toString()
        binding.apply {
            lay1.translationX = (lay1.translationX + 1500f)
            lay2.translationX = (lay2.translationX - 1500f)

            lay1.animate()
                .translationX(0f)
                .setDuration(2000)
                .start()
            lay2.animate()
                .translationX(0f)
                .setDuration(2000)
                .start()
            number.setText(ph)
            sendBtn.setOnClickListener {

                val codes = password.text.toString().trim()
                if (codes.length != 6) {
                    Snackbar.make(binding.root,
                        "parol xato bolishi mumkin!!!",
                        Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(ContextCompat.getColor(requireContext(),
                            R.color.teal_700))
                        .show()
                    return@setOnClickListener
                }
                val request = VerifyRequest(ph, codes)
                retrof.verify(request).onEach {
                    it.onSuccess { mm ->
                        shared.putString("token", mm.token)
                        lay1.animate()
                            .setDuration(2000)
                            .translationX(lay1.translationX - 1500)
                            .start()
                        lay2.animate()
                            .setDuration(2000)
                            .translationX(lay2.translationX + 1500)
                            .withEndAction {
                                txt.apply {
                                    text = "Welcome!"
                                    translationX += 250
                                    animate()
                                        .setDuration(1000)
                                        .translationY(translationY + 500)
                                        .withEndAction {
                                            lifecycleScope.launch {
                                                delay(1000)
                                                findNavController().navigate(
                                                    VerifyFragmentDirections.actionVerifyFragmentToHomeFragment(
                                                        mm.token))
                                            }
                                        }
                                        .start()
                                }

                            }
                            .start()
                    }
                    it.onFailure { mm ->
                        Snackbar.make(binding.root,
                            "${mm.message}",
                            Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(),
                                R.color.teal_700))
                            .show()
                    }
                }.launchIn(lifecycleScope)

            }
        }

    }


}