class ChessBoard {
    private val board = Array<Piece>(64) { Piece.Empty }

    operator fun get(rank: Int, file: Int) : Piece = board[file * 8 + rank]
    operator fun set(rank: Int, file: Int, value: Piece) {
        board[file * 8 + rank] = value
    }

}