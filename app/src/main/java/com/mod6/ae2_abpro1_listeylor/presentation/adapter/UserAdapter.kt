package com.mod6.ae2_abpro1_listeylor.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mod6.ae2_abpro1_listeylor.R
import com.mod6.ae2_abpro1_listeylor.data.model.User

class UserAdapter(private val onClick: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users: List<User> = emptyList()

    fun submitList(newUsers: List<User>) {
        users = newUsers.sortedBy { it.id } // Ordena por ID
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId         : TextView  = itemView.findViewById(R.id.tv_user_id)
        private val tvName       : TextView  = itemView.findViewById(R.id.tv_user_name)
        private val tvEmail      : TextView  = itemView.findViewById(R.id.tv_user_email)
        private val ivPlaceholder: ImageView = itemView.findViewById(R.id.iv_user_placeholder)

        fun bind(user: User) {
            tvId.text    = "ID: ${user.id}"
            tvName.text  = user.name
            tvEmail.text = user.email

            // Placeholder para la imagen (actualmente estático con ícono Peras con Manzanas)
            // Se dejó el diseño para agregar foto del usuario, eventualmente, en otra etapa
            ivPlaceholder.setImageResource(R.drawable.user_placeholder)

            itemView.setOnClickListener {
                onClick(user)
            }
        }
    }
}