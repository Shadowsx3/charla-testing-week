import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bassmd.myenchantedgarden.UserRepository
import com.bassmd.myenchantedgarden.data.remote.dto.LoginRequest
import kotlinx.coroutines.delay


class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    var isLoggedIn by mutableStateOf(false)
    var isBusy by mutableStateOf(false)

    suspend fun signIn(email: String, password:String) {
        isBusy = true
        val loginRequest = LoginRequest(email, password)
        userRepository.loginUser(loginRequest)
        isLoggedIn = true
        isBusy = false
    }

    suspend fun signOut() {
        isBusy = true
        delay(2000)
        isLoggedIn = false
        isBusy = false
    }
}