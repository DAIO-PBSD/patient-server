package daio.patientserver.model

import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.Id

class StatisticKey (
        var patient: String = "",
        var signName: String = "",
        var type: String = "",
): Serializable
