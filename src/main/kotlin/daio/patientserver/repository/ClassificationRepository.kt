package daio.patientserver.repository

import daio.patientserver.model.Classification
import daio.patientserver.model.ClassificationKey
import org.springframework.data.jpa.repository.JpaRepository

interface ClassificationRepository: JpaRepository<Classification, ClassificationKey> {
    fun findAllByGroupC(group: String): List<Classification>
}