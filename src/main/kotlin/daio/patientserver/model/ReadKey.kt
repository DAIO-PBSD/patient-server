package daio.patientserver.model

import java.io.Serializable
import java.time.LocalDateTime

class ReadKey (
        var signName: String = "",
        var timeEmitted: LocalDateTime = LocalDateTime.now(),
        var patientID: String = ""
): Serializable