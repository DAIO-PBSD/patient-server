package daio.patientserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PatientServerApplication

fun main(args: Array<String>) {
	runApplication<PatientServerApplication>(*args)
}
