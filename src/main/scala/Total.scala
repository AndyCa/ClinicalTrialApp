package medihealth

case class Total(balance: BigDecimal = 0, healthServices: List[HealthService] = List()) {

  def addToTotal(healthService: HealthService, salePrice: BigDecimal): Total = {
    this.copy(balance = balance + salePrice, healthServices = healthService :: healthServices)
  }

  def formatValue(value: BigDecimal, currency: String = "£"): String = currency + f"${value}%1.2f"

  def print(salePrice: BigDecimal): Unit = {
    println(
      "Thank you for purchasing " + healthServices.head.name +
        " for the sum of " + formatValue(salePrice) +
        ". Your current total is " + formatValue(balance)
    )
  }

  lazy val servicesAsString = healthServices.map(s => s.name).mkString(", ")

  def finalPrint: Unit = {
    println(
      "Thank you for your custom, you have purchased the following: " +
        servicesAsString + " for the price of " + formatValue(balance)
    )
  }

}
