import com.example.autosell.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DbHelper {
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

    fun addUser(user: User) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            usersRef.child(userId).setValue(user)
        }
    }

    fun getUser(login: String, pass: String, callback: (Boolean) -> Unit) {
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            usersRef.child(currentUser.uid).get().addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(User::class.java)
                if (user != null && user.pass == pass) {
                    callback(true)
                } else {
                    callback(false)
                }
            }.addOnFailureListener {
                callback(false)
            }
        } else {
            callback(false)
        }
    }
}