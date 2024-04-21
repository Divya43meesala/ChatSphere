import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatsphere.ChatActivity
import com.example.chatsphere.R
import com.example.chatsphere.User
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.textName.text = currentUser.name

        // Set profile icon based on position
        holder.profileIcon.setImageResource(getProfileIconResourceId(position))

        // Set background for profile icon
        holder.profileIcon.setBackgroundResource(R.drawable.background_black)

        // Set OnClickListener to start ChatActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)
            context.startActivity(intent)
        }

        // Set OnLongClickListener to delete the user
        holder.itemView.setOnLongClickListener {
            deleteUser(position)
            true // Return true to indicate that the long click event is consumed
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName = itemView.findViewById<TextView>(R.id.txt_name)
        val profileIcon = itemView.findViewById<ImageView>(R.id.profile_icon)
    }

    private fun deleteUser(position: Int) {
        // Implement your user deletion logic here
        // For example, remove the user from the list
        userList.removeAt(position)
        // Notify adapter about the deletion
        notifyDataSetChanged()
    }

    // Method to get resource id for profile icons based on position
    private fun getProfileIconResourceId(position: Int): Int {
        val initialProfileIcons = arrayOf(
            R.drawable.profile_icon2,
            R.drawable.profile_image3,
            R.drawable.profile_icon4,
            R.drawable.profile_icon6,
            R.drawable.profile_icon5,
            R.drawable.profile_icon7,
            R.drawable.profile_icon8,
            R.drawable.profile_icon9
        )
        // Repeat initial 5 profile icons for the next 5 users
        return initialProfileIcons[position % 8]
    }
}
