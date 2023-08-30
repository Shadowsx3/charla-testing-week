import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bassmd.myenchantedgarden.UserRepository
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.RegisterRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.dto.UserResponse


class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    var isLoggedIn by mutableStateOf(false)
    var isBusy by mutableStateOf(false)
    var userName by mutableStateOf("")

    suspend fun signIn(email: String, password: String) {
        isBusy = true
        val loginResult: Result<StatusModel> = userRepository.login(LoginRequest(email, password))
        loginResult.onSuccess {
            isLoggedIn = true
            userRepository.getPlants()
            userRepository.getEvents()
            userRepository.getStore()
            userRepository.getAchievements()
        }
        isBusy = false
    }
}