package medihealth

object Messages {
  def patientDetails(patient: Patient): String =
    s"""
      |Here are your patient details:
      |Age: ${patient.age}
      |Insured: ${patient.insurance}
    """.stripMargin

  val patientInsuranceDetails: String =
    """
      |And do you have insurance?
      |1. Yes
      |2+. No
    """.stripMargin

  //TODO merge common elements of boolean choices.
  val leaveMessage: String =
    """
      |Would you like any more health services?
      |1. Yes
      |2+. No
    """.stripMargin

  val heathServicesListed: String =
    s"""
       |1. Diagnosis
       |2. X-Ray
       |3. Blood Test
       |4. ECG
       |5. Vaccine
    """.stripMargin

  val welcomeMessage: String =
    s"""
       |Welcome to the MediHealth App!
       |Which service would you like?
       |${heathServicesListed}
    """.stripMargin

  def incorrectChoiceMessage[A](incorrectChoice: A): String =
    s"""
       |We're sorry ${incorrectChoice} is not a valid option.
       |Please select a valid option.
    """.stripMargin
}
