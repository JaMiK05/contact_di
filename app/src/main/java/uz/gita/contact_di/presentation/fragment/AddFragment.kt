package uz.gita.contact_di.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.contact_di.R
import uz.gita.contact_di.data.request.AddContactRequest
import uz.gita.contact_di.databinding.FragmentAddBinding
import uz.gita.contact_di.domain.repositories.ContactRepositories
import javax.inject.Inject

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {

    private val binding by viewBinding(FragmentAddBinding::bind)

    private val args by navArgs<AddFragmentArgs>()

    @Inject
    lateinit var repository: ContactRepositories

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = args.token!!

        binding.apply {

            save.setOnClickListener {

                val name = firstName.text.toString()
                val surname = lastName.text.toString()
                val phone = number.text.toString()

                val request = AddContactRequest(name, surname, phone)
                repository.addContact(token, request).onEach { result ->
                    result.onSuccess {
                        when (it.firstName) {
                            "" -> {
                                Snackbar.make(binding.root,
                                    "Xatolik boliwi mumkin",
                                    Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(ContextCompat.getColor(requireContext(),
                                        R.color.teal_700))
                                    .show()
                            }
                            else -> {
                                findNavController().navigateUp()
                            }
                        }
                    }
                    result.onFailure {
                        Snackbar.make(binding.root,
                            "${it.message}",
                            Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(),
                                R.color.teal_700))
                            .show()
                    }

                }.launchIn(lifecycleScope)

            }
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}