import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.catatanhariansaya.PR
import com.example.catatanhariansaya.R

class PRAdapter(val PRS : ArrayList<PR>, val onClick : OnClick) : RecyclerView.Adapter<PRAdapter.MyHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return MyHolder(view)


    }


    override fun getItemCount(): Int = PRS.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {


        holder.bind(PRS.get(position))
        holder.itemView.findViewById<Button>(R.id.btDeleteNote).setOnClickListener {
            onClick.delete(PRS.get(position).key)
        }
        holder.itemView.setOnClickListener {
            onClick.edit(PRS.get(position))
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(PR : PR){
            itemView.findViewById<TextView>(R.id.tvNoteName).text = PR.title
            itemView.findViewById<TextView>(R.id.tvNoteDescription).text = PR.description
        }
    }

    interface OnClick {
        fun delete(key : String?)
        fun edit(PR : PR?)
    }
}