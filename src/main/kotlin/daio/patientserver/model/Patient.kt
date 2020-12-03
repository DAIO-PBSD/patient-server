package daio.patientserver.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
class Patient (
        @Id var id: String = "",
        @OneToMany @JsonManagedReference(value = "signs") var signs: MutableList<Sign> = mutableListOf(),
        @OneToMany @JsonManagedReference(value = "classifications") var classifications: MutableList<Classification> = mutableListOf()
)