package medihealth

import Messages._

object MediHealthApp {

  def loop(patient: Patient, total: Total): Unit = {

    val selectedHealthService: HealthService = Patient.ask(welcomeMessage, Patient.resolveChoice)
    val salePrice: BigDecimal = selectedHealthService.salePrice(patient)
    val newTotal: Total = total.addToTotal(selectedHealthService, salePrice)
    newTotal.print(salePrice)
    Patient.ask(leaveMessage, i => Patient.booleanChoice(i)) match {
      case false => newTotal.finalPrint
      case true => loop(patient, newTotal)
    }

  }

}
