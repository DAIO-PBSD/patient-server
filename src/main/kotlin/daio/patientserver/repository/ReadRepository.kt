package daio.patientserver.repository

import daio.patientserver.model.Read
import daio.patientserver.model.ReadKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface ReadRepository: JpaRepository<Read, ReadKey> {
    fun findAllByPatientIDAndSignName(patientID: String, signName: String): List<Read>

    fun countByPatientIDAndSignName(patientID: String, signName: String): Long
}