package com.example.chatsphere

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ContactActivity : AppCompatActivity() {
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etContactNumber: EditText
    private lateinit var etMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_activity)

        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        etEmail = findViewById(R.id.et_email)
        etContactNumber = findViewById(R.id.et_contact_number)
        etMessage = findViewById(R.id.et_message)
        btnSend = findViewById(R.id.btn_send)
        db = FirebaseFirestore.getInstance()

        btnSend.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmail.text.toString()
            val contact = etContactNumber.text.toString()
            val message = etMessage.text.toString()

            val user = hashMapOf(
                "FirstName" to firstName,
                "LastName" to lastName,
                "Email" to email,
                "ContactNumber" to contact,
                "Message" to message
            )

            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    // Use applicationContext for Toast messages
                    Toast.makeText(applicationContext, "Message sent successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    // Log error for debugging
                    Log.e("ContactActivity", "Error sending message: ${e.message}", e)
                    Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
