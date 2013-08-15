package antsy

trait Appends[T] {
  def append(c: Char): T
  def append(cs: CharSequence): T
}

case class Buffer(underlying: Seq[Char] = Seq.empty[Char])
  extends Appends[Buffer] {

  case class Colors(buffer: Buffer, base: Int, current: Option[Int] = None)
    extends Appends[Colors] {

    override def append(c: Char) = copy(buffer.append(c))
    override def append(cs: CharSequence) = copy(buffer.append(cs))
    def fg = copy(base = 0)
    def bg = copy(base = 10)
    def andThen = buffer

    // colors

    def apply(code: Int) =
      current.filter(_ == code).map( _ => this )
        .getOrElse {
          copy(buffer = buffer.append(
            Term.prefix + (base + code) + Term.suffix),
               current = Some(code))
        }

    def reset = apply(39)
    def white = apply(Term.colors.white)
    def black = apply(Term.colors.black)
    def blue = apply(Term.colors.blue)
    def cyan = apply(Term.colors.cyan)
    def green = apply(Term.colors.green)
    def magenta = apply(Term.colors.magenta)
    def red = apply(Term.colors.red)
    def yellow = apply(Term.colors.yellow)
    def grey = apply(Term.colors.grey)
    def brightBlack = apply(Term.colors.bright.black)
    def brightRed = apply(Term.colors.bright.red)
    def brightGreen = apply(Term.colors.bright.green)
    def brightYellow = apply(Term.colors.bright.yellow)
    def brightBlue = apply(Term.colors.bright.blue)    
    def brightMagenta = apply(Term.colors.bright.magenta)
    def brightCyan = apply(Term.colors.bright.cyan)
    def brightWhite = apply(Term.colors.bright.white)
    override def toString = buffer.toString
  } 

  def append(c: Char) = Buffer(underlying :+ c)
  def append(cs: CharSequence) = Buffer(underlying ++ cs.toString.toCharArray)
  def fg = Colors(this, 0)
  def bg = Colors(this, 10)
  def reset = append(Term.prefix + '0' + Term.suffix)

  override def toString = underlying.mkString("")
}
