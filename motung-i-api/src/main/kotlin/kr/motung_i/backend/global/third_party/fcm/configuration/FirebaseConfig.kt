package kr.motung_i.backend.global.third_party.fcm.configuration

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream

@Configuration
class FirebaseConfig(
    @Value("\${firebase.credentials}")
    private val firebaseCredentials: String,
) {
    @PostConstruct
    fun init() {
        try {
            val serviceAccount = ByteArrayInputStream(firebaseCredentials.toByteArray())
            val options =
                FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build()

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
