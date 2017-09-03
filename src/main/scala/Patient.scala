package medihealth

import medihealth.Messages._

import scala.io.StdIn._
import scala.util.{Failure, Success, Try}

case class Patient(age: Int, insurance: Boolean) {
  def discount: BigDecimal = this.age match {
    case age if age > 70 => 90
    case age if age >= 65  => 60
    case age if age < 5 => 40
    case _ => 0
  }
}

object Patient {
  def getDetails: Patient = {
    val patient = Patient(age = this.getAge, insurance = this.getInsurance)
    println(patientDetails(patient))
    patient
  }

  def getAge: Int = ask(
      question = "Hello new patient, how old are you?",
      age => age)

  def getInsurance: Boolean =  ask(
      question = patientInsuranceDetails,
      i => booleanChoice(i))

  def booleanChoice(choice: Int): Boolean = choice match {
    case 1 => true
    case _ => false
  }

  def ask[A](question: String, resolveChoice: Int => A, errorMessage: String = ""): A = {
    println(errorMessage + question)
    val userInput = readLine
    val tryInt: Try[Int] = Try(userInput.toInt)
    val choice: A = tryInt match {
      case Success(int) => {
        resolveChoice(int)
      }
      case Failure(e) => {
        val errorMessage = incorrectChoiceMessage(userInput)
        ask(question, resolveChoice, errorMessage)
      }
    }
    choice
  }

  def resolveChoice: Int => HealthService = { a =>
    val healthServiceOption: Option[HealthService] = HealthServices.choice(a)
    healthServiceOption
      .getOrElse(Patient.ask(heathServicesListed, resolveChoice, incorrectChoiceMessage(a)))
  }

}
