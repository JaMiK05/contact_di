package uz.gita.contact_di.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.contact_di.R
import uz.gita.contact_di.data.request.DeleteContactRequest
import uz.gita.contact_di.data.source.local.sharedprevreference.SharedPrefRefrence
import uz.gita.contact_di.databinding.FragmentHomeBinding
import uz.gita.contact_di.domain.repositories.ContactRepositories
import uz.gita.contact_di.presentation.adapter.ContactAdapter
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val adap: ContactAdapter by lazy { ContactAdapter() }

    private val args by navArgs<HomeFragmentArgs>()

    private lateinit var token: String

    @Inject
    lateinit var shared: SharedPrefRefrence

    @Inject
    lateinit var repository: ContactRepositories

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        token = args.token!!

        getContacts()

        binding.apply {

            logOut.setOnClickListener {

                shared.putString("token", "")
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRegister())

            }
            recycler.layoutManager = GridLayoutManager(requireContext(), 1)
            recycler.adapter = adap
            adap.setCall {
                Log.d("TTT", "${it.id}")
                val delete = DeleteContactRequest(it.id)
                repository.deleteContact(token, delete.id).onEach { result ->

                    result.apply {
                        onSuccess {
                            Log.d("TTT", "setCall")
                            Snackbar.make(binding.root,
                                "Ochirib tawlandi",
                                Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(ContextCompat.getColor(requireContext(),
                                    R.color.teal_700))
                                .show()
                            getContacts()
                        }
                        onFailure {
                            Snackbar.make(binding.root,
                                "Qandaydir Xatolik boliwi mumkun",
                                Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(ContextCompat.getColor(requireContext(),
                                    R.color.teal_700))
                                .show()
                            getContacts()
                        }
                    }

                }.launchIn(lifecycleScope)

            }

            add.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment(
                    token))
            }

        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


    }

    private fun getContacts() {
        repository.getContact(token)
            .onEach { result ->

                result.onSuccess {
                    adap.submitList(it)
                }
                result.onFailure {
                    Snackbar.make(binding.root,
                        "contactlar mavjud emas",
                        Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(ContextCompat.getColor(requireContext(),
                            R.color.teal_700))
                        .show()
                }
            }.launchIn(lifecycleScope)
    }


}