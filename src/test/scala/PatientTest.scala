package medihealth

import org.scalatest._

class PatientTest extends FlatSpec {

  "A patient over 70" should "get a discount of 90%" in {
    val patient71YearsOld: Patient = Patient(
      age = 71,
      insurance = false
    )
    assert(patient71YearsOld.discount == 90)
  }

  "A patient between 65 and 70" should "get a discount of 60%" in {
    val patient70YearsOld: Patient = Patient(
      age = 70,
      insurance = false
    )
    val patient65YearsOld: Patient = Patient(
      age = 65,
      insurance = false
    )
    assert(patient70YearsOld.discount == 60)
    assert(patient65YearsOld.discount == 60)
  }

  "A patient younger than 65" should "get no discount" in {

    val patient64YearsOld: Patient = Patient(
      age = 64,
      insurance = false
    )
    assert(patient64YearsOld.discount == 0)
  }

  "A child patient younger than 5" should "get a discount of 40%" in {

    val patient4YearsOld: Patient = Patient(
      age = 4,
      insurance = false
    )
    assert(patient4YearsOld.discount == 40)
  }

  "A patient older than 4" should "get no discount" in {

    val patient5YearsOld: Patient = Patient(
      age = 5,
      insurance = false
    )
    assert(patient5YearsOld.discount == 0)
  }

}
