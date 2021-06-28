// @GENERATOR:play-routes-compiler
// @SOURCE:D:/ÁÖÇåÔ´/UoG/team project/ITSD-DT2021-Template/ITSD-DT2021-Template/conf/routes
// @DATE:Sun Jun 06 05:24:45 CST 2021


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
