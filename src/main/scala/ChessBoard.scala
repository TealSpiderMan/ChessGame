import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import javafx.scene.input.MouseEvent
import javafx.event.EventHandler

// create board
class ChessBoard extends Pane {
  val tileSize = 85
  val cols = 8
  val rows = 8

  var selectedPiece: Option[Piece] = None
  val pieces = collection.mutable.ListBuffer[Piece]()
  val inputHandler = new InputHandler(this)

  // adding highlights to the selected piece
  val highlight = new Rectangle {
    width = tileSize
    height = tileSize
    fill = Color.Red
    stroke = Color.Red
    strokeWidth = 2
    visible = false
  }
  children.add(highlight)

  // set preferred size of the Pane
  prefWidth = cols * tileSize
  prefHeight = rows * tileSize

  // draw the chessboard and pieces
  drawBoard()
  placePieces()

  // add mouse event handlers
  addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler[MouseEvent] {
    override def handle(event: MouseEvent): Unit = inputHandler.handleMousePressed(event)
  })

  addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler[MouseEvent] {
    override def handle(event: MouseEvent): Unit = inputHandler.handleMouseDragged(event)
  })

  addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler[MouseEvent] {
    override def handle(event: MouseEvent): Unit = inputHandler.handleMouseReleased(event)
  })

  // draw the board
  def drawBoard(): Unit = {
    for (r <- 0 until rows; c <- 0 until cols) {
      val color = if ((c + r) % 2 == 0) Color.web("#EEEED2") else Color.web("#769656")
      val tile = new Rectangle {
        width = tileSize
        height = tileSize
        fill = color
        x = c * tileSize
        y = r * tileSize
      }
      children.add(tile)
    }
  }

  // place piece on chess board
  def placePieces(): Unit = {
    pieces += new Rook(this, 0, 0, isWhite = false)
    pieces += new Knight(this, 1, 0, isWhite = false)
    pieces += new Bishop(this, 2, 0, isWhite = false)
    pieces += new Queen(this, 3, 0, isWhite = false)
    pieces += new King(this, 4, 0, isWhite = false)
    pieces += new Bishop(this, 5, 0, isWhite = false)
    pieces += new Knight(this, 6, 0, isWhite = false)
    pieces += new Rook(this, 7, 0, isWhite = false)
    for (col <- 0 until cols) pieces += new Pawn(this, col, 1, isWhite = false)

    pieces += new Rook(this, 0, 7, isWhite = true)
    pieces += new Knight(this, 1, 7, isWhite = true)
    pieces += new Bishop(this, 2, 7, isWhite = true)
    pieces += new Queen(this, 3, 7, isWhite = true)
    pieces += new King(this, 4, 7, isWhite = true)
    pieces += new Bishop(this, 5, 7, isWhite = true)
    pieces += new Knight(this, 6, 7, isWhite = true)
    pieces += new Rook(this, 7, 7, isWhite = true)
    for (col <- 0 until cols) pieces += new Pawn(this, col, 6, isWhite = true)
  }

  def getPiece(col: Int, row: Int): Option[Piece] = {
    pieces.find(piece => piece.col == col && piece.row == row)
  }

  def selectPiece(piece: Piece): Unit = {
    selectedPiece = Some(piece)
    highlight.x = piece.sprite.x.value - (tileSize * 0.125)
    highlight.y = piece.sprite.y.value - (tileSize * 0.125)
    highlight.visible = true
  }

  def deselectPiece(): Unit = {
    selectedPiece = None
    highlight.visible = false
  }
}