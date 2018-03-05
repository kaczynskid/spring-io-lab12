@RestController
class DemoController {

  @GetMapping('/greeting')
  def greet() {
    [message: 'Hello world']
  }
}
