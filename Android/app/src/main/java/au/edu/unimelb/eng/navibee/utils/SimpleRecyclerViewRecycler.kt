package au.edu.unimelb.eng.navibee.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import au.edu.unimelb.eng.navibee.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.recycler_view_attributions.view.*
import kotlinx.android.synthetic.main.recycler_view_item_ratings.view.*
import kotlinx.android.synthetic.main.recycler_view_item_text_primary_secondary_clickable.view.*
import kotlinx.android.synthetic.main.recycler_view_item_text_primary_secondary_static.view.*
import kotlinx.android.synthetic.main.recycler_view_item_text_secondary_primary_clickable.view.*
import kotlinx.android.synthetic.main.recycler_view_item_text_secondary_primary_static.view.*
import kotlinx.android.synthetic.main.recycler_view_item_user_chips.view.*

class SimpleRecyclerViewAdaptor(private val data: List<SimpleRecyclerViewItem>) :
        androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val layout = when (viewType) {
            1 -> R.layout.recycler_view_indefinite_progress
            2 -> R.layout.recycler_view_item_text_primary_secondary_clickable
            3 -> R.layout.recycler_view_item_text_primary_secondary_static
            4 -> R.layout.recycler_view_item_text_secondary_primary_clickable
            5 -> R.layout.recycler_view_item_text_secondary_primary_static
            6 -> R.layout.recycler_view_attributions
            7 -> R.layout.recycler_view_item_ratings
            8 -> R.layout.recycler_view_item_user_chips
            else -> R.layout.recycler_view_indefinite_progress
        }
        return SimpleRVViewHolder(LayoutInflater.from(parent.context)
                .inflate(layout, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is SimpleRVIndefiniteProgressBar -> 1
            is SimpleRVTextPrimarySecondaryClickable -> 2
            is SimpleRVTextPrimarySecondaryStatic -> 3
            is SimpleRVTextSecondaryPrimaryClickable -> 4
            is SimpleRVTextSecondaryPrimaryStatic -> 5
            is SimpleRVAttributions -> 6
            is SimpleRVRatings -> 7
            is SimpleRVUserChips -> 8
            else -> 0
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = data[position]
        when (data) {
            is SimpleRVTextPrimarySecondaryClickable -> {
                holder.itemView.general_recycler_view_text_primary_secondary_clickable_primary.text =
                        data.primary
                holder.itemView.general_recycler_view_text_primary_secondary_clickable_secondary.text =
                        data.secondary
                holder.itemView.setOnClickListener(data.onClick)
            }
            is SimpleRVTextPrimarySecondaryStatic -> {
                holder.itemView.general_recycler_view_text_primary_secondary_static_primary.text =
                        data.primary
                holder.itemView.general_recycler_view_text_primary_secondary_static_secondary.text =
                        data.secondary
            }
            is SimpleRVTextSecondaryPrimaryClickable -> {
                holder.itemView.general_recycler_view_text_secondary_primary_clickable_primary.text =
                        data.primary
                holder.itemView.general_recycler_view_text_secondary_primary_clickable_secondary.text =
                        data.secondary
                holder.itemView.setOnClickListener(data.onClick)
            }
            is SimpleRVTextSecondaryPrimaryStatic -> {
                holder.itemView.general_recycler_view_text_secondary_primary_static_primary.text =
                        data.primary
                holder.itemView.general_recycler_view_text_secondary_primary_static_secondary.text =
                        data.secondary
            }
            is SimpleRVAttributions -> {
                holder.itemView.recycler_view_attribution_text_view.text = data.attributes
            }
            is SimpleRVRatings -> {
                holder.itemView.general_recycler_view_ratings_title.text = data.title
                val stars = holder.itemView.general_recycler_view_ratings_stars
                stars.numStars = data.maxRating
                stars.rating = data.rating
                stars.stepSize = data.step
            }
            is SimpleRVUserChips -> {
                holder.itemView.general_recycler_view_user_chip_secondary.text = data.title
                holder.itemView.general_recycler_view_user_chip_chipgroup.apply {
                    data.chips.forEach { addView(it) }
                }
            }
        }
    }
}

abstract class SimpleRecyclerViewItem

class SimpleRVIndefiniteProgressBar: SimpleRecyclerViewItem()
data class SimpleRVTextPrimarySecondaryClickable(
        val primary: CharSequence,
        val secondary: CharSequence,
        val onClick: View.OnClickListener
): SimpleRecyclerViewItem()
data class SimpleRVTextPrimarySecondaryStatic(
        val primary: CharSequence,
        val secondary: CharSequence
): SimpleRecyclerViewItem()
data class SimpleRVTextSecondaryPrimaryClickable(
        val primary: CharSequence,
        val secondary: CharSequence,
        val onClick: View.OnClickListener
): SimpleRecyclerViewItem()
data class SimpleRVTextSecondaryPrimaryStatic(
        val primary: CharSequence,
        val secondary: CharSequence
): SimpleRecyclerViewItem()
data class SimpleRVAttributions(
        val attributes: CharSequence
): SimpleRecyclerViewItem()
data class SimpleRVUserChips(
        val title: CharSequence,
        val chips: ArrayList<Chip>
): SimpleRecyclerViewItem()
data class SimpleRVRatings(
        val title: CharSequence,
        val rating: Float,
        val step: Float,
        val maxRating: Int
): SimpleRecyclerViewItem()
class SimpleRVViewHolder(view: View) :
        RecyclerView.ViewHolder(view)
