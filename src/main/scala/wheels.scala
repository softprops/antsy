package antsy

trait Wheel[T] {
  def turn: Wheel[T]
  def get: T
}

object Wheels {
  def colorWheel(colors: Seq[Int]): Wheel[Int] =
    new Wheel[Int] {
      def turn = colors match {
        case head :: tail =>
          colorWheel(tail ::: head :: Nil)
        case head =>
          colorWheel(head)
      }
      def get = colors.head
    }

  def shuffled =
    colorWheel(util.Random.shuffle(
      Term.colors.values))

  def default =
    colorWheel(Term.colors.values)
}
