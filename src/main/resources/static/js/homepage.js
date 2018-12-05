//toggle all checkboxes
function toggleAll(){
    let checkboxes = document.getElementsByName('source');
    let button = document.getElementById('select-all');

    if(button.value === 'select'){
        for (let i in checkboxes){
            checkboxes[i].checked = 'FALSE';
        }
        button.value = 'deselect'
    }else{
        for (let i in checkboxes){
            checkboxes[i].checked = '';
        }
        button.value = 'select';
    }
}


function renderListItem(item,number) {
    var listRow = document.createElement('a');
    listRow.className = "list-group-item list-group-item-action d-inline-block";
    listRow.setAttribute("id","result-list-item-" + number);
    listRow.setAttribute("href","#result-list-item-" + number + "-details");
    listRow.setAttribute("role","tab");
    listRow.setAttribute("aria-controls","list-item-" + number);
    listRow.setAttribute("data-toggle","list");
    listRow.setAttribute("onclick","getFoodDetails(\"" + item.foodID + "\",\"" + item.dbName + "\")");
    listRow.innerHTML = "<span class='d-inline-block'>" + item.name + "</span>" +
        "<img class='imageClass d-inline-block' src=" + item.photo + " alt=\"" + item.name + "\"/>";

    document.getElementById('result-tab').appendChild(listRow);
}

// creates results list
function showResults() {
    $.ajax({
        url: "/api/food/autocomplete",
        method: "get",
        dataType: "json",
        data: {
            keyword: document.getElementById('searchInput').value
        }
    })
        .done(function (res) {
            document.getElementById('result-tab').innerHTML ="";

            let i = 0;
            res.forEach(function(element) {
                i++;
                renderListItem(element, i);
            });
        });
}

function getFoodDetails(id, dbName) {
    document.getElementById('result-tab-content').innerHTML ="";

    var detailsDiv = document.createElement('div');
    detailsDiv.className = "tab-pane show active";
    detailsDiv.setAttribute("id","result-list-item-1-details");
    detailsDiv.setAttribute("role","tabpanel");
    detailsDiv.setAttribute("aria-labelledby","result-list-item-1");

    $.ajax({
        url: "/api/food/detailed",
        method: "get",
        dataType: "json",
        data: {
            foodID: id,
            dbName: dbName
        }
    })
        .done(function (res) {
            detailsDiv.innerHTML = "<div> "  + "Selected food details:" + res.food_name + "=" +  res.name  + "</div>";
            document.getElementById('result-tab-content').appendChild(detailsDiv);
        });
}