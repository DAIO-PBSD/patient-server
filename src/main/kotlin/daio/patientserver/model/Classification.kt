package daio.patientserver.model

import com.fasterxml.jackson.annotation.JsonBackReference
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@IdClass(ClassificationKey::class)
class Classification (
        @Id var groupC: String,
        @Id @ManyToOne @JsonBackReference(value="classifications") var patient: Patient = Patient(),
        var dateAssigned: LocalDateTime = LocalDateTime.now()
)