package uz.gita.contact_di.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.contact_di.data.response.ResponseAddContact
import uz.gita.contact_di.databinding.ItemContactBinding

class ContactAdapter : ListAdapter<ResponseAddContact, ContactAdapter.Holder>(diffUtil) {

    object diffUtil : DiffUtil.ItemCallback<ResponseAddContact>() {
        override fun areItemsTheSame(
            oldItem: ResponseAddContact,
            newItem: ResponseAddContact,
        ) = oldItem == newItem


        override fun areContentsTheSame(
            oldItem: ResponseAddContact,
            newItem: ResponseAddContact,
        ) = oldItem.id == newItem.id

    }

    private var call: ((int: ResponseAddContact) -> Unit)? = null

    fun setCall(block: (int: ResponseAddContact) -> Unit) {
        call = block
    }

    inner class Holder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            currentList[adapterPosition].apply {
                binding.apply {
                    buttonMore.setOnClickListener {
                        Log.d("TTT", "${currentList[adapterPosition].id}")
                        call?.invoke(currentList[adapterPosition])
                    }
                    textName.text = "$firstName $lastName"
                    textNumber.text = phone
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(ItemContactBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }


}