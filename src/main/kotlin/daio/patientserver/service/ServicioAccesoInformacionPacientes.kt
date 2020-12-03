package daio.patientserver.service

import daio.patientserver.model.*
import daio.patientserver.repository.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.client.RestTemplate
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/api")
class ServicioAccesoInformacionPacientes (private val patientRepository: PatientRepository,
                                          private val readRepository: ReadRepository,
                                          private val signRepository: SignRepository,
                                          private val statisticRepository: StatisticRepository,
                                          private val classificationRepository: ClassificationRepository) {


    @GetMapping("patients/{patient_id}/signs/{sign_name}/reads")
    fun getReads(@PathVariable patient_id: String, @PathVariable sign_name: String): List<Read> {
        return readRepository.findAllByPatientIDAndSignName(patient_id, sign_name)
    }

    @GetMapping("/patients/{patient_id}/signs/{sign_name}/reads/count")
    fun getSignsReadsCount(@PathVariable patient_id: String, @PathVariable sign_name: String): Long {
        return readRepository.countByPatientIDAndSignName(patient_id, sign_name)
    }

    @PostMapping("patients/{patient_id}/signs/{sign_name}/reads")
    fun postRead(@PathVariable patient_id: String, @PathVariable sign_name: String, @RequestBody read: Read, restTemplate: RestTemplate): Read {
        var patient = patientRepository.findByIdOrNull(patient_id)
        if (patient == null) {
            patient = Patient(patient_id)
            patient = patientRepository.save(patient)
        }

        val hasSign = patient.signs.any { it.name == sign_name }
        if (!hasSign) {
            patient.signs.add(Sign(sign_name, patient))
            patient.signs[patient.signs.lastIndex] = signRepository.save(patient.signs.last())
        }

        read.signName = sign_name
        read.patientID = patient_id
        return readRepository.save(read)
    }

    @GetMapping("/patients/{patient_id}/signs")
    fun getPatientSigns(@PathVariable patient_id: String): List<Sign>? {
        val patient = patientRepository.findByIdOrNull(patient_id)
        return patient?.signs
    }

    @GetMapping("/patients/{patient_id}/signs/{sign_name}")
    fun getPatientSign(@PathVariable patient_id: String, @PathVariable sign_name: String): Sign? {
        val patient = patientRepository.findByIdOrNull(patient_id)
        return patient?.signs?.find { it.name == sign_name }
    }

    @PutMapping("/patients/{patient_id}/signs/{sign_name}")
    fun putPatientSign(@PathVariable patient_id: String, @PathVariable sign_name: String, @RequestBody sign: Sign): Sign {
        var patient = patientRepository.findByIdOrNull(patient_id)
        if (patient == null) {
            patient = Patient(patient_id)
            patient = patientRepository.save(patient)
        }

        var signIndex = patient.signs.indexOfFirst { it.name == sign_name }
        if (signIndex == -1) {
            patient.signs.add(Sign(sign_name, patient))
            signIndex = patient.signs.lastIndex
        }

        patient.signs[signIndex].extremeLow = sign.extremeLow
        patient.signs[signIndex].low = sign.low
        patient.signs[signIndex].high = sign.high
        patient.signs[signIndex].extremeHigh = sign.extremeHigh

        return signRepository.save(patient.signs[signIndex])
    }

    @GetMapping("/patients/{patient_id}/signs/{sign_name}/statistics/{statistic_type}")
    fun getStatistic(@PathVariable patient_id: String, @PathVariable sign_name: String, @PathVariable statistic_type: String): Statistic? {
        return statisticRepository.findByIdOrNull(StatisticKey(patient_id, sign_name, statistic_type))
    }

    @PutMapping("/patients/{patient_id}/signs/{sign_name}/statistics/{statistic_type}")
    fun putStatistic(@PathVariable patient_id: String, @PathVariable sign_name: String, @PathVariable statistic_type: String, @RequestBody statistic: Statistic): Statistic {
        var patient = patientRepository.findByIdOrNull(patient_id)
        if (patient == null) {
            patient = Patient(patient_id)
            patient = patientRepository.save(patient)
        }

        val hasSign = patient.signs.any { it.name == sign_name }
        if (!hasSign) {
            patient.signs.add(Sign(sign_name, patient))
            patient.signs[patient.signs.lastIndex] = signRepository.save(patient.signs.last())
        }

        statistic.patient = patient_id
        statistic.signName = sign_name
        statistic.type = statistic_type
        return statisticRepository.save(statistic)
    }

    @GetMapping("/patients/{patient_id}/groups/")
    fun getPatientGroups(@PathVariable patient_id: String): List<Classification>? {
        val patient = patientRepository.findByIdOrNull(patient_id)
        return patient?.classifications
    }

    @GetMapping("/groups/{group_name}")
    fun getPatientsByGroup(@PathVariable group_name: String): List<Patient> {
       val classifications = classificationRepository.findAllByGroupC(group_name)
       return classifications.map{it.patient}
    }

    @PostMapping("/patients/{patient_id}/groups/{group_name}")
    fun postPatientGroup (@PathVariable patient_id: String, @PathVariable group_name: String, @RequestBody classification: Classification): Classification {
        var patient = patientRepository.findByIdOrNull(patient_id)

        if (patient == null) {
            patient = patientRepository.save(Patient(patient_id))
        }

        var groupIndex = patient.classifications.indexOfFirst { it.groupC == group_name }
        if (groupIndex == -1) {
            patient.classifications.add(Classification(group_name, patient))
            groupIndex = patient.classifications.lastIndex
        }

        return classificationRepository.save(patient.classifications[groupIndex])
    }

    @DeleteMapping("/patients/{patient_id}/groups/{group_name}")
    fun deletePatientGroup (@PathVariable patient_id: String, @PathVariable group_name: String): Unit? {
        val classification = classificationRepository.findByIdOrNull(ClassificationKey(group_name, patient_id)) ?: return null
        return classificationRepository.delete(classification)
    }
}