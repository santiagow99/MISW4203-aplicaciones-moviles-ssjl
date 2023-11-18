import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.vinyls_jetpack_application.databinding.AlbumDetailItemBinding
import com.example.vinyls_jetpack_application.models.AlbumDetail

class AlbumDetailAdapter : BaseAdapter() {

    private var album: AlbumDetail? = null

    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Any? {
        return album
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        val view: View

        if (convertView == null) {
            val inflater = LayoutInflater.from(parent?.context)
            val binding = AlbumDetailItemBinding.inflate(inflater, parent, false)
            view = binding.root
            viewHolder = ViewHolder(binding)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        album?.let { viewHolder.bind(it) }
        return view
    }

    private class ViewHolder(val binding: AlbumDetailItemBinding) {
        fun bind(album: AlbumDetail) {
            binding.albumDetail = album
        }
    }
}
