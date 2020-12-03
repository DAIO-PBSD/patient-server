package daio.patientserver.model

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
@IdClass(StatisticKey::class)
class Statistic (
        @Id var patient: String,
        @Id var signName: String,
        @Id var type: String,
        var value: Double
)
