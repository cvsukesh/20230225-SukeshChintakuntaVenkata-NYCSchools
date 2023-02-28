package com.nycschools.ui.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nycschools.data.model.NycSchool
import com.nycschools.databinding.ItemNycSchoolBinding
import com.nycschools.ui.ListItemClickListener

class NYCSchoolViewHolder(
    var binding: ItemNycSchoolBinding,
    var listItemClickListener: ListItemClickListener
) : ViewHolder(binding.root) {

    fun bind(nycSchool: NycSchool) {
        binding.nycSchool = nycSchool
        binding.parentLayout.setOnClickListener {
            listItemClickListener.onItemClick(nycSchool.dbn)
        }
    }
}