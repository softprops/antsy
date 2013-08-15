package antsy

trait Appends[T] {
  def append(c: Char): T
  def append(cs: CharSequence): T
}

trait ColorConversions {
  def fromRgb(c: (Int, Int, Int)): Int = {
    def conv(i: Int) = math.round(i / 255 * 5)
    16 + (conv(c._1) * 36) + (conv(c._2) * 6) + conv(c._3)
  }
  def fromHex(str: String): (Int, Int, Int) = {
    def conv(s: String) = Integer.parseInt(s, 16)
    val c = if (str.startsWith("#")) str.substring(1) else str
    (conv(c.substring(0, 2)),
     conv(c.substring(2, 4)),
     conv(c.substring(4, 6)))
  }
}

case class Buffer(underlying: Seq[Char] = Seq.empty[Char])
  extends Appends[Buffer] {

  case class Colors(buffer: Buffer, base: Int, current: Option[Int] = None)
    extends Appends[Colors] with ColorConversions {

    override def append(c: Char) = copy(buffer.append(c))
    override def append(cs: CharSequence) = copy(buffer.append(cs))
    def fg = copy(base = 0)
    def bg = copy(base = 10)
    def andThen = buffer

    // colors

    def rgb(color: (Int, Int, Int)) =
      apply(fromRgb(color).toString)

    def hex(color: String) =
      rgb(fromHex(color))

    private def apply(convCode: String) =
      copy(buffer = buffer.append(
        Term.prefix + (base + 38) + ";5;" + convCode + Term.suffix))

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

    def bold = append(Term.prefix + Term.styles.bold + Term.suffix)
    def noBold = append(Term.prefix + Term.reset.bold + Term.suffix)
    def italic = append(Term.prefix + Term.styles.italic + Term.suffix)
    def noItalic = append(Term.prefix + Term.reset.italic + Term.suffix)
    def underline = append(Term.prefix + Term.styles.underline + Term.suffix)
    def noUnderline = append(Term.prefix + Term.reset.underline + Term.suffix)
    def invert = append(Term.prefix + Term.styles.inverse + Term.suffix)
    def unvert = append(Term.prefix + Term.reset.inverse + Term.suffix)

    override def toString = buffer.toString
  } 

  def append(c: Char) = Buffer(underlying :+ c)
  def append(cs: CharSequence) = Buffer(underlying ++ cs.toString.toCharArray)
  def fg = Colors(this, 0)
  def bg = Colors(this, 10)
  def reset = append(Term.prefix + '0' + Term.suffix)


  def bold = append(Term.prefix + Term.styles.bold + Term.suffix)
  def unbold = append(Term.prefix + Term.reset.bold + Term.suffix)
  def italic = append(Term.prefix + Term.styles.italic + Term.suffix)
  def unitalic = append(Term.prefix + Term.reset.italic + Term.suffix)
  def underline = append(Term.prefix + Term.styles.underline + Term.suffix)
  def noUnderline = append(Term.prefix + Term.reset.underline + Term.suffix)
  def invert = append(Term.prefix + Term.styles.inverse + Term.suffix)
  def unvert = append(Term.prefix + Term.reset.inverse + Term.suffix)

  override def toString = underlying.mkString("")
}
