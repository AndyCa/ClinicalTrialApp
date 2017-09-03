package medihealth

import org.scalatest._

class HealthServiceTest extends FlatSpec {

  "each vaccine injection" should "costs £15" in {
    for {i <- 1 to 10} {
      val vaccine: Vaccine = Vaccine(injections = i)
      assert(vaccine.injectionsPrice == i * vaccine.injectionPrice)
      assert(vaccine.fullPrice == i * vaccine.injectionPrice + vaccine.defaultCost)
    }

  }

  "sale price" should "be full price for a patient with no insurance or age discount" in {
    val patientWithNoInsuranceAndNoAgeDiscount: Patient = Patient(
      age = 35,
      insurance = false
    )
    for {service <- HealthServices.list} {
      val salePrice = service.salePrice(patientWithNoInsuranceAndNoAgeDiscount)
      assert(salePrice == service.fullPrice)
    }

  }

  "sale price" should "be discounted by 40% for a patient under 5" in {
    val patientWithNoInsuranceAnd4AgeDiscount: Patient = Patient(
      age = 4,
      insurance = false
    )
    for {service <- HealthServices.list} {
      val salePrice = service.salePrice(patientWithNoInsuranceAnd4AgeDiscount)
      assert(salePrice == service.fullPrice - (service.fullPrice/100) * 40)
    }

  }

  "sale price" should "be discounted by 60% for a patient over 64" in {
    val patientWithNoInsuranceAnd65AgeDiscount: Patient = Patient(
      age = 65,
      insurance = false
    )
    for {service <- HealthServices.list} {
      val salePrice = service.salePrice(patientWithNoInsuranceAnd65AgeDiscount)
      assert(salePrice == service.fullPrice - (service.fullPrice/100) * 60)
    }

  }

  "sale price" should "be discounted by 90% for a patient over 70" in {
    val patientWithNoInsuranceAnd70AgeDiscount: Patient = Patient(
      age = 71,
      insurance = false
    )
    for {service <- HealthServices.list} {
      val salePrice = service.salePrice(patientWithNoInsuranceAnd70AgeDiscount)
      assert(salePrice == service.fullPrice - (service.fullPrice/100) * 90)
    }
  }

  "sale price" should "be discounted by a further 15% for a patient buying blood test with insurance" in {
      val patientWithInsuranceAnd70AgeDiscount: Patient = Patient(
        age = 71,
        insurance = true
      )
      val patientWithNoInsuranceAnd65AgeDiscount: Patient = Patient(
        age = 65,
        insurance = true
      )
      val patientWithNoInsuranceAnd4AgeDiscount: Patient = Patient(
        age = 4,
        insurance = true
      )
      val patientWithNoInsuranceAndNoAgeDiscount: Patient = Patient(
        age = 35,
        insurance = true
      )
      val listOfPatients = List(
        patientWithInsuranceAnd70AgeDiscount,
        patientWithNoInsuranceAnd65AgeDiscount,
        patientWithNoInsuranceAnd4AgeDiscount,
        patientWithNoInsuranceAndNoAgeDiscount
      )
      for {patient <- listOfPatients} {
        val service = BloodTest
        val salePrice = service.salePrice(patient)
        val patientDiscount = service.fullPrice - (service.fullPrice/100) * patient.discount
        assert(salePrice ==  patientDiscount - (patientDiscount/100) * 15)
      }

  }

  "a discount" should "be applied if no apply boolean is given" in {
    val service = BloodTest
    assert(service.applyDiscount(service.fullPrice, 50) == service.fullPrice/2)
  }

  "a discount" should "not be applied if the apply boolean is false" in {
    val service = BloodTest
    assert(service.applyDiscount(service.fullPrice, 50, false) == service.fullPrice)
  }

}
