// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages': ['corechart', 'line']});



// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(() => initializeLineChart());
google.charts.setOnLoadCallback(() => initializeBarChart());
google.charts.setOnLoadCallback(() => initializePieChart());
google.charts.setOnLoadCallback(() => initializeDonutChart());



function fetchEventsData() {
    return fetch('/api/v1/stats/events/'+collectionId)
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
    fetchEventsData()
        .then(data => {
            let eventsData = data;
            drawBarChart(eventsData);
        })
        .catch(error => {
            displayError('errorAlertEvents');
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


function fetchSitesData() {
    return fetch('/api/v1/stats/sites/'+collectionId)
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
    fetchSitesData()
        .then(data => {
            sitesData = data;
            drawPieChart(sitesData);
        })
        .catch(error => {
            displayError('errorAlertSites');
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
    var chart = new google.visualization.BarChart(document.getElementById('events-chart'));
    var dataViewFreq = new google.visualization.DataView(data);
    data.sort({column: 1, desc: true});
    dataViewFreq.setColumns([0, 1, {
        calc: "stringify",
        sourceColumn: 1,
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

function drawPieChart(data) {
    var data = google.visualization.arrayToDataTable(data);

    var options = {
        title: '',
        is3D: true,
        chartArea: {
            top: 10,
            bottom: 10,
        }
    };

    var chart = new google.visualization.PieChart(document.getElementById('sites-chart'));

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

function drawDonutChart(data) {
    var data = google.visualization.arrayToDataTable(data);

    var options = {
        title: '',
        pieHole: 0.4,
        chartArea: {
            top: 10,
            bottom: 10,
        }
    };

    var chart = new google.visualization.PieChart(document.getElementById('time-controls-chart'));

    chart.draw(data, options);
}


function initializeDonutChart() {
    fetchTimeControlsData()
        .then(data => {
            timeControlsData = data;
            drawDonutChart(timeControlsData);
        })
        .catch(error => {
            displayError('errorAlertTimeControls');
        });
}


function fetchTimeControlsData() {
    return fetch('/api/v1/stats/time-controls/'+collectionId)
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

function drawLineChart(data) {
    var data = google.visualization.arrayToDataTable(data);

    var options = {
        title: '',
        chartArea: {
            top: 10,
            bottom: 10,
        },
    };

    var chart = new google.charts.Line(document.getElementById('dates-chart'));

    chart.draw(data, options);
}


function fetchDatesData() {
    return fetch('/api/v1/stats/dates/'+collectionId)
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

function initializeLineChart() {
    fetchDatesData()
        .then(data => {
            let datesData = data;
            drawLineChart(datesData);
        })
        .catch(error => {
            displayError('errorAlertDates');
        });
}
