import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.chatsphere.Message
import com.example.chatsphere.R
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_RECEIVE) {
            // Inflate receive layout
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            ReceiveViewHolder(view)
        } else {
            // Inflate sent layout
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            SentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        when (holder.itemViewType) {
            ITEM_SENT -> {
                val sentViewHolder = holder as SentViewHolder
                sentViewHolder.sentMessage.text = currentMessage.message
                sentViewHolder.itemView.setOnLongClickListener {
                    showPopupMenu(sentViewHolder.itemView, currentMessage)
                    true
                }
            }
            ITEM_RECEIVE -> {
                val receiveViewHolder = holder as ReceiveViewHolder
                receiveViewHolder.receiveMessage.text = currentMessage.message
                receiveViewHolder.itemView.setOnLongClickListener {
                    showPopupMenu(receiveViewHolder.itemView, currentMessage)
                    true
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid == currentMessage.senderId) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    private fun showPopupMenu(view: View, message: Message) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.delete_message_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
//                R.id.help -> {
//                    // Navigate to the HelpFragment
//                    findNavController().navigate(R.id.action_global_helpFragment)
//                    return true
//                }
                R.id.delete_sender_only -> {
                    // Implement delete logic for sender only
                    // For example, remove the message from the list
                    messageList.remove(message)
                    notifyDataSetChanged()
                    true
                }
                R.id.delete_both -> {
                    // Implement delete logic for both sender and receiver
                    // For example, remove the message from the list
                    messageList.remove(message)
                    notifyDataSetChanged()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    inner class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }

    inner class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_receive_message)
    }
}
