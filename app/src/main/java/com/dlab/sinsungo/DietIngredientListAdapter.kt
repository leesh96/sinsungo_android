package com.dlab.sinsungo

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dlab.sinsungo.databinding.ItemRcviewDietIngredientBinding
import com.dlab.sinsungo.viewmodel.DietViewModel

class DietIngredientListAdapter(private val useIngredient: List<IngredientModel>?) :
    ListAdapter<IngredientModel, DietIngredientListAdapter.ViewHolder>(DietIngredientDiffUtil) {

    val ingredientList = mutableListOf<IngredientModel>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemRcviewDietIngredientBinding>(layoutInflater, viewType, parent, false)
        val holder = ViewHolder(binding)
        binding.cvIngredientDietItem.setBackgroundResource(R.drawable.bg_dialog_white)
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_rcview_diet_ingredient
    }

    override fun onBindViewHolder(holder: DietIngredientListAdapter.ViewHolder, position: Int) {
        if (useIngredient != null) {
            holder.bind(getItem(position), getItem(position) in useIngredient)
        } else {
            holder.bind(getItem(position), false)
        }
    }

    inner class ViewHolder(val binding: ItemRcviewDietIngredientBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredientModel: IngredientModel, isActivated: Boolean) {
            binding.dataModel = ingredientModel
            binding.btnCheckIngredient.isActivated = isActivated
            binding.btnCheckIngredient.setOnClickListener {
                binding.btnCheckIngredient.isActivated = !binding.btnCheckIngredient.isActivated
                if (binding.btnCheckIngredient.isActivated) {
                    ingredientList.add(ingredientModel)
                } else {
                    ingredientList.remove(ingredientModel)
                }
            }
            if (binding.btnCheckIngredient.isActivated) {
                ingredientList.add(ingredientModel)
            } else {
                ingredientList.remove(ingredientModel)
            }
            Log.d("ingredientList", ingredientList.toString())
            binding.executePendingBindings() //데이터가 수정되면 즉각 바인딩
        }

    }

    companion object DietIngredientDiffUtil : DiffUtil.ItemCallback<IngredientModel>() {
        override fun areItemsTheSame(oldItem: IngredientModel, newItem: IngredientModel): Boolean {
            //각 아이템들의 고유한 값을 비교해야 한다.
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: IngredientModel, newItem: IngredientModel): Boolean {
            return oldItem == newItem
        }
    }
}

