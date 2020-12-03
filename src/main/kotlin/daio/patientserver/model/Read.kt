package daio.patientserver.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@IdClass(ReadKey::class)
class Read (
        @Id var signName: String,
        @Id var timeEmitted: LocalDateTime,
        @Id var patientID: String,
        var value: Double
)