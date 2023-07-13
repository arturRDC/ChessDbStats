// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages': ['corechart']});



// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(() => initializeBarChart());
google.charts.setOnLoadCallback(() => initializeHistogram());
google.charts.setOnLoadCallback(() => initializePieChart());



function fetchOpeningData() {
    return fetch('/api/v1/stats/openings/'+collectionId)
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

function initializeBarChart() {
    fetchOpeningData()
        .then(data => {
            let openingData = data;
            drawBarChart(openingData);
            drawBarChartPct(openingData);
        })
        .catch(error => {
            displayError('errorAlertOpFreq');
            displayError('errorAlertWP');
        });
}

function displayError(id) {
    document.getElementById(id).style.display = 'block';
}


function fetchMoveCountData() {
    return fetch('/api/v1/stats/move-count/'+collectionId)
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


function initializeHistogram() {
    fetchMoveCountData()
        .then(data => {
            moveCountData = data;
            drawHistogram(moveCountData);
        })
        .catch(error => {
            displayError('errorAlertNumMoves');
        });
}


function fetchTerminationData() {
    return fetch('/api/v1/stats/termination/'+collectionId)
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


function initializePieChart() {
    fetchTerminationData()
        .then(data => {
            terminationData = data;
            drawPieChart(terminationData);
        })
        .catch(error => {
            displayError('errorAlertTermi');
        });
}



function drawBarChart(data) {
    var data = google.visualization.arrayToDataTable(data);

    var options = {
        legend: {position: 'top', maxLines: 3},
        bar: {groupWidth: '70%'},
        isStacked: true,
        chartArea: {
            top: 10,
            bottom: 10,
        }
    };


    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.BarChart(document.getElementById('opening-freq'));
    var dataViewFreq = new google.visualization.DataView(data);
    data.sort({column: 4, desc: true});
    dataViewFreq.setColumns([0, 4, {
        calc: "stringify",
        sourceColumn: 4,
        type: "string",
        role: "annotation"
    },]);
    chart.draw(dataViewFreq, options);
}

function drawHistogram(histogramData) {
    var data = google.visualization.arrayToDataTable(histogramData);

    var options = {
        title: '',
        legend: {position: 'none'},
        colors: ['#4285F4'],
    };

    var chart = new google.visualization.Histogram(document.getElementById('num-moves-chart'));
    chart.draw(data, options);
}

function drawPieChart(terminationData) {
    var data = google.visualization.arrayToDataTable(terminationData);

    var options = {
        title: '',
        is3D: true,
        chartArea: {
            top: 10,
            bottom: 10,
        }
    };

    var chart = new google.visualization.PieChart(document.getElementById('termination-chart'));

    chart.draw(data, options);
}

function drawBarChartPct(data) {
    var data = google.visualization.arrayToDataTable(data);


    // Instantiate and draw our chart, passing in some options.
    var pctChart = new google.visualization.BarChart(document.getElementById('opening-win-pct'));
    var pctOptions = {
        legend: {position: 'bottom', maxLines: 3},
        bar: {groupWidth: '80%'},
        isStacked: 'percent'
    };

    // Sort by wins
    data.sort({column: 1, desc: true});
    var dataViewPct = new google.visualization.DataView(data);
    dataViewPct.setColumns([0, 1, 2, 3]);
    pctChart.draw(dataViewPct, pctOptions);
}
