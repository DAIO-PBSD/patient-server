package daio.patientserver.model

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
@IdClass(SignKey::class)
class Sign (
        @Id var name: String,
        @Id @ManyToOne @JsonBackReference(value="signs") var patient: Patient = Patient(),
        var extremeLow: Double = 0.0,
        var low: Double = 0.0,
        var high: Double = 0.0,
        var extremeHigh: Double = 0.0
)