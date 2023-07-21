
import * as Plot from "https://cdn.jsdelivr.net/npm/@observablehq/plot@0.6/+esm";

function fetchSquareFrequencies() {
    return fetch('/api/v1/stats/square-frequencies/'+collectionId)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch data from the backend');
            }
            return response.json();
        })
        .catch(error => {
            console.error('An error occurred:', error);
            throw error;
        });
}

function displayError(id) {
    document.getElementById(id).style.display = 'block';
}


function drawBoardFrequencies(squares) {
    const plot = Plot.plot({
        height: 550,
        width: 550,
        color: {scheme: "Greens"},
        padding: 0,
        grid: true,
        y: {type:"band", reverse:true, label:"rows"},
        x: {type:"band", label: "columns"},
        marks: [
            Plot.cell(squares, Plot.group({fill: "max"}, {
                x: (d) => d.col,
                y: (d) => d.row,
                fill: "freq",
                inset: 0.5,
            })),
            Plot.text(squares,{x:"col", y:"row",text:(d)=>d.col.toUpperCase() + d.row}),
        ]
    });
    const div = document.querySelector("#board-area");
    div.append(plot);
}

fetchSquareFrequencies()
    .then(data => {
        let squares = data;
        drawBoardFrequencies(squares)
    })
    .catch(error => {
        displayError('errorAlertSquares');
    });
