import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.thecookbook.R

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

     val  NOTIFICATION_ID = 1

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.recipe_notification_channel_id)).setSmallIcon(R.drawable.ic_baseline_timer_24)
        .setContentTitle(applicationContext
            .getString(R.string.notification_title))
        .setContentText(messageBody)

    // Deliver the notification
    notify(NOTIFICATION_ID, builder.build())
}
