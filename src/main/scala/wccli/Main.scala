package wccli

object Main {
    def main(args: Array[String]): Unit = {
        println("hello wccli")
        var cli = new Cli()
        cli.menu()
    }
}