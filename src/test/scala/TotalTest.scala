package medihealth

import org.scalatest._

class TotalTest extends FlatSpec {

  "adding services to the total" should "updates the balance and adds the service to the list" in {
    val total: Total = Total()
    val patient: Patient = Patient(age = 45, insurance = false)

    val firstService: HealthService = ECG
    val firstTotal: Total = total.addToTotal(firstService, firstService.salePrice(patient))
    assert(firstTotal == Total(firstService.fullPrice, List(firstService)))

    val secondService: HealthService = BloodTest
    val secondTotal: Total = firstTotal.addToTotal(secondService, secondService.salePrice(patient))
    assert(secondTotal == Total(firstService.fullPrice + secondService.fullPrice, List(secondService, firstService)))

    val thirdService: HealthService = Diagnosis
    val thirdTotal: Total = secondTotal.addToTotal(thirdService, thirdService.salePrice(patient))
    assert(thirdTotal == Total(firstService.fullPrice + secondService.fullPrice + thirdService.fullPrice, List(thirdService, secondService, firstService)))

  }

}
