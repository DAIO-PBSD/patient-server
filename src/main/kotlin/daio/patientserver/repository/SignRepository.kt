package daio.patientserver.repository

import daio.patientserver.model.Patient
import daio.patientserver.model.Sign
import daio.patientserver.model.SignKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface SignRepository: JpaRepository<Sign, SignKey> {
    fun findAllByPatient(patientID: Patient)
}