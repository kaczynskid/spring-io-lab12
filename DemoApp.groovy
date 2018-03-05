@RestController
class DemoController {

  @GetMapping('/greeting/{name}')
  def greet(@PathVariable('name') String name) {
    [message: 'Hello ' + name]
  }
}
