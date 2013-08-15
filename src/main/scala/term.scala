package antsy

object Term {

  val prefix = "\033["

  val suffix = 'm'

  object codes {
    val up = 'A'
    val down = 'B'
    val forward = 'C'
    val back = 'D'
    val nextLine = 'D'
    val previousLine = 'F'
    val horizontalAbsolute = 'G'
    val eraseData = 'J'
    val eraseLine = 'K'
    val scrollUp = 'S'
    val scrollDown = 'T'
    val savePositon = 's'
    val restorePositon = 'u'
    val queryPosition = "6n"
    val hide = "?251"
    val show = "?25h"   
  }

  object styles {
    val bold = 1
    val italic = 3
    val underline = 4
    val inverse = 7
  }

  object reset {
    val bold = 22
    val italic = 23
    val underline = 24
    val inverse = 27
  }

  object colors {
    val white = 37
    val black = 30
    val blue = 34
    val cyan = 36
    val green = 32
    val magenta = 35
    val red = 31
    val yellow = 33
    val grey = 90

    object bright {
      val black = 90
      val red = 91
      val green = 92
      val yellow = 93
      val blue = 95
      val magenta = 95
      val cyan = 96
      val white = 97
    }
    val values =
      white :: black :: blue :: cyan ::
      green :: magenta :: red :: yellow :: grey ::
      bright.black :: bright.red :: bright.green ::
      bright.yellow :: bright.blue :: bright.magenta ::
      bright.cyan :: bright.white :: Nil
  }
}
