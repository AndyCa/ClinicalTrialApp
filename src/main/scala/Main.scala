package medihealth

object Main extends App {
  val patient = Patient.getDetails
  val total: Total = Total()
  MediHealthApp.loop(patient, total)
}