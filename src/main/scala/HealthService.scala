package medihealth

trait HealthService {
  val name: String
  val defaultCost: BigDecimal
  val discount: BigDecimal
  lazy val fullPrice: BigDecimal = defaultCost
  type ApplyDiscounts = Patient => BigDecimal
  def salePrice: ApplyDiscounts = { patient =>
      patient.discount match {
        case d if d > 100 => 0
        //TODO insurance if statement should be refactored so not written twice
        case d if d <= 0  => applyDiscount(fullPrice, discount, patient.insurance)
        case _ => {
          val patientDiscount = applyDiscount(fullPrice, patient.discount)
          val salePrice = applyDiscount(patientDiscount, discount, patient.insurance)
          salePrice
        }
      }
  }
  def applyDiscount(value: BigDecimal, discountPercentage: BigDecimal, apply: Boolean = true): BigDecimal = {
    if (apply) value * (1-(discountPercentage / 100)) else value
  }
}

case object HealthServices {
  def list: List[HealthService] = List(
    Diagnosis, XRay, ECG, BloodTest, Vaccine
  )

  def choice(choice: Int): Option[HealthService] = choice match {
    case 1 => Some(Diagnosis)
    case 2 => Some(XRay)
    case 3 => Some(BloodTest)
    case 4 => Some(ECG)
    case 5 => Some(Vaccine(injections = Vaccine.numberOfInjections))
    case _ => None
  }
}

case object Diagnosis extends HealthService {
  val name = "diagnosis"
  val defaultCost = 60
  val discount = 0
}

case object XRay extends HealthService {
  val name = "x-ray"
  val defaultCost = 150
  val discount = 0
}

case object ECG extends HealthService {
  val name = "ECG"
  val defaultCost = 200.40
  val discount = 0
}

case object BloodTest extends HealthService {
  val name = "blood test"
  val defaultCost = 78
  val discount = 15
}

case object Vaccine extends HealthService {
  val name = "vaccine"
  val defaultCost = 27.50
  val discount = 0

  def numberOfInjections: Int = {
    val question = "How many injections would you like?"
    Patient.ask(question, a => a)
  }
}

case class Vaccine(
   name: String = "vaccine",
   defaultCost: BigDecimal = 27.50,
   discount: BigDecimal = 0,
   injections: Int = 1) extends HealthService {
  val injectionPrice: BigDecimal = 15
  val injectionsPrice: BigDecimal = this.injections * this.injectionPrice

  override lazy val fullPrice: BigDecimal =
    this.defaultCost + this.injectionsPrice

}
