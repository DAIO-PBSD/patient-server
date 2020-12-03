package daio.patientserver.repository

import daio.patientserver.model.Statistic
import daio.patientserver.model.StatisticKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface StatisticRepository: JpaRepository<Statistic, StatisticKey>