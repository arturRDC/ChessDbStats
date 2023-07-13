import LichessPgnViewer from 'lichess-pgn-viewer';

const board = document.querySelector('#board');
const lpv = LichessPgnViewer(board, {
    pgn: 'e4 e5 Nf3 Nc6 Nc3 Nf6 d4',
});