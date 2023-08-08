//package com.example.mycatalog.ui.wishlist
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.resource.bitmap.CenterInside
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners
//import com.example.mycatalog.R
//import com.example.mycatalog.data.local.entity.ProductFavoriteEntity
//import com.example.mycatalog.databinding.ItemProductBinding
//
//class WishlistAdapter(
//    private val data: MutableList<ProductFavoriteEntity.Item> = mutableListOf(),
//    private val listener: (ProductFavoriteEntity.Item) -> Unit
//) :
//    RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {
//
//    fun setData(data: MutableList<ProductFavoriteEntity.Item>) {
//        this.data.clear()
//        this.data.addAll(data)
//        notifyDataSetChanged()
//    }
//
//    class WishlistViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun onBind(item: ProductFavoriteEntity.Item) {
//
//            item.let {
//                Glide.with(itemView.context).load(it.images[0])
//                    .placeholder(R.drawable.outline_downloading_24)
//                    .transform(CenterInside(), RoundedCorners(24))
//                    .into(binding.productImage)
//                binding.productName.text = it.title
//                binding.productPrice.text = it.formattedPrice
//                binding.productRating.rating = it.rating.toFloat()
//
//            }
//
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder =
//        WishlistViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//
//    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
//        val item = data[position]
//        holder.onBind(item)
//        holder.itemView.setOnClickListener {
//            listener(item)
//        }
//    }
//
//    override fun getItemCount(): Int = data.size
//
//}